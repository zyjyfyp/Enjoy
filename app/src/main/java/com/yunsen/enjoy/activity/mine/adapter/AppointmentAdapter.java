package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.OrderDataBean;
import com.yunsen.enjoy.model.OrderGoodsBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 */

public class AppointmentAdapter extends CommonAdapter<OrderDataBean> {
    public AppointmentAdapter(Context context, int layoutId, List<OrderDataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, OrderDataBean orderDataBean, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.appoint_item_img);
        List<OrderGoodsBean> orderGoods = orderDataBean.getOrder_goods();
        if (orderGoods != null && orderGoods.size() > 0) {
            String imgUrl = orderGoods.get(0).getImg_url();
            Glide.with(mContext)
                    .load(imgUrl)
                    .into(imageView);
            holder.setText(R.id.appoint_item_title, orderGoods.get(0).getArticle_title());

        }
        holder.setText(R.id.appoint_item_time, orderDataBean.getMessage());
        holder.setText(R.id.appoint_item_address, orderDataBean.getAddress());
    }

    public void upDatas(List<OrderDataBean> datas) {
        this.mDatas.clear();
        if (datas != null) {
            this.mDatas.addAll(datas);
        }
        this.notifyDataSetChanged();
    }

    public boolean addDatas(List<OrderDataBean> datas) {
        if (datas != null) {
            mDatas.addAll(datas);
            this.notifyDataSetChanged();
            return true;
        }
        return false;
    }
}
