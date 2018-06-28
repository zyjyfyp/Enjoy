package com.yunsen.enjoy.activity.order.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.OrderBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/28.
 */

public class GoodsResetAdapter extends CommonAdapter<OrderBean> {
    public GoodsResetAdapter(Context context, int layoutId, List<OrderBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, OrderBean carDetails, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.left_img);
        Glide.with(mContext)
                .load(carDetails.getImg_url())
                .placeholder(R.mipmap.default_img)
                .into(imageView);
        holder.setText(R.id.goods_title, carDetails.getArticle_title());
        holder.setText(R.id.goods_price_tv, "¥" + carDetails.getSell_price());
        holder.setText(R.id.goods_num_tv, "x" + carDetails.getQuantity());
    }
}
