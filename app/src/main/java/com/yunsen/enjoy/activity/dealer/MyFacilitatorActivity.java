package com.yunsen.enjoy.activity.dealer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.ui.UIHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/10.
 */

public class MyFacilitatorActivity extends BaseFragmentActivity {


    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.order_tv)
    TextView orderTv;
    @Bind(R.id.profits_tv)
    TextView profitsTv;
    @Bind(R.id.commision_tv)
    TextView commisionTv;
    @Bind(R.id.shop_setting_layout)
    LinearLayout shopSettingLayout;
    @Bind(R.id.finance_manager_layout)
    LinearLayout financeManagerLayout;
    @Bind(R.id.order_layout)
    LinearLayout orderLayout;

    @Override
    public int getLayout() {
        return R.layout.activity_my_facilitator;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("我是服务商");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }


    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.shop_setting_layout, R.id.finance_manager_layout, R.id.order_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shop_setting_layout:
                UIHelper.showApplyServiceSecondActivity(this);
                break;
            case R.id.finance_manager_layout:
                break;
            case R.id.order_layout:
                break;
        }
    }
}
