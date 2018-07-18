package com.yunsen.enjoy.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.ui.UIHelper;

import java.net.URL;

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
    @Bind(R.id.shop_phone_img)
    ImageView shopPhoneImg;
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

    Html.ImageGetter imgGetter = null;

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mServiceId = intent.getStringExtra(Constants.SERVICE_SHOP_KEY);
        }

        //这里面的resource就是fromhtml函数的第一个参数里面的含有的url
        imgGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                Log.i("RG", "source---?>>>" + source);
                Drawable drawable = null;
                URL url;
                try {
                    url = new URL(source);
                    Log.i("RG", "url---?>>>" + url);
                    drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                Log.i("RG", "url---?>>>" + url);
                return drawable;
            }
        };

    }

    private void upData(SProviderModel responseData) {
        serviceNameTv.setText(responseData.getArtperson());
        String addr = "详细地址：" + responseData.getProvince() + responseData.getCity() + responseData.getArea()
                + responseData.getAddress();
        addressInfo.setText(addr);
        String advantage = responseData.getContent();
        if (!TextUtils.isEmpty(advantage)) {
            shopInfo.setText(Html.fromHtml(advantage, imgGetter, null));
        }
        Glide.with(this)
                .load(responseData.getImg_url())
                .placeholder(R.mipmap.bindingbg)
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


    @OnClick({R.id.action_back, R.id.shop_phone_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.shop_phone_img:
                requestPermission(Permission.CALL_PHONE, Constants.CALL_PHONE);
                break;
        }
    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        if (requestCode == Constants.CALL_PHONE) {
            if (mData != null) {
                UIHelper.showPhoneNumberActivity(this, mData.getMobile());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
