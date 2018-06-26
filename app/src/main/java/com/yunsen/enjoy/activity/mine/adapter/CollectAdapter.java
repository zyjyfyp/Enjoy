package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.fragment.buy.CarBrandAdapter;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ItemViewDelegate;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/26.
 */

public class CollectAdapter extends MultiItemTypeAdapter<GoodsData> {
    private LayoutInflater mInflater;
    private int mPointLayoutId;
    private int mGoodsLayoutId;
    private int mCarLayoutId;

    public CollectAdapter(Context context, List<GoodsData> datas) {
        super(context, datas);
        mInflater = LayoutInflater.from(context);
        mCarLayoutId = R.layout.goods_item_2;
        mGoodsLayoutId = R.layout.goods_item_3;
        mPointLayoutId = R.layout.goods_item_4;
        addItemViewDelegate(new ItemViewDelegate<GoodsData>() { //汽车
            @Override
            public int getItemViewLayoutId() {
                return mCarLayoutId;
            }

            @Override
            public boolean isForViewType(GoodsData item, int position) {
                return item.getChannel_id() == 7;
            }

            @Override
            public void convert(ViewHolder holder, GoodsData data, int position) {
                CollectAdapter.this.convertCar(holder, data, position);
            }
        });

        addItemViewDelegate(new ItemViewDelegate<GoodsData>() {//商品
            @Override
            public int getItemViewLayoutId() {
                return mGoodsLayoutId;
            }

            @Override
            public boolean isForViewType(GoodsData item, int position) {
                return item.getChannel_id() == 22;
            }

            @Override
            public void convert(ViewHolder holder, GoodsData data, int position) {
                CollectAdapter.this.convertGoods(holder, data, position);
            }
        });

        addItemViewDelegate(new ItemViewDelegate<GoodsData>() {  //积分
            @Override
            public int getItemViewLayoutId() {
                return mPointLayoutId;
            }

            @Override
            public boolean isForViewType(GoodsData item, int position) {
                return item.getChannel_id() == 13;
            }

            @Override
            public void convert(ViewHolder holder, GoodsData t, int position) {
                CollectAdapter.this.convertPoint(holder, t, position);
            }
        });


        addItemViewDelegate(new ItemViewDelegate<GoodsData>() {//其他
            @Override
            public int getItemViewLayoutId() {
                return mGoodsLayoutId;
            }

            @Override
            public boolean isForViewType(GoodsData item, int position) {
                return item.getChannel_id() != 22 && item.getChannel_id() != 13 && item.getChannel_id() != 7;
            }

            @Override
            public void convert(ViewHolder holder, GoodsData data, int position) {
                CollectAdapter.this.convertGoods(holder, data, position);
            }
        });
    }


    /**
     * 汽车
     *
     * @param holder
     * @param data
     * @param position
     */
    private void convertCar(ViewHolder holder, GoodsData data, int position) {
        holder.setText(R.id.goods_title_2, data.getTitle());
        holder.setText(R.id.goods_sub_title_2, data.getSubtitle());
        holder.setText(R.id.goods_money, data.getSell_price() + "万元");//sell_price
        holder.setText(R.id.goods_first_money, "首付" + data.getFirst_payment() + "万元");
        holder.setText(R.id.goods_address, data.getCity());
        Glide.with(mContext)
                .load(data.getImg_url())
                .centerCrop()
                .into(((ImageView) holder.getView(R.id.goods_left_img)));
    }

    /**
     * 商品
     *
     * @param holder
     * @param data
     * @param position
     */
    private void convertGoods(ViewHolder holder, GoodsData data, int position) {
        holder.setText(R.id.goods_title_2, data.getTitle());
        holder.setText(R.id.goods_sub_title_2, data.getSubtitle());
        holder.setText(R.id.goods_money, "¥" + data.getSellPriceStr());//sell_pric
        TextView textView = holder.getView(R.id.goods_first_money);
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.setText(R.id.goods_first_money, "¥" + data.getMarketpriceStr());
        Glide.with(mContext)
                .load(data.getImg_url())
                .centerCrop()
                .into(((ImageView) holder.getView(R.id.goods_left_img)));
    }

    /**
     * 积分
     *
     * @param holder
     * @param data
     * @param position
     */
    private void convertPoint(ViewHolder holder, GoodsData data, int position) {
        holder.setText(R.id.goods_title_2, data.getTitle());
        holder.setText(R.id.goods_sub_title_2, data.getSubtitle());
        holder.setText(R.id.goods_money, data.getMarket_price() + "积分");//sell_pric 7车  22商品 13积分
        TextView textView = holder.getView(R.id.goods_first_money);
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.setText(R.id.goods_first_money, "¥" + data.getSellPriceStr());
        Glide.with(mContext)
                .load(data.getImg_url())
                .centerCrop()
                .into(((ImageView) holder.getView(R.id.goods_left_img)));
    }


    /**
     * 如果返回的数据多余或等于0条 说明有更多数据
     *
     * @param responseData
     * @return
     */
    public boolean upData(List<GoodsData> responseData) {
        boolean flag = false;
        mDatas.clear();
        if (responseData != null && responseData.size() > 0) {
            mDatas.addAll(responseData);
            flag = true;
        }
        notifyDataSetChanged();
        return flag;
    }

    /**
     * @param datas
     * @return
     */
    public boolean addData(List<GoodsData> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            this.notifyDataSetChanged();
            return true;
        }
        return false;
    }


    public void removePosition(int position) {
        if (position >= 0 && position < mDatas.size()) {
            mDatas.remove(position);
            this.notifyItemRemoved(position);
        }
    }
}
