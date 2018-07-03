package com.yunsen.enjoy.activity.mine.adapter;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/26.
 */

public class ListImageAdapter extends CommonAdapter<String> {
    private static final String TAG = "ListImageAdapter";
    private Animation animation;

    public ListImageAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        animation = AnimationUtils.loadAnimation(mContext, R.anim.rotate_anim);
    }

    @Override
    protected void convert(ViewHolder holder, String tImage, int position) {
        final ImageView imageView = (ImageView) holder.getView(R.id.image);
        final ImageView imageViewLoading= (ImageView) holder.getView(R.id.loadImage);
        String url = URLConstants.REALM_URL + tImage;
        imageViewLoading.startAnimation(animation);
        Glide.with(mContext)
                .load(url)
                .error(R.mipmap.default_img)
                .crossFade().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                imageViewLoading.clearAnimation();
                imageViewLoading.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                imageViewLoading.clearAnimation();
                imageViewLoading.setVisibility(View.GONE);
                return false;
            }
        })
                .into(imageView);
        Log.e(TAG, "convert: " + url);
    }

    public void upIndexData(int index, String url) {
        if (index < mDatas.size()) {
            mDatas.remove(index);
            mDatas.add(index, url);
            this.notifyItemChanged(index);
        }
    }

    public int removeUrl(String url) {
        int index = -1;
        for (int i = 0; i < mDatas.size(); i++) {
            if (url.equals(mDatas.get(i))) {
                mDatas.remove(i);
                index = i;
                break;
            }
        }
        this.notifyDataSetChanged();
        return index;
    }
}
