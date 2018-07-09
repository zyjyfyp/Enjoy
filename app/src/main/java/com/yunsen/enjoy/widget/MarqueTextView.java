package com.yunsen.enjoy.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/7/9.
 */

public class MarqueTextView extends AppCompatTextView {

    public MarqueTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MarqueTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueTextView(Context context) {
        super(context);
    }

    @Override

    public boolean isFocused() {
        //就是把这里返回true即可
        return true;
    }
}