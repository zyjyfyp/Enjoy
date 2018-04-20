package com.yunsen.enjoy.fragment.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.fragment.model.CarStoreMode;

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
            Picasso.with(mContext)
                    .load(carStoreMode.getImgUrl())
                    .placeholder(R.mipmap.car_4)
                    .into((ImageView) holder.getView(R.id.shop_item_img));
        } else {
            holder.setImageResource(R.id.shop_item_img, R.mipmap.car_2);
        }
    }


}
