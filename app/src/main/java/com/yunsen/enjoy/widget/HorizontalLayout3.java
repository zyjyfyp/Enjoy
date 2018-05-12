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

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.ImgAndTextModel;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */

public class HorizontalLayout3 extends LinearLayout {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<ImgAndTextModel> mData;
    private onHorizontalItemClick mListener;

    public HorizontalLayout3(Context context) {
        super(context);
        initView(context);
    }


    public HorizontalLayout3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HorizontalLayout3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        mInflater = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    public void setData(List<ImgAndTextModel> data) {
        if (data != null) {
            this.mData = data;
            for (int i = 0; i < data.size(); i++) {
                this.addView(createChild(data.get(i)));
            }
        }
    }

    public void upData(List<ImgAndTextModel> data) {
        if (data != null && mData != null && data.size() == mData.size()) {
            this.mData.clear();
            this.mData.addAll(data);
            int size = this.getChildCount();
            for (int i = 0; i < size; i++) {
                View view = this.getChildAt(0);
                ImageView topImg = view.findViewById(R.id.top_img);
                TextView bottomTv = (TextView) view.findViewById(R.id.bottom_tv);
                topImg.setImageResource(mData.get(i).getResId());
                bottomTv.setText(mData.get(i).getText());
            }
        }
    }


    private LinearLayout createChild( ImgAndTextModel data) {
        final ImgAndTextModel fData = data;
        LinearLayout layout = new LinearLayout(mContext);
        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        layout.setLayoutParams(lp);
        View view = mInflater.inflate(R.layout.img_text_layout, layout, false);
        ImageView topImg = view.findViewById(R.id.top_img);
        TextView bottomTv = (TextView) view.findViewById(R.id.bottom_tv);
        topImg.setImageResource(fData.getResId());
        bottomTv.setText(fData.getText());
        layout.addView(view);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(fData.getId());
                }
            }
        });
        return layout;
    }

    public void setmListener(onHorizontalItemClick listener) {
        this.mListener = listener;
    }

    public interface onHorizontalItemClick {
        public void onItemClick(int index);
    }

}
