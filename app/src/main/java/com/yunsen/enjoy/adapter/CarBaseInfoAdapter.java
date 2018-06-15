package com.yunsen.enjoy.adapter;

import android.content.Context;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarBaseInfo;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/15.
 */

public class CarBaseInfoAdapter extends CommonAdapter<CarBaseInfo> {
    public CarBaseInfoAdapter(Context context, int layoutId, List<CarBaseInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CarBaseInfo carBaseInfo, int position) {
        holder.setText(R.id.car_base_info_title, carBaseInfo.getTitle());
        holder.setText(R.id.car_base_info_content, carBaseInfo.getContent());
    }
}
