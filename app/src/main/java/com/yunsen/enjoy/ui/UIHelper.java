package com.yunsen.enjoy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunsen.enjoy.activity.AdvertActivity;
import com.yunsen.enjoy.activity.CarDetailsActivity;
import com.yunsen.enjoy.activity.HouseDetailActivity;
import com.yunsen.enjoy.activity.MainActivity;
import com.yunsen.enjoy.activity.MoveActivity;
import com.yunsen.enjoy.activity.SearchActivity;
import com.yunsen.enjoy.activity.SelectCityNewActivity;
import com.yunsen.enjoy.activity.ServiceShopInfoActivity;
import com.yunsen.enjoy.activity.WebActivity;
import com.yunsen.enjoy.activity.buy.ApplyBuyFirstActivity;
import com.yunsen.enjoy.activity.buy.ApplyBuyThreeActivity;
import com.yunsen.enjoy.activity.buy.ApplyBuyTwoActivity;
import com.yunsen.enjoy.activity.buy.CarServiceActivity;
import com.yunsen.enjoy.activity.buy.ExchangePointActivity;
import com.yunsen.enjoy.activity.buy.GoodsDescriptionActivityOld;
import com.yunsen.enjoy.activity.buy.MeetAddressActivity;
import com.yunsen.enjoy.activity.buy.PartsShopActivity;
import com.yunsen.enjoy.activity.buy.SecondActivityActivity;
import com.yunsen.enjoy.activity.buy.ServiceMoreActivity;
import com.yunsen.enjoy.activity.buy.WatchCarActivity;
import com.yunsen.enjoy.activity.dealer.ApplyServiceActivity;
import com.yunsen.enjoy.activity.dealer.ApplyServiceSecondActivity;
import com.yunsen.enjoy.activity.dealer.ApplyServiceThreeActivity;
import com.yunsen.enjoy.activity.dealer.MyFacilitatorActivity;
import com.yunsen.enjoy.activity.mine.AddressManagerActivity;
import com.yunsen.enjoy.activity.mine.AppointmentActivity;
import com.yunsen.enjoy.activity.mine.BindBankCardActivity;
import com.yunsen.enjoy.activity.mine.CollectionActivity;
import com.yunsen.enjoy.activity.mine.ExtensionActivity;
import com.yunsen.enjoy.activity.mine.MyAssetsActivity;
import com.yunsen.enjoy.activity.mine.MyOrderConfrimActivity;
import com.yunsen.enjoy.activity.mine.MyQianBaoActivity;
import com.yunsen.enjoy.activity.mine.PersonCenterActivity;
import com.yunsen.enjoy.activity.mine.ShopCartActivity;
import com.yunsen.enjoy.activity.mine.TeamActivity;
import com.yunsen.enjoy.activity.mine.UserForgotPasswordActivity;
import com.yunsen.enjoy.activity.mine.WithdrawCashActivity;
import com.yunsen.enjoy.activity.order.ApplyAfterSaleActivity;
import com.yunsen.enjoy.activity.order.DianPingActivity;
import com.yunsen.enjoy.activity.order.MyOrderActivity;
import com.yunsen.enjoy.activity.order.MyOrderXqActivity;
import com.yunsen.enjoy.activity.pay.MyOrderZFActivity;
import com.yunsen.enjoy.activity.user.DBFengXiangActivity;
import com.yunsen.enjoy.activity.user.TishiWxBangDingActivity;
import com.yunsen.enjoy.activity.user.UserLoginActivity;
import com.yunsen.enjoy.activity.user.UserRegisterActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.buy.SelectBrandActivity;
import com.yunsen.enjoy.fragment.buy.SeniorFilterActivity;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.MyOrderData;
import com.yunsen.enjoy.model.OrderBean;
import com.yunsen.enjoy.model.request.ApplyCarModel;
import com.yunsen.enjoy.model.request.ApplyFacilitatorModel;
import com.yunsen.enjoy.utils.AccountUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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


    public static void showHouseDetailActivity(Activity context) {
        Intent intent = new Intent(context, HouseDetailActivity.class);
        context.startActivity(intent);
    }

    /**
     * z在MainActivity 跳转 fragment
     *
     * @param act
     * @param index
     */
    public static void toMainOtherFragment(Activity act, int index) {
        if (act instanceof MainActivity) {
            ((MainActivity) act).setCurrIndex(index);
        }
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
        Intent intent = new Intent(context, SelectCityNewActivity.class);
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
        Intent intent4 = new Intent(ctx, WebActivity.class);
        String url = URLConstants.REALM_NAME_WEB + "/mobile/news/conent-10334.html";
        intent4.putExtra(Constants.WEB_URL_KEY, url);
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
     * @param type 1 待付款 2 待发货 3待收货 4 已完成
     */
    public static void showOrderActivity(Activity ctx, String type) {
        Intent intent = new Intent(ctx, MyOrderActivity.class);
        intent.putExtra("status", type);
        ctx.startActivityForResult(intent, Constants.ORDER_ACT_REQUEST);
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
     * 袋鼠车宝服务
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
    public static void showWatchCarActivity(Activity ctx, String carId) {
        if (!AccountUtils.hasLogin()) {
            showUserLoginActivity(ctx);
        } else if (!AccountUtils.hasBoundPhone()) {
            showBundPhoneActivity(ctx);
        } else {
            if (!AccountUtils.hasVIP()) {
                DialogUtils.showBecomeVipDialog(ctx);
            } else {
                Intent intent = new Intent(ctx, WatchCarActivity.class);
                intent.putExtra(Constants.WATCH_CAR_ID, carId);
                ctx.startActivity(intent);
            }
        }

    }

    /**
     * 申请买车页面一
     *
     * @param ctx
     */
    public static void showApplyBuyFirstActivity(Activity ctx, String carId) {
        if (!AccountUtils.hasLogin()) {
            showUserLoginActivity(ctx);
        } else if (!AccountUtils.hasBoundPhone()) {
            showBundPhoneActivity(ctx);
        } else {
            if (!AccountUtils.hasVIP()) {
                DialogUtils.showBecomeVipDialog(ctx);
            } else {
                Intent intent = new Intent(ctx, ApplyBuyFirstActivity.class);
                intent.putExtra(Constants.APPLY_BUY_CAR_ID, carId);
                ctx.startActivity(intent);
            }

        }
    }

    /**
     * 申请买车页面二
     *
     * @param ctx
     * @param model
     */

    public static void showApplyTwoActivity(Activity ctx, ApplyCarModel model) {
        if (!AccountUtils.hasLogin()) {
            showUserLoginActivity(ctx);
        } else if (!AccountUtils.hasBoundPhone()) {
            showBundPhoneActivity(ctx);
        } else {
            if (!AccountUtils.hasVIP()) {
                DialogUtils.showBecomeVipDialog(ctx);
            } else {
                Intent intent = new Intent(ctx, ApplyBuyTwoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.APPLY_BUY_CAR_KEY, model);
                intent.putExtras(bundle);
                ctx.startActivity(intent);
            }
        }
    }

    /**
     * 申请卖出页面三
     *
     * @param ctx
     * @param applyCarRequest
     */
    public static void showApplyThreeActivity(Context ctx, Parcelable applyCarRequest) {
        if (!AccountUtils.hasLogin()) {
            showUserLoginActivity(ctx);
        } else if (!AccountUtils.hasBoundPhone()) {
            showBundPhoneActivity(ctx);
        } else {
            if (!AccountUtils.hasVIP()) {
                DialogUtils.showBecomeVipDialog(ctx);
            } else {
                Intent intent = new Intent(ctx, ApplyBuyThreeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.APPLY_BUY_CAR_KEY, applyCarRequest);
                intent.putExtras(bundle);
                ctx.startActivity(intent);
            }
        }
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
     * 车型
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
        Intent intent = new Intent(ctx, MainActivity.class);
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
     * 提现页面
     *
     * @param ctx
     */
    public static void showWithdrawCashActivity(Context ctx, double balance) {
        Intent intent = new Intent(ctx, WithdrawCashActivity.class);
        intent.putExtra(Constants.BALANCE, balance);
        ctx.startActivity(intent);
    }

    /**
     * 配件商城页面
     *
     * @param ctx
     */
    public static void showPartsShopActivity(Context ctx) {
        Intent intent = new Intent(ctx, PartsShopActivity.class);
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
        showGoodsDescriptionActivity(ctx, goodsId, actName, Constants.DEFAULT_BUY);
    }

    /**
     * 显示物品购买界面
     *
     * @param ctx
     * @param goodsId
     * @param actName
     * @param buyType {@link  com.yunsen.enjoy.common.Constants#DEFAULT_BUY} or {@link  com.yunsen.enjoy.common.Constants#POINT_BUY}
     */
    public static void showGoodsDescriptionActivity(Context ctx, String goodsId, String actName, int buyType) {
        showGoodsDescriptionActivity(ctx, goodsId, actName, buyType, 0);
    }

    /**
     * 显示物品购买界面 秒杀
     *
     * @param ctx
     * @param goodsId
     * @param actName
     * @param buyType {@link  com.yunsen.enjoy.common.Constants#DEFAULT_BUY} or {@link  com.yunsen.enjoy.common.Constants#POINT_BUY}
     */
    public static void showGoodsDescriptionActivity(Context ctx, String goodsId, String actName, int buyType, long remainingTime) {
        Intent intent = new Intent(ctx, GoodsDescriptionActivityOld.class);
        intent.putExtra(Constants.GOODS_ID_KEY, goodsId);
        intent.putExtra(Constants.ACT_NAME_KEY, actName);
        intent.putExtra(Constants.ACT_TYPE_KEY, buyType);
        intent.putExtra(Constants.REMAINING_TIME, remainingTime);
        ctx.startActivity(intent);
    }

    /**
     * 去主页面
     *
     * @param ctx
     */
    public static void showHomeShopCar(Context ctx) {
        Intent intent = new Intent(ctx, MainActivity.class);
        intent.putExtra(Constants.FRAGMENT_TYPE_KEY, 0);
        ctx.startActivity(intent);
    }

    /**
     * 跳转购物车
     *
     * @param ctx
     */
    public static void showHomeCarFragment(Context ctx) {
        Intent intent = new Intent(ctx, MainActivity.class);
        intent.putExtra(Constants.FRAGMENT_TYPE_KEY, 3);
        ctx.startActivity(intent);
    }

    /**
     * 点评页面
     *
     * @param ctx       上下文
     * @param articleId
     */
    public static void showDianPingActivity(Context ctx, int articleId) {
        Intent intent = new Intent(ctx, DianPingActivity.class);
        intent.putExtra("article_id", String.valueOf(articleId));
        ctx.startActivity(intent);
    }

    /**
     * 分享页面
     *
     * @param ctx
     * @param spId
     * @param companyId
     * @param title
     * @param subTitle
     * @param imgUrl
     */
    public static void showDBFengXiangActivity(Context ctx, String spId, String companyId, String title, String subTitle, String imgUrl) {
        Intent intent = new Intent(ctx, DBFengXiangActivity.class);
        intent.putExtra("sp_id", spId);
        intent.putExtra("company_id", companyId);
        intent.putExtra("title", title);
        intent.putExtra("subtitle", subTitle);
        intent.putExtra("img_url", imgUrl);
        ctx.startActivity(intent);
    }

    /**
     * 分享商品
     *
     * @param ctx
     * @param shareDescription
     * @param shareUrl
     * @param imgPath
     */
    public static void showShareGoodsActivity(Context ctx, String shareTitle, String shareDescription, String shareUrl, String imgPath) {
        Intent intent = new Intent(ctx, DBFengXiangActivity.class);
        intent.putExtra(Constants.SHARE_IMG_URL, imgPath);
        intent.putExtra(Constants.SHARE_URL, shareUrl);
        intent.putExtra(Constants.SHARE_TITLE, shareTitle);
        intent.putExtra(Constants.SHARE_DESCRIPTION, shareDescription);
        intent.putExtra(Constants.SHARE_TYPE, Constants.SHARE_GOODS_INFO);
        ctx.startActivity(intent);
    }

    /**
     * 分享app
     *
     * @param ctx
     * @param imgPath
     */
    public static void showShareAppInfoActivity(Context ctx, String imgPath) {
        Intent intent = new Intent(ctx, DBFengXiangActivity.class);
        intent.putExtra(Constants.SHARE_IMG_URL, imgPath);
        intent.putExtra(Constants.SHARE_TYPE, Constants.SHARE_APP_INFO);
        ctx.startActivity(intent);
    }

    /**
     * 订单确认页面
     *
     * @param ctx
     * @param buyNo
     */
    public static void showMyOrderConfrimActivity(Context ctx, String buyNo) {
        Intent intent = new Intent(ctx, MyOrderConfrimActivity.class);
        intent.putExtra("buy_no", buyNo);
        ctx.startActivity(intent);
    }

    /**
     * 显示订单详情页面
     *
     * @param ctx
     * @param rechargeNo 交易号
     */
    public static void showMyOrderXqActivity(Context ctx, String rechargeNo) {
        final Context fContext = ctx;
        AsyncHttp.get(URLConstants.REALM_URL + "/tools/mobile_ajax.asmx/get_order_trade_list?trade_no=" + rechargeNo, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String arg1) {
                MyOrderData md = new MyOrderData();
                try {
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    String info = object.getString("info");
                    if (status.equals("y")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        int len = jsonArray.length();
                        for (int i = 0; i < len; i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            md.setId(obj.getString("id"));
                            md.setOrder_no(obj.getString("order_no"));
                            md.setTrade_no(obj.getString("trade_no"));
                            md.setCompany_name(obj.getString("company_name"));
                            md.setPayment_status(obj.getString("payment_status"));
                            md.setAccept_name(obj.getString("accept_name"));
                            md.setExpress_status(obj.getString("express_status"));
                            md.setExpress_fee(obj.getString("express_fee"));
                            md.setStatus(obj.getString("status"));
                            md.setProvince(obj.getString("province"));
                            md.setCashing_packet(obj.getString("cashing_packet_total"));
                            md.setExchange_price_total(obj.getString("exchange_price_total"));
                            md.setExchange_point_total(obj.getString("exchange_point_total"));
                            md.setAddress(obj.getString("address"));
                            md.setUser_name(obj.getString("user_name"));
                            md.setPayment_time(obj.getString("payment_time"));
                            md.setPayable_amount(obj.getString("payable_amount"));
                            md.setAdd_time(obj.getString("add_time"));
                            md.setComplete_time(obj.getString("complete_time"));
                            md.setRebate_time(obj.getString("rebate_time"));
                            md.setMobile(obj.getString("mobile"));
                            md.setCity(obj.getString("city"));
                            md.setArea(obj.getString("area"));

                            String order_groupon = obj.getString("order_goods");

                            md.setList(new ArrayList<OrderBean>());
                            JSONArray ja = new JSONArray(order_groupon);
                            List<OrderBean> lists = new ArrayList<OrderBean>();
                            OrderBean mb;
                            for (int j = 0; j < ja.length(); j++) {
                                JSONObject jo = ja.getJSONObject(j);
                                mb = new OrderBean();
                                mb.setPoint_title(jo.getString("article_title"));
                                mb.setPoint_price(jo.getString("exchange_price"));
                                mb.setPoint_value(jo.getString("exchange_point"));
                                mb.setImg_url(jo.getString("img_url"));
                                mb.setArticle_id(jo.getString("article_id"));
                                md.getList().add(mb);
                                lists.add(mb);
                            }
                            md.setList(lists);
                        }
                        Intent intent = new Intent(fContext, MyOrderXqActivity.class);
                        intent.putExtra("bean", md);
                        fContext.startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, fContext);
    }

    /**
     * 显示积分兑换更多页面
     *
     * @param ctx
     */
    public static void showExchangePointActivity(Context ctx) {
        Intent intent = new Intent(ctx, ExchangePointActivity.class);
        ctx.startActivity(intent);

    }

    /**
     * 去更多
     *
     * @param ctx
     */
    public static void showSecondActivityActivity(Context ctx) {
        Intent intent = new Intent(ctx, SecondActivityActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 去购物车 activity
     *
     * @param ctx
     */
    public static void showShopCar(Context ctx) {
        Intent intent = new Intent(ctx, ShopCartActivity.class);
        ctx.startActivity(intent);
    }


    /**
     * 服务商更多
     *
     * @param ctx
     */
    public static void showServiceMoreActivity(Context ctx) {
        Intent intent = new Intent(ctx, ServiceMoreActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 绑定银行卡
     *
     * @param activity
     */
    public static void showBindBankCardActivity(Activity activity) {
        Intent intent = new Intent(activity, BindBankCardActivity.class);
        activity.startActivityForResult(intent, Constants.BIND_BANK_CARD_REQUEST);
    }

    /**
     * 用户协议
     *
     * @param ctx
     */
    public static void showUserAgreement(Context ctx) {
        String url = URLConstants.REALM_NAME_WEB + "/mobile/news/conent-1006.html";
        showWebActivity(ctx, url);
    }

    /**
     * 服务商协议
     *
     * @param ctx
     */
    public static void showServiceAgreement(Context ctx) {
        String url = URLConstants.REALM_NAME_WEB + "/mobile/news/conent-4023.html";
        showWebActivity(ctx, url);
    }

    /**
     * 面对面推广
     *
     * @param ctx
     */
    public static void showExtensionActivity(Context ctx) {
        Intent intent = new Intent(ctx, ExtensionActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * 跳转微信小程序的在线客服
     *
     * @param ctx
     */
    public static void goWXApp(Context ctx) {
        String appId = Constants.APP_ID; // "wxe60c28541b0fa8a2"填应用AppId
        IWXAPI api = WXAPIFactory.createWXAPI(ctx, appId);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = Constants.WX_GH_ID; // 填小程序原始id
        req.path = "pages/customer/customer";                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
        api.sendReq(req);
    }

    /**
     * 去支付
     *
     * @param ctx
     * @param orderNo
     * @param money
     */
    public static void toPayVipMoney(Context ctx, String orderNo, double money) {
        Intent intent = new Intent(ctx, MyOrderZFActivity.class);
        intent.putExtra("order_no", orderNo);
        intent.putExtra("order_type", "5");
        intent.putExtra("total_c", String.valueOf(money));
        intent.putExtra(MyOrderZFActivity.BE_COME_VIP, true);
        ctx.startActivity(intent);
    }

    /**
     * 售后服务
     *
     * @param ctx
     * @param myOrderData
     */
    public static void showApplyAfterSaleActivity(Context ctx, MyOrderData myOrderData) {
        Intent intent = new Intent(ctx, ApplyAfterSaleActivity.class);
        intent.putExtra(Constants.ORDER_DATA, myOrderData);
        ctx.startActivity(intent);
    }

    /**
     * 去地址页面
     *
     * @param act
     * @param requestCode
     */
    public static void showAddressManagerActivity(Activity act, int requestCode) {
        Intent intent = new Intent(act, AddressManagerActivity.class);
        act.startActivityForResult(intent, requestCode);
    }
}
