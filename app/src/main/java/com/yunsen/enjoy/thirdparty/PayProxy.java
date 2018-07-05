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
    public static final String APPID = "2018052560213307";
    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088131337200802";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final int SDK_PAY_FLAG = 111;  //支付宝支付
    public static String NOTIFY_URL = "http://www.szlxkg.com/api/payment/alipayphone/notify_url.aspx";
    public static String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDrsKfVDpj9Onv2Dkf3OsKMfmmu7TqyFXHCDVETFzv4Z1qDjo2H3cEsbFdIUzDcvCXK+OkHNLN3ofLiWL4I2qiRLJF/ndrUDddYSk0fkB3XjWsiAP5u6Zwktnqr7SaIMQaJnvfy3C+nLYA+nGG8DExRGRkLC+LOHkfaYuWiy/XqS9eeTia0LO914aqA+j6uUTCd6YDyJVAcs+Am7MfKN/jsUQi7wkwMF5BAvSMmDXvpFPKjBh21glSMW/SQYrpPKGyq4DgaEmHrvw5rKvBpRdHW5RUNmwe8GhyNS9qU/AMuGIuvAW1BAtliQtZbhJM2WxouvIT5cgONcVsZUQhWxefpAgMBAAECggEAQI+Dabk17ye2DRCcZfySjimSiXgyO9Xj82PycWhL5R9qi97gpfb4k1KaFhWzrMNdFtxX+MFW585U2eu1InfC9QFgWbTD3JJuCqFPvvWbNxBl6EqdZ4RWYTgbIj0z8Np9D/WbtcrawtCT8JKlsv0raouVcJEw74Ub6ZZtkMqN8j+NkPhyxxpVWN126L0qKPD8kRzAl2SphdBF+H3umP7XKFv3riBaXJKIWnvVwtFmgvpY+D14LKqoXyU6reE8uFGErpKq/Xfsnme3rAlVNbWgnb8kCcLxgBNG8TIt3o/oYpvclUOm6Q35ADa8a8V82TTYZj7zrpiYqsfjo1hfJ/USgQKBgQD8k5vr6C7YsCtWlqcaUX5SP0s74amj508bA06ST+Q/eQdFL0VzyGCCCOEyCaE0d4xRSnjJnhLST5r3BRzmPxA0ozsMDBD6FXQTAsYVcDbAEk71lo/YIs6q9Ek75j2/UiQ3RtT5K8OeD8fCsdIkqLRDWv/v1Z/RbVB8KVXwhf1GHwKBgQDu4nQdmSc1h2IjdMMhFMsIUmSFHo3pqdiCY+eTnFwqD4kxYUAIOQkf1FHmLalLQoUJlHeloTTMep7alzIOG8GPL+zoSb4uekft5IA3nwH5TmajrFWijOfdOjEtuxiE2Zy57IFij7OQ6Ng3OIAc46U4GE8mg+5icrdA40KzazDA9wKBgFH+LSDBB+wqDHeYgniluedDSs4OrL5bia+QPEJ9EvCT/os0FEzI4KN19H0E6UMaCAAft+Rjn2KMmxbTnxUTpdQgpTt880nsCvlc7ibpfayOhPluvhWA/yw6D0gVrzfbl4wmP18AGQnHv6ZxiXGDHrm64djetXoMDZOw64ggRx8rAoGBANrn+bAF4MBUx8HmBcoS29tMZYD3duRhGF6kXloONwb0JaxyidL/MTVRw5tCzJiC5JbazqmhCFk5JMj7NItpM/6a1GYo7XZ55+BAPJ67v3tONRBXpEHJDdDUuqxFfzp+j/hwmkBPnyVzum8f/4Z5tbWp0R5iV9kvdm6n0z/HQm4RAoGAR933l8i3CsAbx4qCSpY6G5sdToHgbiDoaBGnivph1zEcH7yGYCgakeK1YM0dv03RzLzKRaJXp5mgmhGoAdaW6AidpATQ53rr/5XS2t9NKleH3r7Dl0JTKL+gF9TDWvX4E8mNaxAxfS2k8b7TPK2qN0f/z4Yl73drGrOwoS1hDNs=";
    public static final String RSA_PRIVATE = "";
    public static final int SDK_AUTH_FLAG = 222; //支付宝授权

    public static void payV2(final Activity act, final Handler mHandler, Map<String, String> params) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(act).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
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
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
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
