package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.OrderNumberAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.AchieveInfoBean;
import com.yunsen.enjoy.model.ListOrderCountBean;
import com.yunsen.enjoy.ui.recyclerview.EndlessRecyclerOnScrollListener;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.LoadMoreLayout;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/7/4.
 */

public class OrderNumberActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.recycler_order)
    RecyclerView recyclerView;
    private OrderNumberAdapter mAdapter;
    private int mPageIndex = 1;
    private String mUrl;
    private boolean mHasMore;
    private boolean mIsLoadMore;
    private EndlessRecyclerOnScrollListener mOnListener;
    private LoadMoreLayout loadMoreLayout;

    @Override
    public int getLayout() {
        return R.layout.activity_order_number;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("订单数");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(Constants.ORDER_NUMBER_URL_KEY);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new OrderNumberAdapter(this, R.layout.order_number_item, new ArrayList<ListOrderCountBean>());
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
        super.requestData();
        HttpProxy.achievementContentRequest(mUrl, String.valueOf(mPageIndex), new HttpCallBack<AchieveInfoBean>() {
            @Override
            public void onSuccess(AchieveInfoBean responseData) {
                mAdapter.upBaseDatas(responseData.getListOrderCount());
                if (mIsLoadMore) {
                    mHasMore = mAdapter.addBaseDatas(responseData.getListOrderCount());
                } else {
                    mAdapter.upBaseDatas(responseData.getListOrderCount());
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
        ButterKnife.unbind(this);
    }
}
