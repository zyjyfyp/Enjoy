package com.yunsen.enjoy.activity.mine.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.OneNoticeInfoBean;
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
 * Created by Administrator on 2018/5/17.
 */

public class DefaultNoticeFragment extends BaseFragment {
    @Bind(R.id.default_notice_recycler)
    RecyclerView defaultNoticeRecycler;
    private int mFragmentType;
    private int mPageIndex = 1;
    private ArrayList<OneNoticeInfoBean> mDatas;
    private NoticeListAdapter mAdapter;
    private LoadMoreLayout loadMoreLayout;
    private EndlessRecyclerOnScrollListener mOnListener;
    private boolean mIsLoadMore;
    private boolean mHasMore;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_default_notice;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        defaultNoticeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatas = new ArrayList<>();
        mAdapter = new NoticeListAdapter(getActivity(), mDatas);
        HeaderAndFooterRecyclerViewAdapter footerRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        defaultNoticeRecycler.setAdapter(footerRecyclerViewAdapter);
        loadMoreLayout = new LoadMoreLayout(getActivity());
        RecyclerViewUtils.setFooterView(defaultNoticeRecycler, loadMoreLayout);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        mFragmentType = bundle.getInt(Constants.FRAGMENT_TYPE_KEY, 1);
    }

    @Override
    protected void requestData() {
        HttpProxy.getOneTypeNoticeList(String.valueOf(mFragmentType), String.valueOf(mPageIndex), new HttpCallBack<List<OneNoticeInfoBean>>() {
            @Override
            public void onSuccess(List<OneNoticeInfoBean> responseData) {
                if (mIsLoadMore) {
                    mHasMore = mAdapter.addDatas(responseData);
                } else {
                    mAdapter.upDatas(responseData);
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

    @Override
    protected void initListener() {
        mOnListener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                super.onLoadNextPage(view);
                mPageIndex++;
                mIsLoadMore = true;
                defaultNoticeRecycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestData();
                    }
                }, 500);
            }

        };
        mOnListener.setLoadMoreLayout(loadMoreLayout);
        defaultNoticeRecycler.addOnScrollListener(mOnListener);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mOnListener != null) {
            defaultNoticeRecycler.removeOnScrollListener(mOnListener);
        }
        ButterKnife.unbind(this);
    }
}
