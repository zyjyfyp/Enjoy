package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.ShopCartData;

import java.util.ArrayList;

public class ShopingCartOrderAdapter extends BaseAdapter {
    private ArrayList<ShopCartData> list;
    private LayoutInflater inflater = null;
    private Context context;
    private ShopCartData cartData;
    AQuery aQuery;

    // 构造器
    public ShopingCartOrderAdapter(ArrayList<ShopCartData> list, Context context) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        aQuery = new AQuery(context);
        // 初始化数据
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {


        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public static class ViewHolder {
        TextView tv;
        public CheckBox cb;

        ImageButton btn_order_cancle;
        ImageView img_ware;
        TextView tv_warename;
        TextView tv_color;
        TextView tv_1;
        TextView tv_2;
        TextView tv_size, tv_quantity;
        public TextView tv_guige; //属性
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        try {
            ViewHolder holder = null;
            if (convertView == null) {

                System.out.println("6================");
                holder = new ViewHolder();
                convertView = RelativeLayout.inflate(context, R.layout.listitem_shopping_cart_order, null);
                //			convertView = inflater.inflate(R.layout.listviewitem, null);
                holder.img_ware = (ImageView) convertView.findViewById(R.id.img_ware);
                holder.tv_warename = (TextView) convertView.findViewById(R.id.tv_ware_name);
                holder.tv_color = (TextView) convertView.findViewById(R.id.tv_color);
                //			holder.tv_1 = (TextView) convertView.findViewById(R.id.tv_1);
                //			holder.tv_2 = (TextView) convertView.findViewById(R.id.tv_2);
                //			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
                holder.tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
                holder.tv_guige = (TextView) convertView.findViewById(R.id.tv_guige);
                convertView.setTag(holder);

            } else {
                // 取出holder
                holder = (ViewHolder) convertView.getTag();
            }

            ShopCartData shopCartData = list.get(position);
            holder.tv_warename.setText(shopCartData.getTitle());
            holder.tv_color.setText("¥" + shopCartData.getSell_price());
            //		holder.tv_size.setText("¥" + list.get(position).getMarket_price());
            int zhoull = shopCartData.getQuantity();
            System.out.println("=============00=" + zhoull);
            holder.tv_quantity.setText("x" + String.valueOf(shopCartData.getQuantity()));

            //		ImageLoader imageLoaderll=ImageLoader.getInstance();
            String zhou = shopCartData.getImg_url();
            //		imageLoaderll.displayImage(RealmName.REALM_NAME_HTTP + zhou,holder.img_ware);
            aQuery.id(holder.img_ware).image(URLConstants.REALM_NAME_HTTP + zhou);
            holder.tv_guige.setText(shopCartData.spec_text);


        } catch (Exception e) {

            e.printStackTrace();
        }

        return convertView;
    }


}
