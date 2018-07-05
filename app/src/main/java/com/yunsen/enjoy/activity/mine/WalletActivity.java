package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    }

    @Override
    protected void initListener() {

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
