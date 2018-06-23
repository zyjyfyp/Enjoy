package com.yunsen.enjoy.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.user.PhoneLoginActivity;
import com.yunsen.enjoy.activity.user.UserLoginActivity;
import com.yunsen.enjoy.activity.user.UserLoginWayActivity;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.model.WatchCarBean;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.model.request.WatchCarModel;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.SpUtils;
import com.yunsen.enjoy.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/23.
 */

public class DialogUtils {

    private static AlertDialog dialog;
    private static AlertDialog sBecomeVipDialog;

    public static Dialog createNumbmerPickerDialog(Activity act) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(act);
        View view = act.getLayoutInflater().inflate(R.layout.num_picker_dialog, null);
        builder.setView(view);
        TextView leftTv = (TextView) view.findViewById(R.id.left_tv);
        TextView rightTv = (TextView) view.findViewById(R.id.right_tv);
        NumberPicker picker = (NumberPicker) view.findViewById(R.id.number_picker);
        leftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        String[] strings = new String[]{"智能排序", "最新上架", "价格最低", "价格最高", "车龄最短", "理财最少"};
        picker.setDisplayedValues(strings);
        picker.setMaxValue(strings.length - 1);
        picker.setMinValue(0);
        picker.setWrapSelectorWheel(true);
        return builder.create();
    }


    /**
     * 提示是否注销登录
     */
    public static void showLoginDialog(Activity act) {
        final Activity fAct = act;
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setMessage("是否注销登录?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 清空SharedPreferences保存数据
                SharedPreferences jdh_spPreferences = fAct.getSharedPreferences("user_juduihuan", Context.MODE_PRIVATE);
                SharedPreferences spPreferences = fAct.getSharedPreferences("longuserset", Context.MODE_PRIVATE);
                SharedPreferences spPreferences_login = fAct.getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
                SharedPreferences spPreferences_tishi = fAct.getSharedPreferences("longuserset_tishi", Context.MODE_PRIVATE);
                spPreferences_login.edit().clear().commit();
                spPreferences.edit().clear().commit();
                jdh_spPreferences.edit().clear().commit();// 积兑换保存福利清除
                spPreferences_tishi.edit().clear().commit();// 第三方授权登录提示绑定手机号信息清空
                AccountUtils.clearData();
                UIHelper.showUserLoginActivity(fAct);
                EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGOUT));
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    public static void closeLoginDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
    }

    public static void showBecomeVipDialog(Context act) {
        final Context fAct = act;
        AlertDialog.Builder builder = new AlertDialog.Builder(fAct);
        builder.setTitle("升级正式会员");
        builder.setMessage("您目前还是准用户，缴纳9.9元成为正式会员，立享多重优惠！");
        builder.setPositiveButton("去升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserInfo userInfo = SpUtils.getUserInfo();
                WatchCarModel model = new WatchCarModel();
                model.setUser_name(userInfo.getUser_name());
                model.setUser_id(String.valueOf(userInfo.getId()));
                model.setArticle_id("2061");
                model.setGoods_id("11272");
                model.setCity(userInfo.getCity());
                model.setProvince(userInfo.getProvince());
                model.setAddress(userInfo.getAddress());
                model.setArea(userInfo.getArea());
                model.setMobile(userInfo.getMobile());
                model.setAccept_name(userInfo.getUser_name());
                HttpProxy.submitVipOrder(model, new HttpCallBack<WatchCarBean>() {
                    @Override
                    public void onSuccess(WatchCarBean responseData) {
                        UIHelper.toPayVipMoney(fAct, responseData.getTrade_no(), responseData.getTotal_amount());
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        ToastUtils.makeTextShort("数据异常");
                    }
                });
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        sBecomeVipDialog = builder.create();
        sBecomeVipDialog.show();
    }

    public static void closeBecomeVipDialog() {
        if (sBecomeVipDialog != null) {
            if (sBecomeVipDialog.isShowing()) {
                sBecomeVipDialog.dismiss();
            }
            sBecomeVipDialog = null;
        }
    }

}
