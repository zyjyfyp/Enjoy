package com.yunsen.enjoy.activity.goods;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.goods.adapter.CheckItemAdapter;
import com.yunsen.enjoy.activity.goods.adapter.DGoodRecyclerAdapter;
import com.yunsen.enjoy.activity.goods.adapter.GradeAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.CheckedData;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.model.GradeFlagModel;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpCityEvent;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.EndlessRecyclerOnScrollListener;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.LoadMoreLayout;
import com.yunsen.enjoy.ui.recyclerview.RecycleViewDivider;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.utils.GlobalStatic;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/15.
 */

public class ChangeGoodsActivity extends BaseFragmentActivity implements MultiItemTypeAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, AMapLocationListener {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.d_text_hor_1)
    CheckBox dTextHor1;
    @Bind(R.id.d_text_hor_2)
    CheckBox dTextHor2;
    @Bind(R.id.d_text_hor_2top)
    View dTextHor2Top;
    @Bind(R.id.d_text_hor_2bottom)
    View dTextHor2Bottom;
    @Bind(R.id.d_text_hor_3)
    TextView dTextHor3;
    @Bind(R.id.d_text_hor_4)
    CheckBox dTextHor4;
    @Bind(R.id.d_recycler_view)
    RecyclerView dRecyclerView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;
    @Bind(R.id.select_city)
    TextView selectCity;
    @Bind(R.id.grade_recycler)
    RecyclerView gradeRecycler;
    @Bind(R.id.min_price_edt)
    EditText minPriceEdt;
    @Bind(R.id.max_price_edt)
    EditText maxPriceEdt;
    @Bind(R.id.goods_draw_layout)
    DrawerLayout goodsDrawLayout;
    @Bind(R.id.change_goods_right)
    LinearLayout changeGoodsRight;
    @Bind(R.id.change_goods_right_2)
    LinearLayout changeGoodsRight2;
    @Bind(R.id.filter_layout)
    FrameLayout filterLayout;
    @Bind(R.id.reset_btn)
    Button resetBtn;
    @Bind(R.id.submit_btn)
    Button submitBtn;


    private int mActType = 0;

    private List<CarDetails> mData;
    private DGoodRecyclerAdapter mAdapter;
    private String mChannelName;
    private String mCategegoryId;
    private int mPageIndex = 1;
    private boolean isLoadMore = false;
    private boolean mHasMore = true;
    private LoadMoreLayout loadMoreLayout;
    private List<CheckedData> mPopupDatas;
    private String mGoodsType;
    private Drawable mSortDown;//高到底
    private Drawable mSortUp;//低到高
    private LocationManager mLm;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private GradeAdapter mGradeAdapter;
    private SelectCityFragment mCityFragment;

    @Override
    public int getLayout() {
        return R.layout.activity_change_goods;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarRight.setImageResource(R.mipmap.search_icon);
        actionBarRight.setVisibility(View.VISIBLE);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        dRecyclerView.setLayoutManager(layout);
        loadMoreLayout = new LoadMoreLayout(this);
        dRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        swipeRefreshWidget.setOnRefreshListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gradeRecycler.setLayoutManager(gridLayoutManager);
        ArrayList<GradeFlagModel> datas = new ArrayList<>();
        datas.add(new GradeFlagModel("普通会员", 0, false));
        datas.add(new GradeFlagModel("一星会员", 1, false));
        datas.add(new GradeFlagModel("二星会员", 2, false));
        datas.add(new GradeFlagModel("三星会员", 3, false));
        mGradeAdapter = new GradeAdapter(this, R.layout.grade_itme, datas);
        gradeRecycler.setAdapter(mGradeAdapter);

    }

    private void initAddress() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        mLocationOption.setOnceLocation(true);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        requestPermission(new String[]{Permission.ACCESS_COARSE_LOCATION,
                Permission.ACCESS_FINE_LOCATION,
                Permission.READ_PHONE_STATE}, 1);

        Intent intent = getIntent();
        mChannelName = intent.getStringExtra(Constants.CHANNEL_NAME_KEY);
        mCategegoryId = intent.getStringExtra(Constants.CATEGORY_ID_KEY);
        String actName = intent.getStringExtra(Constants.ACT_NAME_KEY);
        mActType = intent.getIntExtra(Constants.ACT_TYPE_KEY, 0);
        actionBarTitle.setText(actName);
        mData = new ArrayList<>();
        mAdapter = new DGoodRecyclerAdapter(this, R.layout.d_goods_item, mData);
        if (mActType == Constants.REPERTORY_ACT) {
            mAdapter.setShowClear(true);
        }
        HeaderAndFooterRecyclerViewAdapter recyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        dRecyclerView.setAdapter(recyclerViewAdapter);
        RecyclerViewUtils.setFooterView(dRecyclerView, loadMoreLayout);


        mSortUp = getResources().getDrawable(R.mipmap.sort_up);
        mSortDown = getResources().getDrawable(R.mipmap.sort_down);
        mSortUp.setBounds(0, 0, mSortUp.getIntrinsicWidth(), (int) (mSortUp.getMinimumHeight()));
        mSortDown.setBounds(0, 0, mSortDown.getIntrinsicWidth(), (int) (mSortDown.getMinimumHeight()));

        mPopupDatas = new ArrayList<>();
        mPopupDatas.add(new CheckedData("1", "全部分类", false));
        mPopupDatas.add(new CheckedData("2", "饮食", false));
        mPopupDatas.add(new CheckedData("3", "家居", false));
        mPopupDatas.add(new CheckedData("4", "户外", false));
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
        dRecyclerView.addOnScrollListener(mOnScrollListener);
        mGradeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                mGradeAdapter.setSelect(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        goodsDrawLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Log.e(TAG, "onDrawerOpened: ");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.e(TAG, "onDrawerClosed: ");
                closeCityFragment();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.e(TAG, "onDrawerStateChanged: ");
            }
        });


    }

    private static final String TAG = "ChangeGoodsActivity";
    /**
     * RecycleView的滑动监听(加载更多)
     */
    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            if (mData.size() > 0) {
                if (mHasMore) {
                    mPageIndex++;
                    isLoadMore = true;
                    requestData();
                    loadMoreLayout.showLoading();
                } else {
                    loadMoreLayout.showLoadNoMore();
                }
            }

        }
    };


    @Override
    public void requestData() {
        if (mHasMore)
            HttpProxy.getChangeGoodsList(mChannelName, mCategegoryId, String.valueOf(mPageIndex), new HttpCallBack<List<CarDetails>>() {
                @Override
                public void onSuccess(List<CarDetails> responseData) {
                    if (isLoadMore) {
                        mHasMore = mAdapter.addData(responseData);
                        if (mHasMore) {
                            loadMoreLayout.showloadingStart();
                        } else {
                            loadMoreLayout.showLoadNoMore();
                        }
                    } else {
                        mAdapter.upData(responseData);
                    }
                    swipeRefreshWidget.setRefreshing(false);
                }

                @Override
                public void onError(Request request, Exception e) {
                    if (!isLoadMore) {
                        mAdapter.upData(null);
                    } else {
                        mHasMore = false;
                    }
                    loadMoreLayout.showLoadNoMore();
                    swipeRefreshWidget.setRefreshing(false);
                }

            });
    }


    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        //得到系统的位置服务，判断GPS是否激活
        if (requestCode == 1) {
            mLm = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean ok = mLm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (ok) {
                initAddress();
            } else {
                Toast.makeText(this, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
    }

    @OnClick({R.id.action_back, R.id.action_bar_right, R.id.d_text_hor_1,
            R.id.d_text_hor_2, R.id.d_text_hor_3, R.id.d_text_hor_4,
            R.id.d_text_hor_2top, R.id.d_text_hor_2bottom, R.id.select_city,
            R.id.submit_btn, R.id.reset_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.action_bar_right:
                UIHelper.showSearchActivity(this);
                break;
            case R.id.d_text_hor_1:
                dTextHor1.setSelected(true);
                showListPopupWindow();
                break;
            case R.id.d_text_hor_3:
                dTextHor3.setSelected(true);
                break;
            case R.id.d_text_hor_4:
                openRightDrag();
                break;
            case R.id.d_text_hor_2:
                dTextHor2.setSelected(true);
                break;
            case R.id.d_text_hor_2top:
                dTextHor2.setSelected(true);
                dTextHor2.setCompoundDrawables(null, null, mSortUp, null);
                upDataInit();
                requestData();
                break;
            case R.id.d_text_hor_2bottom:
                dTextHor2.setSelected(true);
                dTextHor2.setCompoundDrawables(null, null, mSortDown, null);
                upDataInit();
                requestData();
                break;
            case R.id.select_city:
                openCityFragment();
                break;
            case R.id.submit_btn:
                requestData();
                closeRightDrag();
                break;
            case R.id.reset_btn:
                resetData();
                break;
        }
    }

    /**
     * 重置筛选
     */
    private void resetData() {
        selectCity.setText("所有区域");
        mGradeAdapter.clearState();
        maxPriceEdt.setText("");
        minPriceEdt.setText("");

    }

    private void openCityFragment() {
        changeGoodsRight.setVisibility(View.GONE);
        changeGoodsRight2.setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCityFragment == null) {
            mCityFragment = new SelectCityFragment();
            transaction.add(R.id.filter_layout, mCityFragment);
        }
        transaction.show(mCityFragment);
        transaction.commit();
    }

    private void closeCityFragment() {
        changeGoodsRight2.setVisibility(View.GONE);
        changeGoodsRight.setVisibility(View.VISIBLE);
        if (mCityFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(mCityFragment);
            transaction.commit();
        }
    }


    /**
     * 显示类型的列表
     */
    private void showListPopupWindow() {
        int[] location = new int[2];
        dRecyclerView.getLocationInWindow(location);//在屏幕中的位置
        View contentView = LayoutInflater.from(ChangeGoodsActivity.this).inflate(R.layout.list_popup_layout, null);
        RecyclerView recyclerView = contentView.findViewById(R.id.popup_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CheckItemAdapter adapter = new CheckItemAdapter(this, R.layout.checked_item, mPopupDatas);
        recyclerView.setAdapter(adapter);
        final PopupWindow popWnd = new PopupWindow(this);
        popWnd.setContentView(contentView);
        popWnd.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.empty));
        popWnd.setWidth(DeviceUtil.getWidth(this));
        float listViewHeight = mPopupDatas.size() * this.getResources().getDimension(R.dimen.title_height);
        int height = (DeviceUtil.getHeight(this) * 2 / 3);
        height = (int) Math.min(listViewHeight, height);

        popWnd.setHeight(height);
        popWnd.setOutsideTouchable(true);
        popWnd.setFocusable(true);
        popWnd.showAtLocation(dRecyclerView, Gravity.TOP, 0, location[1]);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                if (adapter instanceof CheckItemAdapter) {
                    ((CheckItemAdapter) adapter).setSelected(position);
                    String name = ((CheckItemAdapter) adapter).getDatas().get(position).getName();
                    mGoodsType = name;
                    popWnd.dismiss();
                    dTextHor1.setText(name);
                    upDataInit();
                    requestData();
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    /**
     * 列表的点击事件
     *
     * @param view
     * @param adapter
     * @param holder
     * @param position
     */
    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        List<CarDetails> datas = mAdapter.getDatas();
        if (datas != null && position >= 0 && datas.size() > position) {
            CarDetails data = datas.get(position);
            UIHelper.showGoodsDescriptionActivity(this, String.valueOf(data.getId()), data.getTitle());
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    /**
     * 更新数据前还原状态
     */
    private void upDataInit() {
        mHasMore = true;
        mPageIndex = 1;
        isLoadMore = false;
    }

    @Override
    public void onRefresh() {
        Log.e(TAG, "onRefresh: 刷新");
        upDataInit();
        requestData();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                GlobalStatic.latitude = amapLocation.getLatitude();//获取纬度
                GlobalStatic.longitude = amapLocation.getLongitude();//获取经度
                mAdapter.notifyDataSetChanged();
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    public void openRightDrag() {
        if (!goodsDrawLayout.isDrawerOpen(filterLayout)) {
            goodsDrawLayout.openDrawer(filterLayout);
        }
    }


    public void closeRightDrag() {
        if (goodsDrawLayout.isDrawerOpen(filterLayout)) {
            goodsDrawLayout.closeDrawer(filterLayout);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpCityEvent event) {
        if (event.getEventId() == EventConstants.CHANGE_CITY_EVENT) {
            selectCity.setText(event.getCity());
            closeCityFragment();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && goodsDrawLayout.isDrawerOpen(filterLayout)) {
            closeRightDrag();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
