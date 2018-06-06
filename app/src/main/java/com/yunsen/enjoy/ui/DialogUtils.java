package com.yunsen.enjoy.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.user.PhoneLoginActivity;
import com.yunsen.enjoy.activity.user.UserLoginActivity;
import com.yunsen.enjoy.activity.user.UserLoginWayActivity;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.utils.AccountUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/4/23.
 */

public class DialogUtils {

    private static AlertDialog dialog;

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

}
