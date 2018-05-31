package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.WalletCashBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */

public class WalletCashAdapter extends CommonAdapter<WalletCashBean> {
    public WalletCashAdapter(Context context, int layoutId, List<WalletCashBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, WalletCashBean data, int position) {
        holder.setText(R.id.withdraw_cash_time, data.getUpdate_time());
        holder.setText(R.id.withdraw_cash_title, data.getRemark());
        holder.setText(R.id.withdraw_cash_money, data.getFrom_expenseStr());
//        holder.setText(R.id.withdraw_cash_finish,);
    }

    public void upData(List<WalletCashBean> responseData) {
        mDatas.clear();
        if (responseData != null) {
            mDatas.addAll(responseData);
        }
        this.notifyDataSetChanged();
    }

    public boolean addData(List<WalletCashBean> data) {
        if (data != null && data.size() > 0) {
            mDatas.addAll(data);
            this.notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }
}
