package com.yunsen.enjoy.activity.order.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.GoodsCarInfo;
import com.yunsen.enjoy.model.ShopCarCount;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.AddAndSubView;
import com.yunsen.enjoy.widget.interfaces.GoodsSumInterface;
import com.yunsen.enjoy.widget.interfaces.OnRightOnclickListener;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/7/21/021.
 */

public class ChangeIntoAdapter extends CommonAdapter<GoodsCarInfo> {
    GoodsSumInterface mGoodsSumCall;
    private int mGoodsCount;
    private double mGoodsSumPrice;

    public void setGoodsSumCall(GoodsSumInterface mGoodsSumCall) {
        this.mGoodsSumCall = mGoodsSumCall;
    }

    public ChangeIntoAdapter(Context context, int layoutId, List<GoodsCarInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, GoodsCarInfo goodsCarInfo, int position) {
        final GoodsCarInfo fGoodsData = goodsCarInfo;
        ImageView imgView = (ImageView) holder.getView(R.id.change_into_img);
        Glide.with(mContext)
                .load(goodsCarInfo.getImg_url())
                .into(imgView);
        holder.setText(R.id.change_into_title, goodsCarInfo.getTitle());
        holder.setText(R.id.change_into_price, "¥" + String.valueOf(goodsCarInfo.getSell_price()));
        AddAndSubView andSubView = (AddAndSubView) holder.getView(R.id.change_into_control);
        andSubView.setNum(goodsCarInfo.getQuantity());
        andSubView.setMaxNum(goodsCarInfo.getStock_quantity());
        andSubView.setmMinNum(1);
        andSubView.setRightOnclickListener(new OnRightOnclickListener() {
            @Override
            public void onRightClick(int... index) {
                if (mGoodsSumCall != null) {
                    final int fIndex = index[0];
                    int id = fGoodsData.getId();
                    HttpProxy.upShopCarGoods(String.valueOf(id), String.valueOf(fIndex), new HttpCallBack<ShopCarCount>() {
                        @Override
                        public void onSuccess(ShopCarCount responseData) {
                            fGoodsData.setQuantity(fIndex);
                            double goodsSumPrice = getGoodsSumPrice();
                            mGoodsSumCall.GoodsSumCallBack(mGoodsCount, goodsSumPrice);
                        }

                        @Override
                        public void onError(Request request, Exception e) {
                            ToastUtils.makeTextShort("修改失败");
                        }
                    });


                }
            }
        });
    }

    public double getGoodsSumPrice() {
        double goodsSumPrice = 0.0;
        mGoodsCount = 0;
        int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            GoodsCarInfo goodsInfo = mDatas.get(i);
            mGoodsCount += goodsInfo.getQuantity();
            goodsSumPrice += goodsInfo.getQuantity() * goodsInfo.getSell_price();
        }
        this.mGoodsSumPrice = goodsSumPrice;
        return goodsSumPrice;
    }
}
