package com.yunsen.enjoy.activity.other;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpCityEvent;
import com.yunsen.enjoy.utils.SharedPreference;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/5.
 */

public class SelectCityNewActivity extends BaseFragmentActivity implements AMapLocationListener {
    private AMapLocationClientOption mLocationOption;
    private AMapLocationClient mlocationClient;
    private LocationManager mLm;
    private LocatedCity mCurrentCity = null;

    @Override
    public int getLayout() {
        return -1;
    }

    @Override
    protected void initView() {

        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));
        CityPicker.getInstance()
                .setFragmentManager(getSupportFragmentManager())    //此方法必须调用
//                .enableAnimation(true)    //启用动画效果
//                .setAnimationStyle(anim)	//自定义动画
                .setLocatedCity(mCurrentCity)  //APP自身已定位的城市，默认为null（定位失败）
                .setHotCities(hotCities)    //指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        if (position != -1) {
                            String name = data.getName() + "市";
                            SharedPreference.getInstance().putString(SpConstants.CITY_KEY, name);
                            EventBus.getDefault().postSticky(new UpCityEvent(EventConstants.UP_CITY, name));
                        }
                        finish();
                    }

                    @Override
                    public void onLocate() {
                        //开始定位，这里模拟一下定位
                        requestPermission(new String[]{Permission.ACCESS_COARSE_LOCATION,
                                Permission.ACCESS_FINE_LOCATION}, 1);
                    }
                })
                .show();
        requestPermission(new String[]{Permission.ACCESS_COARSE_LOCATION,
                Permission.ACCESS_FINE_LOCATION}, 1);
    }

    private static final String TAG = "SelectCityNewActivity";

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        //得到系统的位置服务，判断GPS是否激活
        mLm = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean ok = mLm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {
            if (mlocationClient != null) {
                mlocationClient.startLocation();
            } else {
                initAddress();
            }
            initAddress();
        } else {
            Toast.makeText(this, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    private void initAddress() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        mLocationOption.setOnceLocation(true);
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
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            String city = amapLocation.getCity();
            if (city != null) {
                city = city.substring(0, city.length() - 1);
            }
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                //定位完成之后更新数据
                mCurrentCity = new LocatedCity(city, amapLocation.getProvince(), amapLocation.getCityCode());
                CityPicker.getInstance().locateComplete(mCurrentCity, LocateState.SUCCESS);
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                CityPicker.getInstance().locateComplete(new LocatedCity(city,
                        amapLocation.getProvince(), amapLocation.getCityCode()), LocateState.FAILURE);
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
