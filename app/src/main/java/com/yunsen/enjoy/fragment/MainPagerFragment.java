package com.yunsen.enjoy.fragment;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
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
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.MainActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.fragment.home.BannerAdapter;
import com.yunsen.enjoy.fragment.home.HomeGoodsAdapter;
import com.yunsen.enjoy.fragment.home.PreferenceCarAdapter;
import com.yunsen.enjoy.fragment.home.StoreRecyclerAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpCallBack2;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.model.CarBrand;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.CarModel;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.model.HomeCarModel;
import com.yunsen.enjoy.model.NoticeModel;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.model.event.ActivityResultEvent;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpCityEvent;
import com.yunsen.enjoy.model.event.UpHomeUiEvent;
import com.yunsen.enjoy.model.event.UpNoticeUi;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.layout.GoodsPartsLayout;
import com.yunsen.enjoy.ui.layout.IntegralChangeLayout;
import com.yunsen.enjoy.ui.layout.SecondActivityLayout;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.utils.SharedPreference;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.ADTextView;
import com.yunsen.enjoy.widget.HorizontalLayout;
import com.yunsen.enjoy.widget.HorizontalLayout2;
import com.yunsen.enjoy.widget.SearchActionBar;
import com.yunsen.enjoy.widget.ZyToastHelper;
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
public class MainPagerFragment extends BaseFragment implements SearchActionBar.SearchClickListener, View.OnClickListener, MultiItemTypeAdapter.OnItemClickListener {

    private AutoLoopViewPager banner;
    private CirclePageIndicator indicatorLayout;
    private BannerAdapter bannerAdapter;
    private SearchActionBar searchBar;
    private ADTextView adtTv1;
    private ADTextView adtTv2;
    private RecyclerView recyclerView;
    private View topView;
    private HomeGoodsAdapter mAdapter;

    private List<CarDetails> mAdverModels = new ArrayList<>();
    private int mPageIndex = 0;
    private RecyclerView bottomRecycler;


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

    }

    @Override
    protected void initData() {

        bannerAdapter = new BannerAdapter(getData(), getActivity());
        banner.setAdapter(bannerAdapter);
        indicatorLayout.setViewPager(banner);
        indicatorLayout.setPadding(5, 5, 10, 5);


        LinearLayoutManager layoutmanager = new LinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);

        ArrayList<CarDetails> storeModes = new ArrayList<>();
        mAdapter = new HomeGoodsAdapter(getActivity(), R.layout.home_goods_item, storeModes);
        HeaderAndFooterRecyclerViewAdapter recyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerView.setAdapter(recyclerViewAdapter);
        RecyclerViewUtils.setHeaderView(recyclerView, topView);
        String currentCity = SharedPreference.getInstance().getString(SpConstants.CITY_KEY, "深圳市");
        searchBar.setLeftText(currentCity);
        searchBar.setSearchText("请输入车名搜索");

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
                    indicatorLayout.setViewPager(banner);
                    indicatorLayout.setPadding(5, 5, 10, 5);
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
                adtTv1.setTextStillTime(5000, -1, 1);
                adtTv2.setResources(responseData);
                adtTv2.setTextStillTime(5000, 0, 2);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
        /**
         * 首页的商品
         */
        HttpProxy.getHomeGoods(new HttpCallBack<List<CarDetails>>() {
            @Override
            public void onSuccess(List<CarDetails> responseData) {
                mAdapter.upBaseDatas(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }
        });

    }

    @Override
    protected void initListener() {
        searchBar.setSearchClick(this);
        adtTv1.setOnClickListener(this);
        adtTv2.setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);

    }

    public List<AdvertModel> getData() {
        ArrayList<AdvertModel> data = new ArrayList<>();
        data.add(new AdvertModel(R.mipmap.adv_home, null));
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
            case R.id.adt_text1:
                NoticeModel data = adtTv1.getCurrentData();
                if (data != null) {
                    UIHelper.showHasTitleWebActivity(getActivity(), data);
                }
                break;
            case R.id.adt_text2:
                NoticeModel data2 = adtTv2.getCurrentData();
                if (data2 != null) {
                    UIHelper.showHasTitleWebActivity(getActivity(), data2);
                }
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoScroll();
        adtTv1.onStartAuto(1);
        adtTv2.onStartAuto(2);
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
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (adapter instanceof HomeGoodsAdapter) {
            List<CarDetails> datas = ((HomeGoodsAdapter) adapter).getDatas();
            int pos = position - 1;
            if (pos >= 0 && pos < datas.size()) {
                int id = datas.get(pos).getId();
                UIHelper.showGoodsDescriptionActivity(getActivity(), String.valueOf(id));
            }
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
        }
    }

}
