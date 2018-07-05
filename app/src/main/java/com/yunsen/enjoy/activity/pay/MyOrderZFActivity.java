package com.yunsen.enjoy.activity.pay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.thirdparty.Common;
import com.yunsen.enjoy.thirdparty.PayProxy;
import com.yunsen.enjoy.thirdparty.alipay.AuthResult;
import com.yunsen.enjoy.thirdparty.alipay.OrderInfoUtil2_0;
import com.yunsen.enjoy.thirdparty.alipay.PayResult;
import com.yunsen.enjoy.widget.DialogProgress;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 袋鼠车宝
 *
 * @author Administrator
 */
public class MyOrderZFActivity extends AppCompatActivity implements OnClickListener {
    public static final String BE_COME_VIP = "become_vip";
    String total_c;
    private DialogProgress progress;
    String user_name, user_id, login_sign, order_no;
    private SharedPreferences spPreferences;
    boolean zhuangtai = false;
    private String partner_id, prepayid, noncestr, timestamp, package_, sign;
    private IWXAPI api;
    boolean flag;
    String xq = "0";
    String orderxq;
    public static String order_type = "0";
    public static String recharge_no, notify_url;
    public static String province, city, area, user_address, accept_name,
            user_mobile;
    public static String datetime, sell_price, give_pension, article_id;
    LinearLayout ll_zhifu_buju;
    public static String huodong_type = "0";
    private boolean mIsBecomeVip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_delete_pop);
        spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        user_name = spPreferences.getString(SpConstants.USER_NAME, "");
        user_id = spPreferences.getString(SpConstants.USER_ID, "");
        progress = new DialogProgress(MyOrderZFActivity.this);
        api = WXAPIFactory.createWXAPI(MyOrderZFActivity.this, null);
        api.registerApp(Constants.APP_ID);
        Intent intent = getIntent();
        recharge_no = intent.getStringExtra("order_no");
        total_c = intent.getStringExtra("total_c");
        mIsBecomeVip = intent.getBooleanExtra(BE_COME_VIP, false);
        setUpViews();
    }

    private void setUpViews() {
        TextView item0 = (TextView) findViewById(R.id.item0);
        TextView item1 = (TextView) findViewById(R.id.item1);
        TextView item2 = (TextView) findViewById(R.id.item2);
        TextView item3 = (TextView) findViewById(R.id.item3);
        TextView item4 = (TextView) findViewById(R.id.item4);
        ll_zhifu_buju = (LinearLayout) findViewById(R.id.ll_zhifu_buju);
        if (mIsBecomeVip) {
            item0.setVisibility(View.GONE);
        } else {
            item0.setVisibility(View.VISIBLE);
        }
        item0.setOnClickListener(this);
        // item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        item3.setOnClickListener(this);
        item4.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (flag == true) {
//            userloginqm();
        orderxq = getIntent().getStringExtra("5");
//        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.item0:
                // 余额支付
                orderxq = getIntent().getStringExtra("5");
                Intent intent = new Intent(MyOrderZFActivity.this, TishiCarArchivesActivity.class);
                // intent.putExtra("order_type",order_type);
                intent.putExtra("order_no", recharge_no);
                intent.putExtra("order_yue", "order_yue");
                intent.putExtra("orderxq", orderxq);
                intent.putExtra("img_url", getIntent().getStringExtra("img_url"));
                intent.putExtra("hd_title", getIntent().getStringExtra("title"));
                intent.putExtra("start_time", getIntent().getStringExtra("start_time"));
                intent.putExtra("end_time", getIntent().getStringExtra("end_time"));
                intent.putExtra("address", getIntent().getStringExtra("address"));
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("real_name", getIntent().getStringExtra("real_name"));
                intent.putExtra("mobile", getIntent().getStringExtra("mobile"));
                startActivity(intent);
                finish();
                break;
            case R.id.item1:
                break;
            case R.id.item2:// 支付宝
                loadzhidu(recharge_no);
                break;
            case R.id.item3:// 微信
                loadweixinzf2(recharge_no);
                break;
            case R.id.item4:
                finish();
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void dispatchMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    ali_pay();
                    break;
                case 2:// 微信支付
                    try {
                        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                        if (isPaySupported) {
                            try {
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
                                flag = api.sendReq(req);
                                System.out.println("支付" + flag);
                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                        } else {
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                    break;
                case PayProxy.SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(MyOrderZFActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        if (mIsBecomeVip) {
                            EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                        }
                        finish();

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(MyOrderZFActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
                }
                case PayProxy.SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(MyOrderZFActivity.this, "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                        finish();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(MyOrderZFActivity.this, "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
                        finish();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == 2) {
//            if (mIsBecomeVip) {
//                EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
//            }
//        }
    }

    /**
     * 支付宝
     *
     * @param
     */
    private void loadzhidu(String recharge_no) {
        try {

            AsyncHttp.get(URLConstants.REALM_NAME_LL + "/payment_sign?user_id="
                    + user_id + "&user_name=" + user_name + "" + "&total_fee="
                    + total_c + "&out_trade_no=" + recharge_no
                    + "&payment_type=alipay", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int arg0, String arg1) {
                    super.onSuccess(arg0, arg1);
                    try {
                        JSONObject object = new JSONObject(arg1);
                        String status = object.getString("status");
                        String info = object.getString("info");
                        if ("y".equals(status)) {
                            JSONObject obj = object.getJSONObject("data");
                            PayProxy.NOTIFY_URL = notify_url = obj.getString("notify_url");
                            Common.PARTNER = obj.getString("partner");
                            Common.SELLER = obj.getString("seller");
                            Common.RSA_PRIVATE = obj.getString("private_key");
                            PayProxy.RSA2_PRIVATE = obj.getString("private_key");
                            progress.CloseProgress();
                            handler.sendEmptyMessage(1);
                            finish();
                        } else {
                            progress.CloseProgress();
                            Toast.makeText(MyOrderZFActivity.this, info, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 微信支付
     *
     * @param
     */
    private void loadweixinzf2(String recharge_no) {
        try {
            String monney = String.valueOf(Double.parseDouble(total_c) * 100);
            AsyncHttp.get(URLConstants.REALM_NAME_LL + "/payment_sign?user_id="
                            + user_id + "&user_name=" + user_name + "" + "&total_fee="
                            + monney + "&out_trade_no=" + recharge_no + "&payment_type=weixin",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {

                                JSONObject object = new JSONObject(arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    JSONObject jsonObject = object.getJSONObject("data");
                                    partner_id = jsonObject.getString("mch_id");
                                    prepayid = jsonObject.getString("prepay_id");
                                    noncestr = jsonObject.getString("nonce_str");
                                    timestamp = jsonObject.getString("timestamp");
                                    package_ = "Sign=WXPay";
                                    sign = jsonObject.getString("sign");
                                    progress.CloseProgress();
                                    handler.sendEmptyMessage(2);
                                    // loadweixinzf3(recharge_no);
                                    zhuangtai = true;
                                    // finish();
                                    ll_zhifu_buju.setVisibility(View.INVISIBLE);
                                } else {
                                    progress.CloseProgress();
                                    Toast.makeText(MyOrderZFActivity.this, info, Toast.LENGTH_SHORT)
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 获取登录签名
     */
    private void userloginqm() {
        try {
            SharedPreferences spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
            String user_name = spPreferences.getString(SpConstants.USER_NAME, "");
            String strUrlone = URLConstants.REALM_NAME_LL + "/get_user_model?username=" + user_name + "";
            AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
                public void onSuccess(int arg0, String arg1) {
                    try {
                        JSONObject object = new JSONObject(arg1);
                        String status = object.getString("status");
                        JSONObject obj = object.getJSONObject("data");
                        if (status.equals("y")) {
                            UserRegisterllData data = new UserRegisterllData();
                            data.login_sign = obj.getString("login_sign");
                            login_sign = data.login_sign;
                            loadguanggaoll(recharge_no, login_sign);
                        } else {

                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }

                ;
            }, MyOrderZFActivity.this);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 确认付款 更新订单
     *
     * @param login_sign
     * @param
     */
    private void loadguanggaoll(String recharge_noll, String login_sign) {
        try {
            AsyncHttp.get(URLConstants.REALM_NAME_LL
                            + "/update_order_payment?user_id=" + user_id
                            + "&user_name=" + user_name + "" + "&trade_no="
                            + recharge_noll + "&sign=" + login_sign + "",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject object = new JSONObject(arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    progress.CloseProgress();

                                    // 活动支付成功不显示详情
                                    if (BaoMinTiShiActivity.huodong_zf_type.equals("1")) {
                                        BaoMinTiShiActivity.huodong_zf_type = "0";
                                        finish();
                                    } else {
                                        // Intent intent = new Intent(MyOrderZFActivity.this,ZhiFuOKActivity.class);
                                        // startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    progress.CloseProgress();
                                    finish();
                                    Toast.makeText(MyOrderZFActivity.this, info, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {
                            super.onFailure(arg0, arg1);
                            finish();
                        }

                    }, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ali_pay() {
        String bizContent = "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\""
                + total_c + "\",\"subject\":\"袋鼠车宝\",\"body\":\"商品描述\",\"out_trade_no\":\"" + recharge_no + "\"}";
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(PayProxy.APPID, true, bizContent);
        PayProxy.payV2(this, handler, params);
    }


}
