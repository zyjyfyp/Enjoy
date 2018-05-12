package com.yunsen.enjoy.fragment.home;

import android.content.Context;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.ImgAndTextModel;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/12.
 */

public class HomeGoodsAdapter extends CommonAdapter<ImgAndTextModel> {
    public HomeGoodsAdapter(Context context, int layoutId, List<ImgAndTextModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ImgAndTextModel data, int position) {
        holder.setImageResource(R.id.home_goods_top_img, data.getResId());
        holder.setText(R.id.home_goods_title, "商品标题");
        holder.setText(R.id.home_goods_price_tv, "￥8.88");
        holder.setText(R.id.home_goods_address_tv, "深圳" + data.getId());
    }

    public void upDatas(List<ImgAndTextModel> datas) {
        this.mDatas.clear();
        if (datas != null) {
            this.mDatas.addAll(datas);
            this.notifyDataSetChanged();
        }
    }

}
