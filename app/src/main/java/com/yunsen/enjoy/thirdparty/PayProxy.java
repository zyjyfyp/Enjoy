package com.yunsen.enjoy.thirdparty;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.yunsen.enjoy.thirdparty.alipay.OrderInfoUtil2_0;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/21.
 */

public class PayProxy {
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2018042060024307";
    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";
    public static final int SDK_PAY_FLAG = 1;
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCH8RWcXgdmqxQBeaxIiieEMqAsnuciw7lnYiGo0hXt6xwYhCIsP3DIpyNPQ0hUoyy52nv5nevS3iHjOmmyHPsj9YF12q3GQ/mQK5UXUsigNmALCRYtEFLeRZLBPNOWmGE7EOvH/Zt5Vk3Oug9DLHKSsGzgCtQxvxYuiK5leH2UKIblLPXzIax33YQexK8z6P+fPlwnMmYHPhQUNnTctrzsu9xxwqKALjhbPSRyw2u2KZBFMq8PZY/LXoAqLAYPYKnrQK07czviGekYw9rPdYoTtoPORet+11ni+ojbaNzAbbLstv08Eh5XTGkOzKATzWNKdchCZe/saJb7GBR/J8zvAgMBAAECggEAKrrlCBmmSedMioosVUDX2v3uyxfHn23WQWDfI6mlqvtUAYDHHRFz6h5fQvLrMW95B5JdlcDF96pLDJm3z3y8lDj/UIRESyXitCZ+3q3u0i1FUzKv/co7whIsZwEvoQ6UwXQ4KMzWqfUuz+Q8F+lIukMqdeHxgRGHb2kXKF4PWxpMum24G56zKwCzGV0Esh2Y86lD+O2FiqVsXgjKm0KRcBA6eG6TlO/z/OeZ9n054aDhKaSKGq9+NN2hOkkLgZWg4DMKRO9Vgw5L0NdjGAHxOQlVI/V2FrGDI7xe1d10+aex4H/z+Iy6DSMxASE2dcgTuR1Xb02uqouv3DDBPPDfgQKBgQC/tHYwhZSWq/6nZ+fRPRWGx/AGcplwdKCjp1up0J2BKs6mPYGFmlhbVpA3SizkXKYG69l0UEB5K0Z13INqsa8L3xtPEUApykkzh8rLGbfuhlvZmwMgEmPFLXORFe2i/+/DZrvE5gqCC1AgoWSwHJFxBNqyk6uHVHg1HI7pUM6bBwKBgQC1iN3pALQr2IxFE7ecD6blF02ktxB6ZHLNI4UWWvjD080Rgsf49uQrACg32ETNjXmPYdPdNFaWUpma23/GFTRSHpbFwhbntiPWFicK8gZEOgKz+hBD3bFlUjcaiizZbWuZU3Raum7GB2vzq4OuEIEfSw9+6G0WRePs2z9VXNl82QKBgF6rU6JqlRJDJaKuIUCnFbZVjibg+P0b2yh2QUH/D4j2Bs75mLjNm8yfVuC29KxhRGwulFhoOS6dPFJ/7zYFQRhvuSC2uCTrDHMyH+dkb/yOxVUUw9pWuoZpB6UiWxQfp9urJBb8Mzw9MfirOvntYbWaXBXL7u0JvcMcs0Zs7VqJAoGAc0FPauS4f3IANMc89sd1NdQixqY3vw1RWe7oS3Y8pNAvTaPT5LyTxr1Vy4byv/LHsAHzamkVZGFTBt+/cvr5ef3TygDdXAXnIMB1z1w0IFMYbj4g34W8YFDfjWkW6oCvJOy4XfYp6G7D2jQV6YivAjg8wV0+DTsYZ8QXpNHLf5kCgYEAmRrXXyJG8DFV5RHLqGYDRfhmVky0NGDnD0GtCu88BiZwccbOxUFgzEcH9EkJWuOSzu5Te9+FHwyKcP+oQ0JHnEluusY0KcgZfxckJPYB7EqRnuh1/0qrtF4RhMi08vayzxXni2a+/T9Xncb6DuA+Ri1K673M/FzpkqxOqbd3gCk=";
    public static final String RSA_PRIVATE = "aa";
    public static final int SDK_AUTH_FLAG = 2;
    public static void payV2(final Activity act, final Handler mHandler, View v) {
       
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(act).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            act.finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(act);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
