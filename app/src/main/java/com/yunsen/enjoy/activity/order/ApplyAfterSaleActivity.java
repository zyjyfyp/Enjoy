package com.yunsen.enjoy.activity.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.ListImageAdapter;
import com.yunsen.enjoy.activity.order.adapter.GoodsResetAdapter;
import com.yunsen.enjoy.activity.order.adapter.ResetTypeAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.AddressInfo;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.MyOrderData;
import com.yunsen.enjoy.model.ResetTypeModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.NoScrollLinearLayoutManager;
import com.yunsen.enjoy.utils.SharedPreference;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.GlideImageLoader;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/6/28.
 */

public class ApplyAfterSaleActivity extends BaseFragmentActivity {
    private static final int ADDRESS_REQUEST = 5;
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.apply_sale_type)
    TextView applySaleType;
    @Bind(R.id.goods_recycler)
    RecyclerView goodsRecycler;
    @Bind(R.id.description_content_edt)
    TextView descriptionContentEdt;
    @Bind(R.id.description_word_length_tv)
    TextView descriptionWordLengthTv;
    @Bind(R.id.recycler_img)
    RecyclerView recyclerImg;
    @Bind(R.id.add_img)
    ImageView addImg;
    @Bind(R.id.recycler_reset_type)
    RecyclerView recyclerResetType;
    @Bind(R.id.apply_reset_name_edt)
    EditText applyResetNameEdt;
    @Bind(R.id.apply_reset_phone_edt)
    EditText applyResetPhoneEdt;
    @Bind(R.id.apply_reset_address_tv)
    TextView applyResetAddressTv;
    @Bind(R.id.apply_reset_address_layout)
    LinearLayout applyResetAddressLayout;
    @Bind(R.id.apply_reset_submit)
    Button applyResetSubmit;
    private GalleryConfig mGalleryConfig;
    private List<String> mPath = new ArrayList<>();
    private MyOrderData mMyOrderData;
    private ResetTypeAdapter mResetAdapter;
    private String mUserName;
    private ListImageAdapter mImageAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_apply_after_sale;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("申请售后服务");
        goodsRecycler.setLayoutManager(new NoScrollLinearLayoutManager(this));
        descriptionContentEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                descriptionWordLengthTv.setText(length + "/500");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserName = sp.getString(SpConstants.USER_NAME, "");

        Intent intent = getIntent();
        if (intent != null) {
            mMyOrderData = (MyOrderData) intent.getSerializableExtra(Constants.ORDER_DATA);
            goodsRecycler.setAdapter(new GoodsResetAdapter(this, R.layout.goods_reset_item, mMyOrderData.getList()));
        }

        recyclerImg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mImageAdapter = new ListImageAdapter(this, R.layout.img_layout, new ArrayList<String>());
        recyclerImg.setAdapter(mImageAdapter);

        recyclerResetType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<ResetTypeModel> datas = new ArrayList<>();
        datas.add(new ResetTypeModel("送货至自提点", true));
        datas.add(new ResetTypeModel("快递至售货商", false));
        mResetAdapter = new ResetTypeAdapter(this, R.layout.reset_type_item, datas);
        recyclerResetType.setAdapter(mResetAdapter);


        mGalleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider("com.yunsen.enjoy.fileprovider")   // provider (必填)
                .pathList(mPath)                         // 记录已选的图片
                .multiSelect(false)                      // 是否多选   默认：false
                .multiSelect(false, 3)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(3)                             // 配置多选时 的多选数量。    默认：9
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
        mResetAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                mResetAdapter.setSelectView(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private static final String TAG = "ApplyAfterSaleActivity";

    @Override
    public void requestData() {
        HttpProxy.getUserShoppingAddress(mUserName, new HttpCallBack<AddressInfo>() {
            @Override
            public void onSuccess(AddressInfo responseData) {
                String address = responseData.getProvince() + responseData.getCity() + responseData.getArea() + responseData.getUser_address();
                applyResetAddressTv.setText(address);
                applyResetNameEdt.setText(responseData.getUser_accept_name());
                applyResetPhoneEdt.setText(responseData.getUser_mobile());
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    private IHandlerCallBack iHandlerCallBack = new IHandlerCallBack() {
        @Override
        public void onStart() {

        }

        @Override
        public void onSuccess(List<String> photoList) {
//            if (photoList == null || photoList.size() < 3) {
//                addImg.setVisibility(View.VISIBLE);
//            } else {
//                addImg.setVisibility(View.GONE);
//            }
            mImageAdapter.upBaseDatas(photoList);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ADDRESS_REQUEST) {
            String acceptName = data.getStringExtra("user_accept_name");
            String province = data.getStringExtra("province");
            String city = data.getStringExtra("city");
            String userArea = data.getStringExtra("user_area");
            String userAddress = data.getStringExtra("user_address");
            String userMobile = data.getStringExtra("user_mobile");
            String address = province + city + userArea + userAddress;
            applyResetAddressTv.setText(address);
            applyResetNameEdt.setText(acceptName);
            applyResetPhoneEdt.setText(userMobile);
        }
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

    @OnClick({R.id.action_back, R.id.add_img, R.id.apply_reset_address_layout, R.id.apply_reset_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.add_img:
                requestPermission(Permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.apply_reset_address_layout:
                UIHelper.showAddressManagerActivity(this, ADDRESS_REQUEST);
                break;
            case R.id.apply_reset_submit:
                ToastUtils.makeTextShort("功能在开发中");
                break;
        }
    }
}
