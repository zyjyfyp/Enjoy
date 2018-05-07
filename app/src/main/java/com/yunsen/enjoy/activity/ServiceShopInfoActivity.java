package com.yunsen.enjoy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.ui.UIHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/7.
 */

public class ServiceShopInfoActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.service_img)
    ImageView serviceImg;
    @Bind(R.id.service_name_tv)
    TextView serviceNameTv;
    @Bind(R.id.shop_phone_tv)
    TextView shopPhoneTv;
    @Bind(R.id.address_info)
    TextView addressInfo;
    @Bind(R.id.shop_time)
    TextView shopTime;
    @Bind(R.id.shop_info)
    TextView shopInfo;
    private String mServiceId;
    public SProviderModel mData;

    @Override
    public int getLayout() {
        return R.layout.activity_service_shop_info;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("服务商详情");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mServiceId = intent.getStringExtra(Constants.SERVICE_SHOP_KEY);
        }
    }

    private void upData(SProviderModel responseData) {
        serviceNameTv.setText(responseData.getArtperson());
        String addr = responseData.getProvince() + responseData.getCity() + responseData.getArea()
                + responseData.getAddress();
        addressInfo.setText(addr);

        Glide.with(this)
                .load(responseData.getImg_url())
                .into(serviceImg);
    }

    @Override
    public void requestData() {
        super.requestData();
        HttpProxy.getServiceShopInfo(mServiceId, new HttpCallBack<SProviderModel>() {

            @Override
            public void onSuccess(SProviderModel responseData) {
                mData = responseData;
                upData(responseData);
            }


            @Override
            public void onError(Request request, Exception e) {

            }
        });

    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.action_back, R.id.shop_phone_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.shop_phone_tv:
                if (mData != null) {
                    UIHelper.showPhoneNumberActivity(this, mData.getMobile());
                }
                break;
        }
    }
}
