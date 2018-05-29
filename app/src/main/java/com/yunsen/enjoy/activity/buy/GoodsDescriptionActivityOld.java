package com.yunsen.enjoy.activity.buy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
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
    private String mGoodId;
    private CarDetails mCarDetail;

    public static ArrayList data_shuzu, data_monney, data_goods_id, data_market_price,
            data_goods_id_1, data_price, data_spec_text, data_exchange_price, data_exchange_point;
    private String mUserName;
    private String mUserId;
    private int mBuyType = 1;

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


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mGoodId = intent.getStringExtra(Constants.GOODS_ID_KEY);
            mBuyType = intent.getIntExtra(Constants.ACT_TYPE_KEY, Constants.DEFAULT_BUY);
//            String actName = intent.getStringExtra(Constants.ACT_NAME_KEY);
        }
        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserId = sp.getString(SpConstants.USER_ID, "");
        mUserName = sp.getString(SpConstants.USER_NAME, "");

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
        double rebatePrice = defaultSpecItem.getSell_price();
        if (mBuyType == Constants.DEFAULT_BUY) {
            goodsPriceTv.setText("¥" + rebatePrice);
            goodsMarketPriceTv.setText("¥" + defaultSpecItem.getMarkePriceStr());
        } else if (mBuyType == Constants.POINT_BUY) {
            int exchangePoint = defaultSpecItem.getExchange_point();
            goodsPointTv.setText(exchangePoint + "分+" + rebatePrice + "元");
            goodsMarketPriceTv2.setText("¥" + defaultSpecItem.getMarkePriceStr());
        }

        double cashingPacket = defaultSpecItem.getCashing_packet();
        goodsPacketPriceTv.setText("¥" + cashingPacket);
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
            case R.id.enter_shop: //返回购物车
                UIHelper.showShopCar(this);
                finish();
                break;
            case R.id.goods_share_img:
                UIHelper.showDBFengXiangActivity(this, String.valueOf(mCarDetail.getId()), String.valueOf(mCarDetail.getCompany_id()), mCarDetail.getTitle(),
                        mCarDetail.getSubtitle(), mCarDetail.getImg_url());
                break;
            case R.id.btn_dianping: //评论
                if (mCarDetail.getAlbums() != null && mCarDetail.getAlbums().size() > 0) {
                    UIHelper.showDianPingActivity(this, mCarDetail.getAlbums().get(0).getArticle_id());
                }
                break;
            case R.id.btn_collect:  //收藏
                HttpProxy.getAddCollect(mUserId, mUserName, "" + mCarDetail.getId(), new HttpCallBack<String>() {
                    @Override
                    public void onSuccess(String responseData) {
                        Toast.makeText(GoodsDescriptionActivityOld.this, responseData, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        ToastUtils.makeTextShort(e.getMessage());
                    }
                });
                break;
            case R.id.btn_add_shop_cart:
                CommomConfrim.initData(mCarDetail.getDefault_spec_item().getSell_price(), mCarDetail.getImg_url(), 2, "" + mCarDetail.getDefault_spec_item().getGoods_id(), false);
                CommomConfrim.showSheet(this, String.valueOf(mCarDetail.getId()));
                break;
            case R.id.order_shop_now:
                CommomConfrim.initData(mCarDetail.getDefault_spec_item().getSell_price(), mCarDetail.getImg_url(), 1, "" + mCarDetail.getDefault_spec_item().getGoods_id(), false);
                CommomConfrim.showSheet(this, String.valueOf(mCarDetail.getId()));
                break;
            case R.id.market_information_juduihuan:
                CommomConfrim.initData2(mCarDetail.getDefault_spec_item().getSell_price(), mCarDetail.getDefault_spec_item().getExchange_point(), mCarDetail.getImg_url(), 3, "" + mCarDetail.getDefault_spec_item().getGoods_id(), false);
                CommomConfrim.showSheet(GoodsDescriptionActivityOld.this, String.valueOf(mCarDetail.getId()));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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


}
