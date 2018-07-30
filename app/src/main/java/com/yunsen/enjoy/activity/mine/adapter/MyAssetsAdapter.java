package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.MyAssetsActivity;
import com.yunsen.enjoy.model.MyAssetsBean;

import java.util.ArrayList;
import java.util.List;

public class MyAssetsAdapter extends BaseAdapter {

    private ArrayList<MyAssetsBean> list;
    private Context context;

    public MyAssetsAdapter(ArrayList<MyAssetsBean> list, Context context) {
        this.context = context;
        this.list = list;
    }

    public void putData(ArrayList<MyAssetsBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
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

    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == 0) {
            convertView = LinearLayout.inflate(context, R.layout.listitem_myasste, null);
            TextView tv_income = (TextView) convertView.findViewById(R.id.tv_income);
            TextView tv_add_time = (TextView) convertView.findViewById(R.id.tv_add_time);
            TextView tv_remark = (TextView) convertView.findViewById(R.id.tv_remark);
            if (MyAssetsActivity.fund_id.equals("2")) {
                if (!list.get(position).expense.equals("")) {
                    tv_income.setText("-" + list.get(position).expense + "");
                } else if (!list.get(position).income.equals("")) {
                    tv_income.setText("+" + list.get(position).income + "");
                }
            } else {
                if (!list.get(position).expense.equals("")) {
                    tv_income.setText("-" + list.get(position).expense + "元");
                } else if (!list.get(position).income.equals("")) {
                    tv_income.setText("+" + list.get(position).income + "元");
                }
            }
            tv_remark.setText(list.get(position).remark);
            tv_add_time.setText(list.get(position).add_time);
        } else {
            MyAssetsBean data = list.get(position);
            ViewHolder holder = null;
//            if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.withdraw_cash_detail, null);
            holder.titletv = convertView.findViewById(R.id.withdraw_cash_title);
            holder.timeTv = convertView.findViewById(R.id.withdraw_cash_time);
            holder.moneyTv = convertView.findViewById(R.id.withdraw_cash_money);
            holder.messageTv = convertView.findViewById(R.id.withdraw_cash_finish);
//                convertView.setTag(holder);
//            } else {
//                holder = ((ViewHolder) convertView.getTag());
//            }
            holder.moneyTv.setText(String.valueOf(data.getWithdraw_price()));
            holder.titletv.setText(data.getBank_name());
            holder.timeTv.setText(data.getUpdate_time());
            if (data.getStatus() == 0) {
                holder.messageTv.setText("处理中");
                holder.messageTv.setTextColor(Color.RED);
            } else {
                holder.messageTv.setText("提现成功");
                holder.messageTv.setTextColor(context.getResources().getColor(R.color.weixin));
            }

        }
        return convertView;
    }

    class ViewHolder {
        private TextView titletv;
        private TextView moneyTv;
        private TextView messageTv;
        private TextView timeTv;

    }

    public void addData(List<MyAssetsBean> data) {
        if (data != null) {
            list.addAll(data);
        }
        this.notifyDataSetChanged();
    }

    public void upData(List<MyAssetsBean> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        String bankName = list.get(position).getBank_name();
        if (TextUtils.isEmpty(bankName)) {
            return 0;
        } else {
            return 1;
        }
    }
}
