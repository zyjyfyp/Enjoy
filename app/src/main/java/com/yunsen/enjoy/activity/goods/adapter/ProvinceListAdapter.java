package com.yunsen.enjoy.activity.goods.adapter;

import android.content.Context;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.ProvincesBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/17.
 */

public class ProvinceListAdapter extends CommonAdapter<ProvincesBean> {
    public ProvinceListAdapter(Context context, int layoutId, List<ProvincesBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProvincesBean provincesBeans, int position) {
        holder.setText(R.id.text_tv, provincesBeans.getProvinceName());
    }
}
