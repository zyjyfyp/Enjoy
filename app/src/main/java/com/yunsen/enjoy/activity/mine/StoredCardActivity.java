package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.ui.UIHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/4.
 */

public class StoredCardActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.stored_card_tv)
    TextView storedCardTv;


    @Override
    public int getLayout() {
        return R.layout.activity_stored_card;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("储纸卡");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String cardMoney = getIntent().getStringExtra(Constants.CARD_MONEY_KEY);
        if (!TextUtils.isEmpty(cardMoney)) {
            storedCardTv.setText("¥" + cardMoney);
        }
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.action_back, R.id.recharge_btn, R.id.consume_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.recharge_btn:
                UIHelper.showMonneyChongZhiActivity(this);
                break;
            case R.id.consume_tv:
                UIHelper.showMoneyRecordActivity(this, Constants.CONSUMPTION_RECORD);
                break;
        }
    }


}
