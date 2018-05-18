package com.yunsen.enjoy.activity.buy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.model.WatchCarBean;
import com.yunsen.enjoy.model.request.WatchCarModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.SpUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.DatePickerViewDialog;
import com.yunsen.enjoy.widget.interfaces.OnLeftOnclickListener;
import com.yunsen.enjoy.widget.interfaces.OnRightOnclickListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/27.
 * 预约看车
 */

public class WatchCarActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.goods_left_img)
    ImageView goodsLeftImg;
    @Bind(R.id.goods_title_2)
    TextView goodsTitle2;
    @Bind(R.id.goods_sub_title_2)
    TextView goodsSubTitle2;
    @Bind(R.id.goods_money)
    TextView goodsMoney;
    @Bind(R.id.goods_first_money)
    TextView goodsFirstMoney;
    @Bind(R.id.goods_address)
    TextView goodsAddress;
    @Bind(R.id.watch_address_tv)
    EditText watchAddressTv;
    @Bind(R.id.watch_time_tv)
    TextView watchTimeTv;
    @Bind(R.id.submit_tv)
    TextView submit_tv;

    private DatePickerViewDialog pickerView;
    private String mCarId;
    private CarDetails mCarDetails;
    private UserInfo mUserInfo;
    private WatchCarModel mWatchModel;


    @Override
    public int getLayout() {
        return R.layout.activity_watch_car;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("预约看车");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mCarId = intent.getStringExtra(Constants.WATCH_CAR_ID);
        }
        mUserInfo = SpUtils.getUserInfo();
        mWatchModel = new WatchCarModel();
        mWatchModel.setUser_id("" + mUserInfo.getId());
        mWatchModel.setUser_name(mUserInfo.getUser_name());
        mWatchModel.setAccept_name(mUserInfo.getUser_name());
        mWatchModel.setProvince("广州省"); //todo 地址
        mWatchModel.setCity("深圳市");
        mWatchModel.setArea("南山区");
        mWatchModel.setAddress("软件园");
        mWatchModel.setTelphone(mUserInfo.getMobile());
        mWatchModel.setEmail("1040135865@qq.com");
        mWatchModel.setMessage("备注");
        mWatchModel.setPost_code("415700");
        mWatchModel.setInvoice_title("发票抬头");
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void requestData() {
        HttpProxy.getCarDetailsData(new HttpCallBack<CarDetails>() {

            @Override
            public void onSuccess(CarDetails responseData) {
                mCarDetails = responseData;
                initTopItem(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        }, mCarId);
    }

    /**
     * 更新顶部的数据
     *
     * @param data
     */
    private void initTopItem(CarDetails data) {
        String imgUrl = data.getImg_url();
        Glide.with(this)
                .load(imgUrl)
                .into(goodsLeftImg);
        String title = data.getTitle();
        goodsTitle2.setText(title);
        goodsSubTitle2.setText(data.getSubtitle());
        CarDetails.DefaultSpecItemBean specItemBean = data.getDefault_spec_item();
        goodsFirstMoney.setText("" + specItemBean.getFirst_payment());
        goodsMoney.setText("" + specItemBean.getSell_price());
        goodsAddress.setText(data.getAddress());
        mWatchModel.setArticle_id("" + data.getId());
        int goods_id = data.getDefault_spec_item().getGoods_id();
        mWatchModel.setGoods_id("" + goods_id);
    }

    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }


    @OnClick({R.id.watch_address_tv, R.id.watch_time_tv, R.id.submit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.watch_address_tv:
//                UIHelper.showMeetAddressActivity(WatchCarActivity.this);
                break;
            case R.id.watch_time_tv:
                showDateDialog();
                break;
            case R.id.submit_tv:
                submitWatchCar();
                break;
        }
    }

    private void submitWatchCar() {
        String watchCarAddress = watchAddressTv.getText().toString();
        if (TextUtils.isEmpty(watchCarAddress)) {
            ToastUtils.makeTextShort("请输入看车地点");
        } else {
            HttpProxy.requestMeetingCar(mWatchModel, new HttpCallBack<WatchCarBean>() {

                @Override
                public void onSuccess(WatchCarBean responseData) {
                    ToastUtils.makeTextShort("预约成功");
                    UIHelper.showAppointmentActivity(WatchCarActivity.this);
                    finish();
                }

                @Override
                public void onError(Request request, Exception e) {

                }
            });
        }

    }

    private void showDateDialog() {
        if (pickerView == null) {
            pickerView = new DatePickerViewDialog(this);
            pickerView.setLeftOnclickListener("取消", new OnLeftOnclickListener() {
                @Override
                public void onLeftClick() {
                    if (pickerView.isShowing()) {
                        pickerView.cancel();
                    }
                }
            });
            pickerView.setRightOnclickListener("确定", new OnRightOnclickListener() {
                @Override
                public void onRightClick(int... index) {
                    watchTimeTv.setText(pickerView.getDate(index[0], index[1], index[2]));
                    if (pickerView.isShowing()) {
                        pickerView.cancel();
                    }
                }
            });
        }
        if (!pickerView.isShowing()) {
            pickerView.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
