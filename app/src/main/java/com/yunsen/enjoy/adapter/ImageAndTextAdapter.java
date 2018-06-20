package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.UsedFunction;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */

public class ImageAndTextAdapter extends CommonAdapter<UsedFunction> {
    public ImageAndTextAdapter(Context context, int layoutId, List<UsedFunction> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UsedFunction usedFunction, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.top_img);
        holder.setText(R.id.bottom_tv, usedFunction.getTitle());
        imageView.setImageResource(usedFunction.getImgResId());
    }
}
