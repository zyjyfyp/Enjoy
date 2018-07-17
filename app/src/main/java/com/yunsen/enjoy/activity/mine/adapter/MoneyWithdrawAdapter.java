package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.WalletCashBean;
import com.yunsen.enjoy.model.WithdrawLogData;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/7.
 * 提现记录
 */

public class MoneyWithdrawAdapter extends CommonAdapter<WithdrawLogData> {

    private String bankName;

    public MoneyWithdrawAdapter(Context context, int layoutId, List<WithdrawLogData> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, WithdrawLogData data, int position) {

        holder.setText(R.id.money_widthdraw_time, data.getUpdate_time());
        holder.setText(R.id.money_widthdraw_money, "+" + String.valueOf(data.getWithdraw_price()));
        holder.setText(R.id.money_widthdraw_balance, "余额：" + String.valueOf(data.getLast_balance()));
        bankName = data.getBank_name();
        String bankCard = data.getBank_card();
        if (bankCard != null & bankCard.length() > 4) {
            int startIndex = bankCard.length() - 4;
            bankCard = "(" + bankCard.substring(startIndex) + ")";
        }

        holder.setText(R.id.money_widthdraw_title, bankName + bankCard);
    }
}
