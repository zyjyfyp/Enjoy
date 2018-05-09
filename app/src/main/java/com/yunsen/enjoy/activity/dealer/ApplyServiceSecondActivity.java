package com.yunsen.enjoy.activity.dealer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.model.request.ApplyFacilitatorModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.SharedPreference;
import com.yunsen.enjoy.utils.ToastUtils;

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
    private ApplyFacilitatorModel mRequstData;

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
        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        String userName = sp.getString(SpConstants.USER_NAME, "");
        String userId = sp.getString(SpConstants.USER_ID, "");
        mRequstData = new ApplyFacilitatorModel();
        mRequstData.setUser_name(userName);
        mRequstData.setUser_id(userId);
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
                intSMap();
                UIHelper.showApplyServiceThreeActivity(this, mRequstData);
                break;
        }
    }

    private void intSMap() {
        String name = facilitatorNameEdt.getText().toString();
//        Integer tag = (Integer) facilitatorTagTv.getTag();
        Integer tag = 0;
//        Integer projectTag = (Integer) facilitatorProjectEdt.getTag();
        Integer projectTag = (Integer) facilitatorProjectEdt.getTag();
        String phoneNum = facilitatorPhoneEdt.getText().toString();
        String address = facilitatorAddressEdt.getText().toString();
        String gpsAddress = facilitatorGpsEdt.getText().toString();
        String startTime = facilitatorStartTv.getText().toString();
        String endTime = facilitatorEndTv.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.makeTextShort("服务商名称不能为空");
        } else if (TextUtils.isEmpty(phoneNum)) {
            ToastUtils.makeTextShort("联系电话不能为空");
        } else if (TextUtils.isEmpty(address)) {
            ToastUtils.makeTextShort("服务商城市不能为空");
        } else if (TextUtils.isEmpty(gpsAddress)) {
            ToastUtils.makeTextShort("详细地址不能为空");
        } else if (TextUtils.isEmpty(startTime)) {

        } else if (TextUtils.isEmpty(endTime)) {

        } else {
            mRequstData.setName(name);
            mRequstData.setSeo_title(String.valueOf(tag));
            mRequstData.setService_ids(String.valueOf(projectTag));
            mRequstData.setMobile(phoneNum);
            mRequstData.setProvince(address);
            mRequstData.setAddress(gpsAddress);
            mRequstData.setService_time(startTime + "-" + endTime);
        }

    }

    private static final String TAG = "ApplyServiceSecondActiv";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
