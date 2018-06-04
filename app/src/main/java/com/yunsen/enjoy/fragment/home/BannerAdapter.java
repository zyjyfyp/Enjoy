package com.yunsen.enjoy.fragment.home;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.ui.UIHelper;

import java.util.List;

/**
 * Created by yunsenA on 2018/4/18.
 */

public class BannerAdapter extends PagerAdapter {
    private static final String TAG = "BannerAdapter";
    private List<AdvertModel> mDatas;
    private Context mContext;

    public BannerAdapter(List<AdvertModel> datas, Context context) {
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
        final AdvertModel data = mDatas.get(position);
        if (data.getAd_url() == null) {
            item.setImageResource(data.getRseImg());
        } else {
            Picasso.with(mContext).load(data.getAd_url()).placeholder(R.mipmap.banner1).into(item);
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        item.setLayoutParams(params);
        item.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(item);
        final int pos = position;
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int articleId = data.getArticle_id();
                if (articleId != 0) {
                    UIHelper.showCarDetailsActivity(mContext, articleId);
                }
            }
        });
        return item;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    public void upData(List<AdvertModel> datas) {
        if (datas != null) {
            mDatas.clear();
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.e(TAG, "getPageTitle: p= " + position);
        return "标题" + position;
    }
}
