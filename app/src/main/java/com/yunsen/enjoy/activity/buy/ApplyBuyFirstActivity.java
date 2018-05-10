package com.yunsen.enjoy.activity.buy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.model.request.ApplyCarModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.SpUtils;
import com.yunsen.enjoy.widget.BuyCarStepLayout;
import com.yunsen.enjoy.widget.drag.DragLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/27.
 * 申请购买第一步
 */

public class ApplyBuyFirstActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.buy_step_car_name)
    TextView buyStepCarName;
    @Bind(R.id.first_pay_tv)
    TextView firstPayTv;
    @Bind(R.id.first_pay_unit)
    TextView firstPayUnit;
    @Bind(R.id.first_pay_all)
    TextView firstPayAll;
    @Bind(R.id.month_pay_tv_data)
    TextView monthPayTvData;
    @Bind(R.id.month_pay_tv_money)
    TextView monthPayTvMoney;
    @Bind(R.id.apply_first_bottom_btn)
    TextView applyFirstBottomBtn;
    @Bind(R.id.buy_step_layout)
    BuyCarStepLayout buyStepLayout;
    @Bind(R.id.drag_layout)
    DragLayout dragLayout;
    private String mCarId;
    public CarDetails mCarDetail;
    private ApplyCarModel mApplyCar;

    @Override
    public int getLayout() {
        return R.layout.activity_apply_first;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("申请购买");
        buyStepLayout.setOneStep();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        dragLayout.setCanDrag(false);
        Intent intent = getIntent();
        if (intent != null) {
            mCarId = intent.getStringExtra(Constants.APPLY_BUY_CAR_ID);
        }
        mApplyCar = new ApplyCarModel();
        UserInfo userInfo = SpUtils.getUserInfo();
        int id = userInfo.getId();
        //todo 初始化请求参数

    }

    @Override
    protected void initListener() {
        dragLayout.setDragIconClick(this);
    }

    @Override
    public void requestData() {
        HttpProxy.getCarDetailsData(new HttpCallBack<CarDetails>() {


            @Override
            public void onSuccess(CarDetails responseData) {
                mCarDetail = responseData;
                upView(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        }, mCarId);
    }

    private void upView(CarDetails data) {
        String title = data.getTitle();
        buyStepCarName.setText(title);
        CarDetails.DefaultSpecItemBean specItemBean = data.getDefault_spec_item();
        double sellPrice = specItemBean.getSell_price();
        double firstPayment = specItemBean.getFirst_payment();
        int term = specItemBean.getTerm();//几期
        int monthlySupply = specItemBean.getMonthly_supply();//月供
        firstPayTv.setText("" + firstPayment);
        firstPayAll.setText("全款" + sellPrice + "万");
        monthPayTvData.setText("月供(" + term + "期)");
        monthPayTvMoney.setText(monthlySupply + "元");
    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        if (requestCode == Constants.CALL_PHONE) {
            UIHelper.showPhoneNumberActivity(this, "444****120");
        }
    }

    @OnClick({R.id.apply_first_bottom_btn, R.id.action_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.apply_first_bottom_btn:
                UIHelper.showApplyTwoActivity(this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
