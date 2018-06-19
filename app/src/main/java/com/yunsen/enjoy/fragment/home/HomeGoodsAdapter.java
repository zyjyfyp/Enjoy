package com.yunsen.enjoy.fragment.home;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.DefaultSpecItemBean;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/19.
 */

public class HomeGoodsAdapter extends CommonAdapter<CarDetails> {
    public HomeGoodsAdapter(Context context, int layoutId, List<CarDetails> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CarDetails goodsData, int position) {
        ImageView img = (ImageView) holder.getView(R.id.home_goods_img);
        Glide.with(mContext)
                .load(goodsData.getImg_url())
                .into(img);
        holder.setText(R.id.home_goods_title_tv, goodsData.getTitle());
        holder.setText(R.id.home_goods_unit_tv, goodsData.getPacking());
        holder.setText(R.id.home_goods_subtitle_tv, goodsData.getSubtitle());
        DefaultSpecItemBean defaultSpecItem = goodsData.getDefault_spec_item();
        String sellPriceStr = defaultSpecItem.getSellPriceStr();
        holder.setText(R.id.home_goods_price, "¥" + sellPriceStr);
        holder.setText(R.id.home_goods_vip_price, "会员最低价：" + defaultSpecItem.getMarkePriceStr() + "元");
    }
}
