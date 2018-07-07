package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.OrderGoodsBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/6.
 */

public class MonthOrderItemAdapter extends CommonAdapter<OrderGoodsBean> {
    public MonthOrderItemAdapter(Context context, int layoutId, List<OrderGoodsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, OrderGoodsBean orderGoodsBean, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.month_order_item_img);
        Glide.with(mContext)
                .load(orderGoodsBean.getImg_url())
                .into(imageView);
        holder.setText(R.id.month_order_item_title, orderGoodsBean.getArticle_title());
        holder.setText(R.id.month_order_item_time, orderGoodsBean.getTimer_time());
        holder.setText(R.id.month_order_item_price, "¥" + orderGoodsBean.getSell_price());
        holder.setText(R.id.month_order_item_quantity, "x" + orderGoodsBean.getQuantity());
    }
}
