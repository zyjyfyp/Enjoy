package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.buy.ApplyBuyThreeActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.PullImageEvent;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.model.request.UserCertificationRequestModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/7/4.
 */

public class UserCertificationActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.user_certification_name_tv)
    TextView userCertificationNameTv;
    @Bind(R.id.user_certification_card_tv)
    TextView userCertificationCardTv;
    @Bind(R.id.user_certification_img)
    ImageView userCertificationImg;
    @Bind(R.id.back_btn)
    Button backBtn;
    @Bind(R.id.user_certification_ing_layout)
    LinearLayout userCertificationIngLayout;
    @Bind(R.id.user_no_certification_layout)
    ScrollView userNoCertificationLayout;
    @Bind(R.id.user_name_edt)
    EditText userNameEdt;
    @Bind(R.id.user_cert_info_tv)
    TextView userCertInfoTv;
    @Bind(R.id.sex_boy)
    RadioButton sexBoy;
    @Bind(R.id.sex_girl)
    RadioButton sexGirl;
    @Bind(R.id.sex_radio)
    RadioGroup sexRadio;
    @Bind(R.id.user_cert_zmzp)
    ImageView userCertZmzp;
    @Bind(R.id.user_cert_fmzp)
    ImageView userCertFmzp;
    @Bind(R.id.pay_pwd_one_edt)
    EditText payPwdOneEdt;
    @Bind(R.id.pay_pwd_two_edt)
    EditText payPwdTwoEdt;
    @Bind(R.id.user_cert_card_num_edt)
    EditText userCertCardNumEdt;
    @Bind(R.id.submit_btn)
    Button submitBtn;
    private UserCertificationRequestModel mRequestModel;
    private static final int ONE_IMG = 0x01;
    private static final int TWO_IMG = 0x10;
    private int mImgPullFinish = 0x00;
    private String mFristImgUrl;
    private String mTwoImgUrl;
    private int mRequestActivityCode;
    private int mActType;
    private static final int CERTIFICATION_NO = 0; //未认证
    private static final int CERTIFICATION_ING = 1;//认证中
    private static final int CERTIFICATION_OK = 2;//认证成功


    @Override
    public int getLayout() {
        return R.layout.activity_user_certification;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        actionBarTitle.setText("验证身份");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mActType = intent.getIntExtra(Constants.ACT_TYPE_KEY, 0);
        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        String name = sp.getString(SpConstants.REAL_NAME, Constants.EMPTY);
        String card = sp.getString(SpConstants.IDENTITY_CARD, Constants.EMPTY);
        switch (mActType) {
            case CERTIFICATION_NO:
                userCertificationIngLayout.setVisibility(View.GONE);
                userNoCertificationLayout.setVisibility(View.VISIBLE);
                break;
            case CERTIFICATION_ING:
                userCertificationIngLayout.setVisibility(View.VISIBLE);
                userNoCertificationLayout.setVisibility(View.GONE);
                userCertInfoTv.setText("认证信息正在审核中...");
                userCertificationCardTv.setText(card);
                userCertificationNameTv.setText(name);
                break;
            case CERTIFICATION_OK:
                userCertificationIngLayout.setVisibility(View.VISIBLE);
                userNoCertificationLayout.setVisibility(View.GONE);
                userCertInfoTv.setText("认证已完成");
                userCertificationCardTv.setText(card);
                userCertificationNameTv.setText(name);
                break;
        }
        mRequestModel = new UserCertificationRequestModel();
        String userId = sp.getString(SpConstants.USER_ID, null);
        String userName = sp.getString(SpConstants.USER_NAME, null);
        mRequestModel.setUser_id(userId);
        mRequestModel.setUser_name(userName);
    }

    @Override
    protected void initListener() {

    }

    private void userCertRequest() {
        HttpProxy.userCertificationRequest(mRequestModel, new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean responseData) {
                ToastUtils.makeTextShort("提交认证成功");
                EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                finish();
            }

            @Override
            public void onError(Request request, Exception e) {
                if (e instanceof DataException) {
                    ToastUtils.makeTextShort(e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        UIHelper.showPhotoActivity(this, mRequestActivityCode);
    }

    @OnClick({R.id.action_back, R.id.back_btn, R.id.user_cert_zmzp, R.id.user_cert_fmzp, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
            case R.id.back_btn:
                finish();
                break;
            case R.id.user_cert_zmzp:
                mRequestActivityCode = Constants.PHOTO_IC_CARD;
                requestPermission(Permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.user_cert_fmzp:
                mRequestActivityCode = Constants.PHOTO_IC_CARD_BG;
                requestPermission(Permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.submit_btn:
                submitData();
                break;
        }
    }

    private void submitData() {
        String userName = userNameEdt.getText().toString();
        String cardNumber = userCertCardNumEdt.getText().toString();
        String pwdOne = payPwdOneEdt.getText().toString();
        String pwdTwo = payPwdTwoEdt.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.makeTextShort("请输入您的姓名");
            userNameEdt.setFocusable(true);
        } else if (TextUtils.isEmpty(cardNumber)) {
            ToastUtils.makeTextShort("请输入您的身份证号码");
            userCertCardNumEdt.setFocusable(true);
        } else if (TextUtils.isEmpty(pwdOne)) {
            ToastUtils.makeTextShort("请输入支付密码");
            payPwdOneEdt.setFocusable(true);
        } else if (TextUtils.isEmpty(pwdTwo)) {
            ToastUtils.makeTextShort("请再次输入支付密码");
            payPwdTwoEdt.setFocusable(true);
        } else if (cardNumber.length() != 16) {
            ToastUtils.makeTextShort("请输入正确的16位身份证号码");
            userCertCardNumEdt.setFocusable(true);
        } else if ((mImgPullFinish & ONE_IMG) != ONE_IMG) {
            ToastUtils.makeTextShort("请上传身份正面照");
        } else if ((mImgPullFinish & TWO_IMG) != TWO_IMG) {
            ToastUtils.makeTextShort("请上传身份反面照");
        } else if (!pwdOne.equals(pwdTwo)) {
            ToastUtils.makeTextShort("请输入正确的密码");
            payPwdTwoEdt.setFocusable(true);
        } else {
            mRequestModel.setReal_name(userName);
            mRequestModel.setIdentity_card(cardNumber);
            mRequestModel.setPaypassword(pwdOne);
            mRequestModel.setIdentity_card_a(mFristImgUrl);
            mRequestModel.setIdentity_card_b(mTwoImgUrl);
            userCertRequest();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.PHOTO_IC_CARD:
                    loadImgAndPull(data, Constants.PHOTO_IC_CARD);
                    break;
                case Constants.PHOTO_IC_CARD_BG:
                    loadImgAndPull(data, Constants.PHOTO_IC_CARD_BG);
                    break;
            }
        }
    }

    private void loadImgAndPull(Intent data, int index) {
        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
        ImageView imageView = null;
        int type = 0;
        switch (index) {
            case Constants.PHOTO_IC_CARD:
                imageView = userCertZmzp;
                type = Constants.PHOTO_IC_CARD;
                break;
            case Constants.PHOTO_IC_CARD_BG:
                imageView = userCertFmzp;
                type = Constants.PHOTO_IC_CARD_BG;
                break;
        }
        if (imageView != null) {
            Glide.with(this)
                    .load(R.mipmap.img_loading)
                    .into(imageView);
        }
        GetImgUtil.pullImageBase4(this, selectedImage, type);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PullImageEvent event) {
        int evenId = event.getEvenId();
        switch (evenId) {
            case Constants.PHOTO_IC_CARD:
                mImgPullFinish = mImgPullFinish | ONE_IMG;
                mFristImgUrl = event.getImgUrl();
                Glide.with(UserCertificationActivity.this)
                        .load(URLConstants.REALM_URL + mFristImgUrl)
                        .placeholder(R.mipmap.img_loading)
                        .into(userCertZmzp);
                break;
            case Constants.PHOTO_IC_CARD_BG:
                mImgPullFinish = mImgPullFinish | TWO_IMG;
                mTwoImgUrl = event.getImgUrl();
                Glide.with(UserCertificationActivity.this)
                        .load(URLConstants.REALM_URL + mTwoImgUrl)
                        .placeholder(R.mipmap.img_loading)
                        .into(userCertFmzp);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
