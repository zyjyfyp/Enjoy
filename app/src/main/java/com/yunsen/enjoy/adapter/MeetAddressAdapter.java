package com.yunsen.enjoy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.OrderDataBean;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 */

public class MeetAddressAdapter extends CommonAdapter<SProviderModel> {

    public MeetAddressAdapter(Context context, int layoutId, List<SProviderModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, SProviderModel sProviderModel, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.meet_address_item_img);
        String imgUrl = sProviderModel.getImg_url();
        Glide.with(mContext)
                .load(imgUrl)
                .into(imageView);
        holder.setText(R.id.meet_address_tv, sProviderModel.getAddress());
        holder.setText(R.id.meet_address_item_title, sProviderModel.getName());
    }

    public void upDatas(List<SProviderModel> datas) {
        this.mDatas.clear();
        if (datas != null) {
            this.mDatas.addAll(datas);
        }
        this.notifyDataSetChanged();
    }

    public boolean addDatas(List<SProviderModel> datas) {
        if (datas != null) {
            mDatas.addAll(datas);
            this.notifyDataSetChanged();
            return true;
        }
        return false;
    }
}
