package com.yunsen.enjoy.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yunsen.enjoy.model.ClassifyBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */

public class ListPagerAdapter extends FragmentStatePagerAdapter {
    private List<ClassifyBean> mTitles;
    private List<Fragment> mFragments;

    public ListPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<ClassifyBean> titles) {
        super(fm);
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        if (position >= 0 && position < mTitles.size()) {
            ClassifyBean classifyBean = mTitles.get(position);
            title = classifyBean.getTitle();
        }
        return title;
    }
}
