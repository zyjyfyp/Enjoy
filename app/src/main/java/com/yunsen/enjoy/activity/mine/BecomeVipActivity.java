package com.yunsen.enjoy.activity.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.PayMoneyProxy;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.RechargeNoBean;
import com.yunsen.enjoy.thirdparty.PayProxy;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.GlideRoundTransform;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/7/4.
 */

public class BecomeVipActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.become_vip_yes_icon)
    ImageView becomeVipYesIcon;
    @Bind(R.id.vip_true_layout)
    LinearLayout vipTrueLayout;
    @Bind(R.id.vip_false_layout)
    ScrollView vipFalseLayout;

    private int mScreenHeight;
    private TextView mPayTypeTv;
    private View payTypeImg;
    private View payTypeImg2;
    private View payTypeImg3;
    private Dialog mPayTypeDialog;
    private int mPayType = 2; //5微信 ,3 支付宝 ,2余额
    private int mScreenWidth;
    private Button mToPay;
    private String mPayMoney = "0.01";
    private MyHandler mMyHandler;

    @Override
    public int getLayout() {
        return R.layout.activity_become_layout;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("储值卡");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (AccountUtils.isVipAccount()) {
            vipTrueLayout.setVisibility(View.VISIBLE);
            vipFalseLayout.setVisibility(View.GONE);
            Glide.with(this)
                    .load(R.mipmap.login_icon)
                    .transform(new GlideRoundTransform(this))
                    .into(becomeVipYesIcon);
        } else {
            vipTrueLayout.setVisibility(View.GONE);
            vipFalseLayout.setVisibility(View.VISIBLE);
        }

        mMyHandler = new MyHandler(BecomeVipActivity.this);
        mScreenHeight = DeviceUtil.getScreenHeight();
        mScreenWidth = DeviceUtil.getScreenWidth();

    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.action_back, R.id.add_banner_card_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.add_banner_card_btn:
                showToPayDialog();
                break;
        }
    }

    /**
     * 确认支付dialog
     */
    public void showToPayDialog() {
        Dialog dialog = new Dialog(this, R.style.SelectBankDialogStyle);
        View rootView = getLayoutInflater().inflate(R.layout.to_pay_dialog_layout, null);
        rootView.findViewById(R.id.select_pay_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayTypeDialog(mPayType);
            }
        });
        mPayTypeTv = ((TextView) rootView.findViewById(R.id.pay_type_tv));
        mToPay = ((Button) rootView.findViewById(R.id.pay_money_btn));
        mToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toBuyRequest();
            }
        });
        dialog.setCancelable(true);//点击外部不可dismiss
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        dialog.setContentView(rootView, new ViewGroup.LayoutParams(mScreenWidth, mScreenHeight * 3 / 5));
        dialog.show();

    }

    private void toBuyRequest() {
        HttpProxy.addAmountRechargeRequest(mPayMoney, String.valueOf(mPayType), new HttpCallBack<RechargeNoBean>() {
            @Override
            public void onSuccess(RechargeNoBean data) {
                switch (mPayType) {
                    case 2:
                        UIHelper.showTishiCarArchivesActivity(BecomeVipActivity.this, data.getRecharge_no());
                        break;
                    case 3:
                        PayMoneyProxy.getInstance().aliayPay(BecomeVipActivity.this, AccountUtils.getUser_id(), AccountUtils.getUserName(),
                                mPayMoney, data.getRecharge_no(), mMyHandler);
                        break;
                    case 5:
                        PayMoneyProxy.getInstance().weiXinPay(BecomeVipActivity.this, AccountUtils.getUser_id(), AccountUtils.getUserName(),
                                mPayMoney, data.getRecharge_no(), mMyHandler);
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
     * 支付方式dialog
     *
     * @param index
     */
    public void showPayTypeDialog(int index) {
        if (mPayTypeDialog == null) {
            mPayTypeDialog = new Dialog(this, R.style.SelectBankDialogStyle);
            View rootView = getLayoutInflater().inflate(R.layout.pay_type_dialog_layout, null);
            payTypeImg = rootView.findViewById(R.id.pay_type_img);
            payTypeImg2 = rootView.findViewById(R.id.pay_type_img_2);
            payTypeImg3 = rootView.findViewById(R.id.pay_type_img_3);
            rootView.findViewById(R.id.dialog_close_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPayTypeDialog.dismiss();
                }
            });

            rootView.findViewById(R.id.pay_type_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPayType = 5;
                    mPayTypeTv.setText("微信");
                    mPayTypeDialog.dismiss();
                }
            });
            rootView.findViewById(R.id.pay_type_layout_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPayType = 3;
                    mPayTypeTv.setText("支付宝");
                    mPayTypeDialog.dismiss();
                }
            });
            rootView.findViewById(R.id.pay_type_layout_3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPayType = 2;
                    mPayTypeTv.setText("余额支付");
                    mPayTypeDialog.dismiss();
                }
            });
            mPayTypeDialog.setCancelable(true);//点击外部不可dismiss
            mPayTypeDialog.setCanceledOnTouchOutside(true);
            Window window = mPayTypeDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.getDecorView().setPadding(0, 0, 0, 0);
            mPayTypeDialog.setContentView(rootView, new ViewGroup.LayoutParams(mScreenWidth, mScreenHeight * 3 / 5));
        }
        switch (index) {
            case 5:
                payTypeImg.setSelected(true);
                payTypeImg2.setSelected(false);
                payTypeImg3.setSelected(false);
                break;
            case 3:
                payTypeImg.setSelected(false);
                payTypeImg2.setSelected(true);
                payTypeImg3.setSelected(false);
                break;
            case 2:
                payTypeImg.setSelected(false);
                payTypeImg2.setSelected(false);
                payTypeImg3.setSelected(true);
                break;
        }
        mPayType = index;
        if (!mPayTypeDialog.isShowing()) {
            mPayTypeDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == Constants.BALANCE_PAY_REQUEST) {
            finish();
        }
    }

    private static class MyHandler extends Handler {
        WeakReference<BecomeVipActivity> weakReference;

        public MyHandler(BecomeVipActivity act) {
            this.weakReference = new WeakReference<BecomeVipActivity>(act);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BecomeVipActivity act = weakReference.get();
            if (!act.isFinishing()) {
                switch (msg.what) {
                    case PayProxy.SDK_PAY_FLAG: //支付完成
                        act.finish();
                        break;
                }

            }
        }
    }


}
