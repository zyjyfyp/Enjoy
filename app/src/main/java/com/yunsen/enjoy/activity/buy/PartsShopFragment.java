package com.yunsen.enjoy.activity.buy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.WareInformationActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.fragment.home.GoodsPartsAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.EndlessRecyclerOnScrollListener;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.LoadMoreLayout;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/24.
 */

public class PartsShopFragment extends BaseFragment implements MultiItemTypeAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.goods_parts_recycler)
    RecyclerView goodsPartsRecycler;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;
    private ArrayList<CarDetails> mDatas;
    private GoodsPartsAdapter mAdapter;
    private int mFragmentType = 0;
    private int mChannelValue = 0;
    private HeaderAndFooterRecyclerViewAdapter footerRecyclerViewAdapter;
    private LoadMoreLayout loadMoreLayout;
    private int mPageIndex = 1;
    private boolean mHasMore;
    private boolean mIsLoadMore = false;
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
            mPageIndex++;
            mIsLoadMore = true;
            requestData();
        }

        @Override
        public void onRefreshComplete() {
            super.onRefreshComplete();
            loadMoreLayout.goneView();
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_parts_shop;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        goodsPartsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mDatas = new ArrayList<>();
        mAdapter = new GoodsPartsAdapter(getActivity(), R.layout.goods_parts_item, mDatas);
        footerRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        goodsPartsRecycler.setAdapter(footerRecyclerViewAdapter);
        loadMoreLayout = new LoadMoreLayout(getActivity());
        RecyclerViewUtils.setFooterView(goodsPartsRecycler, loadMoreLayout);

    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mFragmentType = arguments.getInt(Constants.FRAGMENT_TYPE_KEY);
            mChannelValue = arguments.getInt(Constants.CHANNEL_KEY);
        }

    }

    @Override
    protected void requestData() {
        HttpProxy.getGoodsMoreDatas(String.valueOf(mPageIndex), String.valueOf(mFragmentType), new HttpCallBack<List<CarDetails>>() {
            @Override
            public void onSuccess(List<CarDetails> responseData) {
                if (mIsLoadMore) {
                    mHasMore = mAdapter.addData(responseData);
                } else {
                    mAdapter.upData(responseData);
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
            }
        });
    }


    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
        swipeRefreshWidget.setOnRefreshListener(this);
        goodsPartsRecycler.addOnScrollListener(onScrollListener);
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (mDatas != null && mDatas.size() > position) {
            CarDetails details = mDatas.get(position);
            UIHelper.showGoodsDescriptionActivity(getActivity(), String.valueOf(details.getId()), details.getTitle());
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
