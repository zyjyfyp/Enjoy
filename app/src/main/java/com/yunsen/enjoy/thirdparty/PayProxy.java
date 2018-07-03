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
    public static final int SDK_PAY_FLAG = 111;
    public static String NOTIFY_URL = "http://www.szlxkg.com/api/payment/alipayphone/notify_url.aspx";
    public static String RSA2_PRIVATE = "MIIEowIBAAKCAQEAxr7xwi5DzDj6iL7v70Y8mc6fOPT8If+xBy17SXb/nXOkv5aIMWimvzP9Va+gJnjJu4j+rpHdeeLtXsn3HKhkpGcbFpDQi9aVqjSae9Y6STglbgJuFTj68N8izKne3kNuAS8eIQZmwVMUaZ7+afKtMD2AtMNlG8F1s3gBz4SS1C0DCwsA2LdZp7ZZnQPOFpQIXE1BRd3Wnr2Oe389fd2kgIp0FlJXFDifXGtlKh2a8C7WJoVVn0xMA6iHDpldCTsjDqJkc0d3vjD8AhYiIZ8i3w5JmPaYKtWEmtIWK/Kh2plZVWSZ/isCeaAp76DWoYX24hz9dLxZ71y7OP+cCqOsuwIDAQABAoIBADxIEkAZlErPRX5Qm6nxndPxDe5SsF8HlQReUTS5OVLijqfl2q1R1rHL7R0eRe8lV9rf9I+ygoDDycekbqVslgofk+HGTtr7XS3hArJpCgjHVOMHLaiWPdbtdzqok4bK4QTO686MiII+1sQqi3RLPSiSnwU09ufyR+cFkVcGGRrtexM7D3YNlVlWsBiaskMdV2HFOtb2dZzt4W1p8PVUWiXzQ9ZAwq8IPvcSsTG0VH/X1hEIxj5VN7jeveMKYDA4LcGime1ZsmkzVucsDglMta6UzaN9+ytpHHSvD6/d1frFlJ4FF/Zipo31uoI3UEQW051bQZtkTZYH+9xFFvC7EKECgYEA8BdqaXenUzRkdlLmWr5CX78RBQrCyhtDn6EWwU2Lotq8iYMSGmzwF1EEdzXx/yMZ/ecORBJPzpPKQBFlEHoNp2Y6GDh9E67MBQ6w8PXydfSscmCeaBatjPyhHDpUTs0reWYnHmO1kGAGlq6HfPHX7xGLMT0hADY8nzMqaoNooXkCgYEA0+oyzjo7sPNiQDr1q6BxqxkrsPxEbuNMnyTatpBTdJ+hVV7T1sLOQy3EN+W6Cvdyk3cW88l4j7KZV2zH3xwQexf09olNmccmHa7RR3V1hE5DFXijnzPpOYHdXSyOkCP462bO9ZHs3o4sSB8aF1FeceFTgS2qbnqk6iDDMwzbxtMCgYAP4YyhY0/HGi0XAo7IK9OaH7pReg++bKVBdtdZe/ajlBJqiif1ZYVQFYTVGdVjCgBbIiB4cjmjMXVsXXow59HlY8at+XaUibhnETy1A5/BG6kYe7meqvlZ3RAPHPCcsceRZSdrRC8rWJ628t51bk3ZO3DYlfGL4QqLgUfp+b0/CQKBgQCI0C/i0tfMwyUQgMAxANHB++soRYXM7XR/XmTR7tXieljHbARqJ7TQzFBdIjR9dgoDyKQJ2m26oDy9o55anZzKS8o8DKML//XMvhug/eVT+M74or1IQFR3ay9GQ0j7KC5BlEtTYdXCTZmrKmFG4qKN9UJbKOdaklW8TlCXudp+PwKBgEQ/Dk2aI074NQlxeLPqcg2rcPy938NUBbZFmBUIfBcpctx65Df31R8dKfVBoxGKrzNzlAMpPxsQzN7NRrNdwrIE6XnnnE8BNIAh27665cGVh4dzi+UEWJE0ijocZA2K4ijRwWGvECF9rjtegdn+wM81WFPd9xr+rx1sXodHg0L3";
    public static final String RSA_PRIVATE = "";
    public static final int SDK_AUTH_FLAG = 222;

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
