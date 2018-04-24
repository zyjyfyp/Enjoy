package com.yunsen.enjoy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.yunsen.enjoy.activity.AdvertActivity;
import com.yunsen.enjoy.activity.CarDetailsActivity;
import com.yunsen.enjoy.activity.HouseDetailActivity;
import com.yunsen.enjoy.activity.LoginActivity;
import com.yunsen.enjoy.activity.MainActivity;
import com.yunsen.enjoy.activity.MoveActivity;
import com.yunsen.enjoy.activity.SearchActivity;
import com.yunsen.enjoy.activity.SelectCityActivity;
import com.yunsen.enjoy.activity.mine.PersonCenterActivity;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 */
public class UIHelper {

    public final static String TAG = "UIHelper";

    public final static int RESULT_OK = 0x00;
    public final static int REQUEST_CODE = 0x01;

    public static void ToastMessage(Context cont, String msg) {
        if (cont == null || msg == null) {
            return;
        }
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context cont, int msg) {
        if (cont == null || msg <= 0) {
            return;
        }
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context cont, String msg, int time) {
        if (cont == null || msg == null) {
            return;
        }
        Toast.makeText(cont, msg, time).show();
    }

    public static void showHome(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        context.finish();
    }

    public static void showLogin(Activity context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void showHouseDetailActivity(Activity context) {
        Intent intent = new Intent(context, HouseDetailActivity.class);
        context.startActivity(intent);
    }

    /**
     * 选择城市
     *
     * @param context
     */
    public static void showSelectCityActivity(Activity context) {
        Intent intent = new Intent(context, SelectCityActivity.class);
        context.startActivity(intent);
    }

    /**
     * 搜索页面
     *
     * @param act
     */
    public static void showSearchActivity(Activity act) {
        Intent intent = new Intent(act, SearchActivity.class);
        act.startActivity(intent);
    }

    /**
     * 广告页面
     *
     * @param act
     */
    public static void showAdvertActivity(Context act) {
        Intent intent = new Intent(act, AdvertActivity.class);
        act.startActivity(intent);
    }

    /**
     * 活动
     *
     * @param act
     */
    public static void showMoveActivity(Context act) {
        Intent intent = new Intent(act, MoveActivity.class);
        act.startActivity(intent);
    }

    /**
     * 汽车详情
     *
     * @param act
     */
    public static void shoCarDetailsActivity(Context act) {
        Intent intent = new Intent(act, CarDetailsActivity.class);
        act.startActivity(intent);
    }

    /**
     * 账户管理
     *
     * @param ctx
     */
    public static void showPersonCenterActivity(Context ctx) {
        Intent intent = new Intent(ctx, PersonCenterActivity.class);
        ctx.startActivity(intent);
    }
}
