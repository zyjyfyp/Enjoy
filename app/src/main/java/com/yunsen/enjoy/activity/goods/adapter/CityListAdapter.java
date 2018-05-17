package com.yunsen.enjoy.activity.goods.adapter;

import android.content.Context;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CitysBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/17.
 */

public class CityListAdapter extends CommonAdapter<CitysBean> {

    public CityListAdapter(Context context, int layoutId, List<CitysBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CitysBean citysBean, int position) {
        holder.setText(R.id.text_tv, citysBean.getCitysName());
    }

    public void upData(List<CitysBean> data) {
        mDatas.clear();
        if (data != null) {
            mDatas.addAll(data);
        }
        this.notifyDataSetChanged();
    }


}
