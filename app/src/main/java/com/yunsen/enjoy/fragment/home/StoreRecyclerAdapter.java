package com.yunsen.enjoy.fragment.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.fragment.model.CarStoreMode;
import com.yunsen.enjoy.widget.BaseRecyclerAdapter;
import com.yunsen.enjoy.widget.BaseRecyclerHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */

public class StoreRecyclerAdapter extends BaseRecyclerAdapter<CarStoreMode> {

    public StoreRecyclerAdapter(Context context, List<CarStoreMode> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, CarStoreMode item, int position, boolean isScrolling) {
        holder.setText(R.id.shop_item_title, item.getTitle());
        if (item.getImgUrl() != null) {
            holder.setImageByUrl(R.id.shop_item_img, item.getImgUrl());
        } else {
            holder.setImageResource(R.id.shop_item_img, R.mipmap.car_2);
        }
    }
}
