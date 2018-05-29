package com.yunsen.enjoy.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.adapter.SearchListAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.ui.recyclerview.EndlessRecyclerOnScrollListener;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.LoadMoreLayout;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * 搜索
 */
public class SearchActivity extends BaseFragmentActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = "SearchActivity";
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.search_view)
    SearchView searchView;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.no_car_tv)
    TextView noCarTv;
    private String mSearchText = "";
    private ArrayList<CarDetails> mDatas;
    private SearchListAdapter mAdapter;
    private int mPagerIndex = 1;
    private boolean mIsLoadMore;
    private boolean mHasMore;
    private LoadMoreLayout loadMoreLayout;
    private EndlessRecyclerOnScrollListener mOnScrollListener;

    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("搜索");
        searchView.setIconified(false);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mDatas = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchListAdapter(this, R.layout.goods_item_2, mDatas);
        HeaderAndFooterRecyclerViewAdapter footerRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerView.setAdapter(footerRecyclerViewAdapter);
        loadMoreLayout = new LoadMoreLayout(this);
        RecyclerViewUtils.setFooterView(recyclerView, loadMoreLayout);
    }

    @Override
    protected void initListener() {
        searchView.setOnQueryTextListener(this);
        mOnScrollListener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                super.onLoadNextPage(view);
                mPagerIndex++;
                mIsLoadMore = true;
                submitSearchRequest();
            }

            @Override
            public void noMore(String text) {
                super.noMore(text);
                ToastUtils.makeTextShort("没有更多商品");
            }
        };
        mOnScrollListener.setLoadMoreLayout(loadMoreLayout);
        recyclerView.addOnScrollListener(mOnScrollListener);
    }


    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Logger.e("提交onQueryTextSubmit: " + query);

//        Toast.makeText(this, "开始搜索" + mSearchText, Toast.LENGTH_SHORT).show();
        submitSearchRequest();
        return false;
    }

    private void submitSearchRequest() {

        HttpProxy.getSearchList(mSearchText, String.valueOf(mPagerIndex), new HttpCallBack<List<CarDetails>>() {
            @Override
            public void onSuccess(List<CarDetails> responseData) {
                if (responseData.size() == 0) {
                    noCarTv.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    if (mIsLoadMore) {
                        mHasMore = mAdapter.addData(responseData);
                    } else {
                        mAdapter.upData(responseData);
                    }
                    if (mHasMore) {
                        mOnScrollListener.onRefreshComplete();
                    } else {
                        mOnScrollListener.noMore(null);
                    }
                    noCarTv.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                noCarTv.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mSearchText = newText;
        return true;
    }


}
