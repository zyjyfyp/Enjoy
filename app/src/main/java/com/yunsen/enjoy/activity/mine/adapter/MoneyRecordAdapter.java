package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.WalletCashBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/7.
 */

public class MoneyRecordAdapter extends CommonAdapter<WalletCashBean> {
    public MoneyRecordAdapter(Context context, int layoutId, List<WalletCashBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, WalletCashBean data, int position) {
        holder.setText(R.id.money_record_time, data.getUpdate_time());
        holder.setText(R.id.money_record_money, data.getFrom_expenseStr());
        holder.setText(R.id.money_record_balance, "余额：" + String.valueOf(data.getFrom_balance()));
        holder.setText(R.id.money_record_title, data.getRemark());
    }
}
