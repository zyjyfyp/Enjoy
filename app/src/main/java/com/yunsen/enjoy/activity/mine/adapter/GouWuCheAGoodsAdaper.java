package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
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
import com.yunsen.enjoy.model.SpListData;

import java.util.ArrayList;

public class GouWuCheAGoodsAdaper extends BaseAdapter {

    private Context mContext;
    private ArrayList<SpListData> list;
    private LayoutInflater mInflater;
    private int clickTemp = 0;
    public static boolean type = false;

    public GouWuCheAGoodsAdaper(ArrayList<SpListData> list, Context context) {
        try {

            //		System.out.println("position=====1================");
            this.list = list;
            this.mContext = context;
            mInflater = LayoutInflater.from(context);
            //		System.out.println("position=====2================");
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        if (list.size() < 1) {
            return 0;
        } else {
            return list.size();
        }
    }

    //	public void setSeclection(int position) {
    //		clickTemp = position;
    //	}

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        try {
            //			System.out.println("position====================="+position);
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.gridview_gouwuche_item, null);
                holder.img = (ImageView) convertView.findViewById(R.id.img);
                holder.tv_biaoti = (TextView) convertView.findViewById(R.id.tv_biaoti);
                holder.tv_jifengduihuan = (TextView) convertView.findViewById(R.id.tv_jiaguo);
                //			holder.tv_shichangjia = (TextView) convertView.findViewById(R.id.tv_shichangjia);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_biaoti.setText(list.get(position).title);
            holder.tv_jifengduihuan.setText("¥" + list.get(position).sell_price);
            //		holder.tv_shichangjia.setText("市场价:¥"+list.get(position).market_price);
            //		holder.tv_shichangjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
            Glide.with(mContext)
                    .load(URLConstants.REALM_NAME_HTTP + list.get(position).img_url)
                    .into(holder.img);
            type = true;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return convertView;
    }


    class ViewHolder {
        ImageView img;
        TextView tv_biaoti, yh2;
        TextView tv_jifengduihuan;
        TextView tv_shichangjia;
        RadioButton radioButton;
    }
}