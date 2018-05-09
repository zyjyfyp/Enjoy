package com.yunsen.enjoy.activity.buy;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.widget.BuyCarStepLayout;


import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018/4/27.
 * 申请购买第三步
 */

public class ApplyBuyThreeActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.buy_step_layout)
    BuyCarStepLayout buyStepLayout;
    @Bind(R.id.apply_first_bottom_btn)
    TextView applyFirstBottomBtn;
    @Bind(R.id.name_input)
    EditText nameInput;
    @Bind(R.id.ic_card_input)
    EditText icCardInput;
    @Bind(R.id.ic_card_img)
    ImageView icCardImg;
    @Bind(R.id.ic_card_img_bg)
    ImageView icCardImgBg;
    @Bind(R.id.banner_money_img)
    ImageView bannerMoneyImg;
    @Bind(R.id.add_banner_image_tv)
    TextView addBannerImageTv;

    private int mRequestActivityCode;
    private Serializable mRequsetData;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_apply_three;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("资料审核");
        buyStepLayout.setThreeStep();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    private static final String TAG = "ApplyBuyThreeActivity";

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        UIHelper.showPhotoActivity(this, mRequestActivityCode);
    }

    @OnClick({R.id.ic_card_img, R.id.ic_card_img_bg, R.id.add_banner_image_tv, R.id.action_back})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.ic_card_img:
                mRequestActivityCode = Constants.PHOTO_IC_CARD;
                requestPermission(Permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.ic_card_img_bg:
                mRequestActivityCode = Constants.PHOTO_IC_CARD_BG;
                requestPermission(Permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.add_banner_image_tv:
                mRequestActivityCode = Constants.PHOTO_BANK;
                requestPermission(Permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_EXTERNAL_STORAGE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //在相册里面选择好相片之后调回到现在的这个activity中
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String path = cursor.getString(columnIndex); //获取照片路径
            cursor.close();
            bitmap = BitmapFactory.decodeFile(path);

            switch (requestCode) {
                case Constants.PHOTO_IC_CARD://这里的requestCode是我自己设置的，就是确定返回到那个Activity的标志
                    Glide.with(ApplyBuyThreeActivity.this)
                            .load(selectedImage)
                            .override(200, 200)
                            .into(icCardImg);
                    break;
                case Constants.PHOTO_IC_CARD_BG:
                    Glide.with(ApplyBuyThreeActivity.this)
                            .load(selectedImage)
                            .override(200, 200)
                            .into(icCardImgBg);
                    break;
                case Constants.PHOTO_BANK:
                    Glide.with(ApplyBuyThreeActivity.this)
                            .load(selectedImage)
                            .override(200, 200)
                            .into(bannerMoneyImg);
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
