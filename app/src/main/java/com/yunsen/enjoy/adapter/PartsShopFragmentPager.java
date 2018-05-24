package com.yunsen.enjoy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yunsen.enjoy.activity.buy.PartsShopFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class PartsShopFragmentPager extends FragmentPagerAdapter {
    private List<String> mListTitles;
    private List<PartsShopFragment> mFragments;

    public PartsShopFragmentPager(FragmentManager fm, List<PartsShopFragment> fragments, List<String> listTitle) {
        super(fm);
        this.mFragments = fragments;
        this.mListTitles = listTitle;
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
        return mListTitles.get(position);
    }
}
