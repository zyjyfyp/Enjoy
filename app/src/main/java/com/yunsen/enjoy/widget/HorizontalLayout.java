package com.yunsen.enjoy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */

public class HorizontalLayout extends LinearLayout {
    private int mColumn = 4;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mData;

    public HorizontalLayout(Context context) {
        super(context);
        initView(context);
    }


    public HorizontalLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HorizontalLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
                TextView topTv = view.findViewById(R.id.top_tv);
                TextView bottomTv = (TextView) view.findViewById(R.id.bottom_tv);
                topTv.setText(mData.get(i));
                bottomTv.setText("修改的汽车名");
            }
        }
    }


    private LinearLayout createChild(String data) {
        LinearLayout layout = new LinearLayout(mContext);
        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        layout.setLayoutParams(lp);
        View view = mInflater.inflate(R.layout.double_text_layout, layout,false);
        TextView topTv = view.findViewById(R.id.top_tv);
        TextView bottomTv = (TextView) view.findViewById(R.id.bottom_tv);
        topTv.setText(data);
        bottomTv.setText("汽车名");
        layout.addView(view);
        return layout;
    }


}
