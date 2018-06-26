package com.yunsen.enjoy.activity.goods.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.utils.GlobalStatic;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.utils.Utils;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */

public class DGoodRecyclerAdapter extends CommonAdapter<CarDetails> {
    private boolean mShowClear = false;

    public DGoodRecyclerAdapter(Context context, int layoutId, List<CarDetails> datas) {
        super(context, layoutId, datas);
    }

    public boolean isShowClear() {
        return mShowClear;
    }

    public void setShowClear(boolean showClear) {
        this.mShowClear = showClear;
    }

    @Override
    protected void convert(ViewHolder holder, CarDetails goodsData, int position) {
        ImageView imgView = holder.getView(R.id.d_goods_left_img);
        View view = holder.getView(R.id.d_goods_item_distance);
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.default_img);
        Glide.with(mContext)
                .load(goodsData.getImg_url())
                .apply(options)
                .into(imgView);
        holder.setText(R.id.d_goods_item_title, goodsData.getTitle());
        holder.setText(R.id.d_goods_item_price, "￥" + goodsData.getDefault_spec_item()
                .getSell_price());
        holder.setText(R.id.d_goods_item_like, "想要换：" + goodsData.getCategory_title());
        String lat = goodsData.getLat().trim();
        String lng = goodsData.getLng().trim();

        if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng) && GlobalStatic.latitude != 0.0 && GlobalStatic.longitude != 0.0 && !("0,0".equals(lat) || "0.0".equals(lng))) {
            view.setVisibility(View.VISIBLE);
            double algorithm = Utils.algorithm(GlobalStatic.longitude, GlobalStatic.latitude, Double.valueOf(lng), Double.valueOf(lat)) / 1000;
            BigDecimal b = new BigDecimal(algorithm);
            double df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            holder.setText(R.id.d_goods_item_distance, df + "千米");
        } else {
//            view.setVisibility(View.GONE);
            holder.setText(R.id.d_goods_item_distance, "0.0 千米");
        }
        View repertory = holder.getView(R.id.d_goods_clear_repertory);
        if (mShowClear) {
            repertory.setVisibility(View.VISIBLE);
            repertory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.makeTextShort("清仓功能暂无");
                }
            });
        } else {
            repertory.setVisibility(View.GONE);
        }

    }

    private void getLatlon(String cityName) {

    }

    public void upData(List<CarDetails> datas) {
        mDatas.clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }
        this.notifyDataSetChanged();
    }

    public boolean addData(List<CarDetails> datas) {
        if (datas != null) {
            mDatas.addAll(datas);
            this.notifyDataSetChanged();
            return true;
        }
        return false;
    }
}
