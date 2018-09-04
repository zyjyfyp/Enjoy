package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CommentBean;
import com.yunsen.enjoy.utils.StringUtils;
import com.yunsen.enjoy.widget.GlideCircleTransform;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/8/28/028.
 */

public class CommentAdapter extends CommonAdapter<CommentBean> {
    public CommentAdapter(Context context, int layoutId, List<CommentBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CommentBean commentBean, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.comment_user_img);
        Glide.with(mContext)
                .load(commentBean.getAvatar())
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
        String content = commentBean.getContent();
        if (!TextUtils.isEmpty(content)) {
            content = content.trim();
            TextView textView = (TextView) holder.getView(R.id.comment_content_tv);
            textView.setText(StringUtils.decodeUnicode(content));
        }
        holder.setText(R.id.comment_sub_time, commentBean.getAdd_time());
        String userName = commentBean.getUser_name();
        holder.setText(R.id.comment_user_name, commentBean.getUser_name());
    }
}
