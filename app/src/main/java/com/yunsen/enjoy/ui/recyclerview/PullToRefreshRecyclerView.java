package com.yunsen.enjoy.ui.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/5/26.
 */

public class PullToRefreshRecyclerView extends RecyclerView {
    private boolean mIsLoading = false;

    public PullToRefreshRecyclerView(Context context) {
        super(context);
        initView(context);
    }


    public PullToRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PullToRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {

    }


    public void onStartLoad() {
        mIsLoading = true;
    }

    public void onRefreshComplete() {
        mIsLoading = false;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }
}
