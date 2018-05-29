package com.yunsen.enjoy.activity.buy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.model.request.ApplyCarModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.utils.Validator;
import com.yunsen.enjoy.widget.BuyCarStepLayout;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/27.
 * 申请购买第二步
 */

public class ApplyBuyTwoActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.buy_step_car_name)
    TextView buyStepCarName;
    @Bind(R.id.phone_input_edt)
    EditText phoneInputEdt;
    @Bind(R.id.phone_img)
    TextView phoneImg;
    @Bind(R.id.apply_two_bottom_btn)
    TextView applyTwoBottomBtn;
    @Bind(R.id.buy_step_layout)
    BuyCarStepLayout buyStepLayout;
    private ApplyCarModel mApplyCarRequest;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_apply_two;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("预约看车");
        buyStepLayout.setTwoStep();

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            mApplyCarRequest = extras.getParcelable(Constants.APPLY_BUY_CAR_KEY);
            String title = mApplyCarRequest.getTitle();
            if (!TextUtils.isEmpty(title)) {
                buyStepCarName.setText(title);
            }
        }
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

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        if (requestCode == Constants.CALL_PHONE) {
            UIHelper.showPhoneNumberActivity(this, "400****120");
        }
    }

    @OnClick({R.id.phone_img, R.id.apply_two_bottom_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phone_img:
                requestPermission(Permission.CALL_PHONE, Constants.CALL_PHONE);
                break;
            case R.id.apply_two_bottom_btn:
                String phone = phoneInputEdt.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.makeTextShort("请输入电话号码");
                } else if (!Validator.isMobile(phone)) {
                    ToastUtils.makeTextShort("请输入正确的电话号码");
                } else {
                    mApplyCarRequest.setMobile(phone);
                    UIHelper.showApplyThreeActivity(this, mApplyCarRequest);
                }
                break;
        }
    }


}
