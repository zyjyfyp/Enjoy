package com.yunsen.enjoy.fragment.buy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.BrandData;
import com.yunsen.enjoy.model.CarBrand;
import com.yunsen.enjoy.model.CarBrandList;
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

public class SelectBrandActivity extends BaseFragmentActivity implements MultiItemTypeAdapter.OnItemClickListener {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<CarBrand> mDatas;
    private CarBrandAdapter mAdapter;
    private RecyclerView topRecyclerView;
    private HotCarBrandAdapter mTopAdapter;
    private View topView;
    private String mFragmentType;
    private boolean mHasTop = false;

    @Override
    public int getLayout() {
        return R.layout.activity_select_brand;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("选择品牌");
        //top start
        topView = getLayoutInflater().inflate(R.layout.select_brand_top_layout, null);
        topRecyclerView = topView.findViewById(R.id.top_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        topRecyclerView.setLayoutManager(gridLayoutManager);
        mTopAdapter = new HotCarBrandAdapter(this, R.layout.hot_brand_item, new ArrayList<CarBrand>());
        topRecyclerView.setAdapter(mTopAdapter);
        //top end

        LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        mDatas = new ArrayList<>();
        mAdapter = new CarBrandAdapter(this, mDatas);
        HeaderAndFooterRecyclerViewAdapter viewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerView.setAdapter(viewAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mFragmentType = intent.getStringExtra(Constants.FRAGMENT_TYPE_KEY);
        }
    }

    private static final String TAG = "SelectBrandActivity";

    @Override
    public void requestData() {
        HttpProxy.getCarBrandDatas(new HttpCallBack<CarBrandList>() {
            @Override
            public void onSuccess(CarBrandList responseData) {
                Log.e(TAG, "onSuccess: " + responseData);
                if (responseData.getList() != null && responseData.getList().size() > 0) {
                    mTopAdapter.upDatas(responseData.getList());
                    mHasTop = true;
                    RecyclerViewUtils.setHeaderView(recyclerView, topView);
                } else {
                    mHasTop = false;
                }
                mAdapter.upDatas(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        }, "");

    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                List<CarBrand> datas = mAdapter.getDatas();
                if (mHasTop) {
                    position = position - 1;
                }
                if (position >= 0 && datas != null && datas.size() > 0 && datas.size() > position) {
                    CarBrand carBrand = datas.get(position);
                    String title = carBrand.getTitle();
                    ActivityResultEvent event = new ActivityResultEvent(EventConstants.CAR_BRAND_ID_KEY, carBrand.getId(), title);
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

        mTopAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                List<CarBrand> datas = mTopAdapter.getDatas();
                if (datas != null && datas.size() > 0 && datas.size() > position) {
                    CarBrand carBrand = datas.get(position);
                    int id = carBrand.getId();
                    String title = carBrand.getTitle();
                    ActivityResultEvent event = new ActivityResultEvent(EventConstants.CAR_BRAND_ID_KEY, id, title);
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
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (adapter instanceof MultiItemTypeAdapter) {
            List datas = ((MultiItemTypeAdapter) adapter).getDatas();
            if (datas != null && datas.size() > 0 && datas.size() > position) {
                Object data = datas.get(position);

            }
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
