package com.yunsen.enjoy.activity.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.BindCardBean;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.model.request.ApplyWalletCashRequest;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.dialog.SelectBankCardDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/15.
 * 提现
 */

public class WithdrawCashActivity extends BaseFragmentActivity implements SelectBankCardDialog.onSelectBankListener {
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
    private List<BindCardBean> mBankCarts;
    private SelectBankCardDialog mSelectBankDialog;

    private BindCardBean mCurrentBankCart;
    private ApplyWalletCashRequest mApplyWalletRequest;
    private AlertDialog inputPwdDialog;

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
        mBankCarts = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        String userId = sp.getString(SpConstants.USER_ID, "");
        String loginSign = sp.getString(SpConstants.LOGIN_SIGN, "");
        mApplyWalletRequest = new ApplyWalletCashRequest(userId, loginSign);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void requestData() {
        selectBankCardRequest();
    }

    @OnClick({R.id.action_back, R.id.bank_card_tv, R.id.withdraw_cash_all, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.bank_card_tv:
                if (mSelectBankDialog == null) {
                    mSelectBankDialog = new SelectBankCardDialog(this, mBankCarts);
                }
                if (!mSelectBankDialog.isShowing()) {
                    mSelectBankDialog.show();
                }
                mSelectBankDialog.setOnSelectBackListener(this);
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
            showInputPwdDialog();
        }
    }

    /**
     * 显示输入密码的框
     */
    private void showInputPwdDialog() {
        if (inputPwdDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.input_pwd_layout, null);
            builder.setView(view);
            final EditText pwdEdt = (EditText) view.findViewById(R.id.input_pwd_edt);
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pwdEdt.setText("");
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String pwd = pwdEdt.getText().toString().trim();
                    if (TextUtils.isEmpty(pwd)) {
                        TextUtils.isEmpty("请输入密码");
                    } else {
                        mApplyWalletRequest.setAmount(moneyEdt.getText().toString().trim());
                        mApplyWalletRequest.setBank_id(String.valueOf(mCurrentBankCart.getId()));
                        mApplyWalletRequest.setPaypassword(pwd);
                        applyWalletCashRequest();
                    }
                }
            });
            inputPwdDialog = builder.create();
        }
        if (!inputPwdDialog.isShowing()) {
            inputPwdDialog.show();
        }

    }

    /**
     * 请求银行列表
     */
    public void selectBankCardRequest() {
        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        String userId = sp.getString(SpConstants.USER_ID, "");
        String loginSign = sp.getString(SpConstants.LOGIN_SIGN, "");
        HttpProxy.getBindBankCardList(userId, loginSign, new HttpCallBack<List<BindCardBean>>() {
            @Override
            public void onSuccess(List<BindCardBean> responseData) {
                if (responseData != null && responseData.size() > 1) {
                    mCurrentBankCart = responseData.get(0);
                    bankCardTv.setText(responseData.get(0).getBank_name());
                }
                if (mSelectBankDialog != null) {
                    mSelectBankDialog.upData(responseData);
                } else {
                    mBankCarts.clear();
                    mBankCarts.addAll(responseData);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                Logger.e("onError: " + e.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.BIND_BANK_CARD_REQUEST) {
                selectBankCardRequest();
            }
        }
    }

    /**
     * 提款
     */
    public void applyWalletCashRequest() {
        HttpProxy.applyWalletCash(mApplyWalletRequest, new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean responseData) {
                ToastUtils.makeTextShort("提现成功");
                EventBus.getDefault().post(new UpUiEvent(EventConstants.APP_LOGIN));//暂时
                finish();
            }

            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.makeTextShort("提现失败");
            }
        });
    }

    /**
     * 选择银行卡
     *
     * @param data
     */
    @Override
    public void onCallBack(BindCardBean data) {
        if (mSelectBankDialog != null && mSelectBankDialog.isShowing()) {
            this.mCurrentBankCart = data;
            bankCardTv.setText(data.getBank_name());
            mSelectBankDialog.dismiss();
        }
    }
}
