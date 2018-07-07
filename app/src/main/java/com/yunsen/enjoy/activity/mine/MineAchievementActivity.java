package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.MineAchieveAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.AchieveInfoBean;
import com.yunsen.enjoy.model.ListcumulativeIncomeBean;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/7/4.
 */

public class MineAchievementActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.recycler_achievement)
    RecyclerView recyclerView;
    private int mPageIndex = 1;
    private String mUrl;
    private MineAchieveAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_mine_achievement;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("我的业绩");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mUrl = getIntent().getStringExtra(Constants.MINE_ACHIEVE_URL_KEY);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MineAchieveAdapter(this, R.layout.mine_achieve_item, new ArrayList<ListcumulativeIncomeBean>());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void requestData() {
        HttpProxy.achievementContentRequest(mUrl, String.valueOf(mPageIndex), new HttpCallBack<AchieveInfoBean>() {
            @Override
            public void onSuccess(AchieveInfoBean responseData) {
                mAdapter.upBaseDatas(responseData.getListcumulative_income());
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
}
