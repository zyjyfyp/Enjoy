package com.yunsen.enjoy.fragment.buy;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarBrand;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/28.
 */

public class HotCarBrandAdapter extends CommonAdapter<CarBrand> {

    public HotCarBrandAdapter(Context context, int layoutId, List<CarBrand> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CarBrand carBrand, int position) {
        holder.setText(R.id.hot_brand_tv, carBrand.getTitle());
        ImageView view = holder.getView(R.id.hot_brand_img);
        Glide.with(mContext)
                .load(carBrand.getImg_url())
                .apply(new RequestOptions().placeholder(R.mipmap.brand_default)
                        .centerCrop())
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
