package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.MonthIncomeAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.MonthAmountBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/6/21.
 */

public class MonthIncomeActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private MonthIncomeAdapter mAdapter;
    private int mPageIndex = 1;

    @Override
    public int getLayout() {
        return R.layout.activity_month_icome;

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("本月盈收");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MonthIncomeAdapter(this, R.layout.month_income_item, new ArrayList<MonthAmountBean>());
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void requestData() {
        HttpProxy.userAmountListRequest(String.valueOf(mPageIndex), new HttpCallBack<List<MonthAmountBean>>() {
            @Override
            public void onSuccess(List<MonthAmountBean> responseData) {
                mAdapter.upBaseDatas(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }
}
