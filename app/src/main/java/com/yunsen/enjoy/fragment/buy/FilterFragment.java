package com.yunsen.enjoy.fragment.buy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.model.event.ActivityResultEvent;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpCityEvent;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.NoScrollLinearLayoutManager;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.utils.SharedPreference;
import com.yunsen.enjoy.widget.FilterHorLayout;
import com.yunsen.enjoy.widget.MoreCarView;
import com.yunsen.enjoy.widget.NumberPickerDialog;
import com.yunsen.enjoy.widget.interfaces.onLeftOnclickListener;
import com.yunsen.enjoy.widget.interfaces.onRightOnclickListener;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/23.
 */

public class FilterFragment extends BaseFragment implements MultiItemTypeAdapter.OnItemClickListener, FilterHorLayout.OnFilterResetListener, FilterHorLayout.OnItemCloseListener {
    private static final String TAG = "FilterFragment";
    @Bind(R.id.text_hor_1)
    TextView textHor1;
    @Bind(R.id.text_hor_2)
    TextView textHor2;
    @Bind(R.id.text_hor_3)
    TextView textHor3;
    @Bind(R.id.text_hor_4)
    TextView textHor4;
    @Bind(R.id.filter_layout)
    FilterHorLayout filterLayout;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private FilterRecAdapter mAdapter;
    private String mChannel; //新车还是二手车
    private String mOrderby = "click desc";//排序条件 智能排序
    private String mPrice = "sell_price>=0";//价格范围
    private Map<String, String> mBrands = new HashMap<>(); //车型：
    private String mCarTitle;
    private String mCarCity; //搜索框条件：
    private String mStrwhere = "sell_price>=0";


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

        NoScrollLinearLayoutManager layoutmanager = new NoScrollLinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        layoutmanager.setScrollEnabled(false);
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
        String city = SharedPreference.getInstance().getString(SpConstants.CITY_KEY, "深圳市");
        HttpProxy.getFilterBuyCarDatas(new HttpCallBack<List<GoodsData>>() {
            @Override
            public void onSuccess(List<GoodsData> responseData) {
                mAdapter.upData(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        }, mChannel, mStrwhere, mOrderby, city);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
        filterLayout.setOnResetListener(this);
        filterLayout.setOnItemColseListener(this);
    }


    @OnClick({R.id.text_hor_1, R.id.text_hor_2, R.id.text_hor_3, R.id.text_hor_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_hor_1:
                showPickerDialog();
                break;
            case R.id.text_hor_2:
                UIHelper.showSelectBrandActivity(getActivity(), mChannel);
                break;
            case R.id.text_hor_3:
                showPriceDialog();
                break;
            case R.id.text_hor_4:
                UIHelper.showSeniorSelectBrandActivity(getActivity(), mChannel);
                break;

        }
    }

    /**
     * 智能排序
     */
    private void showPickerDialog() {
        final NumberPickerDialog picker = new NumberPickerDialog(getActivity(), Constants.SORT_METHED);
        picker.setLeftOnclickListener("取消", new onLeftOnclickListener() {
            @Override
            public void onLeftClick() {
                if (picker != null && picker.isShowing()) {
                    picker.dismiss();
                }
            }
        });
        picker.setRightOnclickListener("确定", new onRightOnclickListener() {
            @Override
            public void onRightClick(int[] index) {
                if (picker != null && picker.isShowing()) {
                    mOrderby = Constants.SHOT_METHED_VALUE.get(Constants.SORT_METHED[index[0]]);
                    textHor1.setText(Constants.SORT_METHED[index[0]]);
                    requestData();
                    picker.dismiss();
                }
            }
        });
        picker.show();
    }

    /**
     * 显示价格排序
     */
    private void showPriceDialog() {
        final NumberPickerDialog picker = new NumberPickerDialog(getActivity(), Constants.SORT_PRICES);

        picker.setLeftOnclickListener("取消", new onLeftOnclickListener() {
            @Override
            public void onLeftClick() {
                if (picker != null && picker.isShowing()) {
                    picker.dismiss();
                }
            }
        });
        picker.setRightOnclickListener("确定", new onRightOnclickListener() {
            @Override
            public void onRightClick(int[] index) {
                if (picker != null && picker.isShowing()) {
                    mStrwhere = Constants.SHOT_PRICES_VALUES.get(Constants.SORT_PRICES[index[0]]);
                    textHor3.setText(Constants.SORT_PRICES[index[0]]);
                    requestData();
                    picker.dismiss();
                }
            }
        });
        picker.show();
    }


    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        List<GoodsData> datas = mAdapter.getDatas();
        if (datas != null && datas.size() > 0 && datas.size() > position) {
            GoodsData goodsData = datas.get(position);
            UIHelper.showCarDetailsActivity(getActivity(), Integer.toString(goodsData.getId()));
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    /**
     * 品牌筛选被关闭
     */
    @Override
    public void onFilterReset() {
        filterLayout.setVisibility(View.GONE);
        mBrands.clear();
        mStrwhere = mPrice;
        requestData();
    }

    @Override
    public void onItemClose(int id) {
        mBrands.remove(String.valueOf(id));
        mStrwhere = mPrice;
        Iterator<String> iterator = mBrands.keySet().iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            mStrwhere += mStrwhere + " and brand_id like \'%" + next + "%\'";
        }
        if (mBrands.size() == 0) {
            filterLayout.setVisibility(View.GONE);
        }
        requestData();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(UpCityEvent event) {
        if (event.getEventId() == EventConstants.UP_CITY) {
            requestData();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onActivityEvent(ActivityResultEvent event) {
        switch (event.getEventId()) {
            case EventConstants.CAR_BRAND_ID_KEY:
            case EventConstants.SENIOR_FILTER_ID:
                if (!TextUtils.isEmpty(mChannel) && mChannel.equals(event.getFragmentType())) {
                    int dataId = event.getDataId();
                    if (!mBrands.containsKey(String.valueOf(dataId))) {
                        mBrands.put(String.valueOf(dataId), event.getName());
                        mStrwhere = mStrwhere + " and brand_id like \'%" + dataId + "%\'";
                        filterLayout.addItemView(event.getName(), dataId);
                    }
                    filterLayout.setVisibility(View.VISIBLE);
                }
                requestData();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}

