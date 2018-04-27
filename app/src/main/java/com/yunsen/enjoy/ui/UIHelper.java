package com.yunsen.enjoy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.yunsen.enjoy.activity.WebActivity;
import com.yunsen.enjoy.activity.mine.ApplyServiceActivity;
import com.yunsen.enjoy.activity.mine.AppointmentActivity;
import com.yunsen.enjoy.activity.mine.CollectionActivity;
import com.yunsen.enjoy.activity.mine.MyAssetsActivity;
import com.yunsen.enjoy.activity.mine.MyQianBaoActivity;
import com.yunsen.enjoy.activity.mine.PersonCenterActivity;
import com.yunsen.enjoy.activity.mine.TeamActivity;
import com.yunsen.enjoy.activity.mine.Webview1;
import com.yunsen.enjoy.activity.order.MyOrderActivity;
import com.yunsen.enjoy.activity.user.TishiWxBangDingActivity;
import com.yunsen.enjoy.activity.user.UserLoginActivity;
import com.yunsen.enjoy.common.Constants;

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
    public static void showCarDetailsActivity(Context act) {
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

    /**
     * 团队信息
     *
     * @param ctx
     */
    public static void showTeamActivity(Context ctx) {
        Intent intent = new Intent(ctx, TeamActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 我的资产
     * 1 2 3 4
     *
     * @param ctx
     */
    public static void showAssetsActivity(Context ctx, String type) {
        Intent intent = new Intent(ctx, MyAssetsActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 充值
     *
     * @param ctx
     */
    public static void showRechargeActivity(Context ctx) {
        Intent intent = new Intent(ctx, MyQianBaoActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 去帮助页面
     *
     * @param ctx
     */
    public static void showHelpActivity(Context ctx) {
        Intent intent4 = new Intent(ctx, Webview1.class);
        intent4.putExtra("jysbz_id", "10334");
        ctx.startActivity(intent4);
    }

    /**
     * 预约管理
     *
     * @param ctx
     */
    public static void showAppointmentActivity(Context ctx) {
        Intent intent = new Intent(ctx, AppointmentActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 申请服务商
     *
     * @param ctx
     */
    public static void showApplyServiceActivity(Context ctx) {
        Intent intent = new Intent(ctx, ApplyServiceActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 去订单页面
     *
     * @param ctx
     * @param type
     */
    public static void showOrderActivity(Context ctx, String type) {
        Intent intent = new Intent(ctx, MyOrderActivity.class);
        intent.putExtra("status", type);
        ctx.startActivity(intent);
    }

    /**
     * 绑定手机
     *
     * @param ctx
     */
    public static void showBundPhoneActivity(Context ctx) {
        Intent intent = new Intent(ctx, TishiWxBangDingActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 微信登录
     *
     * @param ctx
     */
    public static void showUserLoginActivity(Context ctx) {
        Intent intent = new Intent(ctx, UserLoginActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 收藏页面
     *
     * @param ctx
     */
    public static void showCollectionActivity(Context ctx) {
        Intent intent = new Intent(ctx, CollectionActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 显示拨号界面
     *
     * @param ctx
     * @param number
     */
    public static void showPhoneNumberActivity(Context ctx, String number) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));//跳转到拨号界面，同时传递电话号码
        ctx.startActivity(dialIntent);
    }

    /**
     * 跳转网页
     *
     * @param ctx
     * @param url
     */
    public static void showWebActivity(Context ctx, String url) {
        Intent intent = new Intent(ctx, WebActivity.class);
        intent.putExtra(Constants.WEB_URL_KEY, url);
        ctx.startActivity(intent);
    }
}
