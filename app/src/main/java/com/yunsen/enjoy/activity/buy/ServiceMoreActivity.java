package com.yunsen.enjoy.activity.buy;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.fragment.home.StoreRecyclerAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.EndlessRecyclerOnScrollListener;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.LoadMoreLayout;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/25.
 */

public class ServiceMoreActivity extends BaseFragmentActivity implements MultiItemTypeAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.service_more_recycler)
    RecyclerView serviceMoreRecycler;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshLayout;

    private int mPageIndex = 1;
    private StoreRecyclerAdapter mAdapter;
    private ArrayList<SProviderModel> mData;
    private boolean mHasMore;
    private boolean mIsLoadMore;
    private LoadMoreLayout loadMoreLayout;
    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadStart() {
            super.onLoadStart();
            loadMoreLayout.visibleView();
            loadMoreLayout.showloadingStart();
        }

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            mIsLoadMore = true;
            mPageIndex++;
            loadMoreLayout.showLoading();
            requestData();
        }

        @Override
        public void onRefreshComplete() {
            super.onRefreshComplete();
            loadMoreLayout.goneView();
        }
    };

    @Override
    public int getLayout() {
        return R.layout.activity_service_more;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("服务商");
        serviceMoreRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mData = new ArrayList<>();
        mAdapter = new StoreRecyclerAdapter(this, R.layout.shop_item, mData);
        HeaderAndFooterRecyclerViewAdapter footerRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        serviceMoreRecycler.setAdapter(footerRecyclerViewAdapter);
        loadMoreLayout = new LoadMoreLayout(this);
        RecyclerViewUtils.setFooterView(serviceMoreRecycler, loadMoreLayout);
        loadMoreLayout.goneView();
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        serviceMoreRecycler.addOnScrollListener(onScrollListener);
    }

    @Override
    public void requestData() {
        HttpProxy.getServiceMoreProvider(mPageIndex, null, new HttpCallBack<List<SProviderModel>>() {
            @Override
            public void onSuccess(List<SProviderModel> responseData) {
                if (mIsLoadMore) {
                    mHasMore = mAdapter.addDatas(responseData);
                } else {
                    mAdapter.upDatas(responseData);
                }
                if (mHasMore) {
                    onScrollListener.onRefreshComplete();
                } else {
                    loadMoreLayout.showLoadNoMore(null);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Request request, Exception e) {
                loadMoreLayout.showLoadNoMore(null);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (mData != null && mData.size() > position) {
            SProviderModel data = mData.get(position);
            if (data != null) {
                int id = data.getId();
                UIHelper.showServiceShopInfoActivity(this, String.valueOf(id));
            }
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Override
    public void onRefresh() {
        mPageIndex = 1;
        mHasMore = true;
        mIsLoadMore = false;
        requestData();
    }
}
