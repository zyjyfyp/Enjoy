package com.yunsen.enjoy.activity.car.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.GoodsCarInfo;
import com.yunsen.enjoy.model.ShopCarCount;
import com.yunsen.enjoy.ui.DialogUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.AddAndSubView;
import com.yunsen.enjoy.widget.interfaces.OnLeftOnclickListener;
import com.yunsen.enjoy.widget.interfaces.OnRightOnclickListener;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/18.
 */

public class DShoppingCarAdapter extends CommonAdapter<GoodsCarInfo> {
    private GoodsSumInterface mGoodsSumCall;

    public DShoppingCarAdapter(Context context, int layoutId, List<GoodsCarInfo> datas) {
        super(context, layoutId, datas);
    }

    private static final String TAG = "DShoppingCarAdapter";

    @Override
    protected void convert(ViewHolder holder, final GoodsCarInfo goodsData, int position) {
        ImageView imgView = holder.getView(R.id.car_goods_img);
        Glide.with(mContext)
                .load(goodsData.getImg_url())
                .into(imgView);
        holder.setText(R.id.car_goods_title, goodsData.getTitle());
        holder.setText(R.id.car_goods_price, "￥" + goodsData.getSell_price());
        AddAndSubView andSubView = (AddAndSubView) holder.getView(R.id.car_goods_num_control);
        andSubView.setNum(goodsData.getQuantity());
        CheckBox carGoodsCb = (CheckBox) holder.getView(R.id.car_goods_cb);
        View deleteImg = holder.getView(R.id.car_goods_delete_img);
        deleteImg.setTag(goodsData);
        carGoodsCb.setChecked(goodsData.isCheckGoods());
        carGoodsCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                Log.e(TAG, "onClick: checked =" + checked);
                goodsData.setCheckGoods(checked);

                if (mGoodsSumCall != null) {
                    double goodsSumPrice = getGoodsSumPrice();
                    mGoodsSumCall.GoodsSumCallBack(mGoodsCount, goodsSumPrice);
                }
            }
        });

        andSubView.setRightOnclickListener(new OnRightOnclickListener() {
            @Override
            public void onRightClick(int... index) {  //添加和删除都回调该方法
                if (mGoodsSumCall != null) {
                    final int fIndex = index[0];
                    int id = goodsData.getId();
                    HttpProxy.upShopCarGoods("", String.valueOf(id), String.valueOf(fIndex), new HttpCallBack<ShopCarCount>() {
                        @Override
                        public void onSuccess(ShopCarCount responseData) {
                            goodsData.setQuantity(fIndex);
                            double goodsSumPrice = getGoodsSumPrice();
                            mGoodsSumCall.GoodsSumCallBack(mGoodsCount, goodsSumPrice);
                        }

                        @Override
                        public void onError(Request request, Exception e) {
                            ToastUtils.makeTextShort("异常修改失败");
                        }
                    });


                }
            }
        });
        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View fView = v;

                AlertDialog yesAndNoDialog = DialogUtils.createYesAndNoDialog(mContext, "", "先留着", "删除",
                        null, new OnRightOnclickListener() {
                            @Override
                            public void onRightClick(int... index) {
                                final GoodsCarInfo data = (GoodsCarInfo) fView.getTag();

                                HttpProxy.deleteShopCarGoods("", "" + data.getId(), new HttpCallBack<ShopCarCount>() {
                                    @Override
                                    public void onSuccess(ShopCarCount responseData) {
                                        if (mDatas.remove(data)) {
                                            DShoppingCarAdapter.this.notifyDataSetChanged();
                                            if (mGoodsSumCall != null) {
                                                double goodsSumPrice = getGoodsSumPrice();
                                                mGoodsSumCall.GoodsSumCallBack(mGoodsCount, goodsSumPrice);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError(Request request, Exception e) {
                                        ToastUtils.makeTextShort("异常删除失败");
                                    }
                                });
                            }
                        });
                yesAndNoDialog.show();
                yesAndNoDialog.getButton(yesAndNoDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.green));
                yesAndNoDialog.getButton(yesAndNoDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            }
        });


    }


    public void upData(List<GoodsCarInfo> datas) {
        this.mDatas.clear();
        if (datas != null) {
            this.mDatas.addAll(datas);
        }
        this.notifyDataSetChanged();
    }

    private int mGoodsCount;
    private double mGoodsSumPrice;

    public double getGoodsSumPrice() {
        double goodsSumPrice = 0.0;
        mGoodsCount = 0;
        int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            GoodsCarInfo goodsInfo = mDatas.get(i);
            if (goodsInfo.isCheckGoods()) {
                mGoodsCount += goodsInfo.getQuantity();
                goodsSumPrice += goodsInfo.getQuantity() * goodsInfo.getSell_price();
            }
        }
        this.mGoodsSumPrice = goodsSumPrice;
        return goodsSumPrice;
    }

    public int getmGoodsCount() {
        return mGoodsCount;
    }

    public GoodsSumInterface getGoodsSumCall() {
        return mGoodsSumCall;
    }

    public void setGoodsSumCall(GoodsSumInterface goodsSumCall) {
        this.mGoodsSumCall = goodsSumCall;
    }

    public void setCheckAllOrNo(boolean isChecked) {
        int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            mDatas.get(i).setCheckGoods(isChecked);
        }
        this.notifyDataSetChanged();
    }

    public interface GoodsSumInterface {
        public void GoodsSumCallBack(int goodsSum, double goodsPrices);
    }
}
