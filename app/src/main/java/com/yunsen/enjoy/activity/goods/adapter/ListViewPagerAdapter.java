package com.yunsen.enjoy.activity.goods.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/5/17.
 */

public class ListViewPagerAdapter extends PagerAdapter {
    public static String[] mTitls = new String[]{"请选择", "请选择1"};
    private List<RecyclerView> mViews;

    public ListViewPagerAdapter(List<RecyclerView> mViews) {
        this.mViews = mViews;
    }

    @Override
    public int getCount() {
        return mViews == null ? 0 : mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RecyclerView child = mViews.get(position);
        container.addView(child);
        return child;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.addView(mViews.get(position));
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mTitls[position];
//    }
}
