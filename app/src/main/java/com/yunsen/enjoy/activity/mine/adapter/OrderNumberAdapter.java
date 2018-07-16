package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.ListOrderCountBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/6.
 */

public class OrderNumberAdapter extends CommonAdapter<ListOrderCountBean> {
    public OrderNumberAdapter(Context context, int layoutId, List<ListOrderCountBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ListOrderCountBean listOrderCountBean, int position) {
        holder.setText(R.id.order_item_time, listOrderCountBean.getAdd_time());
        holder.setText(R.id.order_item_type, listOrderCountBean.getDatatype());
        holder.setText(R.id.order_item_name, listOrderCountBean.getNick_name());
        holder.setText(R.id.order_item_id, listOrderCountBean.getUser_code());
        holder.setText(R.id.order_item_money, "" +listOrderCountBean.getReal_amount());
        holder.setText(R.id.order_item_income, "" +  listOrderCountBean.getPaycount());
    }
}
