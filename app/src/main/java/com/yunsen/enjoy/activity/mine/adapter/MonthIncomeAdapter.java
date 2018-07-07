package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.MonthAmountBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/6.
 */

public class MonthIncomeAdapter extends CommonAdapter<MonthAmountBean> {
    public MonthIncomeAdapter(Context context, int layoutId, List<MonthAmountBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MonthAmountBean data, int position) {
        holder.setText(R.id.month_income_time, data.getPayment_time());
        double payableAmount = data.getPayable_amount();
        double paymentAmount = data.getPayment_amount();
        holder.setText(R.id.month_income_income, "+" + payableAmount);
        holder.setText(R.id.month_income_pay, "-" + (payableAmount - paymentAmount));
    }
}
