package com.yunsen.enjoy.fragment.buy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarBrand;
import com.yunsen.enjoy.model.event.ActivityResultEvent;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/29.
 */

public class SeniorFilterActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.top_recycler_view)
    RecyclerView topRecyclerView;
    private ArrayList<CarBrand> mTopDatas;
    private HotCarBrandAdapter mTopAdapter;
    private View topView;
    private String mFragmentType;

    @Override
    public int getLayout() {
        return R.layout.activity_senior_filter;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        topView = getLayoutInflater().inflate(R.layout.senior_filter_top_layout, null);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        topRecyclerView.setLayoutManager(layoutManager);
        mTopDatas = new ArrayList<>();
        mTopAdapter = new HotCarBrandAdapter(this, R.layout.hot_brand_item, mTopDatas);
        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mTopAdapter);
        topRecyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setHeaderView(topRecyclerView, topView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mFragmentType = intent.getStringExtra(Constants.FRAGMENT_TYPE_KEY);
        }
    }

    @Override
    protected void initListener() {
        mTopAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                List<CarBrand> datas = mTopAdapter.getDatas();
                if (datas != null && datas.size() > position-1) {
                    CarBrand carBrand = datas.get(position-1);
                    ActivityResultEvent event = new ActivityResultEvent(EventConstants.SENIOR_FILTER_ID, carBrand.getId(), carBrand.getTitle());
                    event.setFragmentType(mFragmentType);
                    EventBus.getDefault().postSticky(event);
                    finish();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void requestData() {
        HttpProxy.getSeniorCarBrandDatas(new HttpCallBack<List<CarBrand>>() {
            @Override
            public void onSuccess(List<CarBrand> responseData) {
                mTopAdapter.upDatas(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        }, "");
    }


    @OnClick(R.id.action_back)
    public void onClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
