package com.yunsen.enjoy.activity.mine.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.fragment.home.StoreRecyclerAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.EndlessRecyclerOnScrollListener;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.LoadMoreLayout;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/5/15.
 */

public class ShopCollectionFragment extends BaseFragment implements MultiItemTypeAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.no_collect_tv)
    TextView noCollectTv;
    @Bind(R.id.collect_recycler)
    RecyclerView collectRecycler;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;
    private SharedPreferences spPreferences;
    private ArrayList<SProviderModel> mDatas;
    private StoreRecyclerAdapter mAdapter;
    private String mUserId;
    private boolean mHasMore;
    private boolean mIsLoadMore = true;
    private LoadMoreLayout loadMoreLayout;
    private int mPageIndex = 1;
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
            collectRecycler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestData();
                }
            }, 500);
        }

        @Override
        public void onRefreshComplete() {
            super.onRefreshComplete();
            loadMoreLayout.goneView();
        }
    };
    private boolean mIsRefresh;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_collection;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        spPreferences = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        collectRecycler = (RecyclerView) rootView.findViewById(R.id.collect_recycler);
        noCollectTv = (TextView) rootView.findViewById(R.id.no_collect_tv);
        collectRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatas = new ArrayList<>();
        mAdapter = new StoreRecyclerAdapter(getActivity(), R.layout.shop_item, mDatas);

        HeaderAndFooterRecyclerViewAdapter andFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        collectRecycler.setAdapter(andFooterRecyclerViewAdapter);
        loadMoreLayout = new LoadMoreLayout(getActivity());
        RecyclerViewUtils.setFooterView(collectRecycler, loadMoreLayout);
        loadMoreLayout.goneView();
    }

    @Override
    protected void initData() {
        SharedPreferences sp = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserId = sp.getString(SpConstants.USER_ID, "");
    }

    @Override
    protected void requestData() {
        HttpProxy.getShopCollectList(mUserId, String.valueOf(mPageIndex), new HttpCallBack<List<SProviderModel>>() {
            @Override
            public void onSuccess(List<SProviderModel> responseData) {
                if (mIsLoadMore) {
                    mHasMore = mAdapter.addDatas(responseData);
                    if (mPageIndex == 1) {
                        if (responseData == null || responseData.size() == 0) {
                            noCollectTv.setVisibility(View.VISIBLE);
                        } else {
                            noCollectTv.setVisibility(View.GONE);
                        }
                    }
                } else {
                    mAdapter.upDatas(responseData);
                    if (responseData == null || responseData.size() == 0) {
                        noCollectTv.setVisibility(View.VISIBLE);
                    } else {
                        noCollectTv.setVisibility(View.GONE);
                    }
                    ToastUtils.makeTextShort("已刷新");
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
        collectRecycler.addOnScrollListener(onScrollListener);
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (mDatas != null && mDatas.size() > position) {
            SProviderModel model = mDatas.get(position);
            int id = model.getUser_id();
            UIHelper.showServiceShopInfoActivity(getActivity(), String.valueOf(id));
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        ToastUtils.makeTextShort("长按删除功能暂无");
        return true;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        mPageIndex = 1;
        mHasMore = true;
        mIsLoadMore = false;
        requestData();
    }
}
