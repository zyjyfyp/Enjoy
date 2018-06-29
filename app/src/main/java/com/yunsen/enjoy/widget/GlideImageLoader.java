package com.yunsen.enjoy.widget;

import android.app.Activity;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.yancy.gallerypick.inter.ImageLoader;
import com.yancy.gallerypick.widget.GalleryImageView;

/**
 * Created by Administrator on 2018/6/26.
 */

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, Context context, String path, GalleryImageView galleryImageView, int width, int height) {
        Glide.with(activity)
                .load(path)
                .into(galleryImageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
