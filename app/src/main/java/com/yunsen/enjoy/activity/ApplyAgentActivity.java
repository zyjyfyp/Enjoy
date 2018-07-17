package com.yunsen.enjoy.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.pay.MyOrderZFActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.PayMoneyProxy;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.RechargeNoBean;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.model.request.ApplyFacilitatorModel;
import com.yunsen.enjoy.thirdparty.PayProxy;
import com.yunsen.enjoy.thirdparty.alipay.PayResult;
import com.yunsen.enjoy.ui.DialogUtils;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.interfaces.OnLeftOnclickListener;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.GlideRoundTransform;
import com.yunsen.enjoy.widget.MyAlertDialog;
import com.yunsen.enjoy.widget.SelectCityProxy;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

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
    @Bind(R.id.pay_type_img_4)
    ImageView payTypeImg4;
    @Bind(R.id.pay_type_layout_4)
    LinearLayout payTypeLayout4;

    @Bind(R.id.select_pay_type_layout)
    LinearLayout selectPayTypeLayout;
    @Bind(R.id.select_city_layout)
    LinearLayout selectCityLayout;
    @Bind(R.id.next_btn)
    Button nextBtn;
    @Bind(R.id.agent_true_icon)
    ImageView agentTrueIcon;
    @Bind(R.id.back_to_mine)
    Button backToMine;
    @Bind(R.id.agent_true_layout)
    LinearLayout agentTrueLayout;
    @Bind(R.id.agent_false_layout)
    LinearLayout agentFalseLayout;

    private int mStep = 1;
    private SelectCityProxy mSelectProxy;
    private MyAlertDialog mCityDialog;
    private int mPayType = Constants.WEI_XIN_PAY_TYPE; //5 微信支付 3 支付宝 2 余额
    private ApplyFacilitatorModel mRequestData;
    private String mPayMoney = "1000";
    private Handler mMyHandler;
    private AlertDialog mApplyOkDialog;

    @Override
    public int getLayout() {
        return R.layout.activity_apply_agent;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("申请代理");
        payTypeLayout4.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (AccountUtils.isAgentUser()) {
            agentFalseLayout.setVisibility(View.GONE);
            agentTrueLayout.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(R.mipmap.login_icon)
                    .transform(new GlideRoundTransform(this))
                    .into(agentTrueIcon);
        } else {
            agentFalseLayout.setVisibility(View.VISIBLE);
            agentTrueLayout.setVisibility(View.GONE);
            mSelectProxy = new SelectCityProxy();
            mRequestData = new ApplyFacilitatorModel();
            mMyHandler = new MyHandler(this);
            payTypeImg.setSelected(true);
            payTypeImg2.setSelected(false);
            payTypeImg3.setSelected(false);
            mPayType = Constants.WEI_XIN_PAY_TYPE;
        }
    }

    @Override
    protected void initListener() {
        if (mSelectProxy != null) {
            mSelectProxy.setOnSelectCityListener(new SelectCityProxy.onSelectCityListener() {
                @Override
                public void onSelectCityCallBack(String... address) {
                    selectCityTv.setText(address[0]);
                    mRequestData.setProvince(address[1]);
                    mRequestData.setCity(address[2]);
                    mRequestData.setArea(address[3]);
                }
            });
        }
    }


    @OnClick({R.id.action_back, R.id.select_city_layout, R.id.pay_type_layout, R.id.pay_type_layout_2,
            R.id.pay_type_layout_3, R.id.next_btn, R.id.back_to_mine, R.id.pay_type_layout_4})
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
                payTypeImg4.setSelected(false);
                mPayType = Constants.WEI_XIN_PAY_TYPE;
                break;
            case R.id.pay_type_layout_2:
                payTypeImg.setSelected(false);
                payTypeImg2.setSelected(true);
                payTypeImg3.setSelected(false);
                payTypeImg4.setSelected(false);
                mPayType = Constants.ALIPAY_TYPE;
                break;
            case R.id.pay_type_layout_3:
                payTypeImg.setSelected(false);
                payTypeImg2.setSelected(false);
                payTypeImg3.setSelected(true);
                payTypeImg4.setSelected(false);
                mPayType = Constants.BALANCE_PAY_TYPE;
                break;
            case R.id.pay_type_layout_4:
                payTypeImg.setSelected(false);
                payTypeImg2.setSelected(false);
                payTypeImg3.setSelected(false);
                payTypeImg4.setSelected(true);
                mPayType = Constants.CARD_PAY_TYPE;

                break;
            case R.id.next_btn:
                nextStep();
                break;
            case R.id.back_to_mine:
                finish();
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
        String city = selectCityTv.getText().toString();
        if (TextUtils.isEmpty(city)) {
            ToastUtils.makeTextShort("请选择省市区");
        } else if (mStep == 1) {
            selectPayTypeLayout.setVisibility(View.VISIBLE);
            selectCityLayout.setVisibility(View.GONE);
            mStep++;
        } else {
//            switch (mPayType) {
//                case Constants.BALANCE_PAY_TYPE:
//                    break;
//                case Constants.ALIPAY_TYPE:
//                    break;
//                case Constants.WEI_XIN_PAY_TYPE:
//                    break;
//            }
            toBuyRequest();
        }
    }

    private void toBuyRequest() {
        HttpProxy.addAmountRechargeRequest(mPayMoney, String.valueOf(mPayType), new HttpCallBack<RechargeNoBean>() {
            @Override
            public void onSuccess(RechargeNoBean data) {
                switch (mPayType) {
                    case Constants.BALANCE_PAY_TYPE:
                        UIHelper.showTishiCarArchivesActivity(ApplyAgentActivity.this, data.getRecharge_no());
                        break;
                    case Constants.ALIPAY_TYPE:
                        PayMoneyProxy.getInstance().aliayPay(ApplyAgentActivity.this, AccountUtils.getUser_id(), AccountUtils.getUserName(),
                                mPayMoney, data.getRecharge_no(), mMyHandler);
                        break;
                    case Constants.WEI_XIN_PAY_TYPE:
                        PayMoneyProxy.getInstance().weiXinPay(ApplyAgentActivity.this, AccountUtils.getUser_id(), AccountUtils.getUserName(),
                                mPayMoney, data.getRecharge_no(), mMyHandler);
                        break;
                    case Constants.CARD_PAY_TYPE:
                        UIHelper.showTishiCardPayActivity(ApplyAgentActivity.this, data.getRecharge_no());
                        break;
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                if (e instanceof DataException) {
                    ToastUtils.makeTextShort(e.getMessage());
                }
            }
        });
    }

    /**
     * 申请代理
     */
    private void applyAgentRequest() {
        mRequestData.setUser_id(AccountUtils.getUser_id());
        mRequestData.setUser_name(AccountUtils.getUserName());
        mRequestData.setTrade_id("0");

        HttpProxy.getApplyServiceForm(this, mRequestData, new HttpCallBack<RestApiResponse>() {
            @Override
            public void onSuccess(RestApiResponse responseData) {
                showApplyAgentOkDialog();
            }

            @Override
            public void onError(Request request, Exception e) {
                if (e instanceof DataException) {
                    ToastUtils.makeTextShort(e.getMessage());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mStep != 1) {
            lastStep();
        } else {
            super.onBackPressed();
        }
    }

    private void showApplyAgentOkDialog() {
        if (mApplyOkDialog != null && mApplyOkDialog.isShowing()) {
            mApplyOkDialog.dismiss();
        }
        mApplyOkDialog = DialogUtils.createOKDialog(this, "您已成为" + selectCityTv.getText().toString() + "代理", "确认", new OnLeftOnclickListener() {
            @Override
            public void onLeftClick() {
                if (mApplyOkDialog != null && mApplyOkDialog.isShowing()) {
                    mApplyOkDialog.dismiss();
                    finish();
                }
            }
        });
        mApplyOkDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == Constants.BALANCE_PAY_REQUEST) {
            applyAgentRequest();
        } else if (requestCode == 2) {
            applyAgentRequest();
        }
    }

    private static class MyHandler extends Handler {
        WeakReference<ApplyAgentActivity> weakReference;

        public MyHandler(ApplyAgentActivity act) {
            this.weakReference = new WeakReference<ApplyAgentActivity>(act);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ApplyAgentActivity act = weakReference.get();
            if (!act.isFinishing()) {
                switch (msg.what) {
                    case PayProxy.SDK_PAY_FLAG: //支付完成

                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            Toast.makeText(act, "支付成功", Toast.LENGTH_SHORT).show();
                            act.applyAgentRequest();
                            EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            Toast.makeText(act, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case PayProxy.PAY_FAIL:

                        break;
                }
            }
        }
    }

}
