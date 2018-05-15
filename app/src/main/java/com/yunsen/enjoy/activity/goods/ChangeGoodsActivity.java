package com.yunsen.enjoy.activity.goods;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.goods.adapter.DGoodRecyclerAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.EndlessRecyclerOnScrollListener;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.LoadMoreLayout;
import com.yunsen.enjoy.ui.recyclerview.RecycleViewDivider;
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
 * Created by Administrator on 2018/5/15.
 */

public class ChangeGoodsActivity extends BaseFragmentActivity implements MultiItemTypeAdapter.OnItemClickListener {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.d_text_hor_1)
    CheckBox dTextHor1;
    @Bind(R.id.d_text_hor_2)
    CheckBox dTextHor2;
    @Bind(R.id.d_text_hor_3)
    TextView dTextHor3;
    @Bind(R.id.d_text_hor_4)
    CheckBox dTextHor4;
    @Bind(R.id.d_recycler_view)
    RecyclerView dRecyclerView;
    private ArrayList<GoodsData> mData;
    private DGoodRecyclerAdapter mAdapter;
    private String mChannelName;
    private String mCategegoryId;
    private int mPageIndex = 1;
    private boolean isLoadMore = false;
    private boolean mHasMore = true;
    private LoadMoreLayout loadMoreLayout;

    @Override
    public int getLayout() {
        return R.layout.activity_change_goods;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarRight.setImageResource(R.mipmap.search_icon);
        actionBarRight.setVisibility(View.VISIBLE);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        dRecyclerView.setLayoutManager(layout);
        loadMoreLayout = new LoadMoreLayout(this);
        dRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mChannelName = intent.getStringExtra(Constants.CHANNEL_NAME_KEY);
        mCategegoryId = intent.getStringExtra(Constants.CATEGORY_ID_KEY);
        String actName = intent.getStringExtra(Constants.ACT_NAME_KEY);
        actionBarTitle.setText(actName);
        mData = new ArrayList<>();
        mAdapter = new DGoodRecyclerAdapter(this, R.layout.d_goods_item, mData);
        HeaderAndFooterRecyclerViewAdapter recyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        dRecyclerView.setAdapter(recyclerViewAdapter);
        RecyclerViewUtils.setFooterView(dRecyclerView, loadMoreLayout);

    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
        dRecyclerView.addOnScrollListener(mOnScrollListener);

    }

    private static final String TAG = "ChangeGoodsActivity";
    /**
     * RecycleView的滑动监听(加载更多)
     */
    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            Log.e(TAG, "onLoadNextPage: 加载更多");
            if (mHasMore) {
                mPageIndex++;
                isLoadMore = true;
                requestData();
                loadMoreLayout.showLoading();
            } else {
                loadMoreLayout.showLoadNoMore();
            }
        }
    };


    @Override
    public void requestData() {
        if (mHasMore)
            HttpProxy.getChangeGoodsList(mChannelName, mCategegoryId, String.valueOf(mPageIndex), new HttpCallBack<List<GoodsData>>() {
                @Override
                public void onSuccess(List<GoodsData> responseData) {
                    if (isLoadMore) {
                        mHasMore = mAdapter.addData(responseData);
                        if (mHasMore) {
                            loadMoreLayout.showloadingStart();
                        } else {
                            loadMoreLayout.showLoadNoMore();
                        }
                    } else {
                        mAdapter.upData(responseData);
                    }
                }

                @Override
                public void onError(Request request, Exception e) {
                    if (!isLoadMore) {
                        mAdapter.upData(null);
                    } else {
                        mHasMore = false;
                        loadMoreLayout.showLoadNoMore();
                    }

                }

            });
    }


    @OnClick({R.id.action_back, R.id.action_bar_right, R.id.d_text_hor_1, R.id.d_text_hor_2, R.id.d_text_hor_3, R.id.d_text_hor_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.action_bar_right:
                UIHelper.showSearchActivity(this);
                break;
            case R.id.d_text_hor_1:
                break;
            case R.id.d_text_hor_2:
                break;
            case R.id.d_text_hor_3:
                break;
            case R.id.d_text_hor_4:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        List<GoodsData> datas = mAdapter.getDatas();
        if (datas != null && position > 0 && datas.size() > position) {
            GoodsData data = datas.get(position);
            UIHelper.showGoodsDescriptionActivity(this, String.valueOf(data.getId()), data.getTitle());
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
