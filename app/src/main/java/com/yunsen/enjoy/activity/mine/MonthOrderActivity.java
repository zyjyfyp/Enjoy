package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.MonthOrderAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.OrderBean;
import com.yunsen.enjoy.model.OrderDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/7/4.
 */

public class MonthOrderActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private MonthOrderAdapter mAdapter;
    private int mPageIndex = 1;

    @Override
    public int getLayout() {
        return R.layout.activity_month_order;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("本月订单");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MonthOrderAdapter(this, R.layout.month_order_item, new ArrayList<OrderDataBean>());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void requestData() {
        HttpProxy.getMonthOrderList(String.valueOf(mPageIndex), new HttpCallBack<List<OrderDataBean>>() {
            @Override
            public void onSuccess(List<OrderDataBean> responseData) {
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
