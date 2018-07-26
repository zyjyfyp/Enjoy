package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.TeamInfoBean;
import com.yunsen.enjoy.model.TeamItemBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/26/026.
 */

public class TeamInfoAdapter extends CommonAdapter<TeamItemBean> {
    public TeamInfoAdapter(Context context, int layoutId, List<TeamItemBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, TeamItemBean teamInfoBean, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.team_item_img);
        Glide.with(mContext)
                .load(teamInfoBean.getAvatar())
                .into(imageView);
        holder.setText(R.id.team_item_name_tv, teamInfoBean.getNick_name());
        String mobile = teamInfoBean.getMobile();
        if (mobile != null && mobile.length() >= 11) {
            String substring = mobile.substring(3, 7);
            mobile = mobile.replace(substring, "****");
        }
        holder.setText(R.id.team_item_phone_tv, mobile);
        holder.setText(R.id.team_item_time_tv, teamInfoBean.getReg_time());
        holder.setText(R.id.team_item_order_count_tv, String.valueOf(teamInfoBean.getOrderCount()));
        holder.setText(R.id.team_item_money_tv, teamInfoBean.getAmount() + "元");

    }
}
