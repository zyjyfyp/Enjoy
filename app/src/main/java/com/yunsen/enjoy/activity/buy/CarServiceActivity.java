package com.yunsen.enjoy.activity.buy;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.model.CarServiceModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/27.
 */

/**
 * 汽车服务
 */
public class CarServiceActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;


    @Override
    public int getLayout() {
        return R.layout.activity_car_service;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("乐享汽车服务");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }


    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public List<CarServiceModel> getDatas() {
        ArrayList<CarServiceModel> list = new ArrayList<>();
        list.add(new CarServiceModel(R.mipmap.ic_launcher_round, "基础保质",
                "交易完成后，在1万或两万内，乐享为本车提供发动机，变速箱两大系统的售后保质"));
        list.add(new CarServiceModel(R.mipmap.ic_launcher_round,
                "基础保质", "14天内如果先退款，联系您的专属购车管家，即可以为您提供完善的售后服务"));
        list.add(new CarServiceModel(R.mipmap.ic_launcher_round, "基础保质",
                "资深行业权威专家，完后两次专业检测，层层为您把关排除隐患，精选放心的车源，拒绝重大事故车、水泡车、火烧车"));
        return list;
    }
}
