package com.yunsen.enjoy.activity.buy;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/5.
 */

public class MyLoanActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;

    @Override
    public int getLayout() {
        return R.layout.activity_my_loan;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("我的贷款");
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
}
