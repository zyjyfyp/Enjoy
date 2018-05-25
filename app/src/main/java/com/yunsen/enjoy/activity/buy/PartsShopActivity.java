package com.yunsen.enjoy.activity.buy;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.adapter.PartsShopFragmentPager;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.home.GoodsPartsAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.ClassifyBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/24.
 */

public class PartsShopActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.part_shop_pager)
    ViewPager partShopPager;

    private ArrayList<PartsShopFragment> mPartsShopFragments;
    private ArrayList<String> mListTitle;

    @Override
    public int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_parts_shop;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("配件商城");
        mPartsShopFragments = new ArrayList<>();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void requestData() {
        super.requestData();
        HttpProxy.getGoodsClassifyDatas("mall", new HttpCallBack<List<ClassifyBean>>() {
            @Override
            public void onSuccess(List<ClassifyBean> responseData) {
                upTabLayout(responseData);
                showFragment();
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    private void showFragment() {
        partShopPager.setAdapter(new PartsShopFragmentPager(getSupportFragmentManager(), mPartsShopFragments, mListTitle));
        tabLayout.setupWithViewPager(partShopPager);
    }

    private void upTabLayout(List<ClassifyBean> responseData) {
        mListTitle = new ArrayList<>();
        if (responseData != null && responseData.size() > 0) {
            int size = responseData.size();
            for (int i = 0; i < size; i++) {
                ClassifyBean bean = responseData.get(i);
//                tabLayout.addTab(tabLayout.newTab().setText());
                mListTitle.add(bean.getTitle());
                PartsShopFragment fragment = new PartsShopFragment();
                Bundle args = new Bundle();
                args.putInt(Constants.FRAGMENT_TYPE_KEY, bean.getId());
                args.putInt(Constants.CHANNEL_KEY, bean.getChannel_id());
                fragment.setArguments(args);
                mPartsShopFragments.add(fragment);
            }
        }
    }


    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }
}
