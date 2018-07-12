package com.yunsen.enjoy.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.MainActivity;
import com.yunsen.enjoy.activity.user.PhoneLoginActivity;
import com.yunsen.enjoy.activity.user.UserLoginActivity;
import com.yunsen.enjoy.activity.user.UserLoginWayActivity;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.interfaces.OnLeftOnclickListener;
import com.yunsen.enjoy.ui.interfaces.OnRightOnclickListener;
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
                if (!(fAct instanceof MainActivity)) {
                    fAct.finish();
                }
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

    /**
     * 目前拥有购物车删除产品
     *
     * @param ctx
     * @param message
     * @param leftText
     * @param rightText
     * @param leftListener
     * @param rightListener
     * @return
     */
    public static AlertDialog createYesAndNoDialog(Context ctx, String message, final String leftText, String rightText,
                                                   final OnLeftOnclickListener leftListener, final OnRightOnclickListener rightListener) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.delete_message_layout, null);
        TextView messageTV = (TextView) view.findViewById(R.id.delete_tv);
        if (!TextUtils.isEmpty(message)) {
            messageTV.setText(message);
        }
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));
        AlertDialog dialog = new AlertDialog.Builder(ctx).setView(view)
                .setPositiveButton(rightText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (rightListener != null) {
                            rightListener.onRightClick();
                        }
                    }
                })
                .setNegativeButton(leftText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (leftListener != null) {
                            leftListener.onLeftClick();
                        }

                    }
                }).create();
        return dialog;
    }

    public static AlertDialog createYesAndNoTitleDialog(Context ctx, String title, String message, final String leftText, String rightText,
                                                        final OnLeftOnclickListener leftListener, final OnRightOnclickListener rightListener) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.title_message_layout, null);
        TextView titleTv = (TextView) view.findViewById(R.id.title);
        TextView contentTv = (TextView) view.findViewById(R.id.content);
        titleTv.setText(title);
        contentTv.setText(message);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        AlertDialog dialog = new AlertDialog.Builder(ctx).setView(view)
                .setPositiveButton(rightText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (rightListener != null) {
                            rightListener.onRightClick();
                        }
                    }
                })
                .setNegativeButton(leftText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (leftListener != null) {
                            leftListener.onLeftClick();
                        }

                    }
                }).create();

        return dialog;
    }


    /**
     * @param ctx
     * @param message
     * @param leftText
     * @param leftListener
     * @return
     */
    public static AlertDialog createOKDialog(Context ctx, String message, final String leftText,
                                             final OnLeftOnclickListener leftListener) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ok_message_layout, null);
        TextView messageTV = (TextView) view.findViewById(R.id.message_tv);
        TextView okBtn = (TextView) view.findViewById(R.id.ok_btn);
        if (!TextUtils.isEmpty(message)) {
            messageTV.setText(message);
        }
        if (!TextUtils.isEmpty(leftText)) {
            okBtn.setText(leftText);
        }
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftListener != null) {
                    leftListener.onLeftClick();
                }
            }
        });

        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
        AlertDialog dialog = new AlertDialog.Builder(ctx).setView(view).create();
        return dialog;
    }
}
