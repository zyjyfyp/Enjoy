package com.yunsen.enjoy.activity.buy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.adapter.MeetAddressAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/4.
 */

public class MeetAddressActivity extends BaseFragmentActivity implements MultiItemTypeAdapter.OnItemClickListener {


    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.no_data_error_layout)
    LinearLayout noDataErrorLayout;
    private ArrayList<SProviderModel> mDatas;
    private MeetAddressAdapter mAdapter;
    private int mPageIndex = 1;

    @Override
    public int getLayout() {
        return R.layout.activity_meet_address;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText(R.string.app_name);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatas = new ArrayList<>();
        mAdapter = new MeetAddressAdapter(this, R.layout.meet_address_item, mDatas);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void requestData() {
        super.requestData();
        HttpProxy.getServiceProvider(mPageIndex, null, new HttpCallBack<List<SProviderModel>>() {
            @Override
            public void onSuccess(List<SProviderModel> responseData) {
                mAdapter.upDatas(responseData);
                noDataErrorLayout.setVisibility(View.GONE);
            }

            @Override
            public void onError(Request request, Exception e) {
                Logger.e("onError: " + e.getMessage());
                noDataErrorLayout.setVisibility(View.VISIBLE);
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

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder
            holder, int position) {
        if (mDatas != null && position < mDatas.size()) {
            Intent data = new Intent();
            SProviderModel model = mDatas.get(position);
            data.putExtra(Constants.ADDRESS_KEY, model.getAddress());
            data.putExtra(Constants.POST_CODE_KEY, model.getPost_code());
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter
            adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
