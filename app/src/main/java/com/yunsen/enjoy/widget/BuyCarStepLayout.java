package com.yunsen.enjoy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.yunsen.enjoy.R;

/**
 * Created by Administrator on 2018/4/27.
 */

public class BuyCarStepLayout extends LinearLayout {
    private Context mContext;

    public BuyCarStepLayout(Context context) {
        super(context);
        initView(context);
    }

    public BuyCarStepLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BuyCarStepLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.buy_car_step_layout,this);
    }
}
