package com.yunsen.enjoy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MoreCarView extends LinearLayout {
    public MoreCarView(Context context) {
        super(context);
        initView(context);
    }

    public MoreCarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MoreCarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        this.setOrientation(VERTICAL);
        inflate(context, R.layout.recycler_bottom_layout, this);
        ((TextView) findViewById(R.id.bottom_more_tv)).setText("更多车源期待中..");
    }
}
