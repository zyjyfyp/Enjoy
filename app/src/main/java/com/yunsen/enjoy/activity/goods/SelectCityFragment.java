package com.yunsen.enjoy.activity.goods;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.goods.adapter.CityListAdapter;
import com.yunsen.enjoy.activity.goods.adapter.ListViewPagerAdapter;
import com.yunsen.enjoy.activity.goods.adapter.ProvinceListAdapter;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.model.CitysBean;
import com.yunsen.enjoy.model.ProvinceCityModel;
import com.yunsen.enjoy.model.ProvincesBean;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpCityEvent;
import com.yunsen.enjoy.utils.StringUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.NoScrollViewPager;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/17.
 */

public class SelectCityFragment extends BaseFragment {
    @Bind(R.id.select_city_tab)
    TabLayout tabLayout;
    @Bind(R.id.no_scroll_pager)
    NoScrollViewPager noScrollPager;
    private RecyclerView mProvinceRecycler;
    private RecyclerView mCityRecycler;
    private ProvinceListAdapter mProvinceAdapter;
    private CityListAdapter mCityAdapter;
    private ArrayList<CitysBean> mCitysData;
    private List<ProvincesBean> mProvincesDatas;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_city;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        String json = StringUtils.getJson(getActivity(), "province_city.json");
        ProvinceCityModel provinceCityModel = JSON.parseObject(json, ProvinceCityModel.class);
        ArrayList<RecyclerView> mViews = new ArrayList<>();
        /**
         * 省
         */
        mProvinceRecycler = new RecyclerView(getActivity());
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mProvinceRecycler.setLayoutParams(layoutParams);
        mProvinceRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProvincesDatas = provinceCityModel.getProvinces();
        mProvinceAdapter = new ProvinceListAdapter(getActivity(), R.layout.text_layout, mProvincesDatas);
        mProvinceRecycler.setAdapter(mProvinceAdapter);
        /**
         * 市
         */
        mCityRecycler = new RecyclerView(getActivity());
        RecyclerView.LayoutParams layoutParams2 = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mCityRecycler.setLayoutParams(layoutParams2);
        mCityRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCitysData = new ArrayList<>();
        mCityAdapter = new CityListAdapter(getActivity(), R.layout.text_layout, mCitysData);
        mCityRecycler.setAdapter(mCityAdapter);

        mViews.add(mProvinceRecycler);
        mViews.add(mCityRecycler);

        noScrollPager.setAdapter(new ListViewPagerAdapter(mViews));
        tabLayout.setupWithViewPager(noScrollPager);
        tabLayout.getTabAt(0).setText("请选择");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void initListener() {
        mProvinceAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                if (mProvincesDatas.size() > position) {
                    ProvincesBean bean = mProvincesDatas.get(position);
                    mCityAdapter.upData(bean.getCitys());
                    noScrollPager.setCurrentItem(1);
                    tabLayout.getTabAt(0).setText(bean.getProvinceName());
                    tabLayout.getTabAt(1).setText("请选择");
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        mCityAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                if ((adapter instanceof CityListAdapter)) {
                    List<CitysBean> datas = ((CityListAdapter) adapter).getDatas();
                    if (datas.size() > position) {
                        CitysBean citysBean = datas.get(position);
                        tabLayout.getTabAt(1).setText(citysBean.getCitysName());
                        EventBus.getDefault().post(new UpCityEvent(EventConstants.CHANGE_CITY_EVENT, citysBean.getCitysName()));
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
