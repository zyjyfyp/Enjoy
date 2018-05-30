package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.UserAddressData;

import java.util.ArrayList;


public class MyAddressManagerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UserAddressData> list = null;

    public MyAddressManagerAdapter(Context context,
                                   ArrayList<UserAddressData> list) {

        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int arg0) {

        return arg0;
    }

    @Override
    public long getItemId(int arg0) {

        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = RelativeLayout.inflate(context,
                    R.layout.listitem_address, null);
        }

        TextView tv_user_name = (TextView) convertView
                .findViewById(R.id.tv_user_name);
        TextView tv_user_address = (TextView) convertView
                .findViewById(R.id.tv_user_address);
        TextView tv_user_phone = (TextView) convertView
                .findViewById(R.id.tv_user_phone);
        tv_user_name.setText("收货人：" + list.get(position).user_accept_name);
        tv_user_phone.setText(list.get(position).user_mobile);
        String user_area = list.get(position).user_area;
        String user_address = list.get(position).user_address;
        tv_user_address.setText("地址：" + list.get(position).province +
                list.get(position).city + user_area + user_address);
        return convertView;
    }
}
