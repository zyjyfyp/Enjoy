package com.yunsen.enjoy.fragment;


import com.yunsen.enjoy.R;
import com.yunsen.enjoy.fragment.home.BannerAdapter;
import com.yunsen.enjoy.fragment.model.BannerData;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.widget.ADTextView;
import com.yunsen.enjoy.widget.SearchActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */
public class MainPagerFragment extends BaseFragment implements SearchActionBar.SearchClickListener {

    private AutoLoopViewPager banner;
    private CirclePageIndicator indicatorLayout;
    private BannerAdapter bannerAdapter;
    private SearchActionBar searchBar;
    private ADTextView adtTv1;
    private ADTextView adtTv2;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView() {
        searchBar = ((SearchActionBar) rootView.findViewById(R.id.search_bar));
        banner = (AutoLoopViewPager) rootView.findViewById(R.id.pager);
        indicatorLayout = ((CirclePageIndicator) rootView.findViewById(R.id.indicator));

        adtTv1 = (ADTextView) rootView.findViewById(R.id.adt_text1);
        adtTv2 = (ADTextView) rootView.findViewById(R.id.adt_text2);


    }

    @Override
    protected void initData() {
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
    }

    @Override
    protected void initListener() {
        searchBar.setSearchClick(this);
    }

    public List<BannerData> getData() {
        ArrayList<BannerData> data = new ArrayList<>();
        data.add(new BannerData("http://img.zcool.cn/community/018d4e554967920000019ae9df1533.jpg@900w_1l_2o_100sh.jpg"));
        data.add(new BannerData("http://pic71.nipic.com/file/20150610/13549908_104823135000_2.jpg"));
        data.add(new BannerData("http://img07.tooopen.com/images/20170316/tooopen_sy_201956178977.jpg"));
        data.add(new BannerData("http://img.zcool.cn/community/010a1b554c01d1000001bf72a68b37.jpg@1280w_1l_2o_100sh.png"));
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

}
