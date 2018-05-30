package com.yunsen.enjoy.ui.layout;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.DefaultSpecItemBean;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class IntegralChangeLayout extends LinearLayout implements View.OnClickListener {
    TextView moreTv;
    private Context mContext;
    private View rootView;

    private List<CarDetails> mDatas;
    private ImageView homeImgViews[] = new ImageView[5];
    private TextView homePointOldPrices[] = new TextView[5];
    private TextView homePointTitles[] = new TextView[5];
    private TextView homePointNewPoints[] = new TextView[5];

    public IntegralChangeLayout(Context context) {
        super(context);
        initView(context);
    }


    public IntegralChangeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public IntegralChangeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        mDatas = new ArrayList<>();
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.integral_change_layout, this);
        moreTv = rootView.findViewById(R.id.more_tv);
        homeImgViews[0] = rootView.findViewById(R.id.hone_point_img_0);
        homeImgViews[1] = rootView.findViewById(R.id.hone_point_img_1);
        homeImgViews[2] = rootView.findViewById(R.id.hone_point_img_2);
        homeImgViews[3] = rootView.findViewById(R.id.hone_point_img_3);
        homeImgViews[4] = rootView.findViewById(R.id.hone_point_img_4);

        homePointOldPrices[0] = rootView.findViewById(R.id.home_point_old_price_0);
        homePointOldPrices[1] = rootView.findViewById(R.id.home_point_old_price_1);
        homePointOldPrices[2] = rootView.findViewById(R.id.home_point_old_price_2);
        homePointOldPrices[3] = rootView.findViewById(R.id.home_point_old_price_3);
        homePointOldPrices[4] = rootView.findViewById(R.id.home_point_old_price_4);

        homePointOldPrices[0].getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        homePointOldPrices[1].getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        homePointOldPrices[2].getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        homePointOldPrices[3].getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        homePointOldPrices[4].getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        homePointTitles[0] = rootView.findViewById(R.id.home_point_title_0);
        homePointTitles[1] = rootView.findViewById(R.id.home_point_title_1);
        homePointTitles[2] = rootView.findViewById(R.id.home_point_title_2);
        homePointTitles[3] = rootView.findViewById(R.id.home_point_title_3);
        homePointTitles[4] = rootView.findViewById(R.id.home_point_title_4);

        homePointNewPoints[0] = rootView.findViewById(R.id.home_point_new_point_0);
        homePointNewPoints[1] = rootView.findViewById(R.id.home_point_new_point_1);
        homePointNewPoints[2] = rootView.findViewById(R.id.home_point_new_point_2);
        homePointNewPoints[3] = rootView.findViewById(R.id.home_point_new_point_3);
        homePointNewPoints[4] = rootView.findViewById(R.id.home_point_new_point_4);
        initListener();
    }

    private void initListener() {
        homeImgViews[0].setOnClickListener(this);
        homeImgViews[1].setOnClickListener(this);
        homeImgViews[2].setOnClickListener(this);
        homeImgViews[3].setOnClickListener(this);
        homeImgViews[4].setOnClickListener(this);
        moreTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        CarDetails carDetails = null;
        if (mDatas == null || mDatas.size() == 0) {
            return;
        }
        switch (view.getId()) {
            case R.id.more_tv:
                UIHelper.showExchangePointActivity(mContext);
                break;
            case R.id.hone_point_img_0:
                if (mDatas.size() > 1) {
                    carDetails = mDatas.get(0);
                    UIHelper.showGoodsDescriptionActivity(mContext, String.valueOf(carDetails.getId()), carDetails.getTitle(), Constants.POINT_BUY);
                }
                break;
            case R.id.hone_point_img_1:
                if (mDatas.size() > 2) {
                    carDetails = mDatas.get(1);
                    UIHelper.showGoodsDescriptionActivity(mContext, String.valueOf(carDetails.getId()), carDetails.getTitle(), Constants.POINT_BUY);
                }
                break;
            case R.id.hone_point_img_2:
                if (mDatas.size() > 3) {
                    carDetails = mDatas.get(2);
                    UIHelper.showGoodsDescriptionActivity(mContext, String.valueOf(carDetails.getId()), carDetails.getTitle(), Constants.POINT_BUY);
                }
                break;
            case R.id.hone_point_img_3:
                if (mDatas.size() > 4) {
                    carDetails = mDatas.get(3);
                    UIHelper.showGoodsDescriptionActivity(mContext, String.valueOf(carDetails.getId()), carDetails.getTitle(), Constants.POINT_BUY);
                }
                break;
            case R.id.hone_point_img_4:
                if (mDatas.size() > 5) {
                    carDetails = mDatas.get(4);
                    UIHelper.showGoodsDescriptionActivity(mContext, String.valueOf(carDetails.getId()), carDetails.getTitle(), Constants.POINT_BUY);
                }
                break;
        }
    }

    /**
     * 添加数据
     *
     * @param datas
     */
    public void setData(List<CarDetails> datas) {
        if (datas != null) {
            mDatas.addAll(datas);
        }
        initData();

    }

    private void initData() {
        int min = Math.min(mDatas.size(), 5);
        for (int i = 0; i < min; i++) {
            CarDetails model = mDatas.get(i);
            Glide.with(mContext)
                    .load(model.getImg_url())
                    .into(homeImgViews[i]);
            homePointTitles[i].setText(model.getTitle());
            DefaultSpecItemBean defaultSpecItem = model.getDefault_spec_item();
            homePointOldPrices[i].setText("¥" + defaultSpecItem.getMarkePriceStr());
            homePointNewPoints[i].setText(defaultSpecItem.getExchange_point() + "积分");
        }
    }

}
