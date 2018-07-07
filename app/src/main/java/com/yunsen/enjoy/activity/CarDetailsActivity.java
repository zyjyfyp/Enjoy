package com.yunsen.enjoy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.adapter.CarBaseInfoAdapter;
import com.yunsen.enjoy.adapter.CarTopBannerAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.AlbumsBean;
import com.yunsen.enjoy.model.CarBaseInfo;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.DefaultSpecItemBean;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.FlowLayout;
import com.yunsen.enjoy.widget.NoticeView;
import com.yunsen.enjoy.widget.drag.DragLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

public class CarDetailsActivity extends BaseFragmentActivity implements NoticeView.OnNoticeListener {
    private static final String TAG = "CarDetailsActivity";
    @Bind(R.id.pager)
    AutoLoopViewPager pager;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.flow_layout)
    FlowLayout flowLayout;
    @Bind(R.id.details_old_car_money)
    TextView detailsOldCarMoney;
    @Bind(R.id.details_title)
    TextView detailsTitle;
    @Bind(R.id.details_car_money)
    TextView detailsCarMoney;
    @Bind(R.id.quality_tv)
    TextView qualityTv;
    @Bind(R.id.can_return_tv)
    TextView canReturnTv;
    @Bind(R.id.car_state_tv)
    TextView carStateTv;
    @Bind(R.id.more_service_layout)
    LinearLayout moreServiceLayout;
    @Bind(R.id.base_info_recycler)
    RecyclerView baseInfoRecycler;
    @Bind(R.id.base_introduce_tv)
    TextView baseIntroduceTv;
    @Bind(R.id.collect_layout)
    LinearLayout collectLayout;
    @Bind(R.id.ask_layout)
    LinearLayout askLayout;
    @Bind(R.id.add_shop_btn)
    Button addShopBtn;
    @Bind(R.id.order_buy_layout)
    LinearLayout orderBuyLayout;
    @Bind(R.id.apply_buy_tv)
    TextView applyBuyTv;
    @Bind(R.id.first_pay_tv)
    TextView firstPayTv;
    @Bind(R.id.drag_layout)
    DragLayout dragLayout;
    @Bind(R.id.notice_view)
    NoticeView noticeView;
    @Bind(R.id.data_layout)
    LinearLayout dataLayout;
    @Bind(R.id.collect_img)
    ImageView collectImg;
    @Bind(R.id.collect_tv)
    TextView collectTv;
    @Bind(R.id.car_introduce_web)
    WebView carIntroduceWeb;
    @Bind(R.id.layout_ent_gallery)
    RelativeLayout layoutEntGallery;
    @Bind(R.id.car_base_info_recycler)
    RecyclerView recyclerCarBaseInfo;

    private String mCarId;
    private CarDetails mData;
    private SharedPreferences mSp;
    private String mUserName;
    private String mUserId;
    private boolean mRequestFinish;
    private int mScreenWidth;
    private ArrayList<CarBaseInfo> mCarBaseInfos;
    private CarBaseInfoAdapter mCarBaseInfoAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_car_details;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        detailsOldCarMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中划线
        actionBarTitle.setText("汽车详情");
        mScreenWidth = DeviceUtil.getWidth(this);
        ViewGroup.LayoutParams lp = layoutEntGallery.getLayoutParams();
        lp.height = mScreenWidth;
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mCarId = intent.getStringExtra(Constants.CAR_DETAILS_ID);
        }
        dragLayout.setCanDrag(false);
        mSp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserName = mSp.getString(SpConstants.USER_NAME, "");
        mUserId = mSp.getString(SpConstants.USER_ID, "");
        recyclerCarBaseInfo.setLayoutManager(new GridLayoutManager(this, 3));
        mCarBaseInfos = new ArrayList<>();
        mCarBaseInfoAdapter = new CarBaseInfoAdapter(this, R.layout.car_base_info_item, mCarBaseInfos);
        recyclerCarBaseInfo.setAdapter(mCarBaseInfoAdapter);

    }

    @Override
    protected void initListener() {
        dragLayout.setDragIconClick(this);
        noticeView.setOnNoticeListener(this);
    }

    @Override
    public void requestData() {
        mRequestFinish = false;
        dataLayout.setVisibility(View.GONE);
        if (!DeviceUtil.isNetworkAvailable(CarDetailsActivity.this)) {
            noticeView.showNoticeType(NoticeView.Type.NO_INTERNET);
            return;
        }
        noticeView.showNoticeType(NoticeView.Type.LOADING);
        HttpProxy.getCarDetailsData(new HttpCallBack<CarDetails>() {

            @Override
            public void onSuccess(CarDetails responseData) {
                mData = responseData;
                upView(responseData);
                upBanner(responseData.getAlbums());
                upCarBaseInfo(responseData.getParam());
                noticeView.closeNoticeView();
                dataLayout.setVisibility(View.VISIBLE);
                List<AlbumsBean> albums = responseData.getAlbums();
                if (albums != null && albums.size() > 0) {
                    baseIntroduceTv.setVisibility(View.GONE);
                    carIntroduceWeb.loadUrl(URLConstants.REALM_NAME_HTTP + "/mobile/goods/conent-" + albums.get(0).getArticle_id() + ".html");//商品介绍
                } else {
                    baseIntroduceTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                Log.e(TAG, "onError: " + e.getMessage());

            }
        }, mCarId);
        /**
         * 判断商品是否被收藏
         */
        HttpProxy.getHasCollectGoods(mCarId, mUserId, new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean responseData) {
                collectImg.setSelected(false); //未收藏
                collectTv.setSelected(false);
                mRequestFinish = true;
                collectTv.setText("收藏");
            }

            @Override
            public void onError(Request request, Exception e) {
                collectImg.setSelected(true);
                collectTv.setSelected(true);
                collectTv.setText("已收藏");
                mRequestFinish = true;
            }
        });

    }

    /**
     * 更新汽车属性
     *
     * @param param
     */
    private void upCarBaseInfo(List<CarBaseInfo> param) {
        if (param != null && param.size() > 0) {
            mCarBaseInfoAdapter.upBaseDatas(param);
            Log.e(TAG, "upCarBaseInfo: " + param.size());
        }
    }

    private void upBanner(List<AlbumsBean> albums) {
        if (albums != null && albums.size() > 0) {
            CarTopBannerAdapter adapter = new CarTopBannerAdapter(albums, CarDetailsActivity.this);
            pager.setAdapter(adapter);
            indicator.setViewPager(pager);
            indicator.setPadding(5, 5, 10, 5);
        }
    }

    /**
     * 更加数据跟新View
     *
     * @param responseData
     */
    private void upView(CarDetails responseData) {
        if (responseData == null) {
            return;
        }
        detailsTitle.setText(responseData.getTitle());
        flowLayout.setDatas(responseData.getDatatype());//超值
        DefaultSpecItemBean defaultSpecItem = responseData.getDefault_spec_item();
        if (defaultSpecItem != null) {
            double rebatePrice = defaultSpecItem.getSell_price();
            detailsCarMoney.setText(rebatePrice + "万");
            double market_price = defaultSpecItem.getMarket_price();
            detailsOldCarMoney.setText("新车含税" + market_price + "万");
            double firstPayment = defaultSpecItem.getFirst_payment();
            int stockQuantity = defaultSpecItem.getStock_quantity();
            firstPayTv.setText("首付" + firstPayment + "万 直卖专享" + stockQuantity + "首付");
        }
    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        if (requestCode == Constants.CALL_PHONE) {
            UIHelper.showPhoneNumberActivity(this, Constants.PHONE_NUMBER);
        }
    }

    @OnClick({R.id.more_service_layout, R.id.collect_layout, R.id.ask_layout,
            R.id.add_shop_btn, R.id.order_buy_layout, R.id.apply_buy_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more_service_layout:
                UIHelper.showCarServiceActivity(this);
                break;
            case R.id.collect_layout:
                if (mData != null) {
                    if (mRequestFinish) {
                        if (collectImg.isSelected()) {
                            deleteCollect(mCarId);
                        } else {
                            getAddCollect(mData);
                        }
                    }

                }
                break;
            case R.id.ask_layout:
                UIHelper.goWXApp(CarDetailsActivity.this);
                break;
            case R.id.add_shop_btn:
                UIHelper.showWatchCarActivity(this, mCarId);
                break;
            case R.id.order_buy_layout:
                if (mRequestFinish) {
                    UIHelper.showApplyBuyFirstActivity(this, mCarId);
                }
                break;
            case R.id.apply_buy_tv:
                if (mRequestFinish) {
                    UIHelper.showApplyBuyFirstActivity(this, mCarId);
                }
                break;
        }
    }

    @Override
    public void noticeClick(View view) {
        requestData();
    }

    public void getAddCollect(CarDetails data) {
        HttpProxy.getAddCollect(mUserId, mUserName, String.valueOf(data.getId()), new HttpCallBack<String>() {
            @Override
            public void onSuccess(String responseData) {
                collectImg.setSelected(true);
                collectTv.setSelected(true);
                collectTv.setText("已收藏");
                ToastUtils.makeTextShort("收藏成功");
            }

            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.makeTextShort("此商品已被收藏");
            }
        });
    }

    /**
     * 删除收藏
     *
     * @param goodId
     */
    private void deleteCollect(String goodId) {
        HttpProxy.cancelCollectGoods(goodId, mUserId, new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean responseData) {
                collectImg.setSelected(false);
                collectTv.setSelected(false);
                collectTv.setText("收藏");
                ToastUtils.makeTextShort("取消收藏成功");
            }

            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.makeTextShort("取消收藏失败");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        pager.startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pager.stopAutoScroll();
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
