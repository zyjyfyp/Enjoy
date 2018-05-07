package com.yunsen.enjoy.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.utils.SharedPreference;
import com.yunsen.enjoy.utils.StringUtils;
import com.yunsen.enjoy.widget.city.CityList;
import com.yunsen.enjoy.widget.city.CityModel;
import com.yunsen.enjoy.widget.city.MyLetterListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectCityActivity extends BaseFragmentActivity implements AdapterView.OnItemClickListener, AMapLocationListener {
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
    private LocationManager mLm;


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
        requestPermission(new String[]{Permission.ACCESS_COARSE_LOCATION,
                Permission.ACCESS_FINE_LOCATION,
                Permission.READ_PHONE_STATE}, 1);
    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        //得到系统的位置服务，判断GPS是否激活
        mLm=(LocationManager) getSystemService(LOCATION_SERVICE);
        boolean ok=mLm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(ok) {
            initAddress();
        }else {
            Toast.makeText(this, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent();
            intent.setAction(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void initAddress() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();

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

    private static final String TAG = "SelectCityActivity";

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                String city = amapLocation.getCity();
                Log.e(TAG, "onLocationChanged: " +city);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
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
