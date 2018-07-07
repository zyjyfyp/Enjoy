package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.BindCardBean;
import com.yunsen.enjoy.widget.GlideCircleTransform;
import com.yunsen.enjoy.widget.GlideRoundTransform;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/7/7.
 */

public class WalletBankCardAdapter extends CommonAdapter<BindCardBean> {

    private Random mRandom;
    final int min = 0;
    final int max = 3;

    public WalletBankCardAdapter(Context context, int layoutId, List<BindCardBean> datas) {
        super(context, layoutId, datas);

        mRandom = new Random();
    }

    @Override
    protected void convert(ViewHolder holder, BindCardBean bindCardBean, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.wallet_item_img);
        LinearLayout bgLayout = (LinearLayout) holder.getView(R.id.wallet_item_bg_layout);
        Glide.with(mContext)
                .load(R.mipmap.bank_icon)
                .transform(new GlideRoundTransform(mContext))
                .into(imageView);
        holder.setText(R.id.wallet_item_title, bindCardBean.getBank_name());
        holder.setText(R.id.wallet_item_card_tv, bindCardBean.getBank_card());
        int num = mRandom.nextInt(max) % (max - min + 1) + min;
        switch (num) {
            case 0:
                bgLayout.setBackgroundResource(R.drawable.violet_bank_bg);
                break;
            case 1:
                bgLayout.setBackgroundResource(R.drawable.pink_bank_bg);
                break;
            default:
                bgLayout.setBackgroundResource(R.drawable.blue_bank_bg);
                break;
        }


    }
}
