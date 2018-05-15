package com.yunsen.enjoy.fragment.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.model.ImgAndTextModel;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/12.
 */

public class HomeGoodsAdapter extends CommonAdapter<GoodsData> {
    public HomeGoodsAdapter(Context context, int layoutId, List<GoodsData> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, GoodsData data, int position) {
        String imgUrl = data.getImg_url();
        ImageView view = holder.getView(R.id.home_goods_top_img);
        Glide.with(mContext)
                .load(imgUrl)
                .into(view);
        holder.setText(R.id.home_goods_title, data.getTitle());
        holder.setText(R.id.home_goods_price_tv, "￥" + data.getSell_price());
        holder.setText(R.id.home_goods_address_tv, data.getCity());

    }

    public void upDatas(List<GoodsData> datas) {
        this.mDatas.clear();
        if (datas != null) {
            this.mDatas.addAll(datas);
            this.notifyDataSetChanged();
        }
    }

}
