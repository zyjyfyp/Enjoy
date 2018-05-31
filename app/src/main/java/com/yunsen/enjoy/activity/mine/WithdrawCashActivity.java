package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.request.ApplyWalletCashRequest;
import com.yunsen.enjoy.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/15.
 */

public class WithdrawCashActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.bank_card_tv)
    TextView bankCardTv;
    @Bind(R.id.money_edt)
    EditText moneyEdt;
    @Bind(R.id.balance_tv)
    TextView balanceTv;
    @Bind(R.id.withdraw_cash_all)
    TextView withdrawCashAll;
    @Bind(R.id.submit)
    Button submit;
    private double mBalance = 0.0;

    @Override
    public int getLayout() {
        return R.layout.activity_withdraw_cash;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("钱包提现");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mBalance = intent.getDoubleExtra(Constants.BALANCE, 0.00);
        }
        balanceTv.setText("当前余额" + mBalance + "元，");
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.action_back, R.id.balance_tv, R.id.withdraw_cash_all, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.balance_tv:
                break;
            case R.id.withdraw_cash_all:
                moneyEdt.setText(String.valueOf(mBalance));
                break;
            case R.id.submit:
                applyWalletCash();
                break;
        }
    }

    private void applyWalletCash() {
        if (TextUtils.isEmpty(moneyEdt.getText().toString().trim())) {
            ToastUtils.makeTextShort("请输入提现金额");
        } else if (TextUtils.isEmpty(bankCardTv.getText().toString().trim())) {
            ToastUtils.makeTextShort("请选择提现的银行卡");
        } else {


        }
    }

    public void applyWalletCashRequest() {
        ApplyWalletCashRequest request = new ApplyWalletCashRequest("", "");
        HttpProxy.applyWalletCash(request, new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean responseData) {

            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }
}
