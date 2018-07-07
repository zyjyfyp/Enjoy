package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.OrderDataBean;
import com.yunsen.enjoy.model.OrderGoodsBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/6.
 */

public class MonthOrderAdapter extends CommonAdapter<OrderDataBean> {
    public MonthOrderAdapter(Context context, int layoutId, List<OrderDataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, OrderDataBean orderDataBean, int position) {
        RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.month_order_recycler);
        List<OrderGoodsBean> orderGoods = orderDataBean.getOrder_goods();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new MonthOrderItemAdapter(mContext, R.layout.month_order_item_item, orderGoods));
        holder.setText(R.id.month_order_tv, orderDataBean.getOrder_no());
        double payableAmount = orderDataBean.getPayable_amount() + orderDataBean.getMarket_price_total();
        int count = orderGoods.size();
        int quantitySum = 0;
        double sellSum = 0;
        for (int i = 0; i < count; i++) {
            OrderGoodsBean data = orderGoods.get(i);
            int quantity = data.getQuantity();
            double sellPrice = data.getSell_price();
            quantitySum += quantity;
            sellSum += sellPrice * quantity;
        }
        holder.setText(R.id.month_order_account_tv, "共" + quantitySum + "件商品，需付款：¥" + payableAmount + "（含运费¥：" + orderDataBean.getMarket_price_total() + "）");
    }
}
