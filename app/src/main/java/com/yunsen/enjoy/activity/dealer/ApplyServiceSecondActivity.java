package com.yunsen.enjoy.activity.dealer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gghl.view.wheelcity.AddressData;
import com.gghl.view.wheelcity.OnWheelChangedListener;
import com.gghl.view.wheelcity.WheelView;
import com.gghl.view.wheelcity.adapters.ArrayWheelAdapter;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.adapter.CountryAdapter;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.ServiceProject;
import com.yunsen.enjoy.model.request.ApplyFacilitatorModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.MyAlertDialog;
import com.yunsen.enjoy.widget.NumberPickerDialog;
import com.yunsen.enjoy.widget.interfaces.onLeftOnclickListener;
import com.yunsen.enjoy.widget.interfaces.onRightOnclickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/9.
 */

public class ApplyServiceSecondActivity extends BaseFragmentActivity {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.facilitator_name_edt)
    EditText facilitatorNameEdt;
    @Bind(R.id.facilitator_tag_tv)
    TextView facilitatorTagTv;
    @Bind(R.id.facilitator_project_edt)
    EditText facilitatorProjectEdt;
    @Bind(R.id.facilitator_phone_edt)
    EditText facilitatorPhoneEdt;
    @Bind(R.id.facilitator_address_edt)
    TextView facilitatorAddressEdt;
    @Bind(R.id.facilitator_gps_edt)
    EditText facilitatorGpsEdt;
    @Bind(R.id.facilitator_start_tv)
    TextView facilitatorStartTv;
    @Bind(R.id.facilitator_end_tv)
    TextView facilitatorEndTv;
    @Bind(R.id.next_btn)
    Button nextBtn;
    private ApplyFacilitatorModel mRequstData;
    private String mAddressTxt;
    private String mProvinces;
    private String mCitys;
    public String mCountry;
    private String[] mServiceProjectDatas;

    @Override
    public int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_apply_service_second;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("申请服务商1/2");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        String userName = sp.getString(SpConstants.USER_NAME, "");
        String userId = sp.getString(SpConstants.USER_ID, "");
        mRequstData = new ApplyFacilitatorModel();
        mRequstData.setUser_name(userName);
        mRequstData.setUser_id(userId);
    }

    @Override
    public void requestData() {
        HttpProxy.getServiceProjectList(new HttpCallBack<List<ServiceProject>>() {
            @Override
            public void onSuccess(List<ServiceProject> responseData) {
                Log.e(TAG, "onSuccess: " + responseData);
                mServiceProjectDatas = new String[responseData.size()];
                for (int i = 0; i < responseData.size(); i++) {
                    mServiceProjectDatas[i] = responseData.get(i).getTitle();
                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.action_back, R.id.facilitator_tag_tv, R.id.facilitator_start_tv, R.id.facilitator_end_tv, R.id.next_btn, R.id.facilitator_address_edt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.facilitator_tag_tv:
                if (mServiceProjectDatas != null) {
                    showPickerDialog(mServiceProjectDatas);
                } else {
                    requestData();
                }
                break;
            case R.id.facilitator_address_edt:
                showAddressPickerDialog();
                break;
            case R.id.facilitator_start_tv:
                break;
            case R.id.facilitator_end_tv:
                break;
            case R.id.next_btn:
                intSMap();
                break;
        }
    }

    private void showPickerDialog(String[] datas) {
        if (datas == null) {
            return;
        }
        int length = datas.length;
        final NumberPickerDialog picker = new NumberPickerDialog(this, datas);
        picker.setLeftOnclickListener("取消", new onLeftOnclickListener() {
            @Override
            public void onLeftClick() {
                if (picker != null && picker.isShowing()) {
                    picker.dismiss();
                }
            }
        });
        picker.setRightOnclickListener("确定", new onRightOnclickListener() {
            @Override
            public void onRightClick(int[] index) {
                if (picker != null && picker.isShowing()) {
                    facilitatorTagTv.setText(mServiceProjectDatas[index[0]]);
                    requestData();
                    picker.dismiss();
                }
            }
        });
        picker.show();
    }


    private void showAddressPickerDialog() {
        View view = dialogm();
        final MyAlertDialog dialog1 = new MyAlertDialog(ApplyServiceSecondActivity.this).builder()
                .setTitle("选择地区").setView(view)
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
        dialog1.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facilitatorAddressEdt.setText(mProvinces + "," + mCitys + "," + mCountry);
            }
        });
        dialog1.show();
    }

    private void intSMap() {
        String name = facilitatorNameEdt.getText().toString();
        //        Integer tag = (Integer) facilitatorTagTv.getTag();
        Integer tag = 0;
        //        Integer projectTag = (Integer) facilitatorProjectEdt.getTag();
        Integer projectTag = (Integer) facilitatorProjectEdt.getTag();
        String phoneNum = facilitatorPhoneEdt.getText().toString();
        String address = facilitatorAddressEdt.getText().toString();
        String gpsAddress = facilitatorGpsEdt.getText().toString();
        String startTime = facilitatorStartTv.getText().toString();
        String endTime = facilitatorEndTv.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.makeTextShort("服务商名称不能为空");
        } else if (TextUtils.isEmpty(phoneNum)) {
            ToastUtils.makeTextShort("联系电话不能为空");
        } else if (TextUtils.isEmpty(address)) {
            ToastUtils.makeTextShort("服务商城市不能为空");
        } else if (TextUtils.isEmpty(gpsAddress)) {
            ToastUtils.makeTextShort("详细地址不能为空");
        } else if (TextUtils.isEmpty(startTime)) {

        } else if (TextUtils.isEmpty(endTime)) {

        } else {
            mRequstData.setName(name);
            mRequstData.setSeo_title(String.valueOf(tag));
            mRequstData.setService_ids(String.valueOf(projectTag));
            mRequstData.setMobile(phoneNum);
            mRequstData.setProvince(address);
            mRequstData.setAddress(gpsAddress);
            mRequstData.setService_time(startTime + "-" + endTime);
        }
        UIHelper.showApplyServiceThreeActivity(this, mRequstData);

    }

    private static final String TAG = "ApplyServiceSecondActiv";

    /**
     * 选择城市
     *
     * @return
     */
    private View dialogm() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.wheelcity_cities_layout, null);
        final WheelView country = (WheelView) contentView.findViewById(R.id.wheelcity_country);
        country.setVisibleItems(3);
        country.setViewAdapter(new CountryAdapter(this));

        final String cities[][] = AddressData.CITIES;
        final String ccities[][][] = AddressData.COUNTIES;
        final WheelView city = (WheelView) contentView.findViewById(R.id.wheelcity_city);
        city.setVisibleItems(0);     // 地区选择
        final WheelView ccity = (WheelView) contentView.findViewById(R.id.wheelcity_ccity);
        ccity.setVisibleItems(0);// 不限城市

        country.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateCities(city, cities, newValue);
                mAddressTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                mProvinces = AddressData.PROVINCES[country.getCurrentItem()];
            }
        });

        city.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updatecCities(ccity, ccities, country.getCurrentItem(), newValue);
                mAddressTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                mCitys = AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()];
            }
        });

        ccity.addChangingListener(new OnWheelChangedListener() {

            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mAddressTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                mCountry = AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
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
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
                cities[index]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

    /**
     * Updates the ccity wheel
     */
    private void updatecCities(WheelView city, String ccities[][][], int index,
                               int index2) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
                ccities[index][index2]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
