package com.yunsen.enjoy.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.MainActivity;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.fragment.home.BannerAdapter;
import com.yunsen.enjoy.fragment.home.StoreRecyclerAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.model.CarModel;
import com.yunsen.enjoy.model.NoticeModel;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.utils.SharedPreference;
import com.yunsen.enjoy.widget.ADTextView;
import com.yunsen.enjoy.widget.HomeFootView;
import com.yunsen.enjoy.widget.HorizontalLayout;
import com.yunsen.enjoy.widget.HorizontalLayout2;
import com.yunsen.enjoy.widget.SearchActionBar;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;
import com.yunsen.enjoy.widget.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;


/**
 * 首页
 */
public class MainPagerFragment extends BaseFragment implements SearchActionBar.SearchClickListener, View.OnClickListener, HorizontalLayout.onHorizontalItemClick, MultiItemTypeAdapter.OnItemClickListener {

    LinearLayout buttonLayout;
    LinearLayout newCarLayout;

    private ImageView[] mCarImgArray
            = new ImageView[6];
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
    private ImageView allCars;
    private View moreCar;
    private HomeFootView footView;


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

        allCars = topView.findViewById(R.id.button_layout);
        moreCar = topView.findViewById(R.id.more_tv);

        mCarImgArray[0] = topView.findViewById(R.id.car_img_big);
        mCarImgArray[1] = topView.findViewById(R.id.car_img_1);
        mCarImgArray[2] = topView.findViewById(R.id.car_img_2);
        mCarImgArray[3] = topView.findViewById(R.id.car_img_3);
        mCarImgArray[4] = topView.findViewById(R.id.car_img_4);
        mCarImgArray[5] = topView.findViewById(R.id.car_img_5);


    }

    @Override
    protected void initData() {
        Glide.with(getActivity())
                .load(R.mipmap.home_img)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(allCars, 1));
        LinearLayoutManager layoutmanager = new LinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);

        ArrayList<SProviderModel> storeModes = new ArrayList<>();
        storeModes.add(new SProviderModel(null, "上海大众汽车广东省深圳市宝安区4S店"));
        storeModes.add(new SProviderModel(null, "上海大众汽车广东省深圳市宝安区4S店上海大众汽车广东省深圳市宝安区4S店"));
        storeModes.add(new SProviderModel(null, "上海大众汽车广东省深圳市宝安区4S店"));
        storeModes.add(new SProviderModel(null, "上海大众汽车广东省深圳市宝安区4S店"));
        storeModes.add(new SProviderModel(null, "上海大众汽车广东省深圳市宝安区4S店"));
        storeModes.add(new SProviderModel(null, "上海大众汽车广东省深圳市宝安区4S店"));

        mAdapter = new StoreRecyclerAdapter(getActivity(), R.layout.shop_item, storeModes);
        HeaderAndFooterRecyclerViewAdapter recyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerView.setAdapter(recyclerViewAdapter);
        RecyclerViewUtils.setHeaderView(recyclerView, topView);
        footView = new HomeFootView(getActivity());
        RecyclerViewUtils.setFooterView(recyclerView, footView);

        bannerAdapter = new BannerAdapter(getData(), getActivity());
        banner.setAdapter(bannerAdapter);
        indicatorLayout.setViewPager(banner);
        indicatorLayout.setPadding(5, 5, 10, 5);
        searchBar.setLeftText("深圳");
        searchBar.setSearchText("请输入车名搜索");

        //最新动态


        //两行汽车类型
        ArrayList<String> data = new ArrayList<>();
        data.add("5万元以下");
        data.add("5-10万元");
        data.add("10-15万元");
        data.add("15万元以上");
        oneHLayout.setData(data);

        ArrayList<CarModel> data1 = new ArrayList<>();
        data1.add(new CarModel("奔驰", ""));
        data1.add(new CarModel("宝马", ""));
        data1.add(new CarModel("奥迪", ""));
        data1.add(new CarModel("大众", ""));
        twoHLayout.setData(data1);

    }

    private static final String TAG = "MainPagerFragment";

    @Override
    protected void requestData() {
        HttpProxy.getHomeAdvertList(13, new HttpCallBack<List<AdvertModel>>() {
            @Override
            public void onSuccess(List<AdvertModel> responseData) {
                bannerAdapter = new BannerAdapter(responseData, getActivity());// TODO: 2018/4/20 need upData方法
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
                    String ad_url = model.getAd_url();
                    Log.e(TAG, "onSuccess: " + ad_url);
                    Picasso.with(getActivity()).load(ad_url)
                            .placeholder(R.mipmap.car_1).into(mCarImgArray[i]);
                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
        //公告1
        HttpProxy.getNoticeData1(new HttpCallBack<List<NoticeModel>>() {
            @Override
            public void onSuccess(List<NoticeModel> responseData) {
                adtTv1.setResources(responseData);
                adtTv1.setTextStillTime(5000, 1);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
        //公告2
        HttpProxy.getNoticeData2(new HttpCallBack<List<NoticeModel>>() {
            @Override
            public void onSuccess(List<NoticeModel> responseData) {
                adtTv2.setResources(responseData);
                adtTv2.setTextStillTime(5000, 2);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
        /**
         * 推荐汽车
         */
        HttpProxy.getBrandData(new HttpCallBack<List<CarModel>>() {
            @Override
            public void onSuccess(List<CarModel> responseData) {
                twoHLayout.upData(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });


    }

    @Override
    protected void initListener() {
        searchBar.setSearchClick(this);
        allCars.setOnClickListener(this);
        moreCar.setOnClickListener(this);
        adtTv1.setOnClickListener(this);
        adtTv2.setOnClickListener(this);
        oneHLayout.setmListener(this);
        mCarImgArray[0].setOnClickListener(this);
        mCarImgArray[1].setOnClickListener(this);
        mCarImgArray[2].setOnClickListener(this);
        mCarImgArray[3].setOnClickListener(this);
        mCarImgArray[4].setOnClickListener(this);
        mCarImgArray[5].setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);
        footView.getLoadMoreBtn().setOnClickListener(this);
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
                UIHelper.showSelectCityActivity(getActivity());
                break;
            case CENTER_LAYOUT:
                UIHelper.showSearchActivity(getActivity());
                break;
            case RIGHT_IMG:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400****120"));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_layout:
            case R.id.more_tv:
                toBuyCarFragment();
                break;
            case R.id.adt_text1:
                UIHelper.showSearchActivity(getActivity());
                break;
            case R.id.adt_text2:
                UIHelper.showSearchActivity(getActivity());
                break;
            case R.id.car_img_big:
                UIHelper.showCarDetailsActivity(getActivity());
                break;
            case R.id.car_img_1:
                UIHelper.showCarDetailsActivity(getActivity());
                break;
            case R.id.car_img_2:
                UIHelper.showCarDetailsActivity(getActivity());
                break;
            case R.id.car_img_3:
                UIHelper.showCarDetailsActivity(getActivity());
                break;
            case R.id.car_img_4:
                UIHelper.showCarDetailsActivity(getActivity());
                break;
            case R.id.car_img_5:
                UIHelper.showCarDetailsActivity(getActivity());
                break;
            case R.id.load_more_btn:
                /**
                 * 服务商
                 */
                HttpProxy.getServiceProvider(new HttpCallBack<List<SProviderModel>>() {
                    @Override
                    public void onSuccess(List<SProviderModel> responseData) {

                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }
                });
                footView.changeState();
                break;
        }

    }

    private void toBuyCarFragment() {
        ((MainActivity) getActivity()).setCurrIndex(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoScroll();
        String currentCity = SharedPreference.getInstance().getString(SpConstants.CITY_KEY, "深圳");
        searchBar.setLeftText(currentCity);
//        adtTv1.onStartAuto(1);
//        adtTv2.onStopAuto(2);
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopAutoScroll();
        //        adtTv1.onStopAuto(1);
        //        adtTv2.onStopAuto(2);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onItemClick(String data) {
        toBuyCarFragment();
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        UIHelper.showCarDetailsActivity(getActivity());
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
