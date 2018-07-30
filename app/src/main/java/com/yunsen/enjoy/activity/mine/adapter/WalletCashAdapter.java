package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.MyAssetsBean;
import com.yunsen.enjoy.model.WalletCashBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */

public class WalletCashAdapter extends CommonAdapter<MyAssetsBean> {
    public WalletCashAdapter(Context context, int layoutId, List<MyAssetsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MyAssetsBean data, int position) {
        holder.setText(R.id.withdraw_cash_time, data.getUpdate_time());
        holder.setText(R.id.withdraw_cash_title, data.getBank_name());
        holder.setText(R.id.withdraw_cash_money, String.valueOf(data.getWithdraw_price()));
        TextView textView = (TextView) holder.getView(R.id.withdraw_cash_finish);
        if (data.getStatus() == 0) {
            textView.setText("处理中");
            textView.setTextColor(Color.RED);
        } else {
            textView.setText("提现成功");
            textView.setTextColor(mContext.getResources().getColor(R.color.weixin));
        }
    }

    public void upData(List<MyAssetsBean> responseData) {
        mDatas.clear();
        if (responseData != null) {
            mDatas.addAll(responseData);
        }
        this.notifyDataSetChanged();
    }

    public boolean addData(List<MyAssetsBean> data) {
        if (data != null && data.size() > 0) {
            mDatas.addAll(data);
            this.notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }
}
