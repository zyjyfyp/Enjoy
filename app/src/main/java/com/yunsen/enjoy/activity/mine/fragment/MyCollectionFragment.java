package com.yunsen.enjoy.activity.mine.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.yunsen.enjoy.activity.mine.adapter.CollectAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.fragment.buy.FilterRecAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CollectWareData;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.EndlessRecyclerOnScrollListener;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.LoadMoreLayout;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.DialogProgress;
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

public class MyCollectionFragment extends BaseFragment implements MultiItemTypeAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.no_collect_tv)
    TextView noCollectTv;
    @Bind(R.id.collect_recycler)
    RecyclerView collectRecycler;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;
    private DialogProgress progress;
    private SharedPreferences spPreferences;
    private ArrayList<GoodsData> mDatas;
    private CollectAdapter mAdapter;
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_collection;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        progress = new DialogProgress(getActivity());
        spPreferences = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        collectRecycler = (RecyclerView) rootView.findViewById(R.id.collect_recycler);
        noCollectTv = (TextView) rootView.findViewById(R.id.no_collect_tv);
        collectRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatas = new ArrayList<>();
        mAdapter = new CollectAdapter(getActivity(), mDatas);
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
        HttpProxy.getCollectList(mUserId, String.valueOf(mPageIndex), new HttpCallBack<List<GoodsData>>() {
            @Override
            public void onSuccess(List<GoodsData> responseData) {
                if (mIsLoadMore) {
                    mHasMore = mAdapter.addData(responseData);
                    if (mPageIndex == 1) {
                        if (responseData == null || responseData.size() == 0) {
                            noCollectTv.setVisibility(View.VISIBLE);
                        } else {
                            noCollectTv.setVisibility(View.GONE);
                        }
                    }
                } else {
                    if (responseData == null || responseData.size() == 0) {
                        noCollectTv.setVisibility(View.VISIBLE);
                    } else {
                        noCollectTv.setVisibility(View.GONE);
                    }
                    mAdapter.upData(responseData);
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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (mDatas != null && mDatas.size() > 0 && mDatas.size() > position) {
            GoodsData goodsData = mDatas.get(position);
            switch (goodsData.getChannel_id()) {//7车  13积分 22商品
                case 7:
                    UIHelper.showCarDetailsActivity(getActivity(), Integer.toString(goodsData.getId()));
                    break;
                case 13:
                    UIHelper.showGoodsDescriptionActivity(getActivity(), Integer.toString(goodsData.getId()), goodsData.getTitle(), Constants.POINT_BUY);
                    break;
                case 22:
                    UIHelper.showGoodsDescriptionActivity(getActivity(), Integer.toString(goodsData.getId()), goodsData.getTitle());
                    break;
                default:
                    UIHelper.showCarDetailsActivity(getActivity(), Integer.toString(goodsData.getId()));
                    break;
            }
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (mDatas != null && mDatas.size() > 0 && mDatas.size() > position) {
            GoodsData goodsData = mDatas.get(position);
            dialog(goodsData.getId(), position);
        }
        return true;
    }

    protected void dialog(final int goodId, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认删除该收藏？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCollect(goodId, position);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void deleteCollect(int goodId, final int position) {
        HttpProxy.getDeleteCollect(mUserId, String.valueOf(goodId), new HttpCallBack<String>() {
            @Override
            public void onSuccess(String responseData) {
                ToastUtils.makeTextShort("取消收藏");
                mAdapter.removePosition(position);
            }

            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.makeTextShort("取消失败");
            }
        });
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
