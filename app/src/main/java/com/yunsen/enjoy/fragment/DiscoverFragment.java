package com.yunsen.enjoy.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.fragment.home.BannerAdapter;
import com.yunsen.enjoy.fragment.home.StoreRecyclerAdapter;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.widget.ZyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/22.
 */

public class DiscoverFragment extends BaseFragment {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
//    @Bind(R.id.loop_pager)
//    AutoLoopViewPager loopPager;
//    @Bind(R.id.indicator)
//    CirclePageIndicator indicator;
    @Bind(R.id.data_pager)
    ZyViewPager dataPager;
    private BannerAdapter bannerAdapter;
    private ListPagerAdapter mListPagerAdapter;
    private StoreRecyclerAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void initData() {
//        bannerAdapter = new BannerAdapter(getData(), getActivity());
//        loopPager.setAdapter(bannerAdapter);
//        indicator.setViewPager(loopPager);
//        indicator.setPadding(5, 5, 10, 5);

        mListPagerAdapter = new ListPagerAdapter(getRecyclerView(), getActivity());
        dataPager.setAdapter(mListPagerAdapter);
        tabLayout.setupWithViewPager(dataPager);
    }

    public List<AdvertModel> getData() {
        ArrayList<AdvertModel> data = new ArrayList<>();
        data.add(new AdvertModel(R.mipmap.adv_home, null));
        data.add(new AdvertModel(R.mipmap.adv_home, "http://pic71.nipic.com/file/20150610/13549908_104823135000_2.jpg"));
        data.add(new AdvertModel(R.mipmap.adv_home, "http://img07.tooopen.com/images/20170316/tooopen_sy_201956178977.jpg"));
        data.add(new AdvertModel(R.mipmap.adv_home, "http://img.zcool.cn/community/010a1b554c01d1000001bf72a68b37.jpg@1280w_1l_2o_100sh.png"));
        return data;
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public List<View> getRecyclerView() {
//        ArrayList<View> views = new ArrayList<>();
//        RecyclerView recyclerView = new RecyclerView(getActivity());
//        LinearLayoutManager layoutmanager = new LinearLayoutManager(getActivity());
//        //设置RecyclerView 布局
//        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutmanager);
//        ArrayList<CarStoreMode> storeModes = new ArrayList<>();
//        storeModes.add(new CarStoreMode(null, "上海大众汽车广东省深圳市宝安区4S店"));
//        storeModes.add(new CarStoreMode(null, "上海大众汽车广东省深圳市宝安区4S店"));
//        storeModes.add(new CarStoreMode(null, "上海大众汽车广东省深圳市宝安区4S店"));
//        storeModes.add(new CarStoreMode(null, "上海大众汽车广东省深圳市宝安区4S店"));
//        storeModes.add(new CarStoreMode(null, "上海大众汽车广东省深圳市宝安区4S店"));
//        storeModes.add(new CarStoreMode(null, "上海大众汽车广东省深圳市宝安区4S店"));
//        mAdapter = new StoreRecyclerAdapter(getActivity(), R.layout.shop_item, storeModes);
//        recyclerView.setAdapter(mAdapter);
//        views.add(recyclerView);
//
//        RecyclerView recyclerView2 = new RecyclerView(getActivity());
//        LinearLayoutManager layoutmanager2 = new LinearLayoutManager(getActivity());
//        //设置RecyclerView 布局
//        layoutmanager2.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView2.setLayoutManager(layoutmanager2);
//        recyclerView2.setAdapter(mAdapter);
//        views.add(recyclerView2);
//
//        RecyclerView recyclerView3= new RecyclerView(getActivity());
//        LinearLayoutManager layoutmanager3 = new LinearLayoutManager(getActivity());
//        //设置RecyclerView 布局
//        layoutmanager3.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView3.setLayoutManager(layoutmanager3);
//        recyclerView3.setAdapter(mAdapter);
//        views.add(recyclerView3);
//
//        RecyclerView recyclerView4= new RecyclerView(getActivity());
//        LinearLayoutManager layoutmanager4 = new LinearLayoutManager(getActivity());
//        //设置RecyclerView 布局
//        layoutmanager4.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView4.setLayoutManager(layoutmanager4);
//        recyclerView4.setAdapter(mAdapter);
//        views.add(recyclerView4);
       View view = getActivity().getLayoutInflater().inflate(R.layout.activity_login,null);
        ArrayList<View> views = new ArrayList<>();
        views.add(view);
        return views;
    }
}
