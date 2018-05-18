package com.yunsen.enjoy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.yunsen.enjoy.activity.AdvertActivity;
import com.yunsen.enjoy.activity.CarDetailsActivity;
import com.yunsen.enjoy.activity.HouseDetailActivity;
import com.yunsen.enjoy.activity.MainActivity;
import com.yunsen.enjoy.activity.MoveActivity;
import com.yunsen.enjoy.activity.SearchActivity;
import com.yunsen.enjoy.activity.SelectCityActivity;
import com.yunsen.enjoy.activity.ServiceShopInfoActivity;
import com.yunsen.enjoy.activity.WebActivity;
import com.yunsen.enjoy.activity.buy.ApplyBuyFirstActivity;
import com.yunsen.enjoy.activity.buy.ApplyBuyThreeActivity;
import com.yunsen.enjoy.activity.buy.ApplyBuyTwoActivity;
import com.yunsen.enjoy.activity.buy.CarServiceActivity;
import com.yunsen.enjoy.activity.buy.GoodsDescriptionActivity;
import com.yunsen.enjoy.activity.buy.GoodsDescriptionActivityOld;
import com.yunsen.enjoy.activity.buy.MeetAddressActivity;
import com.yunsen.enjoy.activity.buy.WatchCarActivity;
import com.yunsen.enjoy.activity.dealer.ApplyServiceActivity;
import com.yunsen.enjoy.activity.dealer.ApplyServiceSecondActivity;
import com.yunsen.enjoy.activity.dealer.ApplyServiceThreeActivity;
import com.yunsen.enjoy.activity.dealer.MyFacilitatorActivity;
import com.yunsen.enjoy.activity.goods.ChangeGoodsActivity;
import com.yunsen.enjoy.activity.mine.AppointmentActivity;
import com.yunsen.enjoy.activity.mine.CollectionActivity;
import com.yunsen.enjoy.activity.mine.MyAssetsActivity;
import com.yunsen.enjoy.activity.mine.MyQianBaoActivity;
import com.yunsen.enjoy.activity.mine.PersonCenterActivity;
import com.yunsen.enjoy.activity.mine.TeamActivity;
import com.yunsen.enjoy.activity.mine.UserForgotPasswordActivity;
import com.yunsen.enjoy.activity.mine.Webview1;
import com.yunsen.enjoy.activity.order.MyOrderActivity;
import com.yunsen.enjoy.activity.user.LoginActivity;
import com.yunsen.enjoy.activity.user.TishiWxBangDingActivity;
import com.yunsen.enjoy.activity.user.UserRegisterActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.buy.SelectBrandActivity;
import com.yunsen.enjoy.fragment.buy.SeniorFilterActivity;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.request.ApplyCarModel;
import com.yunsen.enjoy.model.request.ApplyFacilitatorModel;
import com.yunsen.enjoy.utils.AccountUtils;

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

    public static void showHomeActivity(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        context.finish();
    }

    /**
     * 判断是否是服务商，并跳转 登录，我是服务商，申请服务商页面
     *
     * @param ctx
     * @param isFacilitator
     */
    public static void goLoginOrIsFacilitator(Activity ctx, boolean isFacilitator) {
        Intent intent = null;
        if (!isFacilitator) {
            intent = new Intent(ctx, ApplyServiceActivity.class);
        } else {
            intent = new Intent(ctx, MyFacilitatorActivity.class);
        }
        if (AccountUtils.hasLogin()) {
            if (AccountUtils.hasBoundPhone()) {
                ctx.startActivity(intent);
            } else {
                UIHelper.showBundPhoneActivity(ctx);
            }
        } else {
            if (AccountUtils.hasBoundPhone()) {
                ctx.startActivity(intent);
            } else {
                UIHelper.showUserLoginActivity(ctx);
            }
        }
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
//        intent.putExtra(Constants.SEARCH_KEY, keyWork);
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
    public static void showCarDetailsActivity(Context act, String linkUrl) {
        if (linkUrl == null) {
            Toast.makeText(act, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = linkUrl.trim();
        String id = null;
        int index = url.lastIndexOf("=") + 1;
        if (index == -1) {
            id = linkUrl;
        } else {
            id = url.substring(index);
        }

        Intent intent = new Intent(act, CarDetailsActivity.class);
        intent.putExtra(Constants.CAR_DETAILS_ID, id);
        act.startActivity(intent);
    }

    public static void showCarDetailsActivity(Context act, int id) {
        Intent intent = new Intent(act, CarDetailsActivity.class);
        intent.putExtra(Constants.CAR_DETAILS_ID, String.valueOf(id));
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
        intent.putExtra(Constants.MY_ASSETS_INDEX_KEY, type);
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
        Intent intent = new Intent(ctx, LoginActivity.class);
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
        String num = "tel:" + number;
        try {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(num));//跳转到拨号界面，同时传递电话号码
            ctx.startActivity(dialIntent);
        } catch (NoClassDefFoundError error) {
            Log.e(TAG, "showPhoneNumberActivity: 不支持打电话");
        }
        ;
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

    /**
     * 跳转消息网页
     *
     * @param ctx
     * @param url
     */
    public static void showNoticeWebActivity(Context ctx, int url) {
        Intent intent = new Intent(ctx, WebActivity.class);
        String value = Integer.toString(url);
        intent.putExtra(Constants.WEB_URL_KEY, URLConstants.getNoticeHtmlUrl(value));
        ctx.startActivity(intent);
    }

    /**
     * 乐享汽车服务
     *
     * @param ctx
     */
    public static void showCarServiceActivity(Context ctx) {
        Intent intent = new Intent(ctx, CarServiceActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 预约看车
     *
     * @param ctx
     */
    public static void showWatchCarActivity(Context ctx, String carId) {
        Intent intent = new Intent(ctx, WatchCarActivity.class);
        intent.putExtra(Constants.WATCH_CAR_ID, carId);
        ctx.startActivity(intent);
    }

    /**
     * 申请买车页面一
     *
     * @param ctx
     */
    public static void showApplyBuyFirstActivity(Context ctx, String carId) {
        Intent intent = new Intent(ctx, ApplyBuyFirstActivity.class);
        intent.putExtra(Constants.APPLY_BUY_CAR_ID, carId);
        ctx.startActivity(intent);
    }

    /**
     * 申请买车页面二
     *
     * @param ctx
     * @param model
     */
    public static void showApplyTwoActivity(Context ctx, ApplyCarModel model) {
        Intent intent = new Intent(ctx, ApplyBuyTwoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.APPLY_BUY_CAR_KEY, model);
        intent.putExtras(bundle);
        ctx.startActivity(intent);
    }

    /**
     * 申请卖出页面三
     *
     * @param ctx
     * @param applyCarRequest
     */
    public static void showApplyThreeActivity(Context ctx, Parcelable applyCarRequest) {
        Intent intent = new Intent(ctx, ApplyBuyThreeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.APPLY_BUY_CAR_KEY, applyCarRequest);
        intent.putExtras(bundle);
        ctx.startActivity(intent);
    }

    public static void showPhotoActivity(Activity act, int requestCode) {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //        intent.putExtra("aspectX", 1);
        //        intent.putExtra("aspectY", 1);
        //        //裁剪的大小
        //        intent.putExtra("outputX", 200);
        //        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        act.startActivityForResult(intent, requestCode);
    }

    /**
     * 选择品牌
     *
     * @param act
     * @param type
     */
    public static void showSelectBrandActivity(Activity act, String type) {
        Intent intent = new Intent(act, SelectBrandActivity.class);
        intent.putExtra(Constants.FRAGMENT_TYPE_KEY, type);
        act.startActivity(intent);
    }

    /**
     * 高级筛选
     *
     * @param act
     * @param type
     */
    public static void showSeniorSelectBrandActivity(Activity act, String type) {
        Intent intent = new Intent(act, SeniorFilterActivity.class);
        intent.putExtra(Constants.FRAGMENT_TYPE_KEY, type);
        act.startActivity(intent);
    }

    /**
     * 预约页面
     *
     * @param act
     */
    public static void showMeetAddressActivity(Activity act) {
        Intent intent = new Intent(act, MeetAddressActivity.class);
        act.startActivityForResult(intent, Constants.MEET_ADDRESS_REQUEST);
    }

    /**
     * 服务商页面
     *
     * @param ctx
     * @param id
     */
    public static void showServiceShopInfoActivity(Context ctx, String id) {
        Intent intent = new Intent(ctx, ServiceShopInfoActivity.class);
        intent.putExtra(Constants.SERVICE_SHOP_KEY, id);
        ctx.startActivity(intent);
    }

    /**
     * 申请服务商2
     *
     * @param ctx
     */
    public static void showApplyServiceSecondActivity(Context ctx) {
        Intent intent = new Intent(ctx, ApplyServiceSecondActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 申请服务商3
     *
     * @param ctx
     * @param mSMap
     */
    public static void showApplyServiceThreeActivity(Context ctx, ApplyFacilitatorModel mSMap) {
        Intent intent = new Intent(ctx, ApplyServiceThreeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.APPLY_FACILITATOR_KEY, mSMap);
        intent.putExtras(bundle);
        ctx.startActivity(intent);
    }

    /**
     * 主页面
     *
     * @param ctx
     */
    public static void showMainActivity(Context ctx) {
        Intent intent = new Intent(ctx, ApplyServiceSecondActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 用户注册的页面
     *
     * @param ctx
     */
    public static void showUserRegisterActivity(Context ctx) {
        Intent intent = new Intent(ctx, UserRegisterActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 跳转忘记密码的页面
     *
     * @param ctx
     */
    public static void showForgetPwdActivity(Context ctx) {
        Intent intent = new Intent(ctx, UserForgotPasswordActivity.class);
        intent.putExtra("type", "1");
        ctx.startActivity(intent);
    }

    /**
     * 显示物品交换界面
     *
     * @param ctx
     */
    public static void showChangeGoodsActivity(Context ctx, String channelName, String categoryId, String actName,int actType) {
        Intent intent = new Intent(ctx, ChangeGoodsActivity.class);
        intent.putExtra(Constants.CHANNEL_NAME_KEY, channelName);
        intent.putExtra(Constants.CATEGORY_ID_KEY, categoryId);
        intent.putExtra(Constants.ACT_NAME_KEY, actName);
        intent.putExtra(Constants.ACT_TYPE_KEY, actType);
        ctx.startActivity(intent);
    }

    /**
     * 显示物品购买界面
     *
     * @param ctx
     * @param goodsId
     * @param actName
     */
    public static void showGoodsDescriptionActivity(Context ctx, String goodsId, String actName) {
        Intent intent = new Intent(ctx, GoodsDescriptionActivity.class);
        intent.putExtra(Constants.GOODS_ID_KEY, goodsId);
        intent.putExtra(Constants.ACT_NAME_KEY, actName);
        ctx.startActivity(intent);
    }

}
