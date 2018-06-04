package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.widget.CheckBox;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.BindCardTypeBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/4.
 */

public class BindCardTypeAdapter extends CommonAdapter<BindCardTypeBean> {

    public BindCardTypeAdapter(Context context, int layoutId, List<BindCardTypeBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, BindCardTypeBean data, int position) {
        holder.setText(R.id.bind_card_type_title, data.getName());
        CheckBox checkBox = (CheckBox) holder.getView(R.id.bind_card_type_cb);
        checkBox.setChecked(data.isCheck());
    }
}
