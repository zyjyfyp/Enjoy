package com.yunsen.enjoy.fragment.discover;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.widget.GlideRoundTransform;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/19.
 */

public class SpreadAdapter extends CommonAdapter<CarDetails> {
    public SpreadAdapter(Context context, int layoutId, List<CarDetails> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CarDetails carDetails, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.spread_img);
        Glide.with(mContext)
                .load(carDetails.getImg_url())
                .transform(new GlideRoundTransform(mContext,5))
                .into(imageView);
        holder.setText(R.id.spread_title, carDetails.getTitle());
    }
}
