package com.yunsen.enjoy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MoreCarView extends RelativeLayout {

    private View carMoreLayout;

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
        inflate(context, R.layout.recycler_bottom_layout, this);
        ((TextView) findViewById(R.id.bottom_more_tv)).setText("更多车源期待中..");
        carMoreLayout = findViewById(R.id.car_more_layout);
    }

//    public void setMoreVisibility(int visibility) {
//        carMoreLayout.setVisibility(visibility);
//        if (View.VISIBLE == visibility) {
//            ViewGroup.LayoutParams params = this.getLayoutParams();
//            if (params == null) {
//                params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
//            }
//            params.height = 300;
//            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            this.setLayoutParams(params);
//        }
//    }


}
