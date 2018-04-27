package com.yunsen.enjoy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yunsen.enjoy.R;

/**
 * Created by Administrator on 2018/4/27.
 */

public class BuyCarStepLayout extends LinearLayout {
    private Context mContext;

    private View oneNum;
    private View twoNum;
    private View threeNum;
    private View fourNum;

    private View oneTv;
    private View twoTv;
    private View threeTv;
    private View fourTv;

    private View fourImg;
    private View threeImg;
    private View twoImg;

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
        View view = inflater.inflate(R.layout.buy_car_step_layout, this);
        oneNum = view.findViewById(R.id.buy_first_num);
        twoNum = view.findViewById(R.id.buy_two_num);
        threeNum = view.findViewById(R.id.buy_three_num);
        fourNum = view.findViewById(R.id.buy_four_num);

        oneTv = view.findViewById(R.id.buy_first_tv);
        twoTv = view.findViewById(R.id.buy_two_tv);
        threeTv = view.findViewById(R.id.buy_three_tv);
        fourTv = view.findViewById(R.id.buy_four_tv);

        fourImg = view.findViewById(R.id.right_point_4);
        threeImg = view.findViewById(R.id.right_point_3);
        twoImg = view.findViewById(R.id.right_point_2);

    }

    public void setOneStep() {
        oneNum.setSelected(true);
        oneTv.setSelected(true);
    }

    public void setTwoStep() {
        twoImg.setSelected(true);
        twoNum.setSelected(true);
        twoTv.setSelected(true);
    }

    public void setThreeStep() {
        threeNum.setSelected(true);
        threeImg.setSelected(true);
        threeTv.setSelected(true);
    }

    public void setFourStep() {
        fourImg.setSelected(true);
        fourNum.setSelected(true);
        fourTv.setSelected(true);
    }
}
