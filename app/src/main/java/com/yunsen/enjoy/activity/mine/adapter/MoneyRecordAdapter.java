package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.WalletCashBean;
import com.yunsen.enjoy.model.WalletCashNewBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/7.
 */

public class MoneyRecordAdapter extends CommonAdapter<WalletCashNewBean> {
    private static final String TAG = "MoneyRecordAdapter";

    public MoneyRecordAdapter(Context context, int layoutId, List<WalletCashNewBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, WalletCashNewBean data, int position) {
        double expMoney = 0;
        try {
            expMoney = Double.valueOf(data.getBalance()) - Double.valueOf(data.getPrevious());
        } catch (NumberFormatException e) {
            Log.e(TAG, "convert: " + e.getMessage());
        }
        String payMoney = null;
        if (TextUtils.isEmpty(data.getIncome())) {
            payMoney = "-";
            holder.setText(R.id.money_record_money, payMoney + data.getExpense());
        } else {
            payMoney = "+";
            holder.setText(R.id.money_record_money, payMoney +data.getIncome() );
        }


        holder.setText(R.id.money_record_time, data.getAdd_time());
        holder.setText(R.id.money_record_balance, "余额：" + data.getBalance());
        holder.setText(R.id.money_record_title, data.getRemark());
    }
}
