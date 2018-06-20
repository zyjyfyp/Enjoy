package com.yunsen.enjoy.activity.buy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.utils.AccountUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.yunsen.enjoy.common.Constants.REMAINING_TIME;

/**
 * Created by Administrator on 2018/5/12.
 */

public class GoodsDescriptionActivity extends BaseFragmentActivity {


    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.team_top_layout)
    LinearLayout teamTopLayout;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.goods_desc_top_tv)
    TextView goodsDescTopTv;
    @Bind(R.id.goods_desc_top_info_tv)
    TextView goodsDescTopInfoTv;
    @Bind(R.id.goods_desc_pager)
    AutoLoopViewPager goodsDescPager;
    @Bind(R.id.goods_desc_indicator)
    CirclePageIndicator goodsDescIndicator;
    @Bind(R.id.goods_rLayout)
    RelativeLayout goodsRLayout;
    @Bind(R.id.goods_desc_title_tv)
    TextView goodsDescTitleTv;
    @Bind(R.id.goods_desc_unit_tv)
    TextView goodsDescUnitTv;
    @Bind(R.id.share_img)
    ImageView shareImg;
    @Bind(R.id.goods_desc_price)
    TextView goodsDescPrice;
    @Bind(R.id.goods_desc_vip_price)
    TextView goodsDescVipPrice;
    @Bind(R.id.good_freight_tv)
    TextView goodFreightTv;
    @Bind(R.id.sell_count_tv)
    TextView sellCountTv;
    @Bind(R.id.sell_address)
    TextView sellAddress;
    @Bind(R.id.goods_desc_web_view)
    WebView goodsDescWebView;
    @Bind(R.id.goods_desc_online_service)
    TextView goodsDescOnlineService;
    @Bind(R.id.goods_desc_add_goods)
    TextView goodsDescAddGoods;
    @Bind(R.id.goods_desc_car)
    ImageView goodsDescCar;
    @Bind(R.id.goods_desc_now_buy)
    TextView goodsDescNowBuy;
    @Bind(R.id.goods_desc_info_title_tv)
    TextView goodsDescInfoTitleTv;


    private String mGoodId;
    private int mBuyType;
    private CarDetails mCarDetail;
    private String mUnionid;
    private String mUserId;
    private String mUserName;
    private String mPoint;

    @Override

    public int getLayout() {
        return R.layout.goods_description_layout;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        actionBarTitle.setText("商品详情");
        Intent intent = getIntent();
        if (intent != null) {
            mGoodId = intent.getStringExtra(Constants.GOODS_ID_KEY);
            mBuyType = intent.getIntExtra(Constants.ACT_TYPE_KEY, Constants.DEFAULT_BUY);
        }
        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserId = sp.getString(SpConstants.USER_ID, "");
        mUserName = sp.getString(SpConstants.USER_NAME, "");
        mPoint = sp.getString(SpConstants.POINT, "");
        mUnionid = sp.getString(SpConstants.UNION_ID, "");
    }


    @Override
    protected void initListener() {

    }

    @Override
    public void requestData() {
        HttpProxy.getCarDetailsData(new HttpCallBack<CarDetails>() {
            @Override
            public void onSuccess(CarDetails responseData) {
                List<AlbumsBean> albums = responseData.getAlbums();
                upBanner(albums);
                upView(responseData);
                if (albums != null && albums.size() > 0) {
                    goodsDescWebView.loadUrl(URLConstants.REALM_NAME_HTTP + "/mobile/goods/conent-" + albums.get(0).getArticle_id() + ".html");//商品介绍
                }

            }

            @Override
            public void onError(Request request, Exception e) {

            }
        }, mGoodId);
    }


//
//    /**
//     * need 升级 移植旧代码 拥有添加商品
//     *
//     * @param carDetail
//     */
//    private void upData(CarDetails carDetail) {
//        List<SpecItemBean> datas = carDetail.getSpec_item();
//        data_shuzu = new ArrayList();
//        data_monney = new ArrayList();
//        data_market_price = new ArrayList();
//        data_goods_id = new ArrayList();
//        data_spec_text = new ArrayList();
//        data_exchange_point = new ArrayList();
//        data_exchange_price = new ArrayList();
//
//        data_goods_id_1 = new ArrayList();
//        data_price = new ArrayList();
//
//        for (int i = 0; i < datas.size(); i++) {
//            SpecItemBean data = datas.get(i);
//            data_shuzu.add(data.getSpec_ids());
//            data_monney.add(data.getSell_price());
//            data_market_price.add(data.getMarket_price());
//            data_goods_id.add(data.getGoods_id());
//            data_spec_text.add(data.getSpec_text());
//            data_exchange_point.add(data.getExchange_point());
//            data_exchange_price.add(data.getExchange_price());
//        }
//
//
//    }

    /**
     * 更加数据跟新View
     *
     * @param responseData
     */
    private void upView(CarDetails responseData) {
        if (responseData == null) {
            return;
        }
        this.mCarDetail = responseData;
        goodsDescTitleTv.setText(mCarDetail.getTitle());
        goodsDescUnitTv.setText(mCarDetail.getPacking());
        DefaultSpecItemBean defaultSpecItem = mCarDetail.getDefault_spec_item();
        if (defaultSpecItem != null) {
            goodsDescPrice.setText("¥" + defaultSpecItem.getSellPriceStr());
            goodsDescVipPrice.setText("会员最低价： " + defaultSpecItem.getSellPriceStr() + "元");
            sellCountTv.setText("月销" + defaultSpecItem.getMonthly_supply() + "件");
            sellAddress.setText(mCarDetail.getCity());
        }
    }

    private void upBanner(List<AlbumsBean> albums) {
        if (albums != null && albums.size() > 0) {
            CarTopBannerAdapter adapter = new CarTopBannerAdapter(albums, GoodsDescriptionActivity.this);
            goodsDescPager.setAdapter(adapter);
            goodsDescIndicator.setViewPager(goodsDescPager);
            goodsDescIndicator.setPadding(5, 5, 10, 5);
        }
    }


//    public void onViewClicked(View view) {
//        if (mCarDetail == null) {
//            return;
//        }
//        if (view.getId() == R.id.back_btn) {
//            finish();
//            return;
//        }
//        if (!AccountUtils.hasLogin()) {
//            UIHelper.showUserLoginActivity(this);
//            return;
//        }
//        if (!AccountUtils.hasBoundPhone()) {
//            UIHelper.showBundPhoneActivity(this);
//            return;
//        }
//
//        switch (view.getId()) {
//            case R.id.btn_dianping: // 联系客服
//                UIHelper.goWXApp(GoodsDescriptionActivity.this);
//                break;
//            case R.id.enter_shop: //返回购物车
//                UIHelper.showHomeCarFragment(this);
//                break;
//            case R.id.goods_share_img:

//                break;
//
//            case R.id.btn_collect:  //收藏
//                if (mRequestFinish) {
//                    if (collectImg.isSelected()) {
//                        deleteCollect(mGoodId);
//                    } else {
//                        HttpProxy.getAddCollect(mUserId, mUserName, "" + mCarDetail.getId(), new HttpCallBack<String>() {
//                            @Override
//                            public void onSuccess(String responseData) {
//                                collectImg.setSelected(true);
//                                collectTv.setSelected(true);
//                                collectTv.setText("已收藏");
//                                ToastUtils.makeTextShort("收藏成功");
//                            }
//
//                            @Override
//                            public void onError(Request request, Exception e) {
//                                if (e instanceof DataException) {
//                                    ToastUtils.makeTextShort(e.getMessage());
//                                }
//                            }
//                        });
//                    }
//                } else {
//                    ToastUtils.makeTextShort("数据加载中，请稍后");
//                }
//
//                break;
//            case R.id.btn_add_shop_cart:
//                if (isSecondGoods && (mRemainingTime == 0 || mRemainingTime == -2)) {
//                    ToastUtils.makeTextShort("秒杀活动已结束");
//                } else if (isSecondGoods && mRemainingTime == -1) {
//                    ToastUtils.makeTextShort("秒杀活动即将开始");
//                } else {
//                    CommomConfrim.initData(mCarDetail.getDefault_spec_item().getSell_price(), mCarDetail.getImg_url(), 2, "" + mCarDetail.getDefault_spec_item().getGoods_id(), false);
//                    CommomConfrim.showSheet(this, String.valueOf(mCarDetail.getId()));
//                }
//                break;
//            case R.id.order_shop_now:
//                if (isSecondGoods && (mRemainingTime == 0 || mRemainingTime == -2)) {
//                    ToastUtils.makeTextShort("秒杀活动已结束");
//                } else if (isSecondGoods && mRemainingTime == -1) {
//                    ToastUtils.makeTextShort("秒杀活动即将开始");
//                } else {
//                    CommomConfrim.initData(mCarDetail.getDefault_spec_item().getSell_price(), mCarDetail.getImg_url(), 1, "" + mCarDetail.getDefault_spec_item().getGoods_id(), false);
//                    CommomConfrim.showSheet(this, String.valueOf(mCarDetail.getId()));
//                }
//                break;
//            case R.id.market_information_juduihuan: //兑换
//                if (mCanExchange) {
//                    CommomConfrim.initData2(mCarDetail.getDefault_spec_item().getSell_price(), mCarDetail.getDefault_spec_item().getExchange_point(), mCarDetail.getImg_url(), 3, "" + mCarDetail.getDefault_spec_item().getGoods_id(), false);
//                    CommomConfrim.showSheet(GoodsDescriptionActivity.this, String.valueOf(mCarDetail.getId()));
//                } else {
//                    ToastUtils.makeTextShort("积分不足");
//                }
//                break;
//        }
//    }


    @OnClick({R.id.action_back, R.id.goods_desc_top_tv,
            R.id.goods_desc_top_info_tv, R.id.share_img,
            R.id.goods_desc_online_service, R.id.goods_desc_add_goods,
            R.id.goods_desc_car, R.id.goods_desc_now_buy})
    public void onViewClicked(View view) {
        DefaultSpecItemBean default_spec_item = mCarDetail.getDefault_spec_item();
        if (default_spec_item == null) {
            default_spec_item = new DefaultSpecItemBean();
        }
        if (view.getId() == R.id.action_back) {
            finish();
        } else if (view.getId() == R.id.goods_desc_top_info_tv) {
            int top = goodsDescInfoTitleTv.getTop();
            scrollView.smoothScrollTo(0,top);
        } else {
            if (!AccountUtils.hasLogin()) {
                UIHelper.showUserLoginActivity(this);
            } else if (!AccountUtils.hasBoundPhone()) {
                UIHelper.showBundPhoneActivity(this);
            } else {
                switch (view.getId()) {
                    case R.id.share_img:
                        String shareUrl = URLConstants.SHEAR_URL + "/goods/show-" + mCarDetail.getId() + ".html?cid=" + mCarDetail.getCompany_id() + "&unionid=" + mUnionid + "&shareid=" + mUserId + "&from=android";
                        UIHelper.showShareGoodsActivity(this, mCarDetail.getTitle(), mCarDetail.getSubtitle(), shareUrl, mCarDetail.getImg_url());
                        break;
                    case R.id.goods_desc_online_service: //联系客服
                        UIHelper.goWXApp(GoodsDescriptionActivity.this);
                        break;
                    case R.id.goods_desc_add_goods:
                        CommomConfrim.initData(default_spec_item.getSell_price(), mCarDetail.getImg_url(), 2, "" + default_spec_item.getGoods_id(), false);
                        CommomConfrim.showSheet(this, String.valueOf(mCarDetail.getId()), default_spec_item.getGoods_no());
                        break;
                    case R.id.goods_desc_car:
                        UIHelper.showHomeCarFragment(this);
                        break;
                    case R.id.goods_desc_now_buy: //立即购买
                        CommomConfrim.initData(default_spec_item.getSell_price(), mCarDetail.getImg_url(), 1, "" + default_spec_item.getGoods_id(), false);
                        CommomConfrim.showSheet(this, String.valueOf(mCarDetail.getId()), default_spec_item.getGoods_no());
                        break;
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }

}
