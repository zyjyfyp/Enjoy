package com.yunsen.enjoy.activity.buy;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.widget.DatePickerViewDialog;
import com.yunsen.enjoy.widget.interfaces.onLeftOnclickListener;
import com.yunsen.enjoy.widget.interfaces.onRightOnclickListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    TextView watchAddressTv;
    @Bind(R.id.watch_time_tv)
    TextView watchTimeTv;
    private DatePickerViewDialog pickerView;


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

    }

    @Override
    protected void initListener() {

    }


    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }


    @OnClick({R.id.watch_address_tv, R.id.watch_time_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.watch_address_tv:
                UIHelper.showMeetAddressActivity(WatchCarActivity.this);
                break;
            case R.id.watch_time_tv:
                showDateDialog();
                break;
        }
    }

    private void showDateDialog() {
        if (pickerView == null) {
            pickerView = new DatePickerViewDialog(this);
            pickerView.setLeftOnclickListener("取消", new onLeftOnclickListener() {
                @Override
                public void onLeftClick() {
                    if (pickerView.isShowing()) {
                        pickerView.cancel();
                    }
                }
            });
            pickerView.setRightOnclickListener("确定", new onRightOnclickListener() {
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
