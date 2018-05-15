package com.yunsen.enjoy.ui.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;

/**
 * Created by Administrator on 2018/5/15.
 */

public class LoadMoreLayout extends RelativeLayout {

    private View rootView;
    private View loadMoreing;
    private View loadMoreStart;
    private View noMoreLayout;

    public LoadMoreLayout(Context context) {
        super(context);
        initView(context);
    }


    public LoadMoreLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadMoreLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.recycler_loding, this);
        loadMoreing = rootView.findViewById(R.id.load_more_ing_layout);
        loadMoreStart = rootView.findViewById(R.id.load_more_start_layout);
        noMoreLayout = rootView.findViewById(R.id.no_more_layout);
    }

    public void showLoading() {
        loadMoreing.setVisibility(VISIBLE);
        loadMoreStart.setVisibility(GONE);
        noMoreLayout.setVisibility(GONE);
    }

    public void showloadingStart() {
        loadMoreing.setVisibility(GONE);
        loadMoreStart.setVisibility(VISIBLE);
        noMoreLayout.setVisibility(GONE);
    }

    public void showLoadNoMore() {
        loadMoreing.setVisibility(GONE);
        loadMoreStart.setVisibility(GONE);
        noMoreLayout.setVisibility(VISIBLE);
    }

    public void closeAll() {
        loadMoreing.setVisibility(GONE);
        loadMoreStart.setVisibility(GONE);
        noMoreLayout.setVisibility(GONE);
    }
}
