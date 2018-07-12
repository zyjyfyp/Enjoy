package com.yunsen.enjoy.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.widget.GlideCircleTransform;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/12/012.
 * 盖亚订单详情页面
 */

public class GaiYaOrderInfoActivity extends BaseFragmentActivity {
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.order_info_finish)
    TextView orderInfoFinish;
    @Bind(R.id.order_info_icon)
    ImageView orderInfoIcon;
    @Bind(R.id.order_info_money)
    TextView orderInfoMoney;
    @Bind(R.id.order_info_state)
    TextView orderInfoState;
    @Bind(R.id.order_info_balance)
    TextView orderInfoBalance;
    private String mActType;
    private String mPayMoney;

    @Override
    public int getLayout() {
        return R.layout.activity_order_info;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("订单详情");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mActType = intent.getStringExtra(Constants.ACT_TYPE_KEY);
        mPayMoney = intent.getStringExtra(Constants.PAY_MONEY);
        Glide.with(this)
                .load(R.mipmap.login_icon)
                .transform(new GlideCircleTransform(this))
                .into(orderInfoIcon);
        orderInfoBalance.setText("-" + mPayMoney + "元");
        orderInfoMoney.setText(mPayMoney + "元");
        orderInfoState.setText(mActType);
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.action_back, R.id.order_info_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                UIHelper.showHomeActivity(this);
                finish();
                break;
            case R.id.order_info_finish:
                UIHelper.showHomeActivity(this);
                finish();
                break;
        }
    }
}
