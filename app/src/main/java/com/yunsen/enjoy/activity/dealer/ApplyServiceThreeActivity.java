package com.yunsen.enjoy.activity.dealer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.TradeData;
import com.yunsen.enjoy.model.event.PullImageEvent;
import com.yunsen.enjoy.model.request.ApplyFacilitatorModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.ToastUtils;
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
    private String[] mImageUrls = new String[4];
    private static final int ONE_IMG = 0x0001;
    private static final int TWO_IMG = 0x0010;
    private static final int THREE_IMG = 0x0100;
    private static final int FOUR_IMG = 0x1000;
    private static final int PULL_FINSH = 0x1111;

    private int mImgPullFinish = 0x0000;
    private List<TradeData> mTradeListDatas;

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
                mTradeListDatas = responseData;
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
        String category = facilitatorCategoryTv.getText().toString();//行业类别
        Integer tradeId = (Integer) facilitatorCategoryTv.getTag();
        String synonsis = facilitatorSynopsisEdt.getText().toString();//简介
        String advatage = facilitatorAdvantageEdt.getText().toString();//优势
        String workNumber = facilitatorWorkNumberEdt.getText().toString();//服务工号
        String businessLicence = facilitatorBusinessLicenceEdt.getText().toString();//营业执照注册号
        String refereeNum = facilitatorRefereeNumEdt.getText().toString();//推荐人号码

        mRequsetData.setTrade_id("" + tradeId);
        mRequsetData.setContact(synonsis);
        mRequsetData.setAdvantage(advatage);
        mRequsetData.setLicense(businessLicence);

        if (TextUtils.isEmpty(category)) {
            ToastUtils.makeTextShort("请选择行业类别");
        } else if (TextUtils.isEmpty(synonsis)) {
            ToastUtils.makeTextShort("请填写服务商简介");
        } else if (TextUtils.isEmpty(advatage)) {
            ToastUtils.makeTextShort("请填写特色优势");
        } else if (TextUtils.isEmpty(workNumber)) {
            ToastUtils.makeTextShort("请填写服务工号");
        } else if (TextUtils.isEmpty(businessLicence)) {
            ToastUtils.makeTextShort("请填写营业执照号码");
        } else if (TextUtils.isEmpty(refereeNum)) {
            ToastUtils.makeTextShort("请填写推荐人号码");
        } else if ((mImgPullFinish & ONE_IMG) != ONE_IMG) {
            ToastUtils.makeTextShort("请上传服务商Logo");
        } else if ((mImgPullFinish & TWO_IMG) != TWO_IMG) {
            ToastUtils.makeTextShort("请上传营业执照");
        } else if ((mImgPullFinish & TWO_IMG) != TWO_IMG) {
            ToastUtils.makeTextShort("请上传税务登记证明");
        } else if ((mImgPullFinish & TWO_IMG) != TWO_IMG) {
            ToastUtils.makeTextShort("请上传组织机构代码证明");
        } else if (!protocolSelectionCb.isChecked()) {
            ToastUtils.makeTextShort("请同意协议");
        } else {
            submitData();
        }


    }

    private void submitData() {
        HttpProxy.getApplyServiceForm(this, mRequsetData, new HttpCallBack<RestApiResponse>() {
            @Override
            public void onSuccess(RestApiResponse responseData) {
                Log.e(TAG, "onSuccess: " + responseData.getInfo());
                UIHelper.showMainActivity(ApplyServiceThreeActivity.this);
                finish();
            }

            @Override
            public void onError(Request request, Exception e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }
        });
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
                    facilitatorCategoryTv.setTag(mTradeListDatas.get(index[0]).getId());
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
                type = 0;

                break;
            case Constants.APPLY_SERVICE_REQUEST_2:
                imageView = facilitatorAptitudeImg;
                type = 1;
                break;
            case Constants.APPLY_SERVICE_REQUEST_3:
                imageView = facilitatorRevenueImg;
                type = 2;
                break;
            case Constants.APPLY_SERVICE_REQUEST_4:
                imageView = facilitatorMechanismImg;
                type = 3;
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
        int evenId = event.getEvenId();
        mImageUrls[evenId] = event.getImgUrl();
        switch (evenId) {
            case 0:
                mImgPullFinish = mImgPullFinish | ONE_IMG;
                break;
            case 1:
                mImgPullFinish = mImgPullFinish | TWO_IMG;
                break;
            case 2:
                mImgPullFinish = mImgPullFinish | THREE_IMG;
                break;
            case 3:
                mImgPullFinish = mImgPullFinish | FOUR_IMG;
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
