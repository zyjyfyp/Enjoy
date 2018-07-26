package com.yunsen.enjoy.activity.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.adapter.TeamInfoAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.ListOrderCountBean;
import com.yunsen.enjoy.model.TeamInfoBean;
import com.yunsen.enjoy.model.TeamItemBean;
import com.yunsen.enjoy.ui.recyclerview.EndlessRecyclerOnScrollListener;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.LoadMoreLayout;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/10.
 */

public class TeamFragment extends BaseFragment {

    @Bind(R.id.number_tv)
    TextView numberTv;
    @Bind(R.id.order_tv)
    TextView orderTv;
    @Bind(R.id.money_tv)
    TextView moneyTv;
    @Bind(R.id.no_data_tv)
    TextView noDataTv;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private int mType;
    private int mPageIndex = 1;
    private TeamInfoAdapter mAdapter;
    private EndlessRecyclerOnScrollListener mOnListener;
    private boolean mIsLoadMore;
    private LoadMoreLayout loadMoreLayout;
    private boolean mHasMore;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_team;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        mType = bundle.getInt(Constants.TEAM_TYPE_KEY, Constants.TEAM_TYPE_ONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TeamInfoAdapter(getActivity(), R.layout.team_info_item, new ArrayList<TeamItemBean>());
        HeaderAndFooterRecyclerViewAdapter footerRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerView.setAdapter(footerRecyclerViewAdapter);
        loadMoreLayout = new LoadMoreLayout(getActivity());
        RecyclerViewUtils.setFooterView(recyclerView, loadMoreLayout);
    }

    @Override
    protected void requestData() {
        switch (mType) {
            case Constants.TEAM_TYPE_ONE:
                HttpProxy.getGavelockRing(String.valueOf(mPageIndex), new HttpCallBack<TeamInfoBean>() {
                    @Override
                    public void onSuccess(TeamInfoBean responseData) {
                        numberTv.setText(String.valueOf(responseData.getRecordCount()));
                        orderTv.setText(String.valueOf(responseData.getOrderCounts()));

                        List<TeamItemBean> listModel = responseData.getListModel();
                        List<ListOrderCountBean> listOrderCount = responseData.getListOrderCount();
                        if (listModel != null && listOrderCount != null && listModel.size() >= listOrderCount.size()) {
                            int size = listOrderCount.size();
                            for (int i = 0; i < size; i++) {
                                listModel.get(i).setOrderCount(listOrderCount.get(i).getOrderCount());
                            }
                        }
                        if (mIsLoadMore) {
                            mHasMore = mAdapter.addBaseDatas(listModel);
                        } else {
                            mAdapter.upBaseDatas(listModel);
                        }
                        if (mHasMore) {
                            mOnListener.onRefreshComplete();
                        } else {
                            mOnListener.noMore(null);
                        }
                        noDataTv.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        mHasMore = false;
                        mOnListener.noMore(null);
                    }
                });
                break;
            case Constants.TEAM_TYPE_TWO:
                HttpProxy.getFriendRing(String.valueOf(mPageIndex), new HttpCallBack<TeamInfoBean>() {
                    @Override
                    public void onSuccess(TeamInfoBean responseData) {
                        numberTv.setText(String.valueOf(responseData.getRecordCount()));
                        orderTv.setText(String.valueOf(responseData.getOrderCounts()));
                        List<TeamItemBean> listModel = responseData.getListModel();
                        List<ListOrderCountBean> listOrderCount = responseData.getListOrderCount();
                        if (listModel != null && listOrderCount != null && listModel.size() >= listOrderCount.size()) {
                            int size = listOrderCount.size();
                            for (int i = 0; i < size; i++) {
                                listModel.get(i).setOrderCount(listOrderCount.get(i).getOrderCount());
                            }
                        }
                        if (mIsLoadMore) {
                            mHasMore = mAdapter.addBaseDatas(listModel);
                        } else {
                            mAdapter.upBaseDatas(listModel);
                        }
                        if (mHasMore) {
                            mOnListener.onRefreshComplete();
                        } else {
                            mOnListener.noMore(null);
                        }
                        noDataTv.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        mHasMore = false;
                        mOnListener.noMore(null);
                    }
                });
                break;
            case Constants.TEAM_TYPE_THREE:
                HttpProxy.getVermicelliRing(String.valueOf(mPageIndex), new HttpCallBack<TeamInfoBean>() {
                    @Override
                    public void onSuccess(TeamInfoBean responseData) {
                        numberTv.setText(String.valueOf(responseData.getRecordCount()));
                        orderTv.setText(String.valueOf(responseData.getOrderCounts()));
                        List<TeamItemBean> listModel = responseData.getListModel();
                        List<ListOrderCountBean> listOrderCount = responseData.getListOrderCount();
                        if (listModel != null && listOrderCount != null && listModel.size() >= listOrderCount.size()) {
                            int size = listOrderCount.size();
                            for (int i = 0; i < size; i++) {
                                listModel.get(i).setOrderCount(listOrderCount.get(i).getOrderCount());
                            }
                        }
                        if (mIsLoadMore) {
                            mHasMore = mAdapter.addBaseDatas(listModel);
                        } else {
                            mAdapter.upBaseDatas(listModel);
                        }
                        if (mHasMore) {
                            mOnListener.onRefreshComplete();
                        } else {
                            mOnListener.noMore(null);
                        }
                        noDataTv.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        mHasMore = false;
                        mOnListener.noMore(null);
                    }
                });
                break;
        }
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mOnListener != null) {
            recyclerView.removeOnScrollListener(mOnListener);
        }
        ButterKnife.unbind(this);
    }
}
