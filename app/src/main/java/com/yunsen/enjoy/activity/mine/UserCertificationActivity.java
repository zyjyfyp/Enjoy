package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.user_name_edt)
    EditText userNameEdt;
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

    @Override
    public int getLayout() {
        return R.layout.activity_user_certification;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("验证身份");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.action_back, R.id.back_btn, R.id.user_cert_zmzp, R.id.user_cert_fmzp, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
            case R.id.back_btn:
                finish();
                break;
            case R.id.user_cert_zmzp:
                UIHelper.showPhotoActivity(this, Constants.APPLY_SERVICE_REQUEST_1);

                break;
            case R.id.user_cert_fmzp:
                UIHelper.showPhotoActivity(this, Constants.APPLY_SERVICE_REQUEST_2);
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
        } else if (!pwdOne.equals(pwdTwo)) {
            ToastUtils.makeTextShort("请输入正确的密码");
            payPwdTwoEdt.setFocusable(true);
        } else {
            ToastUtils.makeTextShort("提交验证");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.APPLY_SERVICE_REQUEST_1:
                    loadImgAndPull(data, Constants.APPLY_SERVICE_REQUEST_1);
                    break;
                case Constants.APPLY_SERVICE_REQUEST_2:
                    loadImgAndPull(data, Constants.APPLY_SERVICE_REQUEST_2);
                    break;
            }
        }
    }
    private void loadImgAndPull(Intent data, int index) {
        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
        ImageView imageView = null;
        int type = 0;
        switch (index) {
            case Constants.APPLY_SERVICE_REQUEST_1:
                imageView = userCertZmzp;
                type = 0;
                break;
            case Constants.APPLY_SERVICE_REQUEST_2:
                imageView = userCertFmzp;
                type = 1;
                break;
        }
        if (imageView != null) {
            Glide.with(this)
                    .load(selectedImage)
                    .into(imageView);
        }
        GetImgUtil.pullImageBase4(this, selectedImage, type);
    }
}
