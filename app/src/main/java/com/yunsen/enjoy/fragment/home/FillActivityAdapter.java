package com.yunsen.enjoy.fragment.home;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.DefaultSpecItemBean;
import com.yunsen.enjoy.model.OrderInfo;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.layout.CountDownLayout;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.StringUtils;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/23.
 */

public class FillActivityAdapter extends CommonAdapter<CarDetails> {
    private long mCurrentTime = 0;
//    private long mStartTime;
//    private long mEndTime;

    public FillActivityAdapter(Context context, int layoutId, List<CarDetails> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    protected void convert(ViewHolder holder, CarDetails carDetails, int position) {
        final CarDetails data = carDetails;
        String imgUrl = carDetails.getImg_url();
        ImageView imageView = (ImageView) holder.getView(R.id.fill_left_img);
        TextView marketTv = (TextView) holder.getView(R.id.fill_market_price);
//        ProgressBar progressBar = (ProgressBar) holder.getView(R.id.fill_progress_bar);
        CountDownLayout countDownLayout = (CountDownLayout) holder.getView(R.id.count_down_layout);
        TextView noActivityTv = (TextView) holder.getView(R.id.no_activity_tv);

        marketTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(mContext)
                .load(imgUrl)
                .into(imageView);
        holder.setText(R.id.fill_title, carDetails.getTitle());
        DefaultSpecItemBean defaultSpecItem = carDetails.getDefault_spec_item();
        holder.setText(R.id.fill_market_price, "¥" + defaultSpecItem.getMarkePriceStr());
        holder.setText(R.id.fill_sell_price, "¥" + defaultSpecItem.getSellPriceStr());

        String startString = carDetails.getStart_time();
        String endString = carDetails.getEnd_time();
        TextView fastTv = (TextView) holder.getView(R.id.fast_buy_tv);

        if (startString != null && endString != null) {
            long startTime = StringUtils.toDate(startString) != null ? StringUtils.toDate(startString).getTime() : 0;
            long endTime = StringUtils.toDate(endString) != null ? StringUtils.toDate(endString).getTime() : 0;
            startTime = startTime / 1000;
            endTime = endTime / 1000;
//            countDownLayout.setTag(position);
            if (mCurrentTime < startTime) {
                countDownLayout.setVisibility(View.VISIBLE);
                noActivityTv.setVisibility(View.GONE);
                //活动未开始
                fastTv.setText("即将开抢");
                countDownLayout.setData(startTime, endTime, false);
                countDownLayout.startCountDown(position + 100, mCurrentTime);
                fastTv.setBackground(mContext.getResources().getDrawable(R.drawable.btn_red_no));
                fastTv.setClickable(false);
            } else if (mCurrentTime < endTime) {
                //活动未结束 活动进行中
                countDownLayout.setVisibility(View.VISIBLE);
                noActivityTv.setVisibility(View.GONE);
                fastTv.setText("马上抢购");
                fastTv.setClickable(true);
                countDownLayout.setData(startTime, endTime, true);
                countDownLayout.startCountDown(position + 100, mCurrentTime);
                fastTv.setBackground(mContext.getResources().getDrawable(R.drawable.btn_red));
            } else {
                //活动结束
                countDownLayout.setVisibility(View.GONE);
                noActivityTv.setVisibility(View.VISIBLE);
                noActivityTv.setText(startString + "-" + endString);
                fastTv.setText("已结束");
                fastTv.setClickable(false);
                fastTv.setEnabled(false);
                fastTv.setBackground(mContext.getResources().getDrawable(R.drawable.btn_red_no));
            }
            fastTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!AccountUtils.hasLogin()) {
                        UIHelper.showUserLoginActivity(mContext);
                    } else if (!AccountUtils.hasBoundPhone()) {
                        UIHelper.showBundPhoneActivity(mContext);
                    } else {
                        HttpProxy.getAddShoppingBuy(String.valueOf(data.getId()), "" + data.getDefault_spec_item().getGoods_id(), new HttpCallBack<OrderInfo>() {
                            @Override
                            public void onSuccess(OrderInfo responseData) {
                                if (responseData != null) {
                                    UIHelper.showMyOrderConfrimActivity(mContext, responseData.getBuy_no());
                                }
                            }

                            @Override
                            public void onError(Request request, Exception e) {

                            }
                        });
                    }


                }
            });
        }


//        holder.setText(R.id.fill_sell_state, "已销售 20%");
//        progressBar.setProgress(20);
    }

    public void upData(List<CarDetails> datas, long currentTime) {
        mDatas.clear();
        this.mCurrentTime = currentTime;
        if (datas != null) {
            mDatas.addAll(datas);
        }
        this.notifyDataSetChanged();
    }

    public void upTime(long currentTime) {
        this.mCurrentTime = currentTime;
        this.notifyDataSetChanged();
    }

    private static final String TAG = "FillActivityAdapter";

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ((CountDownLayout) holder.getView(R.id.count_down_layout)).stopMessage();
        Log.e(TAG, "onViewDetachedFromWindow: ");
    }

    /**
     * 添加view
     *
     * @param datas
     * @param currentTime
     * @return 是否还有更多
     */
    public boolean addDatas(List<CarDetails> datas, long currentTime) {
        this.mCurrentTime = currentTime;
        int start = mDatas.size();
        int count = 0;
        boolean flag;
        if (datas != null) {
            mDatas.addAll(datas);
            count = datas.size();
            flag = true;
        } else {
            flag = false;
        }
        this.notifyItemRangeInserted(start, count);
        return flag;
    }
}
