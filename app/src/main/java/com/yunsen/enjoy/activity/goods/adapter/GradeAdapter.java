package com.yunsen.enjoy.activity.goods.adapter;

import android.content.Context;
import android.view.View;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.GradeFlagModel;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class GradeAdapter extends CommonAdapter<GradeFlagModel> {

    public GradeAdapter(Context context, int layoutId, List<GradeFlagModel> datas) {
        super(context, layoutId, datas);
    }


    @Override
    protected void convert(ViewHolder holder, GradeFlagModel data, int position) {
        holder.setText(R.id.grade_tv, data.getText());
        View view = holder.getView(R.id.grade_tv);
        if (data.isHasCheck()) {
            view.setSelected(true);
        } else {
            view.setSelected(false);
        }
    }

    public void setSelect(int position) {
        int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            mDatas.get(i).setHasCheck(i == position);
        }
        this.notifyDataSetChanged();
    }

    public void clearState() {
        int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            mDatas.get(i).setHasCheck(false);
        }
    }
}
