package com.yunsen.enjoy.activity.buy;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.fragment.home.GoodsPartsAdapter;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.GoodsData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/24.
 */

public class PartsShopActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.goods_parts_recycler)
    RecyclerView goodsPartsRecycler;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;
    private ArrayList<CarDetails> mDatas;
    private GoodsPartsAdapter mAdapter;

    @Override
    public int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_parts_shop;
    }

    @Override
    protected void initView() {
        actionBarTitle.setText("配件商城");
        goodsPartsRecycler.setLayoutManager(new GridLayoutManager(this, 2));

        mDatas = new ArrayList<>();
        mAdapter = new GoodsPartsAdapter(this, R.layout.goods_parts_item, mDatas);
        goodsPartsRecycler.setAdapter(mAdapter);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.action_back)
    public void onViewClicked() {
    }
}
