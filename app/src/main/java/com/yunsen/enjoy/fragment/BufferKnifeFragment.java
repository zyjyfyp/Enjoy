package com.yunsen.enjoy.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.fragment.home.BannerAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.widget.SearchActionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;


public class BufferKnifeFragment extends BaseFragment {

    @Bind(R.id.search_bar)
    SearchActionBar searchBar;
    @Bind(R.id.buy_indicator)
    CirclePageIndicator buyIndicator;
    @Bind(R.id.layout_ent_gallery)
    RelativeLayout layoutEntGallery;
    @Bind(R.id.buy_main_tab)
    TabLayout buyMainTab;
    @Bind(R.id.buyMainPager)
    ViewPager buyMainPager;
    private Activity mContext;
    private ArrayList<AdvertModel> mAdvDatas;
    private BannerAdapter bannerAdapter;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recommend_shop_list;
    }

    private static final String TAG = "BufferKnifeFragment";

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        buyMainPager = rootView.findViewById(R.id.buy_pager);
    }

    @Override
    protected void initData() {
        bannerAdapter = new BannerAdapter(getData(), getActivity());
        buyMainPager.setAdapter(bannerAdapter);
        buyIndicator.setViewPager(buyMainPager);
        buyIndicator.setPadding(5, 5, 10, 5);
    }

    @Override
    protected void requestData() {
        Log.e(TAG, "requestData: qeu" );
        HttpProxy.getHomeAdvertList(1017, new HttpCallBack<List<AdvertModel>>() {
            @Override
            public void onSuccess(List<AdvertModel> responseData) {
                bannerAdapter = new BannerAdapter(responseData, getActivity());// TODO: 2018/4/20 need
                buyMainPager.setAdapter(bannerAdapter);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    @Override
    protected void initListener() {

    }

    public List<AdvertModel> getData() {
        ArrayList<AdvertModel> data = new ArrayList<>();
        data.add(new AdvertModel(R.mipmap.adv_home, null));
        data.add(new AdvertModel(R.mipmap.adv_home, "http://pic71.nipic.com/file/20150610/13549908_104823135000_2.jpg"));
        return data;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}