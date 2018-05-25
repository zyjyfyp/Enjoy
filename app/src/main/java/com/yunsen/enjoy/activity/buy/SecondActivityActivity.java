package com.yunsen.enjoy.activity.buy;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.http.HttpCallBack2;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpHomeUiEvent;
import com.yunsen.enjoy.ui.layout.SecondActivityLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/25.
 */

public class SecondActivityActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.secondLayout)
    SecondActivityLayout secondLayout;

    @Override
    public int getLayout() {
        return R.layout.activity_second_activity;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        secondLayout.setTopTitleVisibility(View.GONE);
        actionBarTitle.setText("秒杀活动");
//        secondLayout.setData();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void requestData() {
        HttpProxy.getSecondActivityMoreData(1, new HttpCallBack2<List<CarDetails>>() {
            @Override
            public void onSuccess(List<CarDetails> responseData, Object otherData) {
                String data = (String) otherData;
                secondLayout.setData(responseData, Long.parseLong(data));
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        secondLayout.removeMessage();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent2(UpHomeUiEvent event) {
        if (event.getEventId() == EventConstants.HOME_UI_UP) {
            secondLayout.upTimeUi(event.getCurrentTime());
        }
    }

}
