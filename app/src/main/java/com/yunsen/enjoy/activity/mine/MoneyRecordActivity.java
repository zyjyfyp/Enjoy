package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.MoneyRecordAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.WalletCashBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/7/7.
 */

public class MoneyRecordActivity extends BaseFragmentActivity {

    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.recycler_record)
    RecyclerView recyclerRecord;
    private int mPageIndex = 1;
    private ArrayList<WalletCashBean> mDatas;
    private MoneyRecordAdapter mAdapter;
    private String mFundId;

    @Override
    public int getLayout() {
        return R.layout.activity_money_record;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mFundId = getIntent().getStringExtra(Constants.ACT_TYPE_KEY);

        if (mFundId == Constants.CONSUMPTION_RECORD) {
            actionBarTitle.setText("消费记录");
        } else {
            actionBarTitle.setText("提现记录");
        }

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        recyclerRecord.setLayoutManager(new LinearLayoutManager(this));
        mDatas = new ArrayList<>();
        mAdapter = new MoneyRecordAdapter(this, R.layout.money_record_item, mDatas);
        recyclerRecord.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void requestData() {
        // actionBarTitle.setText("消费记录");
        HttpProxy.getWithDrawCash(String.valueOf(mPageIndex), mFundId, new HttpCallBack<List<WalletCashBean>>() {
            @Override
            public void onSuccess(List<WalletCashBean> responseData) {
                mAdapter.addBaseDatas(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {
            }
        });
    }


    @OnClick(R.id.action_back)
    public void onViewClicked() {
    }
}
