package com.yunsen.enjoy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;

/**
 * Created by Administrator on 2018/4/28.
 */

public class FilterHorLayout extends HorizontalScrollView implements View.OnClickListener {
    private Context mContext;
    private LinearLayout rootLayout;
    private LayoutInflater mInflater;
    private View clearFiter;
    private OnFilterResetListener mOnResetListener;
    private OnItemCloseListener mOnItemCloseListener;

    public FilterHorLayout(Context context) {
        super(context);
        initView(context);
    }

    public FilterHorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FilterHorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.filter_layout, this);
        rootLayout = ((LinearLayout) view.findViewById(R.id.root_layout));
        clearFiter = view.findViewById(R.id.filter_reset_tv);
        clearFiter.setOnClickListener(this);
    }

    public void addItemView(String title, int id) {
        View view = mInflater.inflate(R.layout.filter_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 0, 20, 0);
        view.setLayoutParams(lp);
        TextView textView = (TextView) view.findViewById(R.id.filter_tv);
        View imgView = view.findViewById(R.id.close_img);
        textView.setText(title);
        imgView.setTag(id);
        imgView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewParent parent = v.getParent();
                if (parent instanceof LinearLayout) {
                    rootLayout.removeView(((LinearLayout) parent));
                    if (mOnItemCloseListener != null) {
                        mOnItemCloseListener.onItemClose((Integer) v.getTag());
                    }
                }
            }
        });
        rootLayout.addView(view, 0);
    }

    public void removeItemView(int id) {
        View view = rootLayout.findViewWithTag(id);
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof LinearLayout) {
                rootLayout.removeView((LinearLayout) parent);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int childCount = rootLayout.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            rootLayout.removeViewAt(0);
        }
        mOnResetListener.onFilterReset();
    }

    public void setOnResetListener(OnFilterResetListener listener) {
        this.mOnResetListener = listener;
    }

    public void setOnItemColseListener(OnItemCloseListener listener) {
        this.mOnItemCloseListener = listener;
    }

    public interface OnFilterResetListener {
        void onFilterReset();
    }

    public interface OnItemCloseListener {
        void onItemClose(int id);
    }

}
