package com.yunsen.enjoy.fragment.buy;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.widget.MoreCarView;
import com.yunsen.enjoy.widget.NumberPickerDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/23.
 */

public class FilterFragment extends BaseFragment {
    private static final String TAG = "FilterFragment";
    @Bind(R.id.text_hor_1)
    TextView textHor1;
    @Bind(R.id.text_hor_2)
    TextView textHor2;
    @Bind(R.id.text_hor_3)
    TextView textHor3;
    @Bind(R.id.text_hor_4)
    TextView textHor4;
    @Bind(R.id.filter_tv)
    TextView filterTv;
    @Bind(R.id.filter_reset_tv)
    TextView filterResetTv;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private FilterRecAdapter mAdapter;

    private String mChannel; //新车还是二手车
    private String mStrwhere = "sell_price<5";//价格范围
    private String mOrderby = "click desc";//排序条件


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_filter;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        textHor1.setText("智能排序");
        textHor2.setText("品牌");
        textHor3.setText("价格");
        textHor4.setText("筛选");
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mChannel = bundle.getString(Constants.CHANNEL_KEY, "goods");
        }

        LinearLayoutManager layoutmanager = new LinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);
        mAdapter = new FilterRecAdapter(getActivity(), R.layout.goods_item_2, new ArrayList<GoodsData>());
        HeaderAndFooterRecyclerViewAdapter recyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerView.setAdapter(recyclerViewAdapter);
        RecyclerViewUtils.setFooterView(recyclerView, new MoreCarView(getActivity()));
        EventBus.getDefault().post(new UpUiEvent(EventConstants.UP_VIEW_PAGER_HEIGHT));
    }

    @Override
    protected void requestData() {
        HttpProxy.getFilterBuyCarDatas(new HttpCallBack<List<GoodsData>>() {
            @Override
            public void onSuccess(List<GoodsData> responseData) {
                mAdapter.upData(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        }, mChannel, mStrwhere, mOrderby);
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.text_hor_1, R.id.text_hor_2, R.id.text_hor_3, R.id.text_hor_4, R.id.filter_tv, R.id.filter_reset_tv, R.id.recyclerView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_hor_1:
                showPickerDialog();
                break;
            case R.id.text_hor_2:
                break;
            case R.id.text_hor_3:
                break;
            case R.id.text_hor_4:
                break;
            case R.id.filter_tv:
                break;
            case R.id.filter_reset_tv:
                break;
            case R.id.recyclerView:
                break;
        }
    }

    /**
     * 智能排序
     */
    private void showPickerDialog() {
        final NumberPickerDialog picker = new NumberPickerDialog(getActivity(), Constants.SORT_METHED);
        picker.setLeftOnclickListener("取消", new NumberPickerDialog.onLeftOnclickListener() {
            @Override
            public void onLeftClick() {
                if (picker != null && picker.isShowing()) {
                    picker.dismiss();
                }
            }
        });
        picker.setRightOnclickListener("确定", new NumberPickerDialog.onRightOnclickListener() {
            @Override
            public void onRightClick(int index) {
                if (picker != null && picker.isShowing()) {
                    mOrderby = Constants.SHOT_METHED_VALUE.get(Constants.SORT_METHED[index]);
                    textHor1.setText(Constants.SORT_METHED[index]);
                    requestData();
                    picker.dismiss();
                }
            }
        });
        picker.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
