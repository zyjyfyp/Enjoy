package com.yunsen.enjoy.fragment.buy;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarBrand;
import com.yunsen.enjoy.model.HeightFilterBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/28.
 */

public class HeightFilterAdapter extends CommonAdapter<HeightFilterBean> {

    public HeightFilterAdapter(Context context, int layoutId, List<HeightFilterBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HeightFilterBean carBrand, int position) {
        holder.setText(R.id.bottom_tv, carBrand.getTitle());
        ImageView view = holder.getView(R.id.top_img);
        Glide.with(mContext)
                .load(carBrand.getIcon_url())
                .placeholder(R.mipmap.default_img)
                .into(view);
    }

    public void upDatas(List<HeightFilterBean> list) {
        if (list != null) {
            mDatas.clear();
            mDatas.addAll(list);
            this.notifyDataSetChanged();
        }
    }
}
