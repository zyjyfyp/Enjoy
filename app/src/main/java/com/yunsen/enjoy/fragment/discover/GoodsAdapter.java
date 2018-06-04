package com.yunsen.enjoy.fragment.discover;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */

public class GoodsAdapter extends CommonAdapter<GoodsData> {

    public GoodsAdapter(Context context, int layoutId, List<GoodsData> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, GoodsData goodsData, int position) {
        holder.setText(R.id.goods_title, goodsData.getTitle());
        holder.setText(R.id.goods_sub_title, goodsData.getCategory_title());
        Picasso.with(mContext)
                .load(goodsData.getImg_url())
                .placeholder(R.mipmap.banner1)
                .resize(DeviceUtil.dp2px(mContext,105),DeviceUtil.dp2px(mContext,70))
                .centerCrop()
                .into(((ImageView) holder.getView(R.id.goods_right_img)));
    }

    public void upData(List<GoodsData> responseData) {
        if (responseData != null) {
            mDatas.clear();
            mDatas.addAll(responseData);
            notifyDataSetChanged();
        }
    }
}
