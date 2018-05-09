package com.yunsen.enjoy.activity.dealer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.TradeData;
import com.yunsen.enjoy.model.event.PullImageEvent;
import com.yunsen.enjoy.model.request.ApplyFacilitatorModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.widget.NumberPickerDialog;
import com.yunsen.enjoy.widget.interfaces.onLeftOnclickListener;
import com.yunsen.enjoy.widget.interfaces.onRightOnclickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

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
    private String[] mTradeDatas;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_apply_service_three;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
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
    public void requestData() {
        HttpProxy.getTradeList(new HttpCallBack<List<TradeData>>() {
            @Override
            public void onSuccess(List<TradeData> responseData) {
                int size = responseData.size();
                mTradeDatas = new String[size];
                for (int i = 0; i < size; i++) {
                    mTradeDatas[i] = responseData.get(i).getTitle();
                }

            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
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
                if (mTradeDatas != null) {
                    showPickerDialog(mTradeDatas);
                } else {
                    requestData();
                }
                break;
            case R.id.facilitator_logo_img:
                UIHelper.showPhotoActivity(this, Constants.APPLY_SERVICE_REQUEST_1);
                break;
            case R.id.facilitator_aptitude_img:
                UIHelper.showPhotoActivity(this, Constants.APPLY_SERVICE_REQUEST_2);
                break;
            case R.id.facilitator_revenue_img:
                UIHelper.showPhotoActivity(this, Constants.APPLY_SERVICE_REQUEST_3);
                break;
            case R.id.facilitator_mechanism_img:
                UIHelper.showPhotoActivity(this, Constants.APPLY_SERVICE_REQUEST_4);
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

    private void showPickerDialog(String[] datas) {
        if (datas == null) {
            return;
        }
        int length = datas.length;
        final NumberPickerDialog picker = new NumberPickerDialog(this, datas);
        picker.setLeftOnclickListener("取消", new onLeftOnclickListener() {
            @Override
            public void onLeftClick() {
                if (picker != null && picker.isShowing()) {
                    picker.dismiss();
                }
            }
        });
        picker.setRightOnclickListener("确定", new onRightOnclickListener() {
            @Override
            public void onRightClick(int[] index) {
                if (picker != null && picker.isShowing()) {
                    facilitatorCategoryTv.setText(mTradeDatas[index[0]]);
                    requestData();
                    picker.dismiss();
                }
            }
        });
        picker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.APPLY_SERVICE_REQUEST_1:
                    loadImgAndPull(data, Constants.APPLY_SERVICE_REQUEST_1);
                    break;
                case Constants.APPLY_SERVICE_REQUEST_2:
                    loadImgAndPull(data, Constants.APPLY_SERVICE_REQUEST_2);
                    break;
                case Constants.APPLY_SERVICE_REQUEST_3:
                    loadImgAndPull(data, Constants.APPLY_SERVICE_REQUEST_3);
                    break;
                case Constants.APPLY_SERVICE_REQUEST_4:
                    loadImgAndPull(data, Constants.APPLY_SERVICE_REQUEST_4);
                    break;
            }
        }


    }

    private void loadImgAndPull(Intent data, int index) {
        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
        ImageView imageView = null;
        int type = 0;
        switch (index) {
            case Constants.APPLY_SERVICE_REQUEST_1:
                imageView = facilitatorLogoImg;
                type = 1;

                break;
            case Constants.APPLY_SERVICE_REQUEST_2:
                imageView = facilitatorAptitudeImg;
                type = 2;
                break;
            case Constants.APPLY_SERVICE_REQUEST_3:
                imageView = facilitatorRevenueImg;
                type = 3;
                break;
            case Constants.APPLY_SERVICE_REQUEST_4:
                imageView = facilitatorMechanismImg;
                type = 4;
                break;
        }
        if (imageView != null) {
            Glide.with(this)
                    .load(selectedImage)
                    .into(imageView);
        }
        GetImgUtil.pullImageBase4(this, selectedImage, type);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PullImageEvent event) {
        switch (event.getEvenId()) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
        Log.e(TAG, "onEvent:上传成功 " + event.getImgUrl());
    }

    private static final String TAG = "ApplyServiceThreeActivi";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

}
