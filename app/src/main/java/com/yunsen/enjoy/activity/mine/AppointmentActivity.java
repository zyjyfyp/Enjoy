package com.yunsen.enjoy.activity.mine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.AppointmentAdapter;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.OrderDataBean;
import com.yunsen.enjoy.utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * 预约管理
 */
public class AppointmentActivity extends BaseFragmentActivity {


    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private AppointmentAdapter mAdapter;
    private ArrayList<OrderDataBean> mDatas;
    private int mPageIndex = 1;
    private String mUserId;
    private SharedPreferences mSp;

    @Override
    public int getLayout() {
        return R.layout.activity_move;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("预约看车");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mSp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserId = mSp.getString(SpConstants.USER_ID, "");
        mDatas = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AppointmentAdapter(AppointmentActivity.this, R.layout.appointment_item, mDatas);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void requestData() {
        HttpProxy.getAppointmentCarData(String.valueOf(mPageIndex), mUserId, new HttpCallBack<List<OrderDataBean>>() {
            @Override
            public void onSuccess(List<OrderDataBean> responseData) {
                mAdapter.upDatas(responseData);
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
        ButterKnife.unbind(this);
    }
}
