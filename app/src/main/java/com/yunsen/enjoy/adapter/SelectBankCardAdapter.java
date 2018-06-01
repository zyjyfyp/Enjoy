package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.BindCardBean;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/1.
 */

public class SelectBankCardAdapter extends CommonAdapter<BindCardBean> {

    public SelectBankCardAdapter(Context context, int layoutId, List<BindCardBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, BindCardBean data, int position) {
//        ImageView imageView = (ImageView) holder.getView(R.id.select_bank_item_icon);
        String bankCard = data.getBank_card();
        if (bankCard != null) {
            bankCard = bankCard.trim();
            if (bankCard.length() > 4) {
                bankCard = bankCard.substring(bankCard.length() - 4);
            }
        }
        String bankName = data.getBank_name();
        holder.setText(R.id.select_bank_item_title, bankName + "(" + bankCard + ")");
//        holder.setText(R.id.select_bank_item_sub, bankName + "(" + bankCard + ")");
        CheckBox checkBox = (CheckBox) holder.getView(R.id.select_bank_item_cb);
        checkBox.setChecked(data.getIs_default() == 1);
    }

    public void upDatas(List<BindCardBean> datas) {
        this.mDatas.clear();
        if (datas != null) {
            this.mDatas.addAll(datas);
        }
        this.notifyDataSetChanged();
    }

    public boolean addDatas(List<BindCardBean> datas) {
        if (datas != null) {
            mDatas.addAll(datas);
            this.notifyDataSetChanged();
            return true;
        }
        return false;
    }
}
