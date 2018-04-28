package com.yunsen.enjoy.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdvertActivity extends BaseFragmentActivity {


    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;

    @Override
    public int getLayout() {
        return R.layout.activity_advert;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("广告页面");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {

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
}
