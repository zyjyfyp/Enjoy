package com.yunsen.enjoy.adapter;

/**
 * Created by Administrator on 2018/5/9.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.gghl.view.wheelcity.AddressData;
import com.gghl.view.wheelcity.adapters.AbstractWheelTextAdapter;
import com.yunsen.enjoy.R;

/**
 * Adapter for countries
 */
public class CountryAdapter extends AbstractWheelTextAdapter {
    // Countries names
    private String countries[] = AddressData.PROVINCES;

    /**
     * Constructor
     */
    public CountryAdapter(Context context) {
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
