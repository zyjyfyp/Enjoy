package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.ProfitCountBean;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.widget.ZyRingView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/6/22.
 */

public class CumulativeIncomeActivity extends BaseFragmentActivity {
    @Bind(R.id.zy_ring_view)
    ZyRingView ringVIew;
    @Bind(R.id.income_push_percent)
    TextView incomePushPercent;
    @Bind(R.id.income_team_percent)
    TextView incomeTeamPercent;
    @Bind(R.id.income_proxy_percent)
    TextView incomeProxyPercent;
    @Bind(R.id.income_push_price)
    TextView incomePushPrice;
    @Bind(R.id.income_push_layout)
    LinearLayout incomePushLayout;
    @Bind(R.id.income_team_price)
    TextView incomeTeamPrice;
    @Bind(R.id.income_team_layout)
    LinearLayout incomeTeamLayout;
    @Bind(R.id.income_proxy_money)
    TextView incomeProxyMoney;
    @Bind(R.id.income_proxy_layout)
    LinearLayout incomeProxyLayout;
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.all_income_tv)
    TextView allIncomeTv;
    @Bind(R.id.income_title_tv)
    TextView incomeTitleTv;
    private static final int HUNDRED = 100;
    private boolean mIsYesterday;
    private String mTitle;

    @Override
    public int getLayout() {
        return R.layout.activity_comulative_income;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mIsYesterday = getIntent().getBooleanExtra(Constants.IS_YESTERDAY_KEY, false);
        if (mIsYesterday) {
            mTitle = "昨日收益";
        } else {
            mTitle = "累计收益";
        }
        actionBarTitle.setText(mTitle);
        incomeTitleTv.setText(mTitle + "(元)");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void requestData() {
        HttpProxy.profitCountRequest(mIsYesterday, new HttpCallBack<ProfitCountBean>() {
            @Override
            public void onSuccess(ProfitCountBean responseData) {
                double agentIncome = responseData.getAgent_income();
                double pushIncome = responseData.getPush_income();
                double teamIncome = responseData.getTeam_income();
                double acountIncome = agentIncome + pushIncome + teamIncome;
                if (acountIncome != 0) {
                    ringVIew.initData(acountIncome, pushIncome, teamIncome, agentIncome);
                    allIncomeTv.setText(String.valueOf(acountIncome));
                    int agentIncomePercent = (int) (agentIncome / acountIncome * HUNDRED);
                    int pushIncomePercent = (int) (pushIncome / acountIncome * HUNDRED);
                    int teamIncomePercent = 100 - pushIncomePercent - agentIncomePercent;
                    incomePushPercent.setText(pushIncomePercent + "%");
                    incomeTeamPercent.setText(teamIncomePercent + "%");
                    incomeProxyPercent.setText(agentIncomePercent + "%");
                    incomePushPrice.setText(pushIncome + "元");
                    incomeTeamPrice.setText(teamIncome + "元");
                    incomeProxyMoney.setText(agentIncome + "元");
                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    @OnClick({R.id.income_push_layout, R.id.income_team_layout, R.id.income_proxy_layout, R.id.action_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.income_push_layout:
                UIHelper.showOrderNumberActivity(this, URLConstants.ACHIEVEMENT_CONTENT_URL);
                break;
            case R.id.income_team_layout:
                UIHelper.showOrderNumberActivity(this, URLConstants.TEAM_ACHI_CONTENT_URL);

                break;
            case R.id.income_proxy_layout:
                UIHelper.showOrderNumberActivity(this, URLConstants.AGENT_ACHI_CONTENT_URL);
                break;
            case R.id.action_back:
                finish();
                break;
        }
    }


}
