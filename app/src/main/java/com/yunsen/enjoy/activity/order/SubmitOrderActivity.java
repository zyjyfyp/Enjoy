package com.yunsen.enjoy.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.car.adapter.DShoppingCarAdapter;
import com.yunsen.enjoy.activity.order.adapter.ChangeIntoAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.model.GoodsCarInfo;
import com.yunsen.enjoy.model.UserAddressData;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.NoScrollLinearLayoutManager;
import com.yunsen.enjoy.widget.AddAndSubView;
import com.yunsen.enjoy.widget.interfaces.GoodsSumInterface;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/20/020.
 */

public class SubmitOrderActivity extends BaseFragmentActivity implements GoodsSumInterface {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.person_name_tv)
    TextView personNameTv;
    @Bind(R.id.person_phone_tv)
    TextView personPhoneTv;
    @Bind(R.id.person_address_tv)
    TextView personAddressTv;
    @Bind(R.id.has_address_layout)
    LinearLayout hasAddressLayout;
    @Bind(R.id.no_address_layout)
    LinearLayout noAddressLayout;
    @Bind(R.id.add_address_layout)
    LinearLayout addAddressLayout;
    @Bind(R.id.recycler_into)
    RecyclerView recyclerInto;
    @Bind(R.id.change_out_add_tv)
    TextView changeOutAddTv;
    @Bind(R.id.change_out_img)
    ImageView changeOutImg;
    @Bind(R.id.change_out_title)
    TextView changeOutTitle;
    @Bind(R.id.change_out_price)
    TextView changeOutPrice;
    @Bind(R.id.change_out_layout)
    LinearLayout changeOutLayout;
    @Bind(R.id.change_out_prompt)
    TextView changeOutPrompt;
    @Bind(R.id.change_out_control)
    AddAndSubView changeOutControl;
    @Bind(R.id.change_out_goods_size)
    TextView changeOutGoodsSize;
    @Bind(R.id.change_out_goods_price)
    TextView changeOutGoodsPrice;
    @Bind(R.id.all_goods_size_tv)
    TextView allGoodsSizeTv;
    @Bind(R.id.all_goods_price_tv)
    TextView allGoodsPriceTv;
    @Bind(R.id.submit_btn)
    Button submitBtn;
    @Bind(R.id.change_into_goods_size)
    TextView changeIntoGoodsSize;
    @Bind(R.id.change_into_goods_price)
    TextView changeIntoGoodsPrice;
    private ArrayList<GoodsCarInfo> mChangeIntoDatas;
    private ChangeIntoAdapter mChangeIntoAdapter;
    private int mChangeIntoSize;
    private double mChangeIntoPrice;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_submit_order;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("确认订单");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mChangeIntoDatas = bundle.getParcelableArrayList(Constants.SUBMIT_ORDER_KEY);
        mChangeIntoSize = bundle.getInt(Constants.GOODS_SIZE);
        mChangeIntoPrice = bundle.getDouble(Constants.GOODS_PRICE);
        changeIntoGoodsSize.setText("共" + mChangeIntoSize + "件商品 总价值：");
        changeIntoGoodsPrice.setText("¥" + mChangeIntoPrice);

        mChangeIntoAdapter = new ChangeIntoAdapter(this, R.layout.change_into_item, mChangeIntoDatas);
        recyclerInto.setLayoutManager(new NoScrollLinearLayoutManager(this));
        recyclerInto.setAdapter(mChangeIntoAdapter);
    }

    @Override
    protected void initListener() {
        mChangeIntoAdapter.setGoodsSumCall(this);
        mChangeIntoAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                if (adapter instanceof DShoppingCarAdapter) {
                    List<GoodsCarInfo> datas = ((DShoppingCarAdapter) adapter).getDatas();
                    if (datas != null && datas.size() > position) {
                        GoodsCarInfo data = datas.get(position);
                        UIHelper.showGoodsDescriptionActivity(SubmitOrderActivity.this, String.valueOf(data.getArticle_id()), data.getTitle());
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.ADDRESS_MANAGER_REQUEST) {
                hasAddressLayout.setVisibility(View.VISIBLE);
                noAddressLayout.setVisibility(View.GONE);
                UserAddressData address = (UserAddressData) data.getSerializableExtra(Constants.ADDRESS_DATA_KEY);
                personNameTv.setText(address.user_accept_name);
                personPhoneTv.setText(address.user_mobile);
                personAddressTv.setText(address.city + address.province + address.user_area + address.user_address);
            }
        }

    }

    @OnClick({R.id.action_back, R.id.add_address_layout, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.add_address_layout:
                UIHelper.showAddressManagerActivity(this);
                break;
            case R.id.submit_btn:
                break;
        }
    }

    @Override
    public void GoodsSumCallBack(int goodsSum, double goodsPrices) {
        changeIntoGoodsSize.setText("共" + goodsSum + "件商品 总价值：");
        changeIntoGoodsPrice.setText("¥" + goodsPrices);
    }

    @Override
    public void isCheckAll(boolean isCheckAll) {

    }


}
