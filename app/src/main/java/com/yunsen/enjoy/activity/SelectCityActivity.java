package com.yunsen.enjoy.activity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.utils.SharedPreference;
import com.yunsen.enjoy.utils.StringUtils;
import com.yunsen.enjoy.widget.city.CityList;
import com.yunsen.enjoy.widget.city.CityModel;
import com.yunsen.enjoy.widget.city.MyLetterListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectCityActivity extends BaseFragmentActivity implements AdapterView.OnItemClickListener {
    private static final int TYPE_TITLE = 0;
    private static final int TYPE_CITY = 1;
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.current_city_tv)
    TextView currentCityTv;

    private BaseAdapter adapter;
    private ListView mCityLit;
    private MyLetterListView letterListView;
    private HashMap<String, Integer> alphaIndexer;
    private Handler handler;
    private OverlayThread overlayThread;
    private ArrayList<CityModel> mCityNames;
    WindowManager windowManager;
    private TextView overlay;
    private String[] citys;


    @Override
    public int getLayout() {
        return R.layout.public_cityhot;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mCityLit = (ListView) findViewById(R.id.public_allcity_list);
        letterListView = (MyLetterListView) findViewById(R.id.cityLetterListView);
        actionBarTitle.setText("选择城市");
        String currentCity = SharedPreference.getInstance().getString(SpConstants.CITY_KEY, "深圳");
        currentCityTv.setText("当前的选择的城市：" + currentCity);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String json = StringUtils.getJson(this, "china-city-data.json");
        CityList cityList = JSON.parseObject(json, CityList.class);
        mCityNames = StringUtils.getCityModel(cityList);
        setAdapter(mCityNames);
        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        handler = new Handler();
        overlayThread = new OverlayThread();
        initOverlay();
    }

    @Override
    protected void initListener() {
        mCityLit.setOnItemClickListener(this);
    }

    /**
     * ListView
     *
     * @param list
     */
    private void setAdapter(List<CityModel> list) {
        if (list != null) {
            adapter = new ListAdapter(this, list);
            mCityLit.setAdapter(adapter);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                            long arg3) {
        CityModel cityModel = (CityModel) mCityLit.getAdapter()
                .getItem(pos);
        if (cityModel != null && TextUtils.isEmpty(cityModel.getFirstLetter())) {
            SharedPreference.getInstance().putString(SpConstants.CITY_KEY, cityModel.getName());
            finish();
        }
    }


    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }

    /**
     * ListViewAdapter
     *
     * @author gugalor
     */
    private class ListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<CityModel> list;

        public ListAdapter(Context context, List<CityModel> list) {
            this.inflater = LayoutInflater.from(context);
            this.list = list;
            alphaIndexer = new HashMap<String, Integer>();
            citys = new String[list.size()];
            int j = 0;
            for (int i = 0; i < list.size(); i++) {
                String currentStr = list.get(i).getName();
                if (TextUtils.isEmpty(currentStr)) {
                    alphaIndexer.put(list.get(i).getFirstLetter(), i);
                    j++;
                } else {
                    citys[i - j] = currentStr;
                }
            }

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            String letter = list.get(position).getFirstLetter();
            if (TextUtils.isEmpty(letter)) {
                return TYPE_CITY;
            } else {
                return TYPE_TITLE;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            switch (getItemViewType(position)) {
                case TYPE_TITLE:
                    if (convertView == null) {
                        convertView = inflater.inflate(R.layout.public_cityhot_title_item, null);
                        holder = new ViewHolder();
                        holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
                        convertView.setTag(holder);
                    } else {
                        holder = (ViewHolder) convertView.getTag();
                    }
                    holder.alpha.setText(list.get(position).getFirstLetter());
                    break;
                case TYPE_CITY:
                    if (convertView == null) {
                        convertView = inflater.inflate(R.layout.public_cityhot_item, null);
                        holder = new ViewHolder();
                        holder.name = (TextView) convertView.findViewById(R.id.public_cityhot_item_textview);
                        convertView.setTag(holder);
                    } else {
                        holder = (ViewHolder) convertView.getTag();
                    }
                    holder.name.setText(list.get(position).getName());
                    break;
            }

            return convertView;
        }

        private class ViewHolder {
            TextView alpha;
            TextView name;
        }

    }


    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }


    private class LetterListViewListener implements
            MyLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                mCityLit.setSelection(position);
                overlay.setText(s);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // Уoverlay
                handler.postDelayed(overlayThread, 1500);
            }
        }

    }

    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (windowManager != null) {
            windowManager.removeView(overlay);
        }
        ButterKnife.unbind(this);
    }


}
