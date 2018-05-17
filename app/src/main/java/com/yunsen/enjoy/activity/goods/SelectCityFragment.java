package com.yunsen.enjoy.activity.goods;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.goods.adapter.ListViewPagerAdapter;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.widget.NoScrollViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/17.
 */

public class SelectCityFragment extends BaseFragment {
    @Bind(R.id.buy_main_tab)
    TabLayout buyMainTab;
    @Bind(R.id.no_scroll_pager)
    NoScrollViewPager noScrollPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_city;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        noScrollPager.setAdapter(new ListViewPagerAdapter());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
