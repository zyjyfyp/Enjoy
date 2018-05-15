package com.yunsen.enjoy.activity.buy;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.adapter.CarTopBannerAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.AlbumsBean;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.utils.ToastUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/12.
 */

public class GoodsDescriptionActivity extends BaseFragmentActivity {

    @Bind(R.id.enter_shop)
    Button enterShop;
    @Bind(R.id.back_btn)
    Button backBtn;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
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
    private String mGoodId;

    @Override
    public int getLayout() {
        return R.layout.goods_description_layout;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        goodsMarketPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中划线
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mGoodId = intent.getStringExtra(Constants.GOODS_ID_KEY);
//            String actName = intent.getStringExtra(Constants.ACT_NAME_KEY);
        }
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

            }

            @Override
            public void onError(Request request, Exception e) {

            }
        }, mGoodId);

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
        CarDetails.DefaultSpecItemBean defaultSpecItem = responseData.getDefault_spec_item();
        double rebatePrice = defaultSpecItem.getSell_price();
        goodsPriceTv.setText("￥" + rebatePrice);
        double market_price = defaultSpecItem.getMarket_price();
        goodsMarketPriceTv.setText("￥" + market_price );
        double cashingPacket = defaultSpecItem.getCashing_packet();
        goodsPacketPriceTv.setText("￥" + cashingPacket);

    }

    private void upBanner(List<AlbumsBean> albums) {
        if (albums != null && albums.size() > 0) {
            CarTopBannerAdapter adapter = new CarTopBannerAdapter(albums, GoodsDescriptionActivity.this);
            goodsTopPager.setAdapter(adapter);
            goodsTopIndicator.setViewPager(goodsTopPager);
            goodsTopIndicator.setPadding(5, 5, 10, 5);
        }
    }

    @OnClick({R.id.enter_shop, R.id.back_btn, R.id.goods_share_img, R.id.btn_dianping, R.id.btn_collect, R.id.btn_add_shop_cart, R.id.order_shop_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.enter_shop:
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.goods_share_img:
                ToastUtils.makeTextShort("分享");

                break;
            case R.id.btn_dianping:
                ToastUtils.makeTextShort("评论");
                break;
            case R.id.btn_collect:
                ToastUtils.makeTextShort("收藏");

                break;
            case R.id.btn_add_shop_cart:
                ToastUtils.makeTextShort("添加到购物车");
                break;
            case R.id.order_shop_now:
                ToastUtils.makeTextShort("立刻购买");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
