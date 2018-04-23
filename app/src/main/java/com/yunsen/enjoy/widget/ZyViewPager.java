package com.yunsen.enjoy.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by Administrator on 2018/4/22.
 */

public class ZyViewPager extends ViewPager {

    /**
     * 触摸时按下的点
     **/
    PointF downP = new PointF();
    /**
     * 触摸时当前的点
     **/
    PointF curP = new PointF();

    /**
     * 自定义手势
     **/
    private GestureDetector mGestureDetector;
    private ViewGroup parent;

    public ZyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZyViewPager(Context context) {
        super(context);
    }

    public void setParent(ViewGroup parent) {
        this.parent = parent;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (parent != null) {
            Log.i("dispatch_touch_event", "---" + ev.getAction());
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (parent != null) {
            Log.i("onintercepte_touch_eve", "---" + ev.getAction());
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (parent != null) {
            Log.i("on_touch_event", "---" + ev.getAction());
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(ev);
    }

}
