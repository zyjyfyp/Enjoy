package com.yunsen.enjoy.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.bumptech.glide.Glide;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.Utils;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.amap.api.maps2d.AMap.MAP_TYPE_NORMAL;

/**
 * Created by Administrator on 2018/8/23/023.
 */

public class MapActivity extends BaseFragmentActivity implements AMapLocationListener, AMap.OnMarkerClickListener {
    private static final String TAG = "MapActivity";
    @Bind(R.id.map_view)
    MapView mapView;
    @Bind(R.id.goods_list_img)
    ImageView goodsListImg;
    @Bind(R.id.goods_list_title_tv)
    TextView goodsListTitleTv;
    @Bind(R.id.goods_list_address_tv)
    TextView goodsListAddressTv;
    @Bind(R.id.goods_list_coin_tv)
    TextView goodsListCoinTv;
    @Bind(R.id.goods_list_vip_tv)
    TextView goodsListVipTv;
    @Bind(R.id.goods_list_type_tv)
    TextView goodsListTypeTv;
    @Bind(R.id.goods_list_distance_tv)
    TextView goodsListDistanceTv;

    private AlertDialog mSelectMapDialog;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private LocationManager mLm;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private LatLng mLatLng;
    private boolean mLocationUpMarker = true;
    private double mAddressLon;
    private double mAddressLat;
    private String mEndAddress;
    private Marker mMarker;
    private MarkerOptions meEndMarkerOptions;
    private LatLng latLng2;
    private AlertDialog mShopDialog;
    private SProviderModel mData;

    @Override
    public int getLayout() {
        return R.layout.activity_map;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        mAddressLon = extras.getDouble(Constants.ADDRESS_LONG, 0.00);
        mAddressLat = extras.getDouble(Constants.ADDRESS_LAT, 0.00);
        mEndAddress = extras.getString(Constants.ADDRESS_KEY);
        mData = extras.getParcelable(Constants.DATA);


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
//            aMap.getUiSettings().setMyLocationButtonEnabled(true);
            aMap.setMapType(MAP_TYPE_NORMAL);
            aMap.setOnMarkerClickListener(this);
        }
        requestPermission(new String[]{Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION}, 1);

        Glide.with(MapActivity.this)
                .load(mData.getImg_url())
                .into(goodsListImg);
        goodsListTitleTv.setText(mData.getName());
        goodsListAddressTv.setText(mData.getAddress());


    }

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
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

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
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
        //绘制终点:
        latLng2 = new LatLng(mAddressLat, mAddressLon);
        meEndMarkerOptions = new MarkerOptions()
                .position(latLng2)
                .title(mEndAddress)
                .snippet(mEndAddress)
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.end_point)))
                .draggable(false).visible(true);

        aMap.addMarker(meEndMarkerOptions);
//        Animation animation = new RotateAnimation(marker.getRotateAngle(),marker.getRotateAngle()+180,0,0,0);
//        long duration = 1000L;
//        animation.setDuration(duration);
//        animation.setInterpolator(new LinearInterpolator());

//        marker.setAnimation(animation);
//        marker.startAnimation();

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            String city = amapLocation.getCity();
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                //定位完成之后更新数据
                mLatLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
//                if (mLocationUpMarker) {
//                    mLocationUpMarker = false;
                aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
                upMarker(mLatLng);
//                }
                if (mAddressLat != 0 && mAddressLon != 0) {
                    double algorithm = Utils.algorithm(amapLocation.getLongitude(), amapLocation.getLatitude(), mAddressLon, mAddressLat) / 1000;
                    BigDecimal b = new BigDecimal(algorithm);
                    double df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    goodsListDistanceTv.setText(df + "km");
                } else {
//            view.setVisibility(View.GONE);
                    goodsListDistanceTv.setText("0.0 km");
                }

                Log.e(TAG, "onLocationChanged: amapLocation.getLongitude()=" + amapLocation.getLongitude() + "   amapLocation.getLatitude()=" + amapLocation.getLatitude());
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                CityPicker.getInstance().locateComplete(new LocatedCity(city,
                        amapLocation.getProvince(), amapLocation.getCityCode()), LocateState.FAILURE);
            }
        }
    }

    private void upMarker(LatLng latLng) {
//        if (mMarker != null) {
//            mMarker.remove();
//        }

        MarkerOptions mMarkerOption = new MarkerOptions();
        mMarkerOption.position(latLng);
        mMarkerOption.draggable(true);//设置Marker可拖动
        mMarkerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.upload_location)));
        //点标记是否可拖拽
        //mk.draggable(true);
        //点标记的锚点
//        mMarkerOption.anchor(1.5f, 3.5f);
        //点的透明度
        mMarker = aMap.addMarker(mMarkerOption);
        aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng2));

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
        if (mSelectMapDialog != null && mSelectMapDialog.isShowing()) {
            mSelectMapDialog.dismiss();
        }
        mSelectMapDialog = null;
        if (mShopDialog != null && mShopDialog.isShowing()) {
            mShopDialog.dismiss();
        }
        mShopDialog = null;
    }


    /**
     * 选择地图
     */
    private void showSelectMapDialog() {
        if (mSelectMapDialog == null) {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.select_map_dialog, null);
            LinearLayout gadeLayout = (LinearLayout) view.findViewById(R.id.gade_layout);
            gadeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIHelper.openGaoDeMap(MapActivity.this, mAddressLat, mAddressLon, mEndAddress);
                }
            });
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            mSelectMapDialog = new AlertDialog.Builder(this).setView(view)
                    .create();
        }
        if (!mSelectMapDialog.isShowing()) {
            mSelectMapDialog.show();
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }


    @OnClick({R.id.back_img, R.id.look_line_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.look_line_btn:
                showSelectMapDialog();
                break;
        }
    }
}
