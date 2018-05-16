package com.yunsen.enjoy.activity.goods.adapter;

import android.content.Context;
import android.view.View;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CheckedData;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class CheckItemAdapter extends CommonAdapter<CheckedData> {

    public CheckItemAdapter(Context context, int layoutId, List<CheckedData> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CheckedData checkedData, int position) {
        holder.setText(R.id.popup_select_tv, checkedData.getName());
        View view = holder.getView(R.id.popup_select_img);
        View textView = holder.getView(R.id.popup_select_tv);
        if (checkedData.isChecked()) {
            view.setVisibility(View.VISIBLE);
            textView.setSelected(true);
        } else {
            view.setVisibility(View.INVISIBLE);
            textView.setSelected(false);
        }
    }

    public void setSelected(int position) {
        int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            CheckedData data = mDatas.get(i);
            data.setChecked(i == position);
        }
        this.notifyDataSetChanged();
    }
}
