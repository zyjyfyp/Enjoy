package com.yunsen.enjoy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.adapter.SearchListAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.buy.FilterRecAdapter;
import com.yunsen.enjoy.fragment.discover.GoodsAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.GoodsData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * 搜索
 */
public class SearchActivity extends BaseFragmentActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = "SearchActivity";
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.search_view)
    SearchView searchView;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.no_car_tv)
    TextView noCarTv;
    private String mSearchText = "";
    private ArrayList<CarDetails> mDatas;
    private SearchListAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("搜索");
        searchView.setIconified(false);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mDatas = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchListAdapter(this, R.layout.goods_item_2, mDatas);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        searchView.setOnQueryTextListener(this);
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
    public boolean onQueryTextSubmit(String query) {
        Logger.e("提交onQueryTextSubmit: " + query);

//        Toast.makeText(this, "开始搜索" + mSearchText, Toast.LENGTH_SHORT).show();
        submitSearchRequest();
        return false;
    }

    private void submitSearchRequest() {
        HttpProxy.getSearchList(mSearchText, new HttpCallBack<List<CarDetails>>() {
            @Override
            public void onSuccess(List<CarDetails> responseData) {
                if (responseData.size() == 0) {
                    noCarTv.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    mAdapter.upData(responseData);
                    noCarTv.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                noCarTv.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mSearchText = newText;
        return true;
    }


}
