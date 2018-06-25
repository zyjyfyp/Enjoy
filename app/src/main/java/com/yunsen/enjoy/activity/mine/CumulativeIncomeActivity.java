package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.widget.piechart.PieChart;
import com.yunsen.enjoy.widget.piechart.PieData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/22.
 */

public class CumulativeIncomeActivity extends BaseFragmentActivity {
    @Bind(R.id.pie_chart)
    PieChart pieChart;
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

    @Override
    public int getLayout() {
        return R.layout.activity_comulative_income;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        pieChart.setStartAngle(0);
        pieChart.setName("0元");
        ArrayList<PieData> mPieDatas = new ArrayList<>();
        PieData pieData1 = new PieData();
        pieData1.setValue(0);
        pieData1.setColor(getResources().getColor(R.color.color_yellow));

        PieData pieData2 = new PieData();
        pieData2.setValue(0);
        pieData2.setColor(getResources().getColor(R.color.color_violet));

        PieData pieData3 = new PieData();
        pieData3.setValue(0);
        pieData3.setColor(getResources().getColor(R.color.color_pink));
        mPieDatas.add(pieData1);
        mPieDatas.add(pieData2);
        mPieDatas.add(pieData3);
        pieChart.setPieData(mPieDatas);
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.income_push_layout, R.id.income_team_layout, R.id.income_proxy_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.income_push_layout:
                break;
            case R.id.income_team_layout:
                break;
            case R.id.income_proxy_layout:
                break;
        }
    }
}
