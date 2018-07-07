package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.ListcumulativeIncomeBean;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/6.
 */

public class MineAchieveAdapter extends CommonAdapter<ListcumulativeIncomeBean> {
    public MineAchieveAdapter(Context context, int layoutId, List<ListcumulativeIncomeBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ListcumulativeIncomeBean data, int position) {
                    holder.setText(R.id.mine_achieve_name,data.getConsumer_name());
                    holder.setText(R.id.mine_achieve_id,data.getConsumer_id());
                    holder.setText(R.id.mine_achieve_income,""+data.getMonth_income());
    }
}
