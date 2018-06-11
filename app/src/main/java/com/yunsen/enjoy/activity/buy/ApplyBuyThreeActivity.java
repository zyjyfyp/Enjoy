package com.yunsen.enjoy.activity.buy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.PullImageEvent;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.model.request.ApplyCarModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.BuyCarStepLayout;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;


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
    @Bind(R.id.next_tv)
    TextView nextTv;
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
    private ApplyCarModel mRequsetData;
    private static final int ONE_IMG = 0x001;
    private static final int TWO_IMG = 0x010;
    private static final int THREE_IMG = 0x100;
    private int mImgPullFinish = 0x000;
    private String mFristImgUrl;
    private String mTwoImgUrl;
    private String mThreeImgUrl;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_apply_three;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        actionBarTitle.setText("资料审核");
        buyStepLayout.setThreeStep();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mRequsetData = extras.getParcelable(Constants.APPLY_BUY_CAR_KEY);
            }
        }
    }

    @Override
    protected void initListener() {

    }

    private static final String TAG = "ApplyBuyThreeActivity";

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        UIHelper.showPhotoActivity(this, mRequestActivityCode);
    }

    @OnClick({R.id.ic_card_img, R.id.ic_card_img_bg, R.id.add_banner_image_tv, R.id.action_back, R.id.next_tv})
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
            case R.id.next_tv:
                judgeSubmitInfo();
                break;
        }
    }

    /**
     * 判断并提提申请信息
     */
    private void judgeSubmitInfo() {
        String name = nameInput.getText().toString();
        String icCard = icCardInput.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.makeTextShort("请输入姓名");
        } else if (TextUtils.isEmpty(icCard)) {
            ToastUtils.makeTextShort("请输入身份证号");
        } else if ((mImgPullFinish & ONE_IMG) != ONE_IMG) {
            ToastUtils.makeTextShort("请上传身份正面照");
        } else if ((mImgPullFinish & TWO_IMG) != TWO_IMG) {
            ToastUtils.makeTextShort("请上传身份反面照");
        } else if ((mImgPullFinish & THREE_IMG) != THREE_IMG) {
            ToastUtils.makeTextShort("请上传银行流水照");
        } else {
            mRequsetData.setIdentity_card(icCard);
            mRequsetData.setUser_name(name);
            submitInfo();
        }


    }

    /**
     * 提交申请买车信息
     */
    private void submitInfo() {
        mRequsetData.setIdentity_card_a(mFristImgUrl);
        mRequsetData.setIdentity_card_b(mTwoImgUrl);
        mRequsetData.setBank_flow(mThreeImgUrl);
        HttpProxy.getApplyBuyCar(mRequsetData, new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean responseData) {
                ToastUtils.makeTextShort("申请成功");
                UIHelper.showHomeActivity(ApplyBuyThreeActivity.this);
                EventBus.getDefault().post(new UpUiEvent(EventConstants.APP_LOGIN));
                finish();
            }

            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.makeTextShort(e.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //在相册里面选择好相片之后调回到现在的这个activity中
        if (resultCode == RESULT_OK) {
//            Bitmap bitmap = null;
            Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String path = cursor.getString(columnIndex); //获取照片路径
//            cursor.close();
//            bitmap = BitmapFactory.decodeFile(path);
            switch (requestCode) {
                case Constants.PHOTO_IC_CARD://这里的requestCode是我自己设置的，就是确定返回到那个Activity的标志
                    GetImgUtil.pullImageBase4(this, selectedImage, Constants.PHOTO_IC_CARD);
                    break;
                case Constants.PHOTO_IC_CARD_BG:
                    GetImgUtil.pullImageBase4(this, selectedImage, Constants.PHOTO_IC_CARD_BG);
                    break;
                case Constants.PHOTO_BANK:
                    GetImgUtil.pullImageBase4(this, selectedImage, Constants.PHOTO_BANK);
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PullImageEvent event) {
        int evenId = event.getEvenId();
        switch (evenId) {
            case Constants.PHOTO_IC_CARD:
                mImgPullFinish = mImgPullFinish | ONE_IMG;
                mFristImgUrl = event.getImgUrl();
                Glide.with(ApplyBuyThreeActivity.this)
                        .load(URLConstants.REALM_URL + mFristImgUrl)
                        .placeholder(R.mipmap.img_loading)
                        .into(icCardImg);
                break;
            case Constants.PHOTO_IC_CARD_BG:
                mImgPullFinish = mImgPullFinish | TWO_IMG;
                mTwoImgUrl = event.getImgUrl();
                Glide.with(ApplyBuyThreeActivity.this)
                        .load(URLConstants.REALM_URL + mTwoImgUrl)
                        .placeholder(R.mipmap.img_loading)
                        .into(icCardImgBg);
                break;
            case Constants.PHOTO_BANK:
                mImgPullFinish = mImgPullFinish | THREE_IMG;
                mThreeImgUrl = event.getImgUrl();
                Glide.with(ApplyBuyThreeActivity.this)
                        .load(URLConstants.REALM_URL + mThreeImgUrl)
                        .placeholder(R.mipmap.img_loading)
                        .into(bannerMoneyImg);
                break;
        }
        Log.e(TAG, "onEvent:上传成功 " + event.getImgUrl());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

}
