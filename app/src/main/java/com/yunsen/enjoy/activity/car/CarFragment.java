package com.yunsen.enjoy.activity.car;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/17.
 */

public class CarFragment extends BaseFragment {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.shop_car_recycler)
    RecyclerView shopCarRecycler;
    @Bind(R.id.goods_all_size)
    TextView goodsAllSize;
    @Bind(R.id.goods_all_price)
    TextView goodsAllPrice;
    @Bind(R.id.change_goods_btn)
    Button changeGoodsBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_car;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        actionBarTitle.setText("换物车");
        actionBack.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick(R.id.change_goods_btn)
    public void onViewClicked() {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
