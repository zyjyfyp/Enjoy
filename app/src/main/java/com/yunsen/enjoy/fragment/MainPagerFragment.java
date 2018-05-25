package com.yunsen.enjoy.fragment;


import android.content.Context;
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
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.MainActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.fragment.home.BannerAdapter;
import com.yunsen.enjoy.fragment.home.StoreRecyclerAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpCallBack2;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.CarModel;
import com.yunsen.enjoy.model.DefaultSpecItemBean;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.model.HomeCarModel;
import com.yunsen.enjoy.model.NoticeModel;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpCityEvent;
import com.yunsen.enjoy.model.event.UpHomeUiEvent;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.layout.GoodsPartsLayout;
import com.yunsen.enjoy.ui.layout.IntegralChangeLayout;
import com.yunsen.enjoy.ui.layout.SecondActivityLayout;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private StoreRecyclerAdapter mAdapter;
    private ImageView allCars;
    private View moreCar;
    //    private HomeFootView footView;
    private List<CarDetails> mAdverModels = new ArrayList<>();
    private int mPageIndex = 0;
    private IntegralChangeLayout integralContral;
    private SecondActivityLayout secondActivity;
    private GoodsPartsLayout goodsPartsLayout;
    private View serviceMoreTv;


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

        integralContral = (IntegralChangeLayout) topView.findViewById(R.id.integral_layout);
        secondActivity = (SecondActivityLayout) topView.findViewById(R.id.home_activity_layout);
        goodsPartsLayout = ((GoodsPartsLayout) topView.findViewById(R.id.goods_parts_layout));
        serviceMoreTv = topView.findViewById(R.id.service_more_tv);

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
        mAdapter = new StoreRecyclerAdapter(getActivity(), R.layout.shop_item, storeModes);
        HeaderAndFooterRecyclerViewAdapter recyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerView.setAdapter(recyclerViewAdapter);
        RecyclerViewUtils.setHeaderView(recyclerView, topView);
//        footView = new HomeFootView(getActivity());
//        RecyclerViewUtils.setFooterView(recyclerView, footView);

        bannerAdapter = new BannerAdapter(getData(), getActivity());
        banner.setAdapter(bannerAdapter);
        indicatorLayout.setViewPager(banner);
        indicatorLayout.setPadding(5, 5, 10, 5);
        String currentCity = SharedPreference.getInstance().getString(SpConstants.CITY_KEY, "深圳市");
        searchBar.setLeftText(currentCity);
        searchBar.setSearchText("请输入车名搜索");

        //最新动态


        //两行汽车类型
        ArrayList<HomeCarModel> data = new ArrayList<>();
        data.add(new HomeCarModel("5万元以下", "SUV"));
        data.add(new HomeCarModel("5-10万元", "保买车"));
        data.add(new HomeCarModel("10-15万元", "准新车"));
        data.add(new HomeCarModel("15万元以上", "急售"));
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
        HttpProxy.getHomeAdvertList(11, new HttpCallBack<List<AdvertModel>>() {
            @Override
            public void onSuccess(List<AdvertModel> responseData) {
                if (responseData != null && responseData.size() > 0) {
                    bannerAdapter = new BannerAdapter(responseData, getActivity());// TODO: 2018/4/20 need upData方法
                    banner.setAdapter(bannerAdapter);
                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
        /**
         * 乐享新车
         */
        HttpProxy.getCarList(new HttpCallBack<List<CarDetails>>() {
            @Override
            public void onSuccess(List<CarDetails> responseData) {
                Log.e(TAG, "onSuccess: 乐享新车 ");
                mAdverModels.clear();
                for (int i = 0; i < responseData.size() && i < mCarImgArray.length; i++) {
                    CarDetails model = responseData.get(i);
                    String ad_url = model.getImgh_url();
                    mAdverModels.add(model);
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
                Log.e(TAG, "onSuccess: 公告1");
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
                Log.e(TAG, "onSuccess: 公告2");
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
                Log.e(TAG, "onSuccess: 推荐汽车");
                twoHLayout.upData(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });

        requestServiceProvider(false);
        /**
         * 积分兑换
         */
        HttpProxy.getIntegralChangeData(new HttpCallBack<List<CarDetails>>() {
            @Override
            public void onSuccess(List<CarDetails> responseData) {
                Log.e(TAG, "onSuccess: 积分兑换");
                integralContral.setData(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
        /**
         * 秒杀活动
         */
        HttpProxy.getSecondActivityData(new HttpCallBack2<List<CarDetails>>() {

            @Override
            public void onSuccess(final List<CarDetails> responseData, Object otherData) {
                Log.e(TAG, "onSuccess: 秒杀活动");
                final String data = (String) otherData;
                secondActivity.post(new Runnable() {
                    @Override
                    public void run() {
                        secondActivity.setData(responseData, Long.parseLong(data));
//                        secondActivity.setData(responseData, Long.parseLong(data) );

                    }
                });
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });

        /**
         * 配件商品
         */
        HttpProxy.getGoodsPartsDatas(new HttpCallBack<List<CarDetails>>() {
            @Override
            public void onSuccess(List<CarDetails> responseData) {
                Log.e(TAG, "onSuccess: 配件商品");
                goodsPartsLayout.setData(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });

    }


    private void requestServiceProvider(boolean isLoadMore) {
        final boolean isMore = isLoadMore;
        if (isMore) {
            mPageIndex++;
        } else {
            mPageIndex = 1;
        }
        /**
         * 服务商
         */
        HttpProxy.getServiceProvider(mPageIndex, searchBar.getLeftText(), new HttpCallBack<List<SProviderModel>>() {
            @Override
            public void onSuccess(List<SProviderModel> responseData) {
                Log.e(TAG, "onSuccess: 服务商");
                if (isMore) {
                    if (!mAdapter.addDatas(responseData)) {
                        //      footView.changeState(true);
                    } else {
                        //      footView.changeState(false);
                    }
                } else {
                    mAdapter.upDatas(responseData);
                    //  footView.changeState(false);
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                //footView.changeState(true);
                if (!isMore) {
                    mAdapter.clearData();
                }
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
        //footView.getLoadMoreBtn().setOnClickListener(this);
        serviceMoreTv.setOnClickListener(this);
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
                ((BaseFragmentActivity) getActivity()).requestPermission(Permission.CALL_PHONE, Constants.CALL_PHONE);
                break;
        }
    }

    private String getAdverModelUrl(int index) {
        if (mAdverModels.size() > index) {
            return String.valueOf(mAdverModels.get(index).getId());
        }
        return null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_layout:
            case R.id.more_tv:
                toBuyCarFragment();
                break;
            case R.id.adt_text1:
                NoticeModel data = adtTv1.getCurrentData();
                if (data != null) {
                    UIHelper.showNoticeWebActivity(getActivity(), data.getId());
                }
                break;
            case R.id.adt_text2:
                NoticeModel data2 = adtTv2.getCurrentData();
                if (data2 != null) {
                    UIHelper.showNoticeWebActivity(getActivity(), data2.getId());
                }
                break;
            case R.id.car_img_big:
                if (mAdverModels != null && mAdverModels.size() > 0) {
                    UIHelper.showCarDetailsActivity(getActivity(), getAdverModelUrl(0));
                }
                break;
            case R.id.car_img_1:
                UIHelper.showCarDetailsActivity(getActivity(), getAdverModelUrl(1));
                break;
            case R.id.car_img_2:
                UIHelper.showCarDetailsActivity(getActivity(), getAdverModelUrl(2));
                break;
            case R.id.car_img_3:
                UIHelper.showCarDetailsActivity(getActivity(), getAdverModelUrl(3));
                break;
            case R.id.car_img_4:
                UIHelper.showCarDetailsActivity(getActivity(), getAdverModelUrl(4));
                break;
            case R.id.car_img_5:
                UIHelper.showCarDetailsActivity(getActivity(), getAdverModelUrl(5));
                break;
            case R.id.load_more_btn:
                requestServiceProvider(true);
                break;
            case R.id.service_more_tv:
                UIHelper.showServiceMoreActivity(getActivity());
                break;
        }

    }

    /**
     * 去买车界面
     */
    private void toBuyCarFragment() {
        ((MainActivity) getActivity()).setCurrIndex(1);
    }

    private void toBuyCarFragment(String data) {
        ((MainActivity) getActivity()).setCurrIndex(1);
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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onItemClick(String data) {
        toBuyCarFragment(data);
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        List<SProviderModel> datas = mAdapter.getDatas();
        if (datas != null && position > 0 && datas.size() > position - 1) {
            int id = datas.get(position - 1).getUser_id();
            Log.e(TAG, "onItemClick: " + id);
            UIHelper.showServiceShopInfoActivity(getActivity(), String.valueOf(id));
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(UpCityEvent event) {
        if (event.getEventId() == EventConstants.UP_CITY) {
            searchBar.setLeftText(event.getCity());
            requestServiceProvider(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent2(UpHomeUiEvent event) {
        if (event.getEventId() == EventConstants.HOME_UI_UP) {
            secondActivity.upTimeUi(event.getCurrentTime());
        }
    }

}
