package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/5.
 */

public class ReleaseProductsActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.personal_rb)
    RadioButton personalRb;
    @Bind(R.id.corporate_rb)
    RadioButton corporateRb;
    @Bind(R.id.select_title_edt)
    EditText selectTitleEdt;
    @Bind(R.id.select_title_size_tv)
    TextView selectTitleSizeTv;
    @Bind(R.id.select_goods_tv)
    TextView selectGoodsTv;
    @Bind(R.id.product_discription_edt)
    EditText productDiscriptionEdt;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.classify_tv)
    TextView classifyTv;
    @Bind(R.id.start_number_edt)
    EditText startNumberEdt;
    @Bind(R.id.all_number_edt)
    EditText allNumberEdt;
    @Bind(R.id.quality_date_tv)
    TextView qualityDateTv;
    @Bind(R.id.create_date_tv)
    TextView createDateTv;
    @Bind(R.id.point_tv)
    TextView pointTv;
    @Bind(R.id.price_edt)
    EditText priceEdt;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;

    @Override
    public int getLayout() {
        return R.layout.activity_release_products;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("发布产品");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }



    @OnClick({R.id.action_back, R.id.action_bar_right, R.id.select_title_edt, R.id.select_title_size_tv, R.id.select_goods_tv, R.id.classify_tv, R.id.quality_date_tv, R.id.create_date_tv, R.id.point_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                break;
            case R.id.action_bar_right:
                break;
            case R.id.select_title_edt:
                break;
            case R.id.select_title_size_tv:
                break;
            case R.id.select_goods_tv:
                break;
            case R.id.classify_tv:
                break;
            case R.id.quality_date_tv:
                break;
            case R.id.create_date_tv:
                break;
            case R.id.point_tv:
                break;
        }
    }

    @OnClick(R.id.radio_group)
    public void onViewClicked() {
    }
}
