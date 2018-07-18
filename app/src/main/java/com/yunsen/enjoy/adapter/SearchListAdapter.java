package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.DefaultSpecItemBean;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2018/5/11.
 */

public class SearchListAdapter extends CommonAdapter<CarDetails> {
    public SearchListAdapter(Context context, int layoutId, List<CarDetails> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CarDetails goodsData, int position) {
        holder.setText(R.id.goods_title_2, goodsData.getTitle());
        holder.setText(R.id.goods_sub_title_2, goodsData.getSubtitle());
        DefaultSpecItemBean defaultSpecItem = goodsData.getDefault_spec_item();
        holder.setText(R.id.goods_money, defaultSpecItem.getSell_price() + "万元");//sell_price
        holder.setText(R.id.goods_first_money, "首付" + defaultSpecItem.getFirst_payment() + "万元");
        holder.setText(R.id.goods_address, goodsData.getAddress());
        Picasso.with(mContext)
                .load(goodsData.getImg_url())
                .resize(DeviceUtil.dp2px(mContext, 122), DeviceUtil.dp2px(mContext, 90))
                .centerCrop()
                .into(((ImageView) holder.getView(R.id.goods_left_img)));
    }


    public void upData(List<CarDetails> responseData) {
        mDatas.clear();
        if (responseData != null) {
            mDatas.addAll(responseData);
        }
        notifyDataSetChanged();
    }

    public boolean addData(List<CarDetails> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            this.notifyDataSetChanged();
            return true;
        }
        return false;
    }

}