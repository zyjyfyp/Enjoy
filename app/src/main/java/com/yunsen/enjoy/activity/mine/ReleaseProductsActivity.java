package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import android.widget.Toast;

import com.j256.ormlite.stmt.query.In;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.ListImageAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.DatePickerViewDialog;
import com.yunsen.enjoy.widget.GlideImageLoader;
import com.yunsen.enjoy.widget.NumberPickerDialog;
import com.yunsen.enjoy.widget.interfaces.OnLeftOnclickListener;
import com.yunsen.enjoy.widget.interfaces.OnRightOnclickListener;
import com.yunsen.enjoy.widget.numberkeyboard.KeyboardUtil;
import com.yunsen.enjoy.widget.numberkeyboard.MyKeyBoardView;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/5.
 */

public class ReleaseProductsActivity extends BaseFragmentActivity implements MultiItemTypeAdapter.OnItemClickListener {

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
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
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
    }

    @Override
    protected void initListener() {
        mImageAdapter.setOnItemClickListener(this);
    }


    @OnClick({R.id.action_back, R.id.action_bar_right, R.id.select_goods_layout, R.id.classify_layout,
            R.id.start_number_layout, R.id.all_number_layout, R.id.quality_date_layout, R.id.create_date_layout,
            R.id.point_layout, R.id.price_layout, R.id.add_img})
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
                requestPermission(Permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.classify_layout:
                showClassifyDialog();
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
        }
    }

    private void addImgRes() {
        GalleryPick.getInstance().setGalleryConfig(mGalleryConfig).open(this);
    }


    @OnClick(R.id.radio_group)
    public void onViewClicked() {
    }

    private static final String TAG = "ReleaseProductsActivity";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                addImg.setVisibility(View.VISIBLE);
            } else {
                addImg.setVisibility(View.GONE);
            }
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

    /**
     * classify 分类
     */
    private void showClassifyDialog() {
        if (mCLassifyPicker == null) {
            mCLassifyPicker = new NumberPickerDialog(this, Constants.POINT_METHED);
            mCLassifyPicker.setLeftOnclickListener("取消", new OnLeftOnclickListener() {
                @Override
                public void onLeftClick() {
                    if (mCLassifyPicker.isShowing()) {
                        mCLassifyPicker.dismiss();
                    }
                }
            });
            mCLassifyPicker.setRightOnclickListener("确定", new OnRightOnclickListener() {
                @Override
                public void onRightClick(int[] index) {
                    pointTv.setText(Constants.POINT_METHED[index[0]]);
                    if (mCLassifyPicker.isShowing()) {
                        mCLassifyPicker.dismiss();
                    }
                }
            });
        }
        mCLassifyPicker.show();
    }

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


}
