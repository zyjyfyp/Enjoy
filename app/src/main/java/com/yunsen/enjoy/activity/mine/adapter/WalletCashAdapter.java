package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;

import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */

public class WalletCashAdapter extends CommonAdapter<String> {
    public WalletCashAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {

    }
}
