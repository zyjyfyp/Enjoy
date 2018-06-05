package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.AlbumsBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/7.
 */

public class CarTopBannerAdapter extends PagerAdapter {

    private static final String TAG = "BannerAdapter";
    private List<AlbumsBean> mDatas;
    private Context mContext;

    public CarTopBannerAdapter(List<AlbumsBean> datas, Context context) {
        this.mDatas = datas;
        this.mContext = context;
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
        AlbumsBean data = mDatas.get(position);
        if (data.getThumb_path() == null) {
            item.setImageResource(data.getResId());
        } else {
            Picasso.with(mContext)
                    .load(data.getThumb_path())
                    .placeholder(R.mipmap.default_banner)
                    .into(item);
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        item.setLayoutParams(params);
        item.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    public void upData(List<AlbumsBean> datas) {
        if (datas != null) {
            mDatas.clear();
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }
}
