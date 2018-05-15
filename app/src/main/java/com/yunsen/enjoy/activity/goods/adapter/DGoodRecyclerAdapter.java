package com.yunsen.enjoy.activity.goods.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */

public class DGoodRecyclerAdapter extends CommonAdapter<GoodsData> {
    public DGoodRecyclerAdapter(Context context, int layoutId, List<GoodsData> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, GoodsData goodsData, int position) {
        ImageView imgView = holder.getView(R.id.d_goods_left_img);
        Glide.with(mContext)
                .load(goodsData.getImg_url())
                .into(imgView);
        holder.setText(R.id.d_goods_item_title, goodsData.getTitle());
        holder.setText(R.id.d_goods_item_price, "￥" + goodsData.getSell_price());
        holder.setText(R.id.d_goods_item_like, "想要换：" + goodsData.getCategory_title());
    }

    public void upData(List<GoodsData> datas) {
        mDatas.clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }
        this.notifyDataSetChanged();
    }

    public boolean addData(List<GoodsData> datas) {
        if (datas != null) {
            mDatas.addAll(datas);
            this.notifyDataSetChanged();
            return true;
        }
        return false;
    }
}
