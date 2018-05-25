package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.DefaultSpecItemBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class ExchangePointAdapter extends CommonAdapter<CarDetails> {

    public ExchangePointAdapter(Context context, int layoutId, List<CarDetails> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CarDetails carDetails, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.exchange_goods_img);
        Glide.with(mContext)
                .load(carDetails.getImg_url())
                .into(imageView);
        DefaultSpecItemBean defaultSpecItem = carDetails.getDefault_spec_item();
        int cashingPoint = defaultSpecItem.getCashing_point();
        double sellPrice = defaultSpecItem.getSell_price();
        holder.setText(R.id.exchange_title, carDetails.getTitle());
        holder.setText(R.id.exchange_price_tv, "积分兑换：" + cashingPoint + "积分+" + sellPrice + "元");
        TextView marketTv = (TextView) holder.getView(R.id.exchange_market_tv);
        marketTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        marketTv.setText("市场价：￥" + defaultSpecItem.getMarkePriceStr());
    }

    public void upData(List<CarDetails> responseData) {
        mDatas.clear();
        if (responseData != null) {
            mDatas.addAll(responseData);
        }
        this.notifyDataSetChanged();
    }

    public boolean addData(List<CarDetails> data) {
        if (data != null) {
            mDatas.addAll(data);
            this.notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

}
