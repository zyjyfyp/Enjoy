package com.yunsen.enjoy.activity.buy;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.adapter.ExchangePointAdapter;
import com.yunsen.enjoy.adapter.IntegralTopAdapter;
import com.yunsen.enjoy.fragment.home.BannerAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/24.
 */

public class ExchangePointActivity extends BaseFragmentActivity implements MultiItemTypeAdapter.OnItemClickListener {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.exchange_recycler)
    RecyclerView exchangeRecycler;

    AutoLoopViewPager exchangePager;
    CirclePageIndicator exchangeIndicator;

    private int mPageIndex = 1;
    private ArrayList<CarDetails> mDatas;
    private ExchangePointAdapter mAdapter;
    private View topView;

    @Override
    public int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_exchange_point;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("积分兑换");
        topView = getLayoutInflater().inflate(R.layout.exchange_point_top_layout, null);
        exchangePager = topView.findViewById(R.id.exchange_pager);
        exchangeIndicator = topView.findViewById(R.id.exchange_indicator);


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        exchangeRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mDatas = new ArrayList<>();
        mAdapter = new ExchangePointAdapter(this, R.layout.exchange_point_item, mDatas);
        HeaderAndFooterRecyclerViewAdapter headerAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        exchangeRecycler.setAdapter(headerAdapter);
        RecyclerViewUtils.setHeaderView(exchangeRecycler, topView);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void requestData() {

        HttpProxy.getIntegralChangeData(new HttpCallBack<List<CarDetails>>() {
            @Override
            public void onSuccess(List<CarDetails> responseData) {
                exchangePager.setAdapter(new IntegralTopAdapter(ExchangePointActivity.this, responseData));
                exchangeIndicator.setViewPager(exchangePager);
                exchangeIndicator.setPadding(5, 5, 10, 5);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });

        HttpProxy.getIntegrallMoreDatas(mPageIndex, new HttpCallBack<List<CarDetails>>() {
            @Override
            public void onSuccess(List<CarDetails> responseData) {
                mAdapter.upData(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

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
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        int pos = position - 1;
        if (mDatas != null && pos < mDatas.size()) {
            ToastUtils.makeTextShort(mDatas.get(pos).getTitle());
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
