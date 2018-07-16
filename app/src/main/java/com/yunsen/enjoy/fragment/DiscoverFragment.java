package com.yunsen.enjoy.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.adapter.DiscoverBannerAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.StaticVar;
import com.yunsen.enjoy.fragment.discover.SpreadFragment;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.model.ClassifyBean;
import com.yunsen.enjoy.model.LoadingData;
import com.yunsen.enjoy.model.event.DiscoverEvent;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpFilterReqEvent;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.BaseScrollView;
import com.yunsen.enjoy.widget.PullToRefreshView;
import com.yunsen.enjoy.widget.ZyViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/22.
 * 发现
 */

public class DiscoverFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.loop_pager)
    AutoLoopViewPager loopPager;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;
    @Bind(R.id.data_pager)
    ZyViewPager dataPager;
    @Bind(R.id.srcoll)
    BaseScrollView srcollView;
    @Bind(R.id.pull_to_refresh)
    PullToRefreshView refreshView;

    private DiscoverBannerAdapter bannerAdapter;
    private ListPagerAdapter mListPagerAdapter;

    private static int mScrollHs[] = new int[]{0, 0, 0, 0};
    private List mDataArray[] = new List[4];
    private int mScreenHeight;
    private int mPageIndexs[] = new int[]{1, 1, 1, 1};
    private boolean mHasMores[] = new boolean[]{true, true, true, true};
    private List<LoadingData> mLoadFlag = new ArrayList<>();
    private ArrayList<Fragment> mFragments;
    private ArrayList<ClassifyBean> mTitles;
    private int mCurrentPosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        dataPager.setParent(srcollView);
        indicator.setFocusable(true);
        indicator.setFocusableInTouchMode(true);
        indicator.requestFocus();
        refreshView.setEnablePullTorefresh(false);
    }


    @Override
    protected void initData() {
        bannerAdapter = new DiscoverBannerAdapter(getData(), getActivity());
        loopPager.setAdapter(bannerAdapter);
        indicator.setViewPager(loopPager);
        indicator.setPadding(5, 5, 10, 5);
        mScreenHeight = DeviceUtil.getHeight(getActivity());
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        ClassifyBean e = new ClassifyBean();
        e.setTitle("全部");
        e.setId(0);
        e.setIndex(0);
        mTitles.add(e);
        addFragment(e);
        mListPagerAdapter = new ListPagerAdapter(getChildFragmentManager(), mFragments, mTitles);
        dataPager.setAdapter(mListPagerAdapter);
        dataPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(dataPager);
    }

    public List<AdvertModel> getData() {
        ArrayList<AdvertModel> data = new ArrayList<>();
        data.add(new AdvertModel(R.mipmap.adv_home, null));
        return data;
    }

    private static final String TAG = "DiscoverFragment";

    @Override
    protected void requestData() {
        requestBanner();
        HttpProxy.getGoodsClassifyDatas("news", new HttpCallBack<List<ClassifyBean>>() {
            @Override
            public void onSuccess(List<ClassifyBean> responseData) {
                mLoadFlag.clear();
                upUi(responseData);
                int size = mTitles.size();
                for (int i = 0; i < size; i++) {
                    mLoadFlag.add(new LoadingData(i));
                }
                dataPager.setOffscreenPageLimit(size);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    /**
     * 更新fragment
     *
     * @param responseData
     */
    private void upUi(List<ClassifyBean> responseData) {
        int index = 1;
        if (responseData != null) {
            int size = responseData.size() - 1;
            while (size >= 0) {
                ClassifyBean bean = responseData.get(size);
                bean.setIndex(index);
                addFragment(bean);
                index++;
                size--;
            }
            mTitles.addAll(responseData);
            mListPagerAdapter.notifyDataSetChanged();
        }
    }

    private void addFragment(ClassifyBean bean) {
        SpreadFragment fragment = new SpreadFragment();
        mFragments.add(fragment);
        Bundle args = new Bundle();
        args.putString(Constants.CLASSIFY_ID, String.valueOf(bean.getId()));
        args.putString(Constants.CLASSIFY_PARENT_ID, String.valueOf(bean.getParent_id()));
        args.putString(Constants.CLASSIFY_CHANNEL_ID, String.valueOf(bean.getChannel_id()));
        args.putString(Constants.CLASSIFY_TITLE, bean.getTitle());
        args.putInt(Constants.FRAGMENT_TYPE_KEY, bean.getIndex());
        fragment.setArguments(args);
    }

    private void requestBanner() {
        HttpProxy.getDiscoverBannerList(new HttpCallBack<List<AdvertModel>>() {
            @Override
            public void onSuccess(List<AdvertModel> responseData) {
                if (responseData != null && responseData.size() > 0) {
                    bannerAdapter = new DiscoverBannerAdapter(responseData, getActivity());
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
        refreshView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                refreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.setEnablePullLoadMoreDataStatus(true);
                        EventBus.getDefault().post(new UpFilterReqEvent(EventConstants.SHOW_HAS_MORE, mCurrentPosition));
                    }
                }, 500);

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        dataPager.upViewPagerIndexHeight(position);
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DiscoverEvent event) {
        int position = event.getPosition();
        if (event.getEventId() == EventConstants.SHOW_HAS_MORE) {
            //动态改变ViewPager的高度
            dataPager.upViewPagerIndexHeight(position);
//            if (!event.isMore()) {
//                refreshView.setEnablePullLoadMoreDataStatus(true);
//            }
            refreshView.onFooterRefreshComplete();
//            } else {
//                refreshView.setEnablePullLoadMoreDataStatus(false);
//                refreshView.onFooterRefreshComplete();
//            }
        } else if (event.getEventId() == EventConstants.NO_MORE && mCurrentPosition == position) {
            ToastUtils.makeTextShort("没有更多数据");
            refreshView.onFooterRefreshComplete();
        }
    }

}
