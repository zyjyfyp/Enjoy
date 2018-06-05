package com.yunsen.enjoy.activity.buy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.CommomConfrim;
import com.yunsen.enjoy.adapter.CarTopBannerAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.AlbumsBean;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.DefaultSpecItemBean;
import com.yunsen.enjoy.model.SpecItemBean;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.utils.ToastUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/12.
 */

public class GoodsDescriptionActivityOld extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.goods_top_pager)
    AutoLoopViewPager goodsTopPager;
    @Bind(R.id.goods_top_indicator)
    CirclePageIndicator goodsTopIndicator;
    @Bind(R.id.goods_rLayout)
    RelativeLayout goodsRLayout;
    @Bind(R.id.goods_title)
    TextView goodsTitle;
    @Bind(R.id.goods_share_img)
    ImageView goodsShareImg;
    @Bind(R.id.goods_price_tv)
    TextView goodsPriceTv;
    @Bind(R.id.goods_packet_price_tv)
    TextView goodsPacketPriceTv;
    @Bind(R.id.goods_all_money_layout)
    LinearLayout goodsAllMoneyLayout;
    @Bind(R.id.goods_market_price_tv)
    TextView goodsMarketPriceTv;
    @Bind(R.id.market_information_appraise)
    LinearLayout marketInformationAppraise;
    @Bind(R.id.good_p_tv)
    TextView goodPTv;
    @Bind(R.id.good_p_bar)
    ProgressBar goodPBar;
    @Bind(R.id.middle_p_bar)
    ProgressBar middlePBar;
    @Bind(R.id.warning_p_bar)
    ProgressBar warningPBar;
    @Bind(R.id.imagesLayout)
    LinearLayout imagesLayout;
    @Bind(R.id.goods_description_rb)
    RadioButton goodsDescriptionRb;
    @Bind(R.id.goods_size_rb)
    RadioButton goodsSizeRb;
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.ll_shiyishicai1)
    LinearLayout llShiyishicai1;
    @Bind(R.id.enter_shop)
    Button enterShop;
    @Bind(R.id.back_btn)
    Button backBtn;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.market_information_juduihuan)
    LinearLayout marketInformationJuduihuan;
    @Bind(R.id.btn_dianping)
    LinearLayout btnDianping;
    @Bind(R.id.btn_collect)
    LinearLayout btnCollect;
    @Bind(R.id.btn_add_shop_cart)
    Button btnAddShopCart;
    @Bind(R.id.order_shop_now)
    LinearLayout orderShopNow;
    @Bind(R.id.market_information_bottom)
    LinearLayout marketInformationBottom;
    @Bind(R.id.goods_radio_group)
    RadioGroup goodsRadioGroup;
    public static int fangshi = 0;
    @Bind(R.id.goods_point_tv)
    TextView goodsPointTv;
    @Bind(R.id.point_layout)
    LinearLayout pointLayout;
    @Bind(R.id.goods_market_price_tv2)
    TextView goodsMarketPriceTv2;
    @Bind(R.id.specifications_layout)
    LinearLayout specificationsLayout;

    private static final int REMAINING_TIME = 1;
    @Bind(R.id.collect_img)
    ImageView collectImg;
    @Bind(R.id.collect_tv)
    TextView collectTv;
    private String mGoodId;
    private CarDetails mCarDetail;

    public static ArrayList data_shuzu, data_monney, data_goods_id, data_market_price,
            data_goods_id_1, data_price, data_spec_text, data_exchange_price, data_exchange_point;
    private String mUserName;
    private String mUserId;
    private int mBuyType = 1;
    private String mPoint;
    private boolean mCanExchange = false;
    private long mRemainingTime = 0;
    private static MyHandler sHandler;
    private boolean isSecondGoods = false;
    private boolean mRequestFinish = false;

    @Override

    public int getLayout() {
        return R.layout.goods_description_layout_old;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        goodsMarketPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中划线
        goodsMarketPriceTv2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        goodsRLayout.getLayoutParams().height = DeviceUtil.getWidth(this);
        sHandler = new MyHandler(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mGoodId = intent.getStringExtra(Constants.GOODS_ID_KEY);
            mBuyType = intent.getIntExtra(Constants.ACT_TYPE_KEY, Constants.DEFAULT_BUY);
            mRemainingTime = intent.getLongExtra(Constants.REMAINING_TIME, 0);
        }

        if (mRemainingTime > 0) { //秒杀商品
            isSecondGoods = true;
            sHandler.sendEmptyMessageDelayed(REMAINING_TIME, 1000);
        } else if (mRemainingTime == -1) {
            isSecondGoods = true;
        } else if (mRemainingTime == -2) {
            isSecondGoods = true;
        }

        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserId = sp.getString(SpConstants.USER_ID, "");
        mUserName = sp.getString(SpConstants.USER_NAME, "");
        mPoint = sp.getString(SpConstants.POINT, "");
        switch (mBuyType) {
            case Constants.DEFAULT_BUY:
                marketInformationJuduihuan.setVisibility(View.GONE);
                marketInformationBottom.setVisibility(View.VISIBLE);
                goodsAllMoneyLayout.setVisibility(View.VISIBLE);
                pointLayout.setVisibility(View.GONE);
                break;
            case Constants.POINT_BUY:
                marketInformationJuduihuan.setVisibility(View.VISIBLE);
                marketInformationBottom.setVisibility(View.GONE);
                goodsAllMoneyLayout.setVisibility(View.GONE);
                pointLayout.setVisibility(View.VISIBLE);
                break;
        }

    }


    @Override
    protected void initListener() {
        goodsRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void requestData() {
        mRequestFinish = false;
        HttpProxy.getCarDetailsData(new HttpCallBack<CarDetails>() {
            @Override
            public void onSuccess(CarDetails responseData) {
                List<AlbumsBean> albums = responseData.getAlbums();
                upBanner(albums);
                upView(responseData);
                upData(responseData);
                if (albums != null && albums.size() > 0) {
                    webview.loadUrl(URLConstants.REALM_NAME_HTTP + "/mobile/goods/conent-" + albums.get(0).getArticle_id() + ".html");//商品介绍
                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        }, mGoodId);

        HttpProxy.getHasCollectGoods(mGoodId, mUserId, new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean responseData) {
                collectImg.setSelected(false);
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
     * need 升级 移植旧代码 拥有添加商品
     *
     * @param carDetail
     */
    private void upData(CarDetails carDetail) {
        List<SpecItemBean> datas = carDetail.getSpec_item();
        data_shuzu = new ArrayList();
        data_monney = new ArrayList();
        data_market_price = new ArrayList();
        data_goods_id = new ArrayList();
        data_spec_text = new ArrayList();
        data_exchange_point = new ArrayList();
        data_exchange_price = new ArrayList();

        data_goods_id_1 = new ArrayList();
        data_price = new ArrayList();

        for (int i = 0; i < datas.size(); i++) {
            SpecItemBean data = datas.get(i);
            data_shuzu.add(data.getSpec_ids());
            data_monney.add(data.getSell_price());
            data_market_price.add(data.getMarket_price());
            data_goods_id.add(data.getGoods_id());
            data_spec_text.add(data.getSpec_text());
            data_exchange_point.add(data.getExchange_point());
            data_exchange_price.add(data.getExchange_price());
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
        goodsTitle.setText(responseData.getTitle());
        DefaultSpecItemBean defaultSpecItem = responseData.getDefault_spec_item();
        String rebatePrice = defaultSpecItem.getSellPriceStr();
        if (mBuyType == Constants.DEFAULT_BUY) {
            goodsPriceTv.setText("¥" + rebatePrice);
            goodsMarketPriceTv.setText("¥" + defaultSpecItem.getMarkePriceStr());
        } else if (mBuyType == Constants.POINT_BUY) {
            int exchangePoint = defaultSpecItem.getExchange_point();
            String exchangePriceStr = defaultSpecItem.getExchange_priceStr();
            goodsPointTv.setText(exchangePoint + "分+" + exchangePriceStr + "元");
            goodsMarketPriceTv2.setText("¥" + defaultSpecItem.getMarkePriceStr());
            int ownPoint = Integer.parseInt(mPoint);
            if (exchangePoint < ownPoint) {
                mCanExchange = true;
            } else {
                mCanExchange = false;
            }
        }
        goodsPacketPriceTv.setText("¥" + defaultSpecItem.getCashing_packetStr());
        this.mCarDetail = responseData;

    }

    private void upBanner(List<AlbumsBean> albums) {
        if (albums != null && albums.size() > 0) {
            CarTopBannerAdapter adapter = new CarTopBannerAdapter(albums, GoodsDescriptionActivityOld.this);
            goodsTopPager.setAdapter(adapter);
            goodsTopIndicator.setViewPager(goodsTopPager);
            goodsTopIndicator.setPadding(5, 5, 10, 5);
        }
    }

    @OnClick({R.id.enter_shop, R.id.back_btn, R.id.goods_share_img,
            R.id.btn_dianping, R.id.btn_collect, R.id.btn_add_shop_cart,
            R.id.order_shop_now, R.id.market_information_juduihuan})
    public void onViewClicked(View view) {
        if (mCarDetail == null) {
            return;
        }
        if (view.getId() == R.id.back_btn) {
            finish();
            return;
        }
        if (!AccountUtils.hasLogin()) {
            UIHelper.showUserLoginActivity(this);
            return;
        }
        if (!AccountUtils.hasBoundPhone()) {
            UIHelper.showBundPhoneActivity(this);
            return;
        }

        switch (view.getId()) {
            case R.id.btn_dianping: //购物车
            case R.id.enter_shop: //返回购物车
                UIHelper.showShopCar(this);
                break;
            case R.id.goods_share_img:
                UIHelper.showDBFengXiangActivity(this, String.valueOf(mCarDetail.getId()), String.valueOf(mCarDetail.getCompany_id()), mCarDetail.getTitle(),
                        mCarDetail.getSubtitle(), mCarDetail.getImg_url());
                break;

            case R.id.btn_collect:  //收藏
                if (mRequestFinish) {
                    if (collectImg.isSelected()) {
                        deleteCollect(mGoodId);
                    } else {
                        HttpProxy.getAddCollect(mUserId, mUserName, "" + mCarDetail.getId(), new HttpCallBack<String>() {
                            @Override
                            public void onSuccess(String responseData) {
                                Toast.makeText(GoodsDescriptionActivityOld.this, responseData, Toast.LENGTH_SHORT).show();
                                collectImg.setSelected(true);
                                collectImg.setSelected(true);
                            }

                            @Override
                            public void onError(Request request, Exception e) {
                                ToastUtils.makeTextShort(e.getMessage());
                            }
                        });
                    }
                }

                break;
            case R.id.btn_add_shop_cart:
                if (isSecondGoods && (mRemainingTime == 0 || mRemainingTime == -2)) {
                    ToastUtils.makeTextShort("秒杀活动已结束");
                } else if (isSecondGoods && mRemainingTime == -1) {
                    ToastUtils.makeTextShort("秒杀活动即将开始");
                } else {
                    CommomConfrim.initData(mCarDetail.getDefault_spec_item().getSell_price(), mCarDetail.getImg_url(), 2, "" + mCarDetail.getDefault_spec_item().getGoods_id(), false);
                    CommomConfrim.showSheet(this, String.valueOf(mCarDetail.getId()));
                }
                break;
            case R.id.order_shop_now:
                if (isSecondGoods && (mRemainingTime == 0 || mRemainingTime == -2)) {
                    ToastUtils.makeTextShort("秒杀活动已结束");
                } else if (isSecondGoods && mRemainingTime == -1) {
                    ToastUtils.makeTextShort("秒杀活动即将开始");
                } else {
                    CommomConfrim.initData(mCarDetail.getDefault_spec_item().getSell_price(), mCarDetail.getImg_url(), 1, "" + mCarDetail.getDefault_spec_item().getGoods_id(), false);
                    CommomConfrim.showSheet(this, String.valueOf(mCarDetail.getId()));
                }
                break;
            case R.id.market_information_juduihuan: //兑换
                if (mCanExchange) {
                    CommomConfrim.initData2(mCarDetail.getDefault_spec_item().getSell_price(), mCarDetail.getDefault_spec_item().getExchange_point(), mCarDetail.getImg_url(), 3, "" + mCarDetail.getDefault_spec_item().getGoods_id(), false);
                    CommomConfrim.showSheet(GoodsDescriptionActivityOld.this, String.valueOf(mCarDetail.getId()));
                } else {
                    ToastUtils.makeTextShort("积分不足");
                }
                break;
        }
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
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (sHandler != null && sHandler.hasMessages(REMAINING_TIME)) { //销毁倒计时
            sHandler.removeMessages(REMAINING_TIME);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.goods_description_rb:
                specificationsLayout.setVisibility(View.GONE);
                webview.setVisibility(View.VISIBLE);
                break;
            case R.id.goods_size_rb:
                webview.setVisibility(View.GONE);
                specificationsLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private static class MyHandler extends Handler {
        WeakReference<GoodsDescriptionActivityOld> weakReference;

        public MyHandler(GoodsDescriptionActivityOld act) {
            this.weakReference = new WeakReference<GoodsDescriptionActivityOld>(act);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            GoodsDescriptionActivityOld act = weakReference.get();
            if (act != null) {
                act.mRemainingTime -= 1;
                if (act.mRemainingTime > 0) {
                    sHandler.sendEmptyMessageDelayed(REMAINING_TIME, 1000);
                }
            }
        }
    }

}
