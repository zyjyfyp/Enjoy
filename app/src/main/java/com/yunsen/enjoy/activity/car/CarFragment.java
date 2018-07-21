package com.yunsen.enjoy.activity.car;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.car.adapter.CarShoppingAdapter;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.GoodsCarInfo;
import com.yunsen.enjoy.model.OrderInfo;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.StringUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.interfaces.TotalInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/17.
 */

public class CarFragment extends BaseFragment implements TotalInterface {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.shop_car_recycler)
    RecyclerView shopCarRecycler;
    @Bind(R.id.goods_all_size)
    TextView goodsAllSize;
    @Bind(R.id.goods_all_price)
    TextView goodsAllPrice;
    @Bind(R.id.change_goods_btn)
    Button changeGoodsBtn;
    @Bind(R.id.no_goods_layout)
    LinearLayout noGoodsLayout;
    @Bind(R.id.has_goods_layout)
    FrameLayout hasGoodsLayout;
    List<List<GoodsCarInfo>> mDatas;
    private CarShoppingAdapter mAdapter;
    private CheckBox checkAllGoods;
    private int mPageIndex = 1;
    private boolean mFlagLoad = true;//第一次打开不走onResumen 刷新界面
    private int mCurrentPosition = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_car;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        actionBarTitle.setText("换物车");
        actionBack.setVisibility(View.GONE);
        shopCarRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new ArrayList<GoodsCarInfo>());
        mAdapter = new CarShoppingAdapter(getActivity(), R.layout.car_shopping_item, mDatas);
        shopCarRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mAdapter.setTotalInterface(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFlagLoad) {
            mFlagLoad = false;
        } else {
            requestData();
        }
    }

    @Override
    protected void requestData() {
        HttpProxy.getMyShoppingCart(String.valueOf(mPageIndex), new HttpCallBack<List<GoodsCarInfo>>() {
            @Override
            public void onSuccess(List<GoodsCarInfo> responseData) {
                ArrayList<List<GoodsCarInfo>> list = new ArrayList<>();
                list.add(responseData);
                mAdapter.upBaseDatas(list);
                upTotalData(0, 0, -1);//还原状态
                isShowEmptyView(responseData.size() == 0);
            }

            @Override
            public void onError(Request request, Exception e) {
                isShowEmptyView(true);
            }
        });
    }


    @OnClick(R.id.change_goods_btn)
    public void onViewClicked() {
        if (mAdapter == null) {
            return;
        }
        if (goodsAllSize.getTag() == null || ((int) goodsAllSize.getTag()) == 0 || mCurrentPosition == -1) {
            ToastUtils.makeTextShort("请选择要支付的商品");
        } else {
            submitBuyGoods((int) goodsAllSize.getTag(), (double) goodsAllPrice.getTag());
        }
    }

    private void submitBuyGoods(int account, double price) {
        ArrayList<GoodsCarInfo> requestDatas = mAdapter.getCurrentRequestList(mCurrentPosition);
        UIHelper.showSubmitOrderActivity(getActivity(), requestDatas, account, price);


//        String[] requestDatas = mAdapter.getCurrentRequestDatas(mCurrentPosition);
//        if (requestDatas != null && requestDatas.length >= 3) {
//            HttpProxy.submitShoppingGoods(requestDatas[0], requestDatas[1], requestDatas[2], new HttpCallBack<OrderInfo>() {
//                @Override
//                public void onSuccess(OrderInfo responseData) {
//                    String buyNo = responseData.getBuy_no();
//                    UIHelper.showSubmitOrderActivity(getActivity());
//                }
//
//                @Override
//                public void onError(Request request, Exception e) {
//                    ToastUtils.makeTextShort(e.getMessage());
//                }
//            });
//        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private void isShowEmptyView(boolean isEmptyData) {
        if (isEmptyData) {
            noGoodsLayout.setVisibility(View.VISIBLE);
            hasGoodsLayout.setVisibility(View.GONE);
        } else {
            noGoodsLayout.setVisibility(View.GONE);
            hasGoodsLayout.setVisibility(View.VISIBLE);
        }
    }

    private static final String TAG = "CarFragment";

    @Override
    public void upTotalData(double price, int num, int position) {
        Log.e(TAG, "upTotalData: num=" + num);
        mCurrentPosition = position;
        if (position == -1) {
            isShowEmptyView(true);
        } else {
            isShowEmptyView(false);
            goodsAllSize.setText(num + "件");
            goodsAllSize.setTag(num);
            goodsAllPrice.setTag(price);
            goodsAllPrice.setText("￥" + StringUtils.changeToMoney(price));
        }

//        if (mDatas)
//        goodsAllSize.setText(goodsSum + "件");
//        goodsAllPrice.setText("￥" + StringUtils.changeToMoney(goodsPrices));
//        isShowEmptyView(mDatas.size() == 0);
    }
}
