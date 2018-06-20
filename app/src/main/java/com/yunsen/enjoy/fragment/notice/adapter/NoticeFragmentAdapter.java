package com.yunsen.enjoy.fragment.notice.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/17.
 */

public class NoticeFragmentAdapter extends FragmentPagerAdapter {
    private List<Integer> mNum = null;
    private List<Fragment> mFragments;

    public NoticeFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
        mNum = new ArrayList<>(3);
        mNum.add(0);
        mNum.add(0);
        mNum.add(0);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Integer num = mNum.get(position);
        switch (position) {
            case 0:
                return "交流物流（" + num + ")";
            case 1:
                return "系统消息（" + num + ")";

            case 2:
                return "客服消息（" + num + ")";
            default:
                return "";
        }
    }
}
