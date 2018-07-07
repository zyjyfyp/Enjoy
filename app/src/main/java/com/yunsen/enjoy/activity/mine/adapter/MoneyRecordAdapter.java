package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;

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
    protected void convert(ViewHolder holder, WalletCashBean walletCashBean, int position) {

    }
}
