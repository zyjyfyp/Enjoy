package com.yunsen.enjoy.activity.dealer;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.ui.UIHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/9.
 */

public class ApplyServiceSecondActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.facilitator_name_edt)
    EditText facilitatorNameEdt;
    @Bind(R.id.facilitator_tag_tv)
    TextView facilitatorTagTv;
    @Bind(R.id.facilitator_project_edt)
    EditText facilitatorProjectEdt;
    @Bind(R.id.facilitator_phone_edt)
    EditText facilitatorPhoneEdt;
    @Bind(R.id.facilitator_address_edt)
    EditText facilitatorAddressEdt;
    @Bind(R.id.facilitator_gps_edt)
    EditText facilitatorGpsEdt;
    @Bind(R.id.facilitator_start_tv)
    TextView facilitatorStartTv;
    @Bind(R.id.facilitator_end_tv)
    TextView facilitatorEndTv;
    @Bind(R.id.next_btn)
    Button nextBtn;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_apply_service_second;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("申请服务商1/2");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.action_back, R.id.facilitator_tag_tv, R.id.facilitator_start_tv, R.id.facilitator_end_tv, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.facilitator_tag_tv:
                break;
            case R.id.facilitator_start_tv:
                break;
            case R.id.facilitator_end_tv:
                break;
            case R.id.next_btn:
                UIHelper.showApplyServiceThreeActivity(this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
