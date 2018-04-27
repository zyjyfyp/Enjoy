package com.yunsen.enjoy.fragment.home;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;

import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;

import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */

public class StoreRecyclerAdapter extends CommonAdapter<SProviderModel> {
    public StoreRecyclerAdapter(Context context, int layoutId, List<SProviderModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, SProviderModel carStoreMode, int position) {
        holder.setText(R.id.shop_item_title, carStoreMode.getTitle());
        if (carStoreMode.getImgUrl() != null) {
            Glide.with(mContext)
                    .load(carStoreMode.getImgUrl())
                    .placeholder(R.mipmap.car_4)
                    .centerCrop()
                    .into((ImageView) holder.getView(R.id.shop_item_img));
        } else {
            holder.setImageResource(R.id.shop_item_img, R.mipmap.car_2);
        }
    }


}
