package com.yunsen.enjoy.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.MyAlertDialog;
import com.yunsen.enjoy.widget.SelectCityProxy;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/4.
 */

public class ApplyAgentActivity extends BaseFragmentActivity {


    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.select_city_tv)
    TextView selectCityTv;
    @Bind(R.id.pay_type_img)
    ImageView payTypeImg;
    @Bind(R.id.pay_type_img_2)
    ImageView payTypeImg2;
    @Bind(R.id.pay_type_img_3)
    ImageView payTypeImg3;
    @Bind(R.id.select_pay_type_layout)
    LinearLayout selectPayTypeLayout;
    @Bind(R.id.select_city_layout)
    LinearLayout selectCityLayout;
    @Bind(R.id.next_btn)
    Button nextBtn;

    private int mStep = 1;
    private SelectCityProxy mSelectProxy;
    private MyAlertDialog mCityDialog;
    private int mPayType = 1;

    @Override
    public int getLayout() {
        return R.layout.activity_apply_agent;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("申请代理");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mSelectProxy = new SelectCityProxy();

    }

    @Override
    protected void initListener() {
        mSelectProxy.setOnSelectCityListener(new SelectCityProxy.onSelectCityListener() {
            @Override
            public void onSelectCityCallBack(String... address) {
                selectCityTv.setText(address[0]);
            }
        });
    }


    @OnClick({R.id.action_back, R.id.select_city_layout, R.id.pay_type_layout, R.id.pay_type_layout_2, R.id.pay_type_layout_3, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                if (mStep == 1) {
                    finish();
                } else {
                    lastStep();
                }
                break;
            case R.id.select_city_layout:
                if (mCityDialog == null) {
                    mCityDialog = mSelectProxy.createDialog(this, "选择地区");
                }
                mCityDialog.show();
                break;
            case R.id.pay_type_layout:
                payTypeImg.setSelected(true);
                payTypeImg2.setSelected(false);
                payTypeImg3.setSelected(false);
                mPayType = 1;
                break;
            case R.id.pay_type_layout_2:
                payTypeImg.setSelected(false);
                payTypeImg2.setSelected(true);
                payTypeImg3.setSelected(false);
                mPayType = 2;
                break;
            case R.id.pay_type_layout_3:
                payTypeImg.setSelected(false);
                payTypeImg2.setSelected(false);
                payTypeImg3.setSelected(true);
                mPayType = 3;
                break;
            case R.id.next_btn:
                nextStep();
                break;
        }
    }

    private void lastStep() {
        if (mStep == 2) {
            selectPayTypeLayout.setVisibility(View.GONE);
            selectCityLayout.setVisibility(View.VISIBLE);
        }
        mStep--;
    }

    private void nextStep() {
        if (mStep == 1) {
            selectPayTypeLayout.setVisibility(View.VISIBLE);
            selectCityLayout.setVisibility(View.GONE);
            mStep++;
        } else {
            ToastUtils.makeTextShort("完成");
        }
    }


    @Override
    public void onBackPressed() {
        if (mStep != 1) {
            lastStep();
        } else {
            super.onBackPressed();
        }
    }
}
