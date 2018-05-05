package com.yunsen.enjoy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.ArrayVariables;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.CarDetailsActivity;

/**
 * Created by Administrator on 2018/5/5.
 */

public class NoticeView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater inflater;
    private ImageView mNoticeImg;
    private ProgressBar mNoticeLoading;
    private LinearLayout mNoticeLayout;
    private TextView mNoticeTv;
    private ImageView mNoticeNoInternet;
    private Type mType;
    private OnNoticeListener mOnNoticeListener;

    public NoticeView(@NonNull Context context) {
        this(context, null);
    }


    public NoticeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoticeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NoticeStyle);
        int type =  typedArray.getInt(R.styleable.NoticeStyle_notice_type, 0);
        typedArray.recycle();
        switch (type) {
            case 0:
                mType = Type.NO_INTERNET;
                break;
            case 1:
                mType = Type.LOADING;
                break;
            case 2:
                mType = Type.DATA_ERROR;
                break;
        }
        inflater = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        View view = inflater.inflate(R.layout.notice_view_layout, this);
        mNoticeLoading = ((ProgressBar) view.findViewById(R.id.notice_loading));

        mNoticeLayout = ((LinearLayout) view.findViewById(R.id.notice_layout));
        mNoticeTv = ((TextView) view.findViewById(R.id.notice_tv));
        mNoticeImg = (ImageView) view.findViewById(R.id.notice_img);
        mNoticeNoInternet = ((ImageView) view.findViewById(R.id.notice_no_internet));
    }

    public void setOnNoticeListener(OnNoticeListener onNoticeListener) {
        this.mOnNoticeListener = onNoticeListener;
        mNoticeNoInternet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.notice_no_internet) {
            if (mOnNoticeListener != null) {
                mOnNoticeListener.noticeClick(v);
            }
        }
    }


    public enum Type {NO_INTERNET, DATA_ERROR, LOADING}


    public void showNoticeType(Type type) {
        this.mType = type;
        mNoticeLayout.setVisibility(GONE);
        mNoticeLoading.setVisibility(GONE);
        mNoticeNoInternet.setVisibility(GONE);
        switch (type) {
            case NO_INTERNET:
                mNoticeNoInternet.setVisibility(VISIBLE);
                break;
            case LOADING:
                mNoticeLoading.setVisibility(VISIBLE);
                break;
            case DATA_ERROR:
                mNoticeLayout.setVisibility(VISIBLE);
                break;
        }
    }

    public void setErrorData(String data) {
        if (!TextUtils.isEmpty(data)) {
            mNoticeTv.setText(data);
        }
    }

    public void closeNoticeView() {
        switch (mType) {
            case NO_INTERNET:
                mNoticeNoInternet.setVisibility(GONE);
                break;
            case LOADING:
                mNoticeLoading.setVisibility(GONE);
                break;
            case DATA_ERROR:
                mNoticeLayout.setVisibility(GONE);
                break;
        }
    }

    public interface OnNoticeListener {
        public void noticeClick(View view);
    }

}
