package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gghl.view.wheelcity.AddressData;
import com.gghl.view.wheelcity.OnWheelChangedListener;
import com.gghl.view.wheelcity.WheelView;
import com.gghl.view.wheelcity.adapters.ArrayWheelAdapter;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.dealer.ApplyServiceSecondActivity;
import com.yunsen.enjoy.activity.mine.adapter.ListImageAdapter;
import com.yunsen.enjoy.adapter.CountryAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.request.SubmitGoodsModel;
import com.yunsen.enjoy.ui.DialogUtils;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.quickadapter.BaseQuickAdapter;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.GlideImageLoader;
import com.yunsen.enjoy.widget.MyAlertDialog;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/7/3.
 */

public class SubmitProductsActivity extends BaseFragmentActivity implements MultiItemTypeAdapter.OnItemClickListener {
    private static final String TAG = "SubmitProductsActivity";
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.action_bar_tv_right)
    TextView actionBarTvRight;
    @Bind(R.id.pull_img_or_video)
    TextView pullImgOrVideo;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.select_address_tv)
    TextView selectAddressTv;
    @Bind(R.id.select_address_layout)
    LinearLayout selectAddressLayout;
    @Bind(R.id.select_location_edt)
    TextView selectLocationEdt;
    @Bind(R.id.select_location_layout)
    LinearLayout selectLocationLayout;
    @Bind(R.id.select_change_tv)
    TextView selectChangeTv;
    @Bind(R.id.select_change_layout)
    LinearLayout selectChangeLayout;
    @Bind(R.id.input_change_goods)
    EditText inputChangeGoods;
    @Bind(R.id.submit_btn)
    Button submitBtn;
    private String mAddressTxt;
    private String mProvinces;
    private String mCitys;
    private String mCountry;
    private MyAlertDialog mSelectAddressDialog;
    private ListImageAdapter mImageAdapter;
    private ArrayList<String> mImageData;
    private ArrayList<String> mPhotoPaths = new ArrayList<>();
    private GalleryConfig mGalleryConfig;
    private String mUserCode;
    private String mUserId;
    private Handler mHandler;
    private SubmitGoodsModel mRequestData;

    @Override
    public int getLayout() {
        return R.layout.activity_submit_products;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mRequestData = intent.getExtras().getParcelable(Constants.SUBMIT_GOODS_KEY);

        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserCode = sp.getString(SpConstants.USER_CODE, "0000");
        mUserId = sp.getString(SpConstants.USER_ID, "0000");
        mHandler = new MyHandler(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mImageData = new ArrayList<>();
        mImageAdapter = new ListImageAdapter(this, R.layout.img_layout, new ArrayList<String>());
        recyclerView.setAdapter(mImageAdapter);
        mGalleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider("com.yunsen.yike.fileprovider")   // provider (必填)
                .pathList(mPhotoPaths)                         // 记录已选的图片
                .multiSelect(false)                      // 是否多选   默认：false
                .multiSelect(false, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(9)                             // 配置多选时 的多选数量。    默认：9
                .crop(false)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(false, 1, 1, 500, 500)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();
        mGalleryConfig.getBuilder().multiSelect(true).build();   // 修改多选
        mGalleryConfig.getBuilder().isShowCamera(true).build();   // 修改显示相机

    }

    @Override
    protected void initListener() {
        mImageAdapter.setOnItemClickListener(this);
    }


    @OnClick({R.id.action_back, R.id.select_address_layout, R.id.select_location_layout,
            R.id.select_change_layout, R.id.submit_btn, R.id.pull_img_or_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.select_address_layout:
                showAddressPickerDialog();
                break;
            case R.id.select_location_layout:
                UIHelper.showSelectCityActivity(this);
                break;
            case R.id.select_change_layout:
                UIHelper.showClassifyActivity(this);
                break;
            case R.id.submit_btn:
                submitData();
                break;
            case R.id.pull_img_or_video:
                requestPermission(new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA}, Constants.WRITE_EXTERNAL_STORAGE);
                break;
        }
    }

    private void submitData() {
        int size = mImageData.size();
        if (size == 0) {
            ToastUtils.makeTextShort("请至少上传一张图片");
        } else if (TextUtils.isEmpty(selectAddressTv.getText().toString())) {
            ToastUtils.makeTextShort("请选择地区");
        } else if (TextUtils.isEmpty(selectLocationEdt.getText().toString())) {
            ToastUtils.makeTextShort("请输入详细地址");
        } else if (TextUtils.isEmpty(selectChangeTv.getText().toString())) {
            ToastUtils.makeTextShort("请选择交换需求");
        } else if (TextUtils.isEmpty(inputChangeGoods.getText().toString())) {
            ToastUtils.makeTextShort("请输入想要交换的物品");
        } else {
            mRequestData.setAddress(selectLocationEdt.getText().toString());
            mRequestData.setProvince(mProvinces);
            mRequestData.setCity(mCitys);
            mRequestData.setArea(mAddressTxt);
            mRequestData.setVtype(".mp4");  // TODO: 2018/7/4
            mRequestData.setVideo_url("zz");
            HttpProxy.submitChangeGoods(mRequestData,new HttpCallBack<Boolean>(){
                @Override
                public void onSuccess(Boolean responseData) {

                }

                @Override
                public void onError(Request request, Exception e) {

                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.ClASSIFY_REQUEST) {
                String classifyData = data.getStringExtra(Constants.CLASSIFY_DATA);
                selectChangeTv.setText(classifyData);
            } else if (requestCode == Constants.PHOTO_PRE_REQUEST) {
                String stringExtra = data.getStringExtra(Constants.IMG_URL);
                if (stringExtra == null) {
                    return;
                }
                Log.e(TAG, "onActivityResult: str=" + stringExtra);
                int index = mImageAdapter.removeUrl(stringExtra);
                Log.e(TAG, "onActivityResult: index=" + index);
                if (index != -1) {
                    if (index < mPhotoPaths.size()) {
                        String remove = mPhotoPaths.remove(index);
                        mGalleryConfig.getBuilder().pathList(mPhotoPaths);
                        Log.e(TAG, "onActivityResult: remove" + remove);
                    }
                }
            }
        }
    }

    /**
     * 选择城市
     *
     * @return
     */
    private View dialogm() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.wheelcity_cities_layout, null);
        final WheelView country = (WheelView) contentView.findViewById(R.id.wheelcity_country);
        country.setVisibleItems(3);
        country.setViewAdapter(new CountryAdapter(this));

        final String cities[][] = AddressData.CITIES;
        final String ccities[][][] = AddressData.COUNTIES;
        final WheelView city = (WheelView) contentView.findViewById(R.id.wheelcity_city);
        city.setVisibleItems(0);     // 地区选择
        final WheelView ccity = (WheelView) contentView.findViewById(R.id.wheelcity_ccity);
        ccity.setVisibleItems(0);// 不限城市

        country.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateCities(city, cities, newValue);
                mAddressTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                mProvinces = AddressData.PROVINCES[country.getCurrentItem()];
            }
        });

        city.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updatecCities(ccity, ccities, country.getCurrentItem(), newValue);
                mAddressTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                mCitys = AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()];
            }
        });

        ccity.addChangingListener(new OnWheelChangedListener() {

            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mAddressTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                mCountry = AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
            }
        });

        country.setCurrentItem(1);// 设置北京
        city.setCurrentItem(1);
        ccity.setCurrentItem(1);
        return contentView;
    }

    private void showAddressPickerDialog() {
        if (mSelectAddressDialog == null) {
            View view = dialogm();
            mSelectAddressDialog = new MyAlertDialog(SubmitProductsActivity.this).builder()
                    .setTitle("选择地区").setView(view)
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
            mSelectAddressDialog.setPositiveButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectAddressTv.setText(mProvinces + "," + mCitys + "," + mCountry);
                }
            });
        }
        mSelectAddressDialog.show();
    }

    /**
     * Updates the city wheel
     */
    private void updateCities(WheelView city, String cities[][], int index) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
                cities[index]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

    /**
     * Updates the ccity wheel
     */
    private void updatecCities(WheelView city, String ccities[][][], int index,
                               int index2) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
                ccities[index][index2]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        if (requestCode == Constants.WRITE_EXTERNAL_STORAGE) {
            addImgRes();
        }
    }

    private void addImgRes() {
        GalleryPick.getInstance().setGalleryConfig(mGalleryConfig).open(this);
    }

    private IHandlerCallBack iHandlerCallBack = new IHandlerCallBack() {
        @Override
        public void onStart() {

        }

        @Override
        public void onSuccess(List<String> photoList) {
            if (photoList == null || photoList.size() < 9) {
                int size = photoList.size();
                mImageData.clear();
                mPhotoPaths.clear();
                mPhotoPaths.addAll(photoList);
                for (int i = 0; i < size; i++) {
                    String pathname = photoList.get(i);
                    int startIndex = pathname.lastIndexOf("/");
                    if (TextUtils.isEmpty(mUserCode)) {
                        startIndex += 1;
                    }
                    String filePath = "/upload/phone/" + mUserCode + pathname.substring(startIndex);
                    Log.e(TAG, "onSuccess: filePath= " + filePath);
                    mImageData.add(filePath);
                }
                GetImgUtil.FTPPushImage(photoList, mUserCode, mHandler);
//                addImg.setVisibility(View.VISIBLE);
            } else {
//                addImg.setVisibility(View.GONE);
            }
            mImageAdapter.upBaseDatas(mImageData);
        }

        @Override
        public void onCancel() {
            ToastUtils.makeTextShort("取消");
        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onError() {
            ToastUtils.makeTextShort("出错");
        }
    };

    private static class MyHandler extends Handler {
        WeakReference<SubmitProductsActivity> weakReference;

        public MyHandler(SubmitProductsActivity activity) {
            this.weakReference = new WeakReference<SubmitProductsActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SubmitProductsActivity act = weakReference.get();
            if (!act.isFinishing()) {
                if (msg.what == Constants.SUCCESS) {
                    act.mImageAdapter.upIndexData(msg.arg1, (String) msg.obj);
                } else {

                }
            }

        }
    }
}
