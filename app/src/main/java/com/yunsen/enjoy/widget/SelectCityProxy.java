package com.yunsen.enjoy.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gghl.view.wheelcity.AddressData;
import com.gghl.view.wheelcity.OnWheelChangedListener;
import com.gghl.view.wheelcity.WheelView;
import com.gghl.view.wheelcity.adapters.AbstractWheelTextAdapter;
import com.gghl.view.wheelcity.adapters.ArrayWheelAdapter;
import com.yunsen.enjoy.R;

/**
 * Created by Administrator on 2018/6/4.
 */

public class SelectCityProxy {


    private Context mContext;
    private String cityTxt;  //全称
    private String cityTxt1; //省
    private String cityTxt2; //市
    private String cityTxt3; //区
    private onSelectCityListener mListener;

    public MyAlertDialog createDialog(Context ctx, String title) {
        this.mContext = ctx;
        View view = dialogm();
        MyAlertDialog dialog = new MyAlertDialog(ctx).builder()
                .setTitle(title).setView(view)
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        dialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onSelectCityCallBack(cityTxt, cityTxt1, cityTxt2, cityTxt3);
                }
            }
        });
        return dialog;
    }

    /**
     * 选择城市
     *
     * @return
     */
    private View dialogm() {
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.wheelcity_cities_layout, null);
        final WheelView country = (WheelView) contentView
                .findViewById(R.id.wheelcity_country);
        country.setVisibleItems(3);
        country.setViewAdapter(new CountryAdapter(mContext));

        final String cities[][] = AddressData.CITIES;
        final String ccities[][][] = AddressData.COUNTIES;
        final WheelView city = (WheelView) contentView
                .findViewById(R.id.wheelcity_city);
        city.setVisibleItems(0);

        // 地区选择
        final WheelView ccity = (WheelView) contentView
                .findViewById(R.id.wheelcity_ccity);
        ccity.setVisibleItems(0);// 不限城市

        country.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateCities(city, cities, newValue);
                cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                cityTxt1 = AddressData.PROVINCES[country.getCurrentItem()];
            }
        });

        city.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updatecCities(ccity, ccities, country.getCurrentItem(),
                        newValue);
                cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                cityTxt2 = AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()];
            }
        });

        ccity.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city.getCurrentItem()][ccity.getCurrentItem()];
                cityTxt3 = AddressData.COUNTIES[country.getCurrentItem()][city.getCurrentItem()][ccity.getCurrentItem()];
            }
        });

        country.setCurrentItem(1);// 设置北京
        city.setCurrentItem(1);
        ccity.setCurrentItem(1);
        return contentView;
    }

    /**
     * Updates the city wheel
     */
    private void updateCities(WheelView city, String cities[][], int index) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(mContext, cities[index]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

    /**
     * Updates the ccity wheel
     */
    private void updatecCities(WheelView city, String ccities[][][], int index,
                               int index2) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(mContext, ccities[index][index2]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

    /**
     * Adapter for countries
     */
    private class CountryAdapter extends AbstractWheelTextAdapter {
        // Countries names
        private String countries[] = AddressData.PROVINCES;

        /**
         * Constructor
         */
        protected CountryAdapter(Context context) {
            super(context, R.layout.wheelcity_country_layout, NO_RESOURCE);
            setItemTextResource(R.id.wheelcity_country_name);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return countries.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return countries[index];
        }
    }

    public void setOnSelectCityListener(onSelectCityListener listener) {
        this.mListener = listener;
    }

    public interface onSelectCityListener {
        public void onSelectCityCallBack(String... address);
    }
}
