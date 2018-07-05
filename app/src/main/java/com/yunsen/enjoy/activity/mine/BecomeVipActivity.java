package com.yunsen.enjoy.activity.mine;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.utils.DeviceUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/4.
 */

public class BecomeVipActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;

    private int mScreenHeight;
    private TextView mPayTypeTv;
    private View payTypeImg;
    private View payTypeImg2;
    private View payTypeImg3;
    private Dialog mPayTypeDialog;
    private int mPayType = 1; //1微信 ,2 支付宝 ,3  余额
    private int mScreenWidth;

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
        Dialog dialog = new Dialog(this,R.style.SelectBankDialogStyle);
        View rootView = getLayoutInflater().inflate(R.layout.to_pay_dialog_layout, null);
        rootView.findViewById(R.id.select_pay_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayTypeDialog(mPayType);
            }
        });
        mPayTypeTv = ((TextView) rootView.findViewById(R.id.pay_type_tv));
        dialog.setCancelable(true);//点击外部不可dismiss
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        dialog.setContentView(rootView, new ViewGroup.LayoutParams(mScreenWidth, mScreenHeight * 3 / 5));
        dialog.show();

    }

    /**
     * 支付方式dialog
     * @param index
     */
    public void showPayTypeDialog(int index) {
        if (mPayTypeDialog == null) {
            mPayTypeDialog = new Dialog(this,R.style.SelectBankDialogStyle);
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
                    mPayType = 1;
                    mPayTypeTv.setText("微信");
                    mPayTypeDialog.dismiss();
                }
            });
            rootView.findViewById(R.id.pay_type_layout_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPayType = 2;
                    mPayTypeTv.setText("支付宝");
                    mPayTypeDialog.dismiss();
                }
            });
            rootView.findViewById(R.id.pay_type_layout_3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPayType = 3;
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
            case 1:
                payTypeImg.setSelected(true);
                payTypeImg2.setSelected(false);
                payTypeImg3.setSelected(false);
                break;
            case 2:
                payTypeImg.setSelected(false);
                payTypeImg2.setSelected(true);
                payTypeImg3.setSelected(false);
                break;
            case 3:
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

}
