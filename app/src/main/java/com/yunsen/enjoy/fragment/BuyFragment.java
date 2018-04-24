package com.yunsen.enjoy.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.buy.FilterFragment;
import com.yunsen.enjoy.fragment.buy.FilterFragmentAdapter;
import com.yunsen.enjoy.fragment.home.BannerAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.widget.SearchActionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;


public class BuyFragment extends BaseFragment implements SearchActionBar.SearchClickListener {

    @Bind(R.id.search_bar)
    SearchActionBar searchBar;
    @Bind(R.id.buy_pager)
    ViewPager buyPager;
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
    private FilterFragmentAdapter mFragmentAdapter;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recommend_shop_list;
    }

    private static final String TAG = "BuyFragment";

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        searchBar.setLeftText("深圳");
    }

    @Override
    protected void initData() {
        bannerAdapter = new BannerAdapter(getData(), getActivity());
        buyPager.setAdapter(bannerAdapter);
        buyIndicator.setViewPager(buyPager);
        buyIndicator.setPadding(5, 5, 10, 5);

        mFragmentAdapter = new FilterFragmentAdapter(getChildFragmentManager(), getFragments());
        buyMainPager.setAdapter(mFragmentAdapter);
        buyMainTab.setupWithViewPager(buyMainPager);
    }

    private List<Fragment> getFragments() {
        ArrayList<Fragment> list = new ArrayList<>();
        FilterFragment newCar = new FilterFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CHANNEL_KEY, "goods");
        newCar.setArguments(bundle);
        list.add(newCar);
        FilterFragment olderCar = new FilterFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(Constants.CHANNEL_KEY, "promotion");
        olderCar.setArguments(bundle2);
        list.add(olderCar);
        return list;
    }

    @Override
    protected void requestData() {
        Log.e(TAG, "requestData: qeu");
        HttpProxy.getHomeAdvertList(1017, new HttpCallBack<List<AdvertModel>>() {
            @Override
            public void onSuccess(List<AdvertModel> responseData) {
                bannerAdapter = new BannerAdapter(responseData, getActivity());// TODO: 2018/4/20 need
                buyPager.setAdapter(bannerAdapter);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    @Override
    protected void initListener() {
        searchBar.setSearchClick(this);
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

    @Override
    public void onSearchClick(SearchActionBar.ViewType type) {
        switch (type) {
            case LEFT_IMG:
                UIHelper.showSelectCityActivity(getActivity());
                break;
            case CENTER_LAYOUT:
                break;
            case RIGHT_IMG:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400****120"));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
                break;
        }
    }
}