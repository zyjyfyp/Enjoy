package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.MoneyRecordAdapter;
import com.yunsen.enjoy.activity.mine.adapter.MoneyWithdrawAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.WalletCashBean;
import com.yunsen.enjoy.model.WithdrawLogData;
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
 * Created by Administrator on 2018/7/7.
 */

public class MoneyWithdrawActivity extends BaseFragmentActivity {

    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.recycler_withdraw)
    RecyclerView recyclerWithdraw;
    private int mPageIndex = 1;
    private ArrayList<WithdrawLogData> mDatas;
    private MoneyWithdrawAdapter mAdapter;
    private boolean mHasMore;
    private boolean mIsLoadMore;
    private EndlessRecyclerOnScrollListener mOnListener;
    private LoadMoreLayout loadMoreLayout;

    @Override
    public int getLayout() {
        return R.layout.activity_money_withdraw;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("提现记录");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        recyclerWithdraw.setLayoutManager(new LinearLayoutManager(this));
        mDatas = new ArrayList<>();
        mAdapter = new MoneyWithdrawAdapter(this, R.layout.money_withdraw_item, mDatas);
        HeaderAndFooterRecyclerViewAdapter footerRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerWithdraw.setAdapter(footerRecyclerViewAdapter);
        loadMoreLayout = new LoadMoreLayout(this);
        RecyclerViewUtils.setFooterView(recyclerWithdraw, loadMoreLayout);
    }

    @Override
    protected void initListener() {
        mOnListener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                super.onLoadNextPage(view);
                mPageIndex++;
                mIsLoadMore = true;
                recyclerWithdraw.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestData();
                    }
                }, 500);
            }

        };
        mOnListener.setLoadMoreLayout(loadMoreLayout);
        recyclerWithdraw.addOnScrollListener(mOnListener);

    }

    @Override
    public void requestData() {
        HttpProxy.userApplyWithdrawLog(String.valueOf(mPageIndex), new HttpCallBack<List<WithdrawLogData>>() {
            @Override
            public void onSuccess(List<WithdrawLogData> responseData) {
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
            recyclerWithdraw.removeOnScrollListener(mOnListener);
        }
        ButterKnife.unbind(this);
    }

}
