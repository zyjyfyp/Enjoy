package com.yunsen.enjoy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.yunsen.enjoy.R;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HomeFootView extends LinearLayout {
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
        inflate(context, R.layout.home_foot_view, this);
    }
}
