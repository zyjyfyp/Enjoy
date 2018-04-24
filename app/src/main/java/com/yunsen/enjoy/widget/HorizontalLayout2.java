package com.yunsen.enjoy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.fragment.MainPagerFragment;
import com.yunsen.enjoy.model.CarModel;
import com.yunsen.enjoy.ui.UIHelper;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */

public class HorizontalLayout2 extends LinearLayout implements View.OnClickListener {
    private int mColumn = 4;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<CarModel> mData;
    private OnItemListener mListener;

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

    public void setData(List<CarModel> data) {
        if (data != null) {
            this.mData = data;
            for (int i = 0; i < data.size(); i++) {
                this.addView(createChild(data.get(i), i));
            }
        }
    }

    public void upData(List<CarModel> data) {
        if (data != null && mData != null && data.size() == mData.size()) {
            this.mData.clear();
            this.mData.addAll(data);
            int size = this.getChildCount();
            for (int i = 0; i < size; i++) {
                View view = this.getChildAt(i);
                ImageView leftImg = view.findViewById(R.id.left_img);
                TextView rightTv = (TextView) view.findViewById(R.id.right_tv);
                Log.e(TAG, "upData: " + data.get(i).getImg_url());
                Picasso.with(mContext).load(data.get(i).getImg_url()).placeholder(R.mipmap.car_1).into(leftImg);
                rightTv.setText(data.get(i).getTitle());
            }
        }
    }


    private LinearLayout createChild(CarModel data, int index) {
        LinearLayout layout = new LinearLayout(mContext);
        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        layout.setLayoutParams(lp);
        View view = mInflater.inflate(R.layout.img_and_text_layout, layout, false);
        ImageView leftImg = view.findViewById(R.id.left_img);
        TextView rightTv = (TextView) view.findViewById(R.id.right_tv);
        Picasso.with(mContext).load(data.getImg_url()).placeholder(R.mipmap.car_1).into(leftImg);
        rightTv.setText(data.getTitle());
        layout.addView(view);
        layout.setOnClickListener(this);
        layout.setTag(index);
        return layout;
    }

    private static final String TAG = "HorizontalLayout2";

    @Override
    public void onClick(View v) {
        int tagId = (int) v.getTag();
        if (v.getTag() != null) {
            CarModel carModel = mData.get(tagId);
            Log.e(TAG, "onClick: " + carModel.getTitle());
            UIHelper.showMoveActivity(mContext);
        }
    }

    public void setOnItemListener(OnItemListener listener) {
        this.mListener = listener;
    }

    public interface OnItemListener {
        public void onItem2Click(String data);
    }
}
