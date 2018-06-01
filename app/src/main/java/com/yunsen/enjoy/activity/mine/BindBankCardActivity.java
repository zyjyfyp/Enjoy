package com.yunsen.enjoy.activity.mine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.request.BindBankCardRequest;
import com.yunsen.enjoy.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/31.
 * 绑定银行卡
 */

public class BindBankCardActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.bind_user_name_edt)
    EditText bindUserNameEdt;
    @Bind(R.id.bind_card_id_tv)
    EditText bindCartIdTv;
    @Bind(R.id.bind_card_type_tv)
    TextView bindCardTypeTv;
    @Bind(R.id.bind_occupation_edt)
    EditText bindOccupationEdt;
    @Bind(R.id.bind_address_tv)
    TextView bindAddressTv;
    @Bind(R.id.bind_phone_edt)
    EditText bindPhoneEdt;
    @Bind(R.id.submit)
    Button submit;

    @Override
    public int getLayout() {
        return R.layout.activity_bind_bank_card;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("添加银行卡");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.action_back, R.id.bind_card_type_tv, R.id.bind_address_tv, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.bind_card_type_tv:
                break;
            case R.id.bind_address_tv:
                break;
            case R.id.submit:
                addBankCard();
                break;
        }
    }

    private void addBankCard() {
        String name = bindUserNameEdt.getText().toString().trim();
        String cardId = bindCartIdTv.getText().toString().trim();
        String cardType = bindCardTypeTv.getText().toString().trim();
        String occupation = bindOccupationEdt.getText().toString().trim();
        String address = bindAddressTv.getText().toString().trim();
        String phone = bindPhoneEdt.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.makeTextShort("请输入姓名");
        } else if (TextUtils.isEmpty(cardId)) {
            ToastUtils.makeTextShort("请输入卡号");
        } else if (TextUtils.isEmpty(cardType)) {
            ToastUtils.makeTextShort("请输选择银行卡类型");
        } else if (TextUtils.isEmpty(occupation)) {
            ToastUtils.makeTextShort("请输入职业");
        } else if (TextUtils.isEmpty(address)) {
            ToastUtils.makeTextShort("请选择地区");
        } else if (TextUtils.isEmpty(phone)) {
            ToastUtils.makeTextShort("请输入手机号");
        } else {
            //成功
            SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
            String userId = sp.getString(SpConstants.USER_ID, "");
            String loginSign = sp.getString(SpConstants.LOGIN_SIGN, "");
            BindBankCardRequest request = new BindBankCardRequest(userId, loginSign);
            request.setBank_account(name);
            request.setBank_card(cardId);
            request.setBank_name(cardType);
            // ddd
            request.setBank_certtype(occupation);


            HttpProxy.bindBankCard(request, new HttpCallBack<Boolean>() {
                @Override
                public void onSuccess(Boolean responseData) {
                    ToastUtils.makeTextShort("绑定成功");
                    finish();
                }

                @Override
                public void onError(Request request, Exception e) {

                }
            });
        }
    }
}
