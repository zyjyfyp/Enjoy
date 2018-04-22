package com.yunsen.enjoy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2018/4/23.
 */

public class BaseScrollView extends ScrollView {
    private float mDownPosX;
    private float mDownPosY;

    public BaseScrollView(Context context) {
        super(context);
        intiView();
    }

    public BaseScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intiView();
    }

    public BaseScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiView();
    }

    private void intiView() {

    }
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        final float x = ev.getX();
//        final float y = ev.getY();
//
//        final int action = ev.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mDownPosX = x;
//                mDownPosY = y;
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//                final float deltaX = Math.abs(x - mDownPosX);
//                final float deltaY = Math.abs(y - mDownPosY);
//                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
//                if (deltaX > deltaY) {
//                    Log.e(TAG, "onInterceptTouchEvent: false" );
//                    return false;
//                }
//        }
//        return super.onInterceptTouchEvent(ev);
//    }

    private static final String TAG = "BaseScrollView";
}
