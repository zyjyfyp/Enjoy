package com.yunsen.enjoy.fragment.home;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarBrand;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/5.
 */

public class PreferenceCarAdapter extends CommonAdapter<CarBrand> {

    public PreferenceCarAdapter(Context context, int layoutId, List<CarBrand> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CarBrand carBrand, int position) {
        holder.setText(R.id.right_tv, carBrand.getTitle());
        ImageView view = holder.getView(R.id.left_img);
        Glide.with(mContext)
                .load(carBrand.getImg_url())
                .centerCrop()
                .into(view);
    }

    public void upDatas(List<CarBrand> list) {
        if (list != null) {
            mDatas.clear();
            mDatas.addAll(list);
            this.notifyDataSetChanged();
        }
    }
}
