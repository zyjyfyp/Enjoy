package com.yunsen.enjoy.fragment.home;

import android.content.Context;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.DefaultSpecItemBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class FillActivityAdapter extends CommonAdapter<CarDetails> {

    public FillActivityAdapter(Context context, int layoutId, List<CarDetails> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CarDetails carDetails, int position) {
        String imgUrl = carDetails.getImg_url();
        ImageView imageView = (ImageView) holder.getView(R.id.fill_left_img);
        TextView marketTv = (TextView) holder.getView(R.id.fill_market_price);
        ProgressBar progressBar = (ProgressBar) holder.getView(R.id.fill_progress_bar);
        marketTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(mContext)
                .load(imgUrl)
                .into(imageView);
        holder.setText(R.id.fill_title, carDetails.getTitle());
        holder.setText(R.id.fill_sub_title, carDetails.getSubtitle());
        DefaultSpecItemBean defaultSpecItem = carDetails.getDefault_spec_item();
        holder.setText(R.id.fill_market_price, "￥" + defaultSpecItem.getMarket_price());
        holder.setText(R.id.fill_sell_price, "￥" + defaultSpecItem.getSell_price());
        holder.setText(R.id.fill_sell_state, "已销售 20%");// TODO: 2018/5/23
        progressBar.setProgress(20);
    }

    public void upData(List<CarDetails> datas) {
        mDatas.clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }
        this.notifyDataSetChanged();
    }
}
