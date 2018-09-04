package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.DefaultNoticeFragment;
import com.yunsen.enjoy.activity.mine.adapter.NoticeFragmentAdapter;
import com.yunsen.enjoy.common.Constants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/28/028.
 */

public class NoticeActivity extends BaseFragmentActivity {
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
    public int getLayout() {
        return R.layout.activity_notice;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("消息");

        ArrayList<Fragment> fragments = new ArrayList<>();
        mDefaultFragment1 = new DefaultNoticeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.FRAGMENT_TYPE_KEY, Constants.TRANSACTION_MESSAGE);
        mDefaultFragment1.setArguments(bundle);
        fragments.add(mDefaultFragment1);

        mDefaultFragment2 = new DefaultNoticeFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt(Constants.FRAGMENT_TYPE_KEY, Constants.SYSTEM_MESSAGE);
        mDefaultFragment2.setArguments(bundle2);
        fragments.add(mDefaultFragment2);

        mDefaultFragment3 = new DefaultNoticeFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt(Constants.FRAGMENT_TYPE_KEY, Constants.SERVICE_MESSAGE);
        mDefaultFragment3.setArguments(bundle3);
        fragments.add(mDefaultFragment3);
        mAdapter = new NoticeFragmentAdapter(getSupportFragmentManager(), fragments);
        noticeTab.setupWithViewPager(noticeViewPager);
        noticeViewPager.setAdapter(mAdapter);
        noticeViewPager.setOffscreenPageLimit(3);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    public void setMessageNumber(int mFragmentType, int messageSize) {
        switch (mFragmentType) {
            case Constants.SYSTEM_MESSAGE:
                TabLayout.Tab sysTab = noticeTab.getTabAt(1);
                sysTab.setText("系统消息（" + messageSize + "）");
                break;
            case Constants.SERVICE_MESSAGE:
                TabLayout.Tab serviceTab = noticeTab.getTabAt(2);
                serviceTab.setText("客服消息（" + messageSize + "）");
                break;
            case Constants.TRANSACTION_MESSAGE:
                TabLayout.Tab transTab = noticeTab.getTabAt(0);
                transTab.setText("交易物流（" + messageSize + "）");
                break;
        }
    }
}
