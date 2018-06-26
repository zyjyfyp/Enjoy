package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.ListImageAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.GlideImageLoader;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/5.
 */

public class ReleaseProductsActivity extends BaseFragmentActivity {

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
    EditText startNumberEdt;
    @Bind(R.id.all_number_edt)
    EditText allNumberEdt;
    @Bind(R.id.quality_date_tv)
    TextView qualityDateTv;
    @Bind(R.id.create_date_tv)
    TextView createDateTv;
    @Bind(R.id.point_tv)
    TextView pointTv;
    @Bind(R.id.price_edt)
    EditText priceEdt;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.add_img)
    ImageView addImg;
    private List<String> mPath = new ArrayList<>();
    private GalleryConfig mGalleryConfig;
    private ListImageAdapter mImageAdapter;


    @Override
    public int getLayout() {
        return R.layout.activity_release_products;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("发布产品");
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
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.action_back, R.id.action_bar_right, R.id.select_title_edt,
            R.id.select_title_size_tv, R.id.select_goods_tv, R.id.classify_tv,
            R.id.quality_date_tv, R.id.create_date_tv, R.id.point_tv,
            R.id.add_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                break;
            case R.id.action_bar_right:
                break;
            case R.id.select_title_edt:
                break;
            case R.id.select_title_size_tv:
                break;
            case R.id.select_goods_tv:
                break;
            case R.id.classify_tv:
                break;
            case R.id.quality_date_tv:
                break;
            case R.id.create_date_tv:
                break;
            case R.id.point_tv:
                break;
            case R.id.add_img:
//                UIHelper.showMyTakePhotoActivity(Constants.LOAD_IMG_REQUEST, this);
                addImgRes();
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

    private IHandlerCallBack iHandlerCallBack = new IHandlerCallBack() {
        @Override
        public void onStart() {

        }

        @Override
        public void onSuccess(List<String> photoList) {
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
}
