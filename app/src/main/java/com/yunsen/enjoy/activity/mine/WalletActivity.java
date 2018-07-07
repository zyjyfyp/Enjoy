package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.WalletBankCardAdapter;
import com.yunsen.enjoy.adapter.SelectBankCardAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.BindCardBean;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/7/4.
 * 钱包
 */

public class WalletActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private double mBalance;
    private List<BindCardBean> mData;
    private WalletBankCardAdapter mAdapter;


    @Override
    public int getLayout() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("钱包");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBalance = getIntent().getDoubleExtra(Constants.BALANCE, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mData = new ArrayList<>();
        mAdapter = new WalletBankCardAdapter(this, R.layout.wallet_bank_card_item, mData);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void requestData() {
        HttpProxy.getBindBankCardList(new HttpCallBack<List<BindCardBean>>() {
            @Override
            public void onSuccess(List<BindCardBean> responseData) {
                mAdapter.upBaseDatas(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {
                Logger.e("onError: " + e.getMessage());
            }
        });
    }

    @OnClick({R.id.action_back, R.id.add_banner_card_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.add_banner_card_btn:
                UIHelper.showBindBankCardActivity(this);
                break;
        }
    }
}
