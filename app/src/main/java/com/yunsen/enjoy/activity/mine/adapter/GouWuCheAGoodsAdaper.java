package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.DefaultSpecItemBean;
import com.yunsen.enjoy.model.SpListData;

import java.util.ArrayList;
import java.util.List;

public class GouWuCheAGoodsAdaper extends BaseAdapter {

    private Context mContext;
    private List<CarDetails> list;
    private LayoutInflater mInflater;

    public GouWuCheAGoodsAdaper(List<CarDetails> list, Context context) {
        this.list = list;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (list.size() < 1) {
            return 0;
        } else {
            return list.size();
        }
    }


    @Override
    public CarDetails getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.goods_parts_item, parent, false);
            holder.img = (ImageView) convertView.findViewById(R.id.goods_parts_img);
            holder.title = (TextView) convertView.findViewById(R.id.goods_parts_title);
            holder.newPrice = (TextView) convertView.findViewById(R.id.goods_parts_sell_price);
            holder.oldPrice = (TextView) convertView.findViewById(R.id.goods_parts_market_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).getTitle());
        DefaultSpecItemBean defaultSpecItem = list.get(position).getDefault_spec_item();
        holder.oldPrice.setText("¥" + defaultSpecItem.getMarkePriceStr());
        holder.newPrice.setText("¥" + defaultSpecItem.getSellPriceStr());
        holder.oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(mContext)
                .load(list.get(position).getImg_url())
                .into(holder.img);
        return convertView;
    }


    class ViewHolder {
        ImageView img;
        TextView title;
        TextView oldPrice;
        TextView newPrice;
    }
}