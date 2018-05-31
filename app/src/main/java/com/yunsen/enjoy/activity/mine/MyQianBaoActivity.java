package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.WalletCashAdapter;
import com.yunsen.enjoy.activity.pay.MonneyChongZhiActivity;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.model.WalletCashBean;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * 余额充值
 *
 * @author Administrator
 */
public class MyQianBaoActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.yu_pay0)
    LinearLayout yuPay0;
    @Bind(R.id.push_layout)
    LinearLayout pushLayout;
    @Bind(R.id.pull_layout)
    LinearLayout pullLayout;
    @Bind(R.id.wallet_recycler)
    RecyclerView walletRecycler;
    private SharedPreferences spPreferences;
    String pwd;
    public static String recharge_no;
    private WalletCashAdapter mAdapter;
    private ArrayList<WalletCashBean> mDatas;
    private String mUserId;
    private int mPageIndex = 1;
    private String mUserName;
    private double mBalance = 0.0;

    @Override
    protected void onResume() {
        super.onResume();
        getAmount();
    }

    @Override
    public int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_qianbao_chongzhi;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("我的余额");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        walletRecycler.setLayoutManager(new LinearLayoutManager(this));
        View view = getLayoutInflater().inflate(R.layout.wallet_top_layout, null);
        mDatas = new ArrayList<>();
        mAdapter = new WalletCashAdapter(this, R.layout.withdraw_cash_detail, mDatas);
        HeaderAndFooterRecyclerViewAdapter recyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        walletRecycler.setAdapter(recyclerViewAdapter);
        RecyclerViewUtils.setHeaderView(walletRecycler, view);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserName = spPreferences.getString(SpConstants.USER_NAME, "");
        mUserId = spPreferences.getString(SpConstants.USER_ID, "");
        pwd = spPreferences.getString(SpConstants.PWD, "");
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void requestData() {
        super.requestData();
        HttpProxy.getWithDrawCash(mUserId, String.valueOf(mPageIndex), new HttpCallBack<List<WalletCashBean>>() {
            @Override
            public void onSuccess(List<WalletCashBean> responseData) {
                mAdapter.upData(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }


    /**
     * 获取Amount
     */
    private void getAmount() {
        HttpProxy.getUserInfo(mUserName, new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo responseData) {
                tvMoney.setText(responseData.getAmountStr());
                SharedPreferences.Editor edit = spPreferences.edit();
                mBalance = responseData.getAmount();
                edit.putString(SpConstants.AMOUNT, String.valueOf(responseData.getAmount()));
                edit.commit();
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }


    @OnClick({R.id.action_back, R.id.push_layout, R.id.pull_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.push_layout:
                Intent intent = new Intent(MyQianBaoActivity.this, MonneyChongZhiActivity.class);
                startActivity(intent);
                break;
            case R.id.pull_layout:
                UIHelper.showWithdrawCashActivity(this, mBalance);
                break;
        }
    }
}
