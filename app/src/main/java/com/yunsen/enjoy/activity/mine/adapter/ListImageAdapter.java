package com.yunsen.enjoy.activity.mine.adapter;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/26.
 */

public class ListImageAdapter extends CommonAdapter<String> {
    public ListImageAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String tImage, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.image);
        Glide.with(mContext)
                .load(tImage)
                .into(imageView);

    }
}
