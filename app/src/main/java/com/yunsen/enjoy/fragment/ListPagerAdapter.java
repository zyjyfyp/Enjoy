package com.yunsen.enjoy.fragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yunsen.enjoy.common.Constants;

import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */

public class ListPagerAdapter extends PagerAdapter {
    private final Context mContext;
    private List<RecyclerView> mViews;

    public ListPagerAdapter(List<RecyclerView> views, Context context) {
        this.mViews = views;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        if (position >= 0 && position <= Constants.DISCOVER_TITLE.length) {
            title = Constants.DISCOVER_TITLE[position];
        }
        return title;
    }

    public RecyclerView getRecyclerView(int index) {
        if (index >= 0 && index < mViews.size()) {
            return mViews.get(index);
        }
        return null;
    }
}
