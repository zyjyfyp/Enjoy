package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.WareInformationActivity;
import com.yunsen.enjoy.activity.mine.Webview1;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.CollectWareData;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.Utils;

import java.util.ArrayList;

public class MyCollectWareAdapter extends BaseAdapter {

    private ArrayList<CollectWareData> list;
    private Context context;
    private Handler handler;
    private int mRightWidth = 0;

    public MyCollectWareAdapter(Context contextr,
                                ArrayList<CollectWareData> list, int mRightWidth, Handler handler) {

        this.context = contextr;
        this.list = list;
        this.mRightWidth = mRightWidth;
        this.handler = handler;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LinearLayout.inflate(context,
                    R.layout.listitem_collect_ware, null);
        }
        try {

            ImageView image = (ImageView) convertView
                    .findViewById(R.id.img_ware);
            TextView tv_name = (TextView) convertView
                    .findViewById(R.id.tv_ware_name);
            TextView tv_price = (TextView) convertView
                    .findViewById(R.id.tv_ware_price);
            TextView tv_total = (TextView) convertView
                    .findViewById(R.id.tv_ware_total);
            RelativeLayout item_left = (RelativeLayout) convertView
                    .findViewById(R.id.item_left);
            LinearLayout item_right = (LinearLayout) convertView
                    .findViewById(R.id.item_right);
            Button btn_delete = (Button) convertView
                    .findViewById(R.id.btn_delete);

            LinearLayout.LayoutParams lp1 = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            item_left.setLayoutParams(lp1);
            LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth,
                    LayoutParams.MATCH_PARENT);
            item_right.setLayoutParams(lp2);

            /**
             * 删除
             */
            btn_delete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = list.get(position).id;
                    handler.sendMessage(msg);
                }
            });

            item_left.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    System.out.println("id============1========");

                    UIHelper.showCarDetailsActivity(context, String.valueOf(list.get(position).article_id));
                }
            });

            if (list.get(position).datatype.equals("0")) {
                tv_price.setText(list.get(position).add_time);
                tv_price.setTextColor(Color.BLACK);
            } else {
                tv_price.setText("￥" + list.get(position).price);
            }

            tv_name.setText(list.get(position).title);

            Glide.with(context)
                    .load(URLConstants.REALM_URL + list.get(position).img_url)
                    .into(image);

        } catch (Exception e) {

            e.printStackTrace();
        }
        return convertView;
    }

    public void setShow(boolean bool, View v) {
        if (bool) {
            v.startAnimation(Utils.mShowActionRight());
            v.setVisibility(View.VISIBLE);
        } else {
            if (v.getVisibility() == View.VISIBLE) {
                v.startAnimation(Utils.mHiddenActionRights());
                v.setVisibility(View.GONE);
            }
        }
    }
}
