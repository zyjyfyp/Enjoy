package com.yunsen.enjoy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */

public class HorizontalLayout2 extends LinearLayout {
    private int mColumn = 4;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mData;

    public HorizontalLayout2(Context context) {
        super(context);
        initView(context);
    }


    public HorizontalLayout2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HorizontalLayout2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        mInflater = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    public void setData(List<String> data) {
        if (data != null) {
            this.mData = data;
            for (int i = 0; i < data.size(); i++) {
                this.addView(createChild(data.get(i)));
            }
        }
    }

    public void upData(List<String> data) {
        if (data != null && mData != null && data.size() == mData.size()) {
            this.mData.clear();
            this.mData.addAll(data);
            int size = this.getChildCount();
            for (int i = 0; i < size; i++) {
                View view = this.getChildAt(0);
                ImageView leftImg = view.findViewById(R.id.left_img);
                TextView rightTv = (TextView) view.findViewById(R.id.right_tv);
                Picasso.with(mContext).load("https://gss3.bdstatic.com/7Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike150%2C5%2C5%2C150%2C50/sign=ecb81c924036acaf4ded9eae1db0e675/9825bc315c6034a87988e72ac013495409237636.jpg").placeholder(R.mipmap.car_1).into(leftImg);
                rightTv.setText(data.get(i));
            }
        }
    }


    private LinearLayout createChild(String data) {
        LinearLayout layout = new LinearLayout(mContext);
        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        layout.setLayoutParams(lp);
        View view = mInflater.inflate(R.layout.img_and_text_layout, layout, false);
        ImageView leftImg = view.findViewById(R.id.left_img);
        TextView rightTv = (TextView) view.findViewById(R.id.right_tv);
        Picasso.with(mContext).load("http://www.iconpng.com/download/png/95817").placeholder(R.mipmap.car_1).into(leftImg);
        rightTv.setText(data);
        layout.addView(view);
        return layout;
    }


}
