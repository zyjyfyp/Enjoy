package com.yunsen.enjoy.fragment.buy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yunsen.enjoy.common.Constants;

import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */

public class FilterFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public FilterFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
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
        return Constants.BUY_CAR[position];
    }
}
