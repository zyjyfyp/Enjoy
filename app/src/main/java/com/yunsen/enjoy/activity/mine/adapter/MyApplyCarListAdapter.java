package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.MyApplyCarBean;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/12/012.
 */

public class MyApplyCarListAdapter extends CommonAdapter<MyApplyCarBean> {
    public MyApplyCarListAdapter(Context context, int layoutId, List<MyApplyCarBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MyApplyCarBean data, int position) {
        holder.setText(R.id.apply_title, data.getTitle());
        holder.setText(R.id.apply_people_tv, "申购人：" + data.getReal_name());
        holder.setText(R.id.apply_money, data.getAll_payment() + "万元");//sell_price
        holder.setText(R.id.apply_first_money, "首付" + data.getFirst_payment() + "万元");
        Picasso.with(mContext)
                .load(data.getImg_url())
                .resize(DeviceUtil.dp2px(mContext, 120), DeviceUtil.dp2px(mContext, 80))
                .centerCrop()
                .into(((ImageView) holder.getView(R.id.apply_left_img)));
    }
}
