package com.yunsen.enjoy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;


/**
 * Created by yunsenA on 2018/4/18.
 */

public class SearchActionBar extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private TextView leftTv;
    private ImageView leftImg;
    private LinearLayout searchLayout;
    private TextView rightNumTv;
    private ImageView rightImg;
    private TextView searchTv;
    private SearchClickListener mListener;

    public SearchActionBar(Context context) {
        super(context);
        initView(context);
    }

    public SearchActionBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SearchActionBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.search_bar_layout, this);
        leftTv = view.findViewById(R.id.left_tv);
        leftImg = view.findViewById(R.id.left_img);
        searchLayout = view.findViewById(R.id.search_layout);
        rightNumTv = view.findViewById(R.id.right_num_tv);
        rightImg = ((ImageView) view.findViewById(R.id.right_img));
        searchTv = ((TextView) view.findViewById(R.id.search_tv));
    }

    public void setLeftText(String text) {
        leftTv.setText(text);
    }

    public String getLeftText() {
        return leftTv.getText().toString();
    }
    /**
     * 右边小红点的文字
     *
     * @param text
     */

    public void setRightNumText(String text) {
        rightNumTv.setVisibility(VISIBLE);
        rightNumTv.setText(text);
    }

    public void setRightNumTextGone() {
        rightNumTv.setVisibility(GONE);
    }

    public void setSearchText(String text) {
        searchTv.setText(text);
    }

    public void setSearchClick(SearchClickListener listener) {
        this.mListener = listener;
        leftImg.setOnClickListener(this);
        rightImg.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        leftTv.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.left_img:
            case R.id.left_tv:
                mListener.onSearchClick(ViewType.LEFT_IMG);
                break;
            case R.id.search_layout:
                mListener.onSearchClick(ViewType.CENTER_LAYOUT);
                break;
            case R.id.right_img:
                mListener.onSearchClick(ViewType.RIGHT_IMG);
                break;
        }
    }


    public interface SearchClickListener {
        public void onSearchClick(ViewType type);
    }

    //    LEFT_TEXT,
    public enum ViewType {
        LEFT_IMG, CENTER_LAYOUT, RIGHT_IMG
    }

}
