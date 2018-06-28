package com.yunsen.enjoy.fragment.buy;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.common.StaticVar;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.model.event.ActivityResultEvent;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpCityEvent;
import com.yunsen.enjoy.model.event.UpFilterReqEvent;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.NoScrollLinearLayoutManager;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.utils.SharedPreference;
import com.yunsen.enjoy.widget.FilterHorLayout;
import com.yunsen.enjoy.widget.MoreCarView;
import com.yunsen.enjoy.widget.NoticeView;
import com.yunsen.enjoy.widget.NumberPickerDialog;
import com.yunsen.enjoy.widget.interfaces.onLeftOnclickListener;
import com.yunsen.enjoy.widget.interfaces.onRightOnclickListener;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Bind(R.id.notice_view)
    NoticeView noticeView;

    private FilterRecAdapter mAdapter;
    private String mChannel; //新车还是二手车
    private String mOrderby = "click desc";//排序条件 智能排序
    private String mPrice = "sell_price>=0";//价格范围
    private Map<String, String> mBrands = new HashMap<>(); //车型：
    private String mCarTitle;
    private String mCarCity; //搜索框条件：
    private String mStrwhere = "sell_price>=0";
    private String mBrandId = null; //汽车类型 高级筛选
    private String mBrandIdOne = null; //品牌

    private int mPageIndex = 1;
    private boolean mIsLoadMore = false;

    private MoreCarView moreCarView;
    private int mCurrentPosition = 0;
    private NumberPickerDialog mSortPicker;
    private NumberPickerDialog mPricePicker;

    @Override

    protected int getLayoutId() {
        return R.layout.fragment_filter;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
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
        if (!"goods".equals(mChannel)) {
            mCurrentPosition = 1;
        }
        NoScrollLinearLayoutManager layoutmanager = new NoScrollLinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        layoutmanager.setScrollEnabled(false);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);
        mAdapter = new FilterRecAdapter(getActivity(), R.layout.goods_item_2, new ArrayList<GoodsData>());
        HeaderAndFooterRecyclerViewAdapter recyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerView.setAdapter(recyclerViewAdapter);
        moreCarView = new MoreCarView(getActivity());
        RecyclerViewUtils.setFooterView(recyclerView, moreCarView);
        startLoading();
//        EventBus.getDefault().post(new UpUiEvent(EventConstants.UP_VIEW_PAGER_HEIGHT));
    }

    /**
     * 开始加载 loading
     */
    private void startLoading() {
        recyclerView.setVisibility(View.GONE);
        moreCarView.setVisibility(View.GONE);
        noticeView.showNoticeType(NoticeView.Type.LOADING);
    }

    @Override
    protected void requestData() {
        String city = SharedPreference.getInstance().getString(SpConstants.CITY_KEY, "深圳市");
        HttpProxy.getFilterBuyCarDatas(String.valueOf(mPageIndex), new HttpCallBack<List<GoodsData>>() {
            @Override
            public void onSuccess(List<GoodsData> responseData) {
                if (mIsLoadMore) {
                    StaticVar.sHasMore[mCurrentPosition] = mAdapter.addData(responseData);
                } else {
                    StaticVar.sHasMore[mCurrentPosition] = mAdapter.upData(responseData);
                }
                if (!StaticVar.sHasMore[mCurrentPosition]) {
                    moreCarView.setVisibility(View.VISIBLE);
                } else {
                    moreCarView.setVisibility(View.GONE);
                }

                UpUiEvent event = new UpUiEvent(EventConstants.UP_VIEW_PAGER_HEIGHT, StaticVar.sHasMore[mCurrentPosition]);
                event.setMore(mIsLoadMore);
                EventBus.getDefault().post(event);
                noticeView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Request request, Exception e) {
                StaticVar.sHasMore[mCurrentPosition] = mAdapter.addData(null);
                moreCarView.setVisibility(View.VISIBLE);
                noticeView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }, mBrandIdOne, mBrandId, mChannel, mStrwhere, mOrderby, city);
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
        if (mSortPicker == null) {
            mSortPicker = new NumberPickerDialog(getActivity(), Constants.SORT_METHED);
            mSortPicker.setLeftOnclickListener("取消", new onLeftOnclickListener() {
                @Override
                public void onLeftClick() {
                    if (mSortPicker.isShowing()) {
                        mSortPicker.dismiss();
                    }
                }
            });
            mSortPicker.setRightOnclickListener("确定", new onRightOnclickListener() {
                @Override
                public void onRightClick(int[] index) {
                    if (mSortPicker.isShowing()) {
                        mOrderby = Constants.SHOT_METHED_VALUE.get(Constants.SORT_METHED[index[0]]);
                        textHor1.setText(Constants.SORT_METHED[index[0]]);
                        initRequestDta();
                        requestData();
                        mSortPicker.dismiss();
                    }
                }
            });
        }
        mSortPicker.show();
    }

    /**
     * 显示价格排序
     */
    private void showPriceDialog() {
        if (mPricePicker == null) {
            mPricePicker = new NumberPickerDialog(getActivity(), Constants.SORT_PRICES);

            mPricePicker.setLeftOnclickListener("取消", new onLeftOnclickListener() {
                @Override
                public void onLeftClick() {
                    if (mPricePicker.isShowing()) {
                        mPricePicker.dismiss();
                    }
                }
            });
            mPricePicker.setRightOnclickListener("确定", new onRightOnclickListener() {
                @Override
                public void onRightClick(int[] index) {
                    if (mPricePicker.isShowing()) {
                        mStrwhere = Constants.SHOT_PRICES_VALUES.get(Constants.SORT_PRICES[index[0]]);
                        textHor3.setText(Constants.SORT_PRICES[index[0]]);
                        initRequestDta();
                        requestData();
                        mPricePicker.dismiss();
                    }
                }
            });
        }
        mPricePicker.show();
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
        mBrandId = null;
        mBrandIdOne = null;
        mStrwhere = mPrice;
        initRequestDta();
        requestData();
    }

    @Override
    public void onItemClose(int id) {
        mStrwhere = mPrice;

        if (!TextUtils.isEmpty(mBrandId) && id == Integer.parseInt(mBrandId)) {
            mBrandId = null;
        } else if (!TextUtils.isEmpty(mBrandIdOne) && id == Integer.parseInt(mBrandIdOne)) {
            mBrandIdOne = null;
        }
        if (TextUtils.isEmpty(mBrandId) && TextUtils.isEmpty(mBrandIdOne)) {
            filterLayout.setVisibility(View.GONE);
        }
        initRequestDta();
        requestData();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(UpCityEvent event) {
        if (event.getEventId() == EventConstants.UP_CITY) {
            initRequestDta();
            requestData();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onActivityEvent(ActivityResultEvent event) {
        switch (event.getEventId()) {
            case EventConstants.CAR_BRAND_ID_KEY:
                if (!TextUtils.isEmpty(mChannel) && mChannel.equals(event.getFragmentType())) {
                    if (!TextUtils.isEmpty(mBrandIdOne)) {
                        int id = Integer.parseInt(mBrandIdOne);
                        filterLayout.removeItemView(id);
                    }
                    int dataId = event.getDataId();
                    mBrandIdOne = String.valueOf(dataId);
                    filterLayout.addItemView(event.getName(), dataId);
                    filterLayout.setVisibility(View.VISIBLE);
                } else if (Constants.ALL_CAR_TYPE.equals(event.getFragmentType())) {
                    if (!TextUtils.isEmpty(mBrandIdOne)) {
                        int id = Integer.parseInt(mBrandIdOne);
                        filterLayout.removeItemView(id);
                    }
                    int dataId = event.getDataId();
                    mBrandIdOne = String.valueOf(dataId);
                    filterLayout.addItemView(event.getName(), dataId);
                    filterLayout.setVisibility(View.VISIBLE);
                }
                break;
            case EventConstants.SENIOR_FILTER_ID:
                if (!TextUtils.isEmpty(mChannel) && mChannel.equals(event.getFragmentType())) {
                    if (!TextUtils.isEmpty(mBrandId)) {
                        int id = Integer.parseInt(mBrandId);
                        filterLayout.removeItemView(id);
                    }
                    int dataId = event.getDataId();
                    mBrandId = String.valueOf(dataId);
                    filterLayout.addItemView(event.getName(), dataId);
                    filterLayout.setVisibility(View.VISIBLE);
                }
                break;
        }

        initRequestDta();
        requestData();
    }

    /**
     * 还原  请求数据
     */
    private void initRequestDta() {
        startLoading();
        mPageIndex = 1;
        mIsLoadMore = false;
        StaticVar.sHasMore[mCurrentPosition] = true;
        moreCarView.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLodeMoreEvent(UpFilterReqEvent event) {
        if (event.getEventId() == EventConstants.UP_FILTER_FRG && mChannel.equals(event.getType())) {
            mPageIndex++;
            mIsLoadMore = true;
            requestData();
        } else if (event.getEventId() == EventConstants.SHOW_HAS_MORE && mChannel.equals(event.getType())) {
            moreCarView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }
}

