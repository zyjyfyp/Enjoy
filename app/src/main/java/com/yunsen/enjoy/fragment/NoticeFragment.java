package com.yunsen.enjoy.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.notice.DefaultNoticeFragment;
import com.yunsen.enjoy.fragment.notice.adapter.NoticeFragmentAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/17.
 */

public class NoticeFragment extends BaseFragment {
    private static final String TAG = "BuyFragment";
    @Bind(R.id.notice_tab)
    TabLayout noticeTab;
    @Bind(R.id.notice_view_pager)
    ViewPager noticeViewPager;
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    private DefaultNoticeFragment mDefaultFragment1;
    private DefaultNoticeFragment mDefaultFragment2;
    private DefaultNoticeFragment mDefaultFragment3;
    private NoticeFragmentAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notice;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        actionBack.setVisibility(View.GONE);
        actionBarTitle.setGravity(Gravity.CENTER);
        actionBarTitle.setText("消息");

        ArrayList<Fragment> fragments = new ArrayList<>();
        mDefaultFragment1 = new DefaultNoticeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.FRAGMENT_TYPE_KEY, 1);
        mDefaultFragment1.setArguments(bundle);
        fragments.add(mDefaultFragment1);

        mDefaultFragment2 = new DefaultNoticeFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt(Constants.FRAGMENT_TYPE_KEY, 2);
        mDefaultFragment2.setArguments(bundle2);
        fragments.add(mDefaultFragment2);

        mDefaultFragment3 = new DefaultNoticeFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt(Constants.FRAGMENT_TYPE_KEY, 3);
        mDefaultFragment3.setArguments(bundle3);
        fragments.add(mDefaultFragment3);
        mAdapter = new NoticeFragmentAdapter(getChildFragmentManager(), fragments);
        noticeTab.setupWithViewPager(noticeViewPager);
        noticeViewPager.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void initListener() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
