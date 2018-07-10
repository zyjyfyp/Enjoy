package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.BindCardBean;
import com.yunsen.enjoy.ui.UIHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/7/5.
 */

public class BalanceCashActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.consume_tv)
    TextView consumeTv;
    @Bind(R.id.withdraw_cash_btn)
    Button withdrawCashBtn;
    @Bind(R.id.balance_tv)
    TextView balanceTv;
    @Bind(R.id.recharge_btn)
    Button rechargeBtn;
    private double mBalance;
    private boolean mNeedToWallectActivity = true;

    @Override
    public int getLayout() {
        return R.layout.activity_balance_cash;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("余额提现");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBalance = getIntent().getDoubleExtra(Constants.BALANCE, 0.00);
        balanceTv.setText(String.valueOf(mBalance));
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void requestData() {
        HttpProxy.getBindBankCardList(new HttpCallBack<List<BindCardBean>>() {
            @Override
            public void onSuccess(List<BindCardBean> responseData) {
                if (responseData != null && responseData.size() > 0) {
                    mNeedToWallectActivity = false;
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                Logger.e("onError: " + e.getMessage());
            }
        });
    }

    @OnClick({R.id.action_back, R.id.consume_tv, R.id.withdraw_cash_btn, R.id.recharge_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.consume_tv:
                UIHelper.showMoneyRecordActivity(this, Constants.WITHDRAW_RECORD);
                break;
            case R.id.withdraw_cash_btn:
                if (mNeedToWallectActivity) {
                    UIHelper.showWalletActivity(this, mBalance);
                } else {
                    UIHelper.showWithdrawCashActivity(this, mBalance);
                }
                break;
            case R.id.recharge_btn:
                UIHelper.showMonneyChongZhiActivity(this);
                break;
        }
    }
}
