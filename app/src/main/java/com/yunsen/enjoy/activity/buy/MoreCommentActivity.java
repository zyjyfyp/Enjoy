package com.yunsen.enjoy.activity.buy;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.adapter.CommentAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CommentBean;
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
 * Created by Administrator on 2018/8/28/028.
 */

public class MoreCommentActivity extends BaseFragmentActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.team_top_layout)
    LinearLayout teamTopLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;
    @Bind(R.id.no_data_error_layout)
    LinearLayout noDataErrorLayout;
    private ArrayList<CommentBean> mData;
    private CommentAdapter mAdapter;
    private LoadMoreLayout loadMoreLayout;
    private String mGoodId;
    private int mPageIndex = 1;
    private boolean mIsLoadMore;
    private boolean mHasMore;
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
        return R.layout.activity_more_comment;

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mGoodId = getIntent().getStringExtra(Constants.ACT_DATA);
        actionBarTitle.setText("评价");
        mData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommentAdapter(this, R.layout.comment_item_layout, mData);
        HeaderAndFooterRecyclerViewAdapter footerRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerView.setAdapter(footerRecyclerViewAdapter);
        loadMoreLayout = new LoadMoreLayout(this);
        RecyclerViewUtils.setFooterView(recyclerView, loadMoreLayout);
        loadMoreLayout.goneView();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    public void requestData() {
        HttpProxy.getCommentList(mGoodId, String.valueOf(mPageIndex), "8", new HttpCallBack<List<CommentBean>>() {
            @Override
            public void onSuccess(List<CommentBean> responseData) {
                noDataErrorLayout.setVisibility(View.GONE);
                if (mIsLoadMore) {
                    mHasMore = mAdapter.addBaseDatas(responseData);
                } else {
                    mAdapter.upBaseDatas(responseData);
                }
                if (mHasMore) {
                    onScrollListener.onRefreshComplete();
                } else {
                    loadMoreLayout.showLoadNoMore(null);
                }
                swipeRefreshWidget.setRefreshing(false);
            }

            @Override
            public void onError(Request request, Exception e) {
                loadMoreLayout.showLoadNoMore(null);
                swipeRefreshWidget.setRefreshing(false);
                if (mData.size() == 0) {
                    noDataErrorLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void initListener() {
        swipeRefreshWidget.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(onScrollListener);
    }


    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {
        mPageIndex = 1;
        mHasMore = true;
        mIsLoadMore = false;
        requestData();
    }


}
