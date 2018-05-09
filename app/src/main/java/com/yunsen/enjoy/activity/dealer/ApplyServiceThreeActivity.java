package com.yunsen.enjoy.activity.dealer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.model.request.ApplyFacilitatorModel;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/9.
 */

public class ApplyServiceThreeActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.facilitator_category_tv)
    TextView facilitatorCategoryTv;
    @Bind(R.id.facilitator_synopsis_edt)
    EditText facilitatorSynopsisEdt;
    @Bind(R.id.facilitator_advantage_edt)
    EditText facilitatorAdvantageEdt;
    @Bind(R.id.facilitator_work_number_edt)
    EditText facilitatorWorkNumberEdt;
    @Bind(R.id.facilitator_logo_img)
    ImageView facilitatorLogoImg;
    @Bind(R.id.facilitator_aptitude_img)
    ImageView facilitatorAptitudeImg;
    @Bind(R.id.facilitator_business_licence_edt)
    EditText facilitatorBusinessLicenceEdt;
    @Bind(R.id.facilitator_revenue_img)
    ImageView facilitatorRevenueImg;
    @Bind(R.id.facilitator_mechanism_img)
    ImageView facilitatorMechanismImg;
    @Bind(R.id.protocol_selection_cb)
    CheckBox protocolSelectionCb;
    @Bind(R.id.join_protocol_tv)
    TextView joinProtocolTv;
    @Bind(R.id.facilitator_referee_num_edt)
    EditText facilitatorRefereeNumEdt;
    @Bind(R.id.submit_btn)
    Button submitBtn;
    private ApplyFacilitatorModel mRequsetData;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_apply_service_three;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("申请服务商2/2");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            mRequsetData = extras.getParcelable(Constants.APPLY_FACILITATOR_KEY);
        }
        if (mRequsetData != null) {

        }
    }

    @Override
    protected void initListener() {
    }


    @OnClick({R.id.action_back, R.id.facilitator_category_tv, R.id.facilitator_logo_img, R.id.facilitator_aptitude_img, R.id.facilitator_revenue_img, R.id.facilitator_mechanism_img, R.id.join_protocol_tv, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.facilitator_category_tv:
                break;
            case R.id.facilitator_logo_img:
                break;
            case R.id.facilitator_aptitude_img:
                break;
            case R.id.facilitator_revenue_img:
                break;
            case R.id.facilitator_mechanism_img:
                break;
            case R.id.join_protocol_tv:
                break;
            case R.id.submit_btn:
                initRequestData();
                break;
        }
    }

    private void initRequestData() {
        String category = facilitatorCategoryTv.getText().toString();
        String synonsis = facilitatorSynopsisEdt.getText().toString();
        String advatage = facilitatorAdvantageEdt.getText().toString();
        String businessLicence = facilitatorBusinessLicenceEdt.getText().toString();//营业执照注册号
        String refereeNum = facilitatorRefereeNumEdt.getText().toString();//推荐人号码
        String workNumber = facilitatorWorkNumberEdt.getText().toString();//服务工号

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
