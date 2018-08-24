package com.yunsen.enjoy.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.adapter.CollectAdapter;
import com.yunsen.enjoy.activity.mine.adapter.GoodsSearchAdapter;
import com.yunsen.enjoy.adapter.SearchListAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.GoodsData;
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
    @Bind(R.id.search_spinner)
    Spinner searchSpinner;

    private String mSearchText = "";
    private ArrayList<CarDetails> mDatas;
    private ArrayList<CarDetails> mDatas2;
    private SearchListAdapter mAdapter1;
    private int mPagerIndex = 1;
    private boolean mIsLoadMore;
    private boolean mHasMore;
    private LoadMoreLayout loadMoreLayout;
    private EndlessRecyclerOnScrollListener mOnScrollListener;
    private PopupWindow popupWindow;
    private int mCurrentPosition = 0;
    private GoodsSearchAdapter mAdapter2;
    private String mEndSearcheKey;

    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("搜索");
        searchView.setIconified(false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"汽车", "商品"});

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        searchSpinner.setAdapter(adapter);
        searchSpinner.setSelection(0, false);
        //添加事件Spinner事件监听
        searchSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mDatas = new ArrayList<>();
        mDatas2 = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter1 = new SearchListAdapter(this, R.layout.goods_item_2, mDatas);
        mAdapter2 = new GoodsSearchAdapter(this, mDatas2);
        HeaderAndFooterRecyclerViewAdapter footerRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter1);
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
        mAdapter1.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                List<CarDetails> datas = mAdapter1.getDatas();
                if (datas != null && datas.size() > 0 && datas.size() > position) {
                    CarDetails goodsData = datas.get(position);
                    UIHelper.showCarDetailsActivity(SearchActivity.this, Integer.toString(goodsData.getId()));
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mAdapter2.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                if (mDatas2 != null && mDatas2.size() > 0 && mDatas2.size() > position) {
                    CarDetails goodsData = mDatas2.get(position);
                    switch (goodsData.getChannel_id()) {//7车  13积分 22商品
                        case 7:
                            UIHelper.showCarDetailsActivity(SearchActivity.this, Integer.toString(goodsData.getId()));
                            break;
                        case 13:
                            UIHelper.showGoodsDescriptionActivity(SearchActivity.this, Integer.toString(goodsData.getId()), goodsData.getTitle(), Constants.POINT_BUY);
                            break;
                        case 22:
                            UIHelper.showGoodsDescriptionActivity(SearchActivity.this, Integer.toString(goodsData.getId()), goodsData.getTitle());
                            break;
                        default:
                            UIHelper.showCarDetailsActivity(SearchActivity.this, Integer.toString(goodsData.getId()));
                            break;
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                return false;
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
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Logger.e("提交onQueryTextSubmit: " + query);
//        Toast.makeText(this, "开始搜索" + mSearchText, Toast.LENGTH_SHORT).show();
        mPagerIndex = 1;
        mIsLoadMore = false;
        submitSearchRequest();
        return false;
    }

    /**
     * 搜索汽车
     */
    private void submitSearchRequest() {
        if (mCurrentPosition == 1) {
            submitGoodsSearchRequest();
        } else {
            HttpProxy.getSearchList(mSearchText, String.valueOf(mPagerIndex), new HttpCallBack<List<CarDetails>>() {
                @Override
                public void onSuccess(List<CarDetails> responseData) {
                    mEndSearcheKey = mSearchText;
                    if (mIsLoadMore) {
                        mHasMore = mAdapter1.addData(responseData);
                    } else {
                        mAdapter1.upData(responseData);
                    }
                    if (mHasMore) {
                        noCarTv.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        mOnScrollListener.onRefreshComplete();
                    } else {
                        mOnScrollListener.noMore(null);
                    }
                }

                @Override
                public void onError(Request request, Exception e) {
                    noCarTv.setVisibility(View.VISIBLE);
                    if (!mSearchText.equals(mEndSearcheKey)) {
                        recyclerView.setVisibility(View.GONE);
                    }
                    mOnScrollListener.noMore(null);
                }
            });
        }
    }

    /**
     * 搜索商品
     */
    private void submitGoodsSearchRequest() {
        HttpProxy.getSearchGoods(String.valueOf(mPagerIndex), mSearchText, new HttpCallBack<List<CarDetails>>() {
            @Override
            public void onSuccess(List<CarDetails> responseData) {
                mEndSearcheKey = mSearchText;
                if (mIsLoadMore) {
                    mHasMore = mAdapter2.addData(responseData);
                } else {
                    mAdapter2.upData(responseData);
                }
                if (mHasMore) {
                    noCarTv.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    mOnScrollListener.onRefreshComplete();
                } else {
                    mOnScrollListener.noMore(null);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                noCarTv.setVisibility(View.VISIBLE);
                if (!mSearchText.equals(mEndSearcheKey)) {
                    recyclerView.setVisibility(View.GONE);
                }
                mOnScrollListener.noMore(null);
            }
        });
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        mSearchText = newText;
        return true;
    }

    private class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            searchSpinner.setSelection(position, true);
            mCurrentPosition = position;
            mPagerIndex = 1;
            mHasMore = true;
            mIsLoadMore = false;
            if (position == 0) {
                mDatas.clear();
                HeaderAndFooterRecyclerViewAdapter footerRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter1);
                recyclerView.setAdapter(footerRecyclerViewAdapter);
                RecyclerViewUtils.setFooterView(recyclerView, loadMoreLayout);
            } else {
                mDatas2.clear();
                HeaderAndFooterRecyclerViewAdapter footerRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter2);
                recyclerView.setAdapter(footerRecyclerViewAdapter);
                RecyclerViewUtils.setFooterView(recyclerView, loadMoreLayout);
            }
            submitSearchRequest();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
