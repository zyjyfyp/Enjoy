package com.yunsen.enjoy.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunsen.enjoy.activity.pay.MyOrderZFActivity;
import com.yunsen.enjoy.activity.pay.TishiCarArchivesActivity;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.AliPaySignBean;
import com.yunsen.enjoy.model.WxPaySignBean;
import com.yunsen.enjoy.thirdparty.Common;
import com.yunsen.enjoy.thirdparty.PayProxy;
import com.yunsen.enjoy.thirdparty.alipay.OrderInfoUtil2_0;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.wxapi.WXEntryActivity;

import java.util.Map;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/7/5.
 */

public class PayMoneyProxy {
    private static PayMoneyProxy payMoneyProxy;

    public static PayMoneyProxy getInstance() {
        if (payMoneyProxy == null) {
            synchronized (PayMoneyProxy.class) {
                if (payMoneyProxy == null) {
                    payMoneyProxy = new PayMoneyProxy();
                }
            }
        }
        return payMoneyProxy;
    }

    private PayMoneyProxy() {
    }

    /**
     * 支付宝
     *
     * @param
     */
    public void aliayPay(Activity act, String userId, String userName, String totalMoney, String recharge_no, final Handler handler) {
        final Activity fAct = act;
        final String fTotalMoney = totalMoney;
        final String fRechargeNo = recharge_no;
        /**
         * 获取私钥
         */
        HttpProxy.alipaySign(userId, userName, totalMoney, recharge_no, new HttpCallBack<AliPaySignBean>() {
            @Override
            public void onSuccess(AliPaySignBean responseData) {
                PayProxy.NOTIFY_URL = responseData.getNotify_url();
                Common.PARTNER = responseData.getPartner();
                Common.SELLER = responseData.getSeller();
                Common.RSA_PRIVATE = responseData.getPrivate_key();
                PayProxy.RSA2_PRIVATE = responseData.getPrivate_key();
                ali_pay(fAct, fTotalMoney, fRechargeNo, handler);
            }

            @Override
            public void onError(Request request, Exception e) {
                if (e instanceof DataException) {
                    ToastUtils.makeTextShort(e.getMessage());
                }
            }
        });

    }

    /**
     * 调取支付宝支付界面
     *
     * @param act
     * @param total_fee
     * @param recharge_no
     * @param handler
     */
    private void ali_pay(Activity act, String total_fee, String recharge_no, Handler handler) {
        String bizContent = "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\""
                + total_fee + "\",\"subject\":\"袋鼠车宝\",\"body\":\"商品描述\",\"out_trade_no\":\"" + recharge_no + "\"}";
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(PayProxy.APPID, true, bizContent);
        PayProxy.payV2(act, handler, params);
    }

    /**
     * 微信支付
     *
     * @param
     */
    public void weiXinPay(Activity act, String userId, String userName, String totalMoney, final String recharge_no, Handler handler) {
        final Activity fAct = act;
        final String fTotalMoney = totalMoney;
        final String fRechargeNo = recharge_no;

        HttpProxy.wXinPaySign(userId, userName, totalMoney, recharge_no, new HttpCallBack<WxPaySignBean>() {
            @Override
            public void onSuccess(WxPaySignBean responseData) {
                String sign = responseData.getSign();
                responseData.getMch_id();
                String prepayid = responseData.getPrepay_id();
                String noncestr = responseData.getNonce_str();
                String timestamp = responseData.getTimestampX();
                String package_ = "Sign=WXPay";
                wXinPay(prepayid, noncestr, timestamp, package_, sign);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    private void wXinPay(String prepayid, String noncestr, String timestamp, String package_, String sign) {
        IWXAPI api = WXAPIFactory.createWXAPI(AppContext.getInstance(), null);
        api.registerApp(Constants.APP_ID);
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isPaySupported) {
            PayReq req = new PayReq();
            req.appId = Constants.APP_ID;
            req.partnerId = Constants.MCH_ID;
            req.prepayId = prepayid;// 7
            req.nonceStr = noncestr;// 3
            req.timeStamp = timestamp;// -1
            req.packageValue = package_;
            req.sign = sign;// -3
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            api.registerApp(Constants.APP_ID);
            api.sendReq(req);
        }
    }

    /**
     * 余额支付
     *
     * @param act
     * @param orderNo
     */
    public void balancePay(Activity act, String orderNo) {
        UIHelper.showTishiCarArchivesActivity(act, orderNo);
    }


}
