package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.widget.ZyRingView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/22.
 */

public class CumulativeIncomeActivity extends BaseFragmentActivity {
    @Bind(R.id.zy_ring_view)
    ZyRingView ringVIew;
    @Bind(R.id.income_push_percent)
    TextView incomePushPercent;
    @Bind(R.id.income_team_percent)
    TextView incomeTeamPercent;
    @Bind(R.id.income_proxy_percent)
    TextView incomeProxyPercent;
    @Bind(R.id.income_push_price)
    TextView incomePushPrice;
    @Bind(R.id.income_push_layout)
    LinearLayout incomePushLayout;
    @Bind(R.id.income_team_price)
    TextView incomeTeamPrice;
    @Bind(R.id.income_team_layout)
    LinearLayout incomeTeamLayout;
    @Bind(R.id.income_proxy_money)
    TextView incomeProxyMoney;
    @Bind(R.id.income_proxy_layout)
    LinearLayout incomeProxyLayout;
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;

    @Override
    public int getLayout() {
        return R.layout.activity_comulative_income;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("累计收益");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.income_push_layout, R.id.income_team_layout, R.id.income_proxy_layout, R.id.action_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.income_push_layout:
                break;
            case R.id.income_team_layout:
                break;
            case R.id.income_proxy_layout:
                break;
            case R.id.action_back:
                finish();
                break;
        }
    }


}
