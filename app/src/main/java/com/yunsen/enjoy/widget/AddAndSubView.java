package com.yunsen.enjoy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.interfaces.OnRightOnclickListener;

/**
 * Created by Administrator on 2018/5/18.
 */

public class AddAndSubView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private View addTv;
    private View subTv;
    private EditText numEdt;
    private int mMaxNum = 100;
    private OnRightOnclickListener rightOnclickListener;
    private int mMinNum = 1;

    public AddAndSubView(Context context) {
        super(context);
        initView(context);
    }

    public AddAndSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AddAndSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.add_and_sub_layout, this);
        addTv = rootView.findViewById(R.id.add_tv);
        subTv = rootView.findViewById(R.id.sub_tv);
        numEdt = rootView.findViewById(R.id.num_edt);
        addTv.setOnClickListener(this);
        subTv.setOnClickListener(this);
    }

    public void setMaxNum(int maxNum) {
        this.mMaxNum = maxNum;
    }

    @Override
    public void onClick(View v) {
        String numStr = numEdt.getText().toString();
        int num = 0;
        if (!TextUtils.isEmpty(numStr)) {
            num = Integer.parseInt(numStr);
        }
        switch (v.getId()) {
            case R.id.add_tv:
                if (num + 1 > mMaxNum) {
                    ToastUtils.makeTextShort("货物不足");
                } else {
                    numEdt.setText(String.valueOf((num + 1)));
                    if (rightOnclickListener != null) {
                        rightOnclickListener.onRightClick((num + 1));
                    }
                }
                break;
            case R.id.sub_tv:
                if (num <= mMinNum) {
                    ToastUtils.makeTextShort("不能减少了");
                } else {
                    numEdt.setText(String.valueOf((num - 1)));
                    if (rightOnclickListener != null) {
                        rightOnclickListener.onRightClick((num - 1));
                    }
                }
                break;
        }
    }

    public void setNum(int num) {
        numEdt.setText("" + num);
    }

    public int getmMinNum() {
        return mMinNum;
    }

    public void setmMinNum(int mMinNum) {
        this.mMinNum = mMinNum;
    }

    public void setRightOnclickListener(OnRightOnclickListener rightOnclickListener) {
        this.rightOnclickListener = rightOnclickListener;
    }
}
