package com.yunsen.enjoy.activity.mine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.BindCardBean;
import com.yunsen.enjoy.model.BindCardTypeBean;
import com.yunsen.enjoy.model.request.BindBankCardRequest;
import com.yunsen.enjoy.model.request.BindBankCardRequest2;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.utils.Validator;
import com.yunsen.enjoy.widget.dialog.BindCardTypeDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/31.
 * 绑定银行卡
 */

public class BindBankCardActivity extends BaseFragmentActivity implements BindCardTypeDialog.onBindBankTypeListener {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.bind_user_name_edt)
    EditText bindUserNameEdt;
    @Bind(R.id.bind_card_id_tv)
    EditText bindCardIdTv;
    @Bind(R.id.one_setup_layout)
    LinearLayout oneSetupLayout;
    @Bind(R.id.bind_card_type_tv)
    TextView bindCardTypeTv;
    @Bind(R.id.bind_phone_edt)
    EditText bindPhoneEdt;
    @Bind(R.id.two_setup_layout)
    LinearLayout twoSetupLayout;
    @Bind(R.id.verification_edt)
    EditText verificationEdt;
    @Bind(R.id.get_verification_btn)
    Button getVerificationBtn;
    @Bind(R.id.three_setup_layout)
    LinearLayout threeSetupLayout;
    @Bind(R.id.submit)
    Button submit;
    private ArrayList<BindCardTypeBean> mTypeDatas;
    private BindCardTypeDialog mBindCardTypeDialog;
    private BindBankCardRequest2 mRequestDatas;

    private int mStepUp = 1;

    @Override
    public int getLayout() {
        return R.layout.activity_bind_bank_card;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("填写银行卡信息");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mTypeDatas = new ArrayList<>();
        mTypeDatas.add(new BindCardTypeBean("工商银行", false));
        mTypeDatas.add(new BindCardTypeBean("建设银行", false));
        mTypeDatas.add(new BindCardTypeBean("农业银行", false));
        mTypeDatas.add(new BindCardTypeBean("邮政银行", false));
        mTypeDatas.add(new BindCardTypeBean("中国银行", false));
        mTypeDatas.add(new BindCardTypeBean("广发银行", false));
        mTypeDatas.add(new BindCardTypeBean("光大银行", false));
        mTypeDatas.add(new BindCardTypeBean("商户银行", false));
        //成功
        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        String userId = sp.getString(SpConstants.USER_ID, "");
        String loginSign = sp.getString(SpConstants.LOGIN_SIGN, "");
        String userName = sp.getString(SpConstants.USER_NAME, "");
        mRequestDatas = new BindBankCardRequest2(userId, userName);
        mBindCardTypeDialog = new BindCardTypeDialog(BindBankCardActivity.this, mTypeDatas);
    }

    @Override
    protected void initListener() {
        mBindCardTypeDialog.setOnBindBackTypeListener(this);
    }


    @OnClick({R.id.action_back, R.id.bind_card_type_tv, R.id.get_verification_btn, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                if (mStepUp == 1) {
                    finish();
                } else {
                    lastStep();
                }
                break;
            case R.id.bind_card_type_tv:
                if (!mBindCardTypeDialog.isShowing()) {
                    mBindCardTypeDialog.show();
                }
                break;
            case R.id.get_verification_btn:
                String phone = bindPhoneEdt.getText().toString().trim();
                if (!Validator.isMobile(phone)) {
                    ToastUtils.makeTextShort("请输入正确的验证码");
                } else {
                    HttpProxy.userOauthSmscodeRequest(phone);
                }
                break;
            case R.id.submit:
                submitData(mStepUp);
                break;
        }

    }


    private void submitData(int step) {
        String name = bindUserNameEdt.getText().toString().trim();
        String cardId = bindCardIdTv.getText().toString().trim();
        String cardType = bindCardTypeTv.getText().toString().trim();
        String phone = bindPhoneEdt.getText().toString().trim();
        String verification = verificationEdt.getText().toString().trim();
        switch (step) {
            case 1:
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.makeTextShort("请输入姓名");
                } else if (TextUtils.isEmpty(cardId)) {
                    ToastUtils.makeTextShort("请输入卡号");
                } else {
                    mRequestDatas.setBank_card(cardId);
                    mRequestDatas.setBank_account(name);
                    nextStep();
                }
                break;
            case 2:
                if (TextUtils.isEmpty(cardType)) {
                    ToastUtils.makeTextShort("请输选择银行卡类型");
                } else if (TextUtils.isEmpty(phone)) {
                    ToastUtils.makeTextShort("请输入手机号");
                } else if (!Validator.isMobile(phone)) {
                    ToastUtils.makeTextShort("请输入正确的电话号码");
                } else {
                    mRequestDatas.setBank_name(cardType);
                    mRequestDatas.setMobile(phone);
                    nextStep();
                }
                break;
            case 3:
                if (TextUtils.isEmpty(verification)) {
                    ToastUtils.makeTextShort("请输入验证码");
                } else {
                    mRequestDatas.setCode(verification);
                    nextStep();
                }
                break;
        }
    }

    @Override
    public void onCallBack(BindCardTypeBean data) {
        bindCardTypeTv.setText(data.getName());
    }

    @Override
    public void onBackPressed() {
        if (mStepUp == 1) {
            super.onBackPressed();
        } else {
            lastStep();
        }

    }

    private void lastStep() {
        switch (mStepUp) {
            case 2:
                twoSetupLayout.setVisibility(View.GONE);
                oneSetupLayout.setVisibility(View.VISIBLE);
                break;
            case 3:
                threeSetupLayout.setVisibility(View.GONE);
                twoSetupLayout.setVisibility(View.VISIBLE);
                break;
        }
        mStepUp--;
    }

    public void nextStep() {
        switch (mStepUp) {
            case 1:
                oneSetupLayout.setVisibility(View.GONE);
                twoSetupLayout.setVisibility(View.VISIBLE);
                break;
            case 2:
                twoSetupLayout.setVisibility(View.GONE);
                threeSetupLayout.setVisibility(View.VISIBLE);
                break;
            case 3:
                // ddd
                HttpProxy.postUserBankcardRequest(mRequestDatas, new HttpCallBack<Boolean>() {
                    @Override
                    public void onSuccess(Boolean responseData) {
                        ToastUtils.makeTextShort("绑定成功");
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }
                });
                break;
        }
        mStepUp++;
    }
}
