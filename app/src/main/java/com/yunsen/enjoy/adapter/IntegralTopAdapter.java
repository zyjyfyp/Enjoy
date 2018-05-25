package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.ui.UIHelper;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class IntegralTopAdapter extends PagerAdapter {
    private Context mContext;
    private List<CarDetails> mDatas;

    public IntegralTopAdapter(Context mContext, List<CarDetails> datas) {
        this.mContext = mContext;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView item = new ImageView(mContext);
        CarDetails data = mDatas.get(position);
        Glide.with(mContext)
                .load(data.getImg_url())
                .into(item);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        item.setLayoutParams(params);
        item.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(item);
        final CarDetails fCarDetail = data;
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showGoodsDescriptionActivity(mContext, String.valueOf(fCarDetail.getId()), fCarDetail.getTitle(), Constants.POINT_BUY);
            }
        });
        return item;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }


}
