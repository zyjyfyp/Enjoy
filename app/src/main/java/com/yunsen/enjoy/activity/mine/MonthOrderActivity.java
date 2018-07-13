package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.MonthOrderAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.OrderBean;
import com.yunsen.enjoy.model.OrderDataBean;
import com.yunsen.enjoy.ui.recyclerview.EndlessRecyclerOnScrollListener;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.LoadMoreLayout;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;

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
    private LoadMoreLayout loadMoreLayout;
    private EndlessRecyclerOnScrollListener mOnListener;
    private boolean mIsLoadMore;
    private boolean mHasMore = true;

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
        HeaderAndFooterRecyclerViewAdapter footerRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerView.setAdapter(footerRecyclerViewAdapter);
        loadMoreLayout = new LoadMoreLayout(this);
        RecyclerViewUtils.setFooterView(recyclerView, loadMoreLayout);
    }

    @Override
    protected void initListener() {
        mOnListener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                super.onLoadNextPage(view);
                mPageIndex++;
                mIsLoadMore = true;
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestData();
                    }
                }, 500);
            }

        };
        mOnListener.setLoadMoreLayout(loadMoreLayout);
        recyclerView.addOnScrollListener(mOnListener);
    }

    @Override
    public void requestData() {
        HttpProxy.getMonthOrderList(String.valueOf(mPageIndex), new HttpCallBack<List<OrderDataBean>>() {
            @Override
            public void onSuccess(List<OrderDataBean> responseData) {
                if (mIsLoadMore) {
                    mHasMore = mAdapter.addBaseDatas(responseData);
                } else {
                    mAdapter.upBaseDatas(responseData);
                }
                if (mHasMore) {
                    mOnListener.onRefreshComplete();
                } else {
                    mOnListener.noMore(null);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                mHasMore = false;
                mOnListener.noMore(null);
            }
        });
    }

    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOnListener != null) {
            recyclerView.removeOnScrollListener(mOnListener);
        }
    }
}
