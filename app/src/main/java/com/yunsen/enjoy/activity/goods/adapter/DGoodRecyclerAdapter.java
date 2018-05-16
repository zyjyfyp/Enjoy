package com.yunsen.enjoy.activity.goods.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.utils.GlobalStatic;
import com.yunsen.enjoy.utils.Utils;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.math.BigDecimal;
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
        View view = holder.getView(R.id.d_goods_item_distance);
        Glide.with(mContext)
                .load(goodsData.getImg_url())
                .into(imgView);
        holder.setText(R.id.d_goods_item_title, goodsData.getTitle());
        holder.setText(R.id.d_goods_item_price, "￥" + goodsData.getSell_price());
        holder.setText(R.id.d_goods_item_like, "想要换：" + goodsData.getCategory_title());
        String lat = goodsData.getLat().trim();
        String lng = goodsData.getLng().trim();

        if (GlobalStatic.latitude != 0.0 && GlobalStatic.longitude != 0.0 && !("0,0".equals(lat) || "0.0".equals(lng))) {
            view.setVisibility(View.VISIBLE);
            double algorithm = Utils.algorithm(GlobalStatic.longitude, GlobalStatic.latitude, Double.valueOf(lng), Double.valueOf(lat)) / 1000;
            BigDecimal b = new BigDecimal(algorithm);
            double df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            holder.setText(R.id.d_goods_item_distance, df + "千米");
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void getLatlon(String cityName) {

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
