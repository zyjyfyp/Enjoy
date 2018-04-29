package com.yunsen.enjoy.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.DatatypeBean;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.widget.FlowLayout;
import com.yunsen.enjoy.widget.drag.DragLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

public class CarDetailsActivity extends BaseFragmentActivity {
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

    private GalleryPagerAdapter galleryAdapter;
    private int[] imageViewIds;
    private List<String> imageList = new ArrayList<String>(Arrays.asList(
            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg",
            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg",
            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg"));
    private ArrayList<DatatypeBean> datas = new ArrayList<>();
    private String mCarId;
    private CarDetails mData;

    @Override
    public int getLayout() {
        return R.layout.activity_car_details;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        detailsOldCarMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中划线
        actionBarTitle.setText("汽车详情");
        imageViewIds = new int[]{R.drawable.house_background, R.drawable.house_background_1, R.drawable.house_background_2};
        galleryAdapter = new GalleryPagerAdapter();
        pager.setAdapter(galleryAdapter);
        indicator.setViewPager(pager);
        indicator.setPadding(5, 5, 10, 5);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mCarId = intent.getStringExtra(Constants.CAR_DETAILS_ID);
        }
        dragLayout.setCanDrag(false);
    }

    @Override
    protected void initListener() {
        dragLayout.setDragIconClick(this);
    }

    @Override
    public void requestData() {
        HttpProxy.getCarDetailsData(new HttpCallBack<CarDetails>() {

            @Override
            public void onSuccess(CarDetails responseData) {
                mData=responseData;
                upView(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        }, mCarId);
    }

    /**
     * 更加数据跟新View
     *
     * @param responseData
     */
    private void upView(CarDetails responseData) {
        detailsTitle.setText(responseData.getTitle());
        flowLayout.setDatas(responseData.getDatatype());//超值
        CarDetails.DefaultSpecItemBean defaultSpecItem = responseData.getDefault_spec_item();
        double rebatePrice = defaultSpecItem.getRebate_price();
        detailsCarMoney.setText(rebatePrice + "万");
        double market_price = defaultSpecItem.getMarket_price();
        detailsOldCarMoney.setText("新车含税" + market_price + "万");
        double firstPayment = defaultSpecItem.getFirst_payment();
        int stockQuantity = defaultSpecItem.getStock_quantity();
        firstPayTv.setText("首付" + firstPayment + "万 直卖专享" + stockQuantity + "首付");

    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        if (requestCode == Constants.CALL_PHONE) {
            UIHelper.showPhoneNumberActivity(this, "444****120");
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
                Toast.makeText(this, "添加收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ask_layout:
                requestPermission(Permission.CALL_PHONE, Constants.CALL_PHONE);
                break;
            case R.id.add_shop_btn:
                UIHelper.showWatchCarActivity(this);
                break;
            case R.id.order_buy_layout:
                UIHelper.showApplyBuyFirstActivity(this);
                break;
            case R.id.apply_buy_tv:
                UIHelper.showApplyBuyFirstActivity(this);
                break;
        }
    }


    //轮播图适配器
    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViewIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(CarDetailsActivity.this);
            item.setImageResource(imageViewIds[position]);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            item.setLayoutParams(params);
            item.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(item);

            final int pos = position;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CarDetailsActivity.this, ImageGalleryActivity.class);
                    intent.putStringArrayListExtra("images", (ArrayList<String>) imageList);
                    intent.putExtra("position", pos);
                    startActivity(intent);
                }
            });

            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
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
