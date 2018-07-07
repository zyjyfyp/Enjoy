package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/6.
 */

public class PersonNumberAdapter extends CommonAdapter<UserInfo> {
    public PersonNumberAdapter(Context context, int layoutId, List<UserInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UserInfo userInfo, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.person_item_icon);
        String avatar = userInfo.getAvatar();
        if (!TextUtils.isEmpty(avatar)) {
            if (!avatar.startsWith("http")) {
                avatar = URLConstants.REALM_URL + avatar;
            }
        }
        Glide.with(mContext)
                .load(avatar)
                .placeholder(R.mipmap.default_img)
                .into(imageView);
        holder.setText(R.id.person_item_id, String.valueOf(userInfo.getId()));
        holder.setText(R.id.person_item_name, userInfo.getNick_name());
        holder.setText(R.id.person_item_time, userInfo.getReg_time());
    }
}
