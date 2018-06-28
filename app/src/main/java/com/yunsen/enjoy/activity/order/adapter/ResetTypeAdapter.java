package com.yunsen.enjoy.activity.order.adapter;

import android.content.Context;
import android.view.View;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.ResetTypeModel;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/6/28.
 */

public class ResetTypeAdapter extends CommonAdapter<ResetTypeModel> {
    private ResetTypeModel mCurrentModel;

    public ResetTypeAdapter(Context context, int layoutId, List<ResetTypeModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ResetTypeModel resetTypeModel, int position) {
        holder.setText(R.id.reset_type_tv, resetTypeModel.getTitle());
        View view = holder.getView(R.id.reset_type_tv);
        view.setSelected(resetTypeModel.isCheck());
        if (resetTypeModel.isCheck()) {
            mCurrentModel = resetTypeModel;
        }
    }

    /**
     * index 下标
     *
     * @param index
     */
    public void setSelectView(int index) {
        int size = mDatas.size();
        if (size > index) {
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    mDatas.get(i).setCheck(true);
                } else {
                    mDatas.get(i).setCheck(false);
                }
            }
        }
        notifyDataSetChanged();
    }

    public ResetTypeModel getCurrentTypeModel() {
        return mCurrentModel;
    }
}
