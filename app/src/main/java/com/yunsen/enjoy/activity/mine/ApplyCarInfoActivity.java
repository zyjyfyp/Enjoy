package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.model.MyApplyCarBean;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018/4/27.
 * 申请购买的详情
 */

public class ApplyCarInfoActivity extends BaseFragmentActivity {


    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.team_top_layout)
    LinearLayout teamTopLayout;
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
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.phone_num_tv)
    TextView phoneNumTv;
    @Bind(R.id.ic_card_tv)
    TextView icCardTv;
    @Bind(R.id.ic_card_img)
    ImageView icCardImg;
    @Bind(R.id.ic_card_img_bg)
    ImageView icCardImgBg;
    @Bind(R.id.banner_money_img)
    ImageView bannerMoneyImg;
    private MyApplyCarBean mData;

    @Override
    public int getLayout() {
        return R.layout.activity_apply_car_info;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("申购详情");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mData = intent.getParcelableExtra(Constants.ACT_DATA);
            if (mData == null) {
                return;
            }
            buyStepCarName.setText(mData.getTitle());
            firstPayTv.setText(mData.getFirst_payment() + "万起");
            firstPayAll.setText("全款" + mData.getAll_payment() + "万");
            monthPayTvData.setText("月供（" + mData.getTerm() + "期）");
            monthPayTvMoney.setText(mData.getMonthly_supply() + "元");
            nameTv.setText(mData.getReal_name());
            phoneNumTv.setText(mData.getMobile());
            icCardTv.setText(mData.getIdentity_card());
            Glide.with(this)
                    .load(mData.getIdentity_card_a())
                    .into(icCardImg);
            Glide.with(this)
                    .load(mData.getIdentity_card_b())
                    .into(icCardImgBg);
            Glide.with(this)
                    .load(mData.getBank_flow())
                    .into(bannerMoneyImg);
        }
    }

    @Override
    protected void initListener() {

    }


    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }


}
