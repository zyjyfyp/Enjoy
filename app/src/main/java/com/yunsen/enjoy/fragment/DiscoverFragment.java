package com.yunsen.enjoy.fragment;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.adapter.DiscoverBannerAdapter;
import com.yunsen.enjoy.fragment.discover.GoodsAdapter;
import com.yunsen.enjoy.fragment.home.BannerAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.NoScrollLinearLayoutManager;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.widget.BaseScrollView;
import com.yunsen.enjoy.widget.LoadMoreView;
import com.yunsen.enjoy.widget.PullToRefreshView;
import com.yunsen.enjoy.widget.ZyViewPager;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/22.
 * 发现
 */

public class DiscoverFragment extends BaseFragment implements ViewPager.OnPageChangeListener,
        TabLayout.OnTabSelectedListener, MultiItemTypeAdapter.OnItemClickListener {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.layout_ent_gallery)
    RelativeLayout galleryLayout;
    @Bind(R.id.loop_pager)
    AutoLoopViewPager loopPager;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;
    @Bind(R.id.data_pager)
    ZyViewPager dataPager;
    @Bind(R.id.srcoll)
    BaseScrollView srcollView;
    @Bind(R.id.pull_to_refresh)
    PullToRefreshView pullToResh;

    private BannerAdapter bannerAdapter;
    private ListPagerAdapter mListPagerAdapter;

    private GoodsAdapter mAdapter1;
    private GoodsAdapter mAdapter2;
    private GoodsAdapter mAdapter3;
    private GoodsAdapter mAdapter4;

    private static final String ONE_ADAPTER = "one_adapter";
    private static final String TWO_ADAPTER = "two_adapter";
    private static final String THREE_ADAPTER = "three_adapter";
    private static final String FOUR_ADAPTER = "four_adapter";

    private static int mScrollHs[] = new int[]{0, 0, 0, 0};
    private List<RecyclerView> mRecyclers;
    private List mDataArray[] = new List[4];
    private int mScreenHeight;
    private int mPageIndexs[] = new int[]{1, 1, 1, 1};
    private boolean mHasMores[] = new boolean[]{true, true, true, true};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        dataPager.setParent(srcollView);
        tabLayout.addOnTabSelectedListener(this);
        indicator.setFocusable(true);
        indicator.setFocusableInTouchMode(true);
        indicator.requestFocus();
        pullToResh.setEnablePullTorefresh(false);
        ViewGroup.LayoutParams lp = galleryLayout.getLayoutParams();
        lp.height = (int) (DeviceUtil.getScreenWidth() * 0.49);
    }

    /**
     * 选中tabLayout加载
     *
     * @param tab
     * @param isSelect
     */
    private void updateTabTextView(TabLayout.Tab tab, boolean isSelect) {
        if (isSelect) {
            try {
                java.lang.reflect.Field fieldView = tab.getClass().getDeclaredField("mView");
                fieldView.setAccessible(true);
                View view = (View) fieldView.get(tab);
                java.lang.reflect.Field fieldTxt = view.getClass().getDeclaredField("mTextView");
                fieldTxt.setAccessible(true);
                TextView tabSelect = (TextView) fieldTxt.get(view);
                tabSelect.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tabSelect.setText(tab.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                java.lang.reflect.Field fieldView = tab.getClass().getDeclaredField("mView");
                fieldView.setAccessible(true);
                View view = (View) fieldView.get(tab);
                java.lang.reflect.Field fieldTxt = view.getClass().getDeclaredField("mTextView");
                fieldTxt.setAccessible(true);
                TextView tabSelect = (TextView) fieldTxt.get(view);
                tabSelect.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tabSelect.setText(tab.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void initData() {
        bannerAdapter = new BannerAdapter(getData(), getActivity());
        loopPager.setAdapter(bannerAdapter);
        indicator.setViewPager(loopPager);
        indicator.setPadding(5, 5, 10, 5);
        mScreenHeight = DeviceUtil.getHeight(getActivity());
        mListPagerAdapter = new ListPagerAdapter(getRecyclerView(), getActivity());
        dataPager.setAdapter(mListPagerAdapter);
        tabLayout.setupWithViewPager(dataPager);
        dataPager.setOffscreenPageLimit(4);
    }

    public List<AdvertModel> getData() {
        ArrayList<AdvertModel> data = new ArrayList<>();
        data.add(new AdvertModel(R.mipmap.adv_home, ""));
        return data;
    }

    private static final String TAG = "DiscoverFragment";

    @Override
    protected void requestData() {
        requestBanner();
        requestOne();
        requestTwo();
        requestThree();
        requestFour();
    }

    private void requestBanner() {
//        HttpProxy.getDiscoverBannerList(new HttpCallBack<List<GoodsData>>() {
//            @Override
//            public void onSuccess(List<GoodsData> responseData) {
//                if (responseData != null && responseData.size() > 0) {
//                    bannerAdapter = new DiscoverBannerAdapter(responseData, getActivity());
//                    loopPager.setAdapter(bannerAdapter);
//                }
//            }
//
//            @Override
//            public void onError(Request request, Exception e) {
//
//            }
//        });

        HttpProxy.getHomeAdvertList(1017, new HttpCallBack<List<AdvertModel>>() {
            @Override
            public void onSuccess(List<AdvertModel> responseData) {
                if (responseData != null && responseData.size() > 0) {
                    bannerAdapter = new BannerAdapter(responseData, getActivity());// TODO: 2018/4/20 need upData方法
                    loopPager.setAdapter(bannerAdapter);
                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }


    @Override
    protected void initListener() {
        dataPager.setOnPageChangeListener(this);
        mAdapter1.setOnItemClickListener(this);
        mAdapter2.setOnItemClickListener(this);
        mAdapter3.setOnItemClickListener(this);
        mAdapter4.setOnItemClickListener(this);
        pullToResh.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                final int index = dataPager.getCurrentItem();
                mPageIndexs[index]++;
                pullToResh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (index) {
                            case 0:
                                requestOne();
                                break;
                            case 1:
                                requestTwo();
                                break;
                            case 2:
                                requestThree();
                                break;
                            case 3:
                                requestFour();
                                break;
                        }
                    }
                }, 500);

            }
        });
    }


    /**
     * 头条-3 / 导购-2778 / 用车-2750 / 百科-4065,
     */
    private void requestOne() {
        HttpProxy.getDiscoverDatas(String.valueOf(mPageIndexs[0]), new HttpCallBack<List<GoodsData>>() {
            @Override
            public void onSuccess(final List<GoodsData> responseData) {
                boolean hasMore = mAdapter1.addBaseDatas(responseData);
                if (!hasMore) {
                    RecyclerViewUtils.setFooterView(mRecyclers.get(0), new LoadMoreView(getActivity()));
                }
                pullToResh.onFooterRefreshComplete();
            }

            @Override
            public void onError(Request request, Exception e) {
                RecyclerViewUtils.setFooterView(mRecyclers.get(3), new LoadMoreView(getActivity()));
                pullToResh.onFooterRefreshComplete();
            }
        }, "3");

    }

    /**
     * 导购
     */
    private void requestTwo() {
        HttpProxy.getDiscoverDatas(String.valueOf(mPageIndexs[1]), new HttpCallBack<List<GoodsData>>() {
            @Override
            public void onSuccess(List<GoodsData> responseData) {
                boolean hasMore = mAdapter2.addBaseDatas(responseData);
                if (!hasMore) {
                    RecyclerViewUtils.setFooterView(mRecyclers.get(1), new LoadMoreView(getActivity()));
                }
                pullToResh.onFooterRefreshComplete();
            }

            @Override
            public void onError(Request request, Exception e) {
                RecyclerViewUtils.setFooterView(mRecyclers.get(3), new LoadMoreView(getActivity()));
                pullToResh.onFooterRefreshComplete();
            }

        }, "2778");//2778

    }

    /**
     * 用车
     */
    private void requestThree() {
        HttpProxy.getDiscoverDatas(String.valueOf(mPageIndexs[2]), new HttpCallBack<List<GoodsData>>() {
            @Override
            public void onSuccess(List<GoodsData> responseData) {
                boolean hasMore = mAdapter3.addBaseDatas(responseData);
                if (!hasMore) {
                    RecyclerViewUtils.setFooterView(mRecyclers.get(2), new LoadMoreView(getActivity()));
                }
                pullToResh.onFooterRefreshComplete();
            }

            @Override
            public void onError(Request request, Exception e) {
                RecyclerViewUtils.setFooterView(mRecyclers.get(3), new LoadMoreView(getActivity()));
                pullToResh.onFooterRefreshComplete();
            }
        }, "2750");
    }

    /**
     * 百科
     */
    private void requestFour() {
        HttpProxy.getDiscoverDatas(String.valueOf(mPageIndexs[3]), new HttpCallBack<List<GoodsData>>() {
            @Override
            public void onSuccess(List<GoodsData> responseData) {
                boolean hasMore = mAdapter4.addBaseDatas(responseData);
                if (!hasMore) {
                    RecyclerViewUtils.setFooterView(mRecyclers.get(3), new LoadMoreView(getActivity()));
                }
                pullToResh.onFooterRefreshComplete();
            }

            @Override
            public void onError(Request request, Exception e) {
                RecyclerViewUtils.setFooterView(mRecyclers.get(3), new LoadMoreView(getActivity()));
                pullToResh.onFooterRefreshComplete();
            }
        }, "4065");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (tabLayout != null) {
            tabLayout.removeOnTabSelectedListener(this);
        }
        ButterKnife.unbind(this);
    }

    public List<RecyclerView> getRecyclerView() {
        mRecyclers = new ArrayList<>();

        RecyclerView recyclerView = new RecyclerView(getActivity());
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        NoScrollLinearLayoutManager layoutmanager = new NoScrollLinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        layoutmanager.setScrollEnabled(false);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutmanager);
        ArrayList<GoodsData> storeModes = new ArrayList<>();
        mDataArray[0] = storeModes;
        mAdapter1 = new GoodsAdapter(getActivity(), R.layout.goods_item, storeModes);
        mAdapter1.setmAdapterTag(ONE_ADAPTER);
        HeaderAndFooterRecyclerViewAdapter recyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter1);
        recyclerView.setAdapter(recyclerViewAdapter);
//        RecyclerViewUtils.setFooterView(recyclerView, new LoadMoreView(getActivity()));
        mRecyclers.add(recyclerView);


        RecyclerView recyclerView2 = new RecyclerView(getActivity());
        NoScrollLinearLayoutManager layoutmanager2 = new NoScrollLinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        layoutmanager2.setScrollEnabled(false);
        layoutmanager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(layoutmanager2);
        ArrayList<GoodsData> datas = new ArrayList<>();
        mDataArray[1] = datas;
        mAdapter2 = new GoodsAdapter(getActivity(), R.layout.goods_item, datas);
        mAdapter2.setmAdapterTag(TWO_ADAPTER);

        HeaderAndFooterRecyclerViewAdapter recyclerViewAdapter2 = new HeaderAndFooterRecyclerViewAdapter(mAdapter2);
        recyclerView2.setAdapter(recyclerViewAdapter2);

//        RecyclerViewUtils.setFooterView(recyclerView2, new LoadMoreView(getActivity()));
        mRecyclers.add(recyclerView2);


        RecyclerView recyclerView3 = new RecyclerView(getActivity());
        NoScrollLinearLayoutManager layoutmanager3 = new NoScrollLinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        layoutmanager3.setScrollEnabled(false);
        layoutmanager3.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView3.setLayoutManager(layoutmanager3);

        ArrayList<GoodsData> datas3 = new ArrayList<>();
        mDataArray[2] = datas3;
        mAdapter3 = new GoodsAdapter(getActivity(), R.layout.goods_item, datas3);
        mAdapter3.setmAdapterTag(THREE_ADAPTER);
        HeaderAndFooterRecyclerViewAdapter recyclerViewAdapter3 = new HeaderAndFooterRecyclerViewAdapter(mAdapter3);
        recyclerView3.setAdapter(recyclerViewAdapter3);
//        RecyclerViewUtils.setFooterView(recyclerView3, new LoadMoreView(getActivity()));
        mRecyclers.add(recyclerView3);


        RecyclerView recyclerView4 = new RecyclerView(getActivity());
        NoScrollLinearLayoutManager layoutmanager4 = new NoScrollLinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        layoutmanager4.setScrollEnabled(false);
        layoutmanager4.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView4.setLayoutManager(layoutmanager4);
        ArrayList<GoodsData> datas4 = new ArrayList<>();
        mDataArray[3] = datas4;
        mAdapter4 = new GoodsAdapter(getActivity(), R.layout.goods_item, datas4);
        mAdapter4.setmAdapterTag(FOUR_ADAPTER);
        HeaderAndFooterRecyclerViewAdapter recyclerViewAdapter4 = new HeaderAndFooterRecyclerViewAdapter(mAdapter4);
        recyclerView4.setAdapter(recyclerViewAdapter4);
//        RecyclerViewUtils.setFooterView(recyclerView4, new LoadMoreView(getActivity()));
        mRecyclers.add(recyclerView4);
        return mRecyclers;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        final int fPosition = position;
        srcollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollHs[fPosition] = mDataArray[fPosition].size() * getResources().getDimensionPixelSize(R.dimen.dpi_85);
                if (mScrollHs[fPosition] < mScreenHeight) {
                    mScrollHs[fPosition] = mScreenHeight;
                }
                dataPager.upViewPagerIndexHeight(fPosition);
            }
        });

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        updateTabTextView(tab, true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        updateTabTextView(tab, false);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        List<GoodsData> datas = null;
        if (adapter instanceof MultiItemTypeAdapter) {
            MultiItemTypeAdapter typeAdapter = (MultiItemTypeAdapter) adapter;
            switch ((typeAdapter.getmAdapterTag())) {
                case ONE_ADAPTER:
                    datas = mAdapter1.getDatas();
                    break;
                case TWO_ADAPTER:
                    datas = mAdapter2.getDatas();
                    break;
                case THREE_ADAPTER:
                    datas = mAdapter3.getDatas();
                    break;
                case FOUR_ADAPTER:
                    datas = mAdapter4.getDatas();
                    break;
            }
            if (datas != null && datas.size() > 0 && datas.size() > position) {
                GoodsData goodsData = datas.get(position);
                if (goodsData != null) {
                    UIHelper.showNoticeWebActivity(getActivity(), goodsData.getId());
                }
            }
        }

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }


}
