package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.ListImageAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.model.request.SubmitGoodsModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.DatePickerViewDialog;
import com.yunsen.enjoy.widget.GlideImageLoader;
import com.yunsen.enjoy.widget.NumberPickerDialog;
import com.yunsen.enjoy.widget.interfaces.OnLeftOnclickListener;
import com.yunsen.enjoy.widget.interfaces.OnRightOnclickListener;
import com.yunsen.enjoy.widget.numberkeyboard.KeyboardUtil;
import com.yunsen.enjoy.widget.numberkeyboard.MyKeyBoardView;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/5.
 */

public class ReleaseProductsActivity extends BaseFragmentActivity implements MultiItemTypeAdapter.OnItemClickListener, RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.personal_rb)
    RadioButton personalRb;
    @Bind(R.id.corporate_rb)
    RadioButton corporateRb;
    @Bind(R.id.select_title_edt)
    EditText selectTitleEdt;
    @Bind(R.id.select_title_size_tv)
    TextView selectTitleSizeTv;
    @Bind(R.id.select_goods_tv)
    TextView selectGoodsTv;
    @Bind(R.id.product_discription_edt)
    EditText productDiscriptionEdt;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.classify_tv)
    TextView classifyTv;
    @Bind(R.id.start_number_edt)
    TextView startNumberEdt;
    @Bind(R.id.all_number_edt)
    TextView allNumberEdt;
    @Bind(R.id.quality_date_tv)
    TextView qualityDateTv;
    @Bind(R.id.create_date_tv)
    TextView createDateTv;
    @Bind(R.id.point_tv)
    TextView pointTv;
    @Bind(R.id.price_edt)
    TextView priceEdt;
    @Bind(R.id.price_layout)
    LinearLayout priceLayout;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.add_img)
    ImageView addImg;

    @Bind(R.id.et_price)
    EditText et_price;
    @Bind(R.id.keyboard_view)
    MyKeyBoardView keyboard_view;
    @Bind(R.id.ll_price_select)
    LinearLayout ll_price_select;
    @Bind(R.id.keyboard_title)
    TextView keyboardTitle;

    private List<String> mPath = new ArrayList<>();
    private GalleryConfig mGalleryConfig;
    private ListImageAdapter mImageAdapter;
    private NumberPickerDialog mNewOrOldPicker;
    private DatePickerViewDialog guaranteeDataPickerView;
    private DatePickerViewDialog createDataPickerView;
    private NumberPickerDialog mPointPicker;
    private KeyboardUtil keyboardUtil;
    private int mCurrentClick = -1;  // 0 起换量， 1 库存 2 价格
    private NumberPickerDialog mCLassifyPicker;
    private String mUserCode;
    private Handler mHandler;
    private ArrayList<String> mPhotoPaths;
    private ArrayList<String> mImageData;
    private SubmitGoodsModel mRequestData;
    private String mCurrentType = "个人用户";
    private String mUserId;


    @Override
    public int getLayout() {
        return R.layout.activity_release_products;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        actionBarTitle.setText("发布产品");
        actionBarRight.setVisibility(View.INVISIBLE);
        mHandler = new MyHandler(this);
        mRequestData = new SubmitGoodsModel();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        mPhotoPaths = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserCode = sp.getString(SpConstants.USER_CODE, "0000");
        mUserId = sp.getString(SpConstants.USER_ID, "0000");
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mImageData = new ArrayList<>();
        mImageAdapter = new ListImageAdapter(this, R.layout.img_layout, new ArrayList<String>());
        recyclerView.setAdapter(mImageAdapter);
        mGalleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider("com.yunsen.yike.fileprovider")   // provider (必填)
                .pathList(mPath)                         // 记录已选的图片
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
        initKeyBoardUtil();
        selectTitleEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                selectTitleSizeTv.setText(length + "/30");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initListener() {
        mImageAdapter.setOnItemClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }


    @OnClick({R.id.action_back, R.id.action_bar_right, R.id.select_goods_layout, R.id.classify_layout,
            R.id.start_number_layout, R.id.all_number_layout, R.id.quality_date_layout, R.id.create_date_layout,
            R.id.point_layout, R.id.price_layout, R.id.add_img, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.action_bar_right:
                break;
            case R.id.select_goods_layout:
                showPickerDialog();
                break;
            case R.id.add_img:
                requestPermission(new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA}, Constants.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.classify_layout: //分类
                UIHelper.showClassifyActivity(this);
                break;
            case R.id.start_number_layout:
                mCurrentClick = 0;
                keyboardTitle.setText("起换量");
                et_price.setHint("0");
                et_price.setText("");
                keyboardUtil.attachTo(et_price);
                et_price.setFocusable(true);
                et_price.setFocusableInTouchMode(true);
                et_price.requestFocus();
                ll_price_select.setVisibility(View.VISIBLE);
                break;
            case R.id.all_number_layout:
                mCurrentClick = 1;
                keyboardTitle.setText("库存");
                et_price.setHint("0");
                et_price.setText("");
                keyboardUtil.attachTo(et_price);
                et_price.setFocusable(true);
                et_price.setFocusableInTouchMode(true);
                et_price.requestFocus();
                ll_price_select.setVisibility(View.VISIBLE);
                break;
            case R.id.quality_date_layout:
                showDuaranteeDateDialog();
                break;
            case R.id.create_date_layout:
                showCreateDateDialog();
                break;
            case R.id.point_layout:
                showPointPickerDialog();
                break;
            case R.id.price_layout:
                mCurrentClick = 2;
                keyboardTitle.setText("价格:");
                et_price.setHint("¥0.00");
                et_price.setText("");
                keyboardUtil.attachTo(et_price);
                et_price.setFocusable(true);
                et_price.setFocusableInTouchMode(true);
                et_price.requestFocus();
                ll_price_select.setVisibility(View.VISIBLE);
                break;
            case R.id.next_btn:
                nextSubmit();
                break;
        }
    }

    /**
     * 下一步 检查数据完成
     */
    private void nextSubmit() {
        String title = selectTitleEdt.getText().toString();
        if (TextUtils.isEmpty(title)) {
            ToastUtils.makeTextShort("请输入标题");
            selectTitleEdt.setFocusable(true);
        } else if (TextUtils.isEmpty(selectGoodsTv.getText().toString())) {
            ToastUtils.makeTextShort("请选择产品的几成新");
        } else if (TextUtils.isEmpty(productDiscriptionEdt.getText().toString())) {
            ToastUtils.makeTextShort("请输入商品描述");
            productDiscriptionEdt.setFocusable(true);
        } else {
            int size = mImageData.size();
            if (size == 0) {
                ToastUtils.makeTextShort("请至少上传一张图片");
            } else if (TextUtils.isEmpty(classifyTv.getText().toString())) {
                ToastUtils.makeTextShort("请选择分类");
            } else if (TextUtils.isEmpty(startNumberEdt.getText().toString())) {
                ToastUtils.makeTextShort("请输入起换量");
            } else if (TextUtils.isEmpty(allNumberEdt.getText().toString())) {
                ToastUtils.makeTextShort("请输入库存量");
            } else if (TextUtils.isEmpty(qualityDateTv.getText().toString())) {
                ToastUtils.makeTextShort("请选择保质日期");
            } else if (TextUtils.isEmpty(createDateTv.getText().toString())) {
                ToastUtils.makeTextShort("请选择生产日期");
            } else if (TextUtils.isEmpty(pointTv.getText().toString())) {
                ToastUtils.makeTextShort("请输入商品单位");
            } else if (TextUtils.isEmpty(priceEdt.getText().toString())) {
                ToastUtils.makeTextShort("请选择商品价格");
            } else {
                mRequestData.setChannel_name(mCurrentType);
                String categoryStr = classifyTv.getText().toString(); //todo zyjy
                mRequestData.setCategory_id("1");
                mRequestData.setTitle(selectTitleEdt.getText().toString());
                mRequestData.setSubtitle(selectTitleEdt.getText().toString());
                mRequestData.setUser_id(mUserId);
                mRequestData.setBrand_id("11");// TODO: 2018/7/3 品牌id
                mRequestData.setImg_url(mImageData.get(0));
                mRequestData.setSeo_title(selectTitleEdt.getText().toString());
                //seo_keywords      seo关键词
                //seo_description   seo描述
                mRequestData.setContent(productDiscriptionEdt.getText().toString());
                mRequestData.setSummary(productDiscriptionEdt.getText().toString());
                mRequestData.setMcontent(productDiscriptionEdt.getText().toString());
                mRequestData.setStart_time(createDateTv.getText().toString());
                mRequestData.setEnd_time(qualityDateTv.getText().toString());
                mRequestData.setMin_quantity(startNumberEdt.getText().toString());
                mRequestData.setUnit(pointTv.getText().toString());
                mRequestData.setStock_quantity(allNumberEdt.getText().toString());
                String albums = "";
                int i = 0;
                for (; i < size - 1; i++) {
                    albums += mImageData.get(i) + ",";
                }
                albums += mImageData.get(i);
                mRequestData.setAlbums(albums);
                UIHelper.showSubmitProductsActivity(this,mRequestData);
            }
        }

    }

    private void addImgRes() {
        GalleryPick.getInstance().setGalleryConfig(mGalleryConfig).open(this);
    }


    private static final String TAG = "ReleaseProductsActivity";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.ClASSIFY_REQUEST) {
                String classifyData = data.getStringExtra(Constants.CLASSIFY_DATA);
                classifyTv.setText(classifyData);
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

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        if (requestCode == Constants.WRITE_EXTERNAL_STORAGE) {
            addImgRes();
        }
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
                addImg.setVisibility(View.VISIBLE);
            } else {
                addImg.setVisibility(View.GONE);
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

    /**
     * 新旧
     */
    private void showPickerDialog() {

        if (mNewOrOldPicker == null) {
            mNewOrOldPicker = new NumberPickerDialog(this, Constants.SORT_METHED);
            mNewOrOldPicker.setLeftOnclickListener("取消", new OnLeftOnclickListener() {
                @Override
                public void onLeftClick() {
                    if (mNewOrOldPicker.isShowing()) {
                        mNewOrOldPicker.dismiss();
                    }
                }
            });
            mNewOrOldPicker.setRightOnclickListener("确定", new OnRightOnclickListener() {
                @Override
                public void onRightClick(int[] index) {
                    selectGoodsTv.setText(Constants.SORT_METHED[index[0]]);
                    if (mNewOrOldPicker.isShowing()) {
                        mNewOrOldPicker.dismiss();
                    }
                }
            });
        }
        mNewOrOldPicker.show();
    }

    /**
     * 保质日期
     */
    private void showDuaranteeDateDialog() {
        if (guaranteeDataPickerView == null) {
            guaranteeDataPickerView = new DatePickerViewDialog(this);
            guaranteeDataPickerView.setLeftOnclickListener("取消", new OnLeftOnclickListener() {
                @Override
                public void onLeftClick() {
                    if (guaranteeDataPickerView.isShowing()) {
                        guaranteeDataPickerView.cancel();
                    }
                }
            });
            guaranteeDataPickerView.setRightOnclickListener("确定", new OnRightOnclickListener() {
                @Override
                public void onRightClick(int... index) {
                    qualityDateTv.setText(guaranteeDataPickerView.getDate(index[0], index[1], index[2]));
                    if (guaranteeDataPickerView.isShowing()) {
                        guaranteeDataPickerView.cancel();
                    }
                }
            });
        }
        if (!guaranteeDataPickerView.isShowing()) {
            guaranteeDataPickerView.show();
        }
    }

    /**
     * 生产日期
     */
    private void showCreateDateDialog() {
        if (createDataPickerView == null) {
            createDataPickerView = new DatePickerViewDialog(this);
            createDataPickerView.setLeftOnclickListener("取消", new OnLeftOnclickListener() {
                @Override
                public void onLeftClick() {
                    if (createDataPickerView.isShowing()) {
                        createDataPickerView.cancel();
                    }
                }
            });
            createDataPickerView.setRightOnclickListener("确定", new OnRightOnclickListener() {
                @Override
                public void onRightClick(int... index) {
                    createDateTv.setText(createDataPickerView.getDate(index[0], index[1], index[2]));
                    if (createDataPickerView.isShowing()) {
                        createDataPickerView.cancel();
                    }
                }
            });
        }
        if (!createDataPickerView.isShowing()) {
            createDataPickerView.show();
        }
    }

    /**
     * 单位
     */
    private void showPointPickerDialog() {
        if (mPointPicker == null) {
            mPointPicker = new NumberPickerDialog(this, Constants.POINT_METHED);
            mPointPicker.setLeftOnclickListener("取消", new OnLeftOnclickListener() {
                @Override
                public void onLeftClick() {
                    if (mPointPicker.isShowing()) {
                        mPointPicker.dismiss();
                    }
                }
            });
            mPointPicker.setRightOnclickListener("确定", new OnRightOnclickListener() {
                @Override
                public void onRightClick(int[] index) {
                    pointTv.setText(Constants.POINT_METHED[index[0]]);
                    if (mPointPicker.isShowing()) {
                        mPointPicker.dismiss();
                    }
                }
            });
        }
        mPointPicker.show();
    }

    /**
     * 键盘
     */
    private void initKeyBoardUtil() {
        keyboardUtil = new KeyboardUtil(ReleaseProductsActivity.this);
        keyboardUtil.setOnOkClick(new KeyboardUtil.OnOkClick() {
            @Override
            public void onOkClick() {
                if (validate()) {
                    ll_price_select.setVisibility(View.GONE);
                    String data = et_price.getText().toString();
                    switch (mCurrentClick) {
                        case 0:
                            startNumberEdt.setText(data);
                            break;
                        case 1:
                            allNumberEdt.setText(data);
                            break;
                        case 2:
                            priceEdt.setText(data);
                            break;

                    }
                }
            }
        });

        keyboardUtil.setOnCancelClick(new KeyboardUtil.onCancelClick() {
            @Override
            public void onCancellClick() {
                ll_price_select.setVisibility(View.GONE);
            }
        });


        et_price.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyboardUtil.attachTo(et_price);
                return false;
            }
        });

    }

    public boolean validate() {
        String edtString = et_price.getText().toString();
        double num = 0;
        if (!TextUtils.isEmpty(edtString)) {
            num = Double.parseDouble(edtString);
        }
        if (mCurrentClick == 0 && num < 1) {
            ToastUtils.makeTextShort("起换数量不能小于1");
            return false;
        } else if (mCurrentClick == 1 && num < 1) {
            ToastUtils.makeTextShort("库存量数量不能小于1");
            return false;
        } else if (mCurrentClick == 2 && edtString.equals("")) {
            ToastUtils.makeTextShort("价格不能为空");
            return false;
        }
        return true;
    }


    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (adapter instanceof ListImageAdapter) {
            List<String> datas = ((ListImageAdapter) adapter).getDatas();
            if (datas != null && datas.size() > position) {
                UIHelper.showPhotoPreviewActivity(this, datas.get(position));
            }
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.personal_rb) {
            mCurrentType = "个人用户";
        } else if (checkedId == R.id.corporate_rb) {
            mCurrentType = "企业用户";
        }
    }

    private static class MyHandler extends Handler {
        WeakReference<ReleaseProductsActivity> weakReference;

        public MyHandler(ReleaseProductsActivity activity) {
            this.weakReference = new WeakReference<ReleaseProductsActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ReleaseProductsActivity act = weakReference.get();
            if (!act.isFinishing()) {
                if (msg.what == Constants.SUCCESS) {
                    act.mImageAdapter.upIndexData(msg.arg1, (String) msg.obj);
                } else {

                }
            }

        }
    }
}
