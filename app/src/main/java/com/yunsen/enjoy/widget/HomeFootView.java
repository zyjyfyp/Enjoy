package com.yunsen.enjoy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.yunsen.enjoy.R;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomeFootView extends LinearLayout {

    private View noMoreTv;
    private View loadMoreBtn;

    public HomeFootView(Context context) {
        super(context);
        initView(context);
    }

    public HomeFootView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HomeFootView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        this.setOrientation(VERTICAL);
        View view = inflate(context, R.layout.home_foot_view, this);
        noMoreTv = view.findViewById(R.id.no_more_tv);
        loadMoreBtn = view.findViewById(R.id.load_more_btn);
    }

    public View getLoadMoreBtn() {
        return loadMoreBtn;
    }

    /**
     * 显示没有更多内容
     */
    public void changeState(boolean flag) {
        if (flag) {
            noMoreTv.setVisibility(VISIBLE);
            loadMoreBtn.setVisibility(GONE);
        } else {
            noMoreTv.setVisibility(GONE);
            loadMoreBtn.setVisibility(VISIBLE);
        }
    }
}
