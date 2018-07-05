package com.yunsen.enjoy.activity.pay.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.RechargeModel;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/5.
 */

public class RechargeMoneyAdapter extends CommonAdapter<RechargeModel> {
    public RechargeMoneyAdapter(Context context, int layoutId, List<RechargeModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, RechargeModel rechargeModel, int position) {
        TextView textView = (TextView) holder.getView(R.id.recharge_tv);
        textView.setText("充" + rechargeModel.getMoney() + "元");
        if (rechargeModel.isCheck()) {
            textView.setSelected(true);
        } else {
            textView.setSelected(false);
        }
    }

    public void setSelected(int position) {
        int size = mDatas.size();
        if (position < size) {
            for (int i = 0; i < size; i++) {
                if (position == i) {
                    mDatas.get(i).setCheck(true);
                } else {
                    mDatas.get(i).setCheck(false);
                }
            }
        }
        this.notifyDataSetChanged();
    }
}
