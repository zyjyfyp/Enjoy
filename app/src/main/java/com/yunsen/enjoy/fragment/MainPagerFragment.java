package com.yunsen.enjoy.fragment;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.fragment.home.BannerAdapter;
import com.yunsen.enjoy.fragment.home.StoreRecyclerAdapter;
import com.yunsen.enjoy.fragment.model.BannerData;
import com.yunsen.enjoy.fragment.model.CarStoreMode;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.AdvertList;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.utils.BitmapUtil;
import com.yunsen.enjoy.widget.ADTextView;
import com.yunsen.enjoy.widget.HorizontalLayout;
import com.yunsen.enjoy.widget.HorizontalLayout2;
import com.yunsen.enjoy.widget.SearchActionBar;
import com.yunsen.enjoy.widget.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Request;


/**
 * 首页
 */
public class MainPagerFragment extends BaseFragment implements SearchActionBar.SearchClickListener {

    LinearLayout buttonLayout;
    LinearLayout newCarLayout;
    ImageView carImgBig;
    ImageView carImg1;
    ImageView carImg2;
    ImageView carImg3;
    ImageView carImg4;
    ImageView carImg5;
    private ImageView[] mCarImgArray
            = new ImageView[]{carImgBig, carImg1, carImg2, carImg3, carImg4, carImg5};
    LinearLayout shopLayout;
    private AutoLoopViewPager banner;
    private CirclePageIndicator indicatorLayout;
    private BannerAdapter bannerAdapter;
    private SearchActionBar searchBar;
    private ADTextView adtTv1;
    private ADTextView adtTv2;
    private HorizontalLayout oneHLayout;
    private HorizontalLayout2 twoHLayout;
    private RecyclerView recyclerView;
    private View topView;
    private HeaderAndFooterWrapper mHeaderWrapper;
    private StoreRecyclerAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView() {
        searchBar = ((SearchActionBar) rootView.findViewById(R.id.search_bar));
        recyclerView = rootView.findViewById(R.id.recyclerView);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        topView = inflater.inflate(R.layout.fragment_top_layout, null);

        banner = (AutoLoopViewPager) topView.findViewById(R.id.pager);
        indicatorLayout = ((CirclePageIndicator) topView.findViewById(R.id.indicator));

        adtTv1 = (ADTextView) topView.findViewById(R.id.adt_text1);
        adtTv2 = (ADTextView) topView.findViewById(R.id.adt_text2);

        oneHLayout = (HorizontalLayout) topView.findViewById(R.id.one_horizontal_layout);
        twoHLayout = (HorizontalLayout2) topView.findViewById(R.id.two_horizontal_layout);

        carImgBig = topView.findViewById(R.id.car_img_big);
        carImg1 = topView.findViewById(R.id.car_img_1);
        carImg2 = topView.findViewById(R.id.car_img_2);
        carImg3 = topView.findViewById(R.id.car_img_3);
        carImg4 = topView.findViewById(R.id.car_img_4);
        carImg5 = topView.findViewById(R.id.car_img_5);


    }

    @Override
    protected void initData() {
        LinearLayoutManager layoutmanager = new LinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);
        ArrayList<CarStoreMode> storeModes = new ArrayList<>();
        storeModes.add(new CarStoreMode(null, "上海大众汽车广东省深圳市宝安区4S店"));
        storeModes.add(new CarStoreMode(null, "上海大众汽车广东省深圳市宝安区4S店"));
        storeModes.add(new CarStoreMode(null, "上海大众汽车广东省深圳市宝安区4S店"));
        storeModes.add(new CarStoreMode(null, "上海大众汽车广东省深圳市宝安区4S店"));
        storeModes.add(new CarStoreMode(null, "上海大众汽车广东省深圳市宝安区4S店"));
        storeModes.add(new CarStoreMode(null, "上海大众汽车广东省深圳市宝安区4S店"));

        mAdapter = new StoreRecyclerAdapter(getActivity(), R.layout.shop_item, storeModes);
        mHeaderWrapper = new HeaderAndFooterWrapper(mAdapter);
        mHeaderWrapper.addHeaderView(topView);
        recyclerView.setAdapter(mHeaderWrapper);

        bannerAdapter = new BannerAdapter(getData(), getActivity());
        banner.setAdapter(bannerAdapter);
        indicatorLayout.setViewPager(banner);
        indicatorLayout.setPadding(5, 5, 10, 5);
        searchBar.setLeftText("深圳");
        searchBar.setSearchText("请输入车名搜索");

        //最新动态
        final List<String> texts = new ArrayList<>();
        texts.add("11 111111111111111");
        texts.add("22 2222222222222222");
        texts.add("33 3333333333333333");
        texts.add("44 44444444444444444444");
        ArrayList<String> list = new ArrayList<>();
        list.add("3-5万元汽车");
        list.add("8-10万元汽车");
        list.add("30-50万元汽车");
        adtTv1.setResources(list);
        adtTv1.setTextStillTime(5000, 1);


        adtTv2.setResources(texts);
        adtTv2.setTextStillTime(3000, 2);
        //两行汽车类型
        ArrayList<String> data = new ArrayList<>();
        data.add("5万元以下");
        data.add("5-10万元");
        data.add("10-15万元");
        data.add("15万元以上");
        oneHLayout.setData(data);

        ArrayList<String> data1 = new ArrayList<>();
        data1.add("奔驰");
        data1.add("宝马");
        data1.add("奥迪");
        data1.add("大众");
        twoHLayout.setData(data1);

    }

    @Override
    protected void requestData() {
        HttpProxy.getHomeAdvertList(new HttpCallBack<List<AdvertModel>>() {
            @Override
            public void onSuccess(List<AdvertModel> responseData) {
                bannerAdapter = new BannerAdapter(responseData, getActivity());// TODO: 2018/4/20 need
                banner.setAdapter(bannerAdapter);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });

        HttpProxy.getCarList(new HttpCallBack<List<AdvertModel>>() {
            @Override
            public void onSuccess(List<AdvertModel> responseData) {
                for (int i = 0; i < responseData.size() && i < mCarImgArray.length; i++) {
                    AdvertModel model = responseData.get(i);
                    BitmapUtil.loadImgRes(getActivity(), mCarImgArray[i], model.getAd_url());
                }
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
        data.add(new AdvertModel(R.mipmap.adv_home, "http://img07.tooopen.com/images/20170316/tooopen_sy_201956178977.jpg"));
        data.add(new AdvertModel(R.mipmap.adv_home, "http://img.zcool.cn/community/010a1b554c01d1000001bf72a68b37.jpg@1280w_1l_2o_100sh.png"));
        return data;
    }

    @Override
    public void onSearchClick(SearchActionBar.ViewType type) {
        switch (type) {
            case LEFT_IMG:
                break;
            case CENTER_LAYOUT:
                break;
            case RIGHT_IMG:
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoScroll();
        adtTv1.onStartAuto(1);
        adtTv2.onStopAuto(2);
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopAutoScroll();
        adtTv1.onStopAuto(1);
        adtTv2.onStopAuto(2);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
