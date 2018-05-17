package com.yunsen.enjoy.activity.goods.adapter;

import android.content.Context;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class GradeAdapter extends CommonAdapter<String> {
    public GradeAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.grade_tv, s);
    }
}
