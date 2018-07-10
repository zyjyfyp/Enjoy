package com.yunsen.enjoy.activity.pay;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.db.SharedUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.MyOrderConfrimActivity;
import com.yunsen.enjoy.activity.pay.adapter.RechargeMoneyAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.RechargeModel;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.model.WxSignData;
import com.yunsen.enjoy.thirdparty.Common;
import com.yunsen.enjoy.thirdparty.PayProxy;
import com.yunsen.enjoy.thirdparty.alipay.OrderInfoUtil2_0;
import com.yunsen.enjoy.thirdparty.alipay.PayResult;
import com.yunsen.enjoy.thirdparty.alipay.SignUtils;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 余额充值
 *
 * @author Administrator
 */
public class MonneyChongZhiActivity extends AppCompatActivity implements OnClickListener, MultiItemTypeAdapter.OnItemClickListener {
    private Button chongzhi_submit;
    private EditText chongzhi_edit;
    private TextView textView1;
    private LinearLayout yu_pay0, yu_pay1, yu_pay2;
    private CheckBox yu_pay_c0, yu_pay_c1, yu_pay_c2;
    private IWXAPI api;
    private WareDao wareDao;
    private SharedPreferences spPreferences;
    String user_name, user_id, pwd;
    String payment_id; //2 3 5微信
    public static String recharge_no, notify_url, return_url;
    private ImageView iv_fanhui;
    private DialogProgress progress;
    String pety;
    boolean flag = false;
    private WxSignData mSignData;
    private RecyclerView recyclerView;

    @Override
    protected void onResume() {
        super.onResume();
        if (flag == true) {
            userloginqm();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yanglao_chongzhi);
        iv_fanhui = (ImageView) findViewById(R.id.action_back);
        iv_fanhui.setOnClickListener(this);
        textView1 = (TextView) findViewById(R.id.action_bar_title);
        textView1.setText("钱包充值");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        api = WXAPIFactory.createWXAPI(MonneyChongZhiActivity.this, null);
        api.registerApp(Constants.APP_ID);
        wareDao = new WareDao(getApplicationContext());
        progress = new DialogProgress(this);
        spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        user_name = spPreferences.getString(SpConstants.USER_NAME, "");
        user_id = spPreferences.getString("user_id", "");
        pwd = spPreferences.getString("pwd", "");

        chongzhi_edit = (EditText) findViewById(R.id.chongzhi_edit);
        chongzhi_submit = (Button) findViewById(R.id.chongzhi_submit);
        yu_pay0 = (LinearLayout) findViewById(R.id.yu_pay0);
        yu_pay1 = (LinearLayout) findViewById(R.id.yu_pay1);
        yu_pay2 = (LinearLayout) findViewById(R.id.yu_pay2);
        yu_pay_c0 = (CheckBox) findViewById(R.id.yu_pay_c0);
        yu_pay_c1 = (CheckBox) findViewById(R.id.yu_pay_c1);
        yu_pay_c2 = (CheckBox) findViewById(R.id.yu_pay_c2);
        chongzhi_edit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        initData();


        /**
         * 微信支付
         */
        yu_pay0.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (yu_pay_c1.isChecked()) {
                    yu_pay_c1.setChecked(false);
                } else if (yu_pay_c2.isChecked()) {
                    yu_pay_c2.setChecked(false);
                }
                yu_pay_c0.setChecked(true);
            }
        });
        yu_pay_c0.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (yu_pay_c1.isChecked()) {
                    //点击设置是否为点击状态
                    yu_pay_c1.setChecked(false);
                } else if (yu_pay_c2.isChecked()) {
                    yu_pay_c2.setChecked(false);
                }
                yu_pay_c0.setChecked(true);
            }
        });
        /**
         * 支付宝支付
         */
        yu_pay_c1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (yu_pay_c0.isChecked()) {
                    yu_pay_c0.setChecked(false);
                } else if (yu_pay_c2.isChecked()) {
                    yu_pay_c2.setChecked(false);
                }
                yu_pay_c1.setChecked(true);
            }
        });
        yu_pay1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (yu_pay_c0.isChecked()) {
                    yu_pay_c0.setChecked(false);
                } else if (yu_pay_c2.isChecked()) {
                    yu_pay_c2.setChecked(false);
                }
                yu_pay_c1.setChecked(true);
            }
        });
        /**
         * 余额支付
         */
        yu_pay_c2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (yu_pay_c0.isChecked()) {
                    yu_pay_c0.setChecked(false);
                } else if (yu_pay_c1.isChecked()) {
                    yu_pay_c1.setChecked(false);
                }
                yu_pay_c2.setChecked(true);
            }
        });
        yu_pay2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (yu_pay_c0.isChecked()) {
                    yu_pay_c0.setChecked(false);
                } else if (yu_pay_c1.isChecked()) {
                    yu_pay_c1.setChecked(false);
                }
                yu_pay_c2.setChecked(true);
            }
        });

        chongzhi_submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String yue = chongzhi_edit.getText().toString();
                try {
                    Double.parseDouble(yue);
                    double monney = Double.parseDouble(yue);

                    if (yu_pay_c0.isChecked()) {
                        payment_id = "5";
                        loadweixinzf1(payment_id);
                    } else if (yu_pay_c1.isChecked()) {
                        payment_id = "3";
                        loadguanggao(payment_id);
                    } else if (yu_pay_c2.isChecked()) {
                        payment_id = "2";
                        loadguanggao(payment_id);
                    } else {
                        Toast.makeText(getApplicationContext(), "请选择支付方式", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "请输入金额", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void initData() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ArrayList<RechargeModel> datas = new ArrayList<>();
        datas.add(new RechargeModel("1000", true));
        datas.add(new RechargeModel("2000", false));
        datas.add(new RechargeModel("3000", false));
        datas.add(new RechargeModel("4000", false));
        RechargeMoneyAdapter adapter = new RechargeMoneyAdapter(this, R.layout.recharge_item, datas);
        recyclerView.setAdapter(adapter);
        chongzhi_edit.setText("1000");
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fanhui:
                finish();
                break;
            default:
                break;
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
                            String login_sign = data.login_sign;
                            loadguanggaoll(recharge_no, login_sign);
                        } else {
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }

                ;
            }, MonneyChongZhiActivity.this);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 更新支付宝支付
     *
     * @param login_sign
     * @param
     */
    private void loadguanggaoll(String recharge_noll, String login_sign) {
        try {
            recharge_no = recharge_noll;
            AsyncHttp.get(URLConstants.REALM_NAME_LL + "/update_user_amount?user_id=" + user_id + "&user_name=" + user_name + "" +
                            "&recharge_no=" + recharge_noll + "&sign=" + login_sign + "",

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
                                    Toast.makeText(MonneyChongZhiActivity.this, info, Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    progress.CloseProgress();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {
                            super.onFailure(arg0, arg1);
                            Toast.makeText(MonneyChongZhiActivity.this, "网络超时异常", Toast.LENGTH_SHORT).show();
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 充值单生成
     *
     * @param payment_id
     */
    private void loadguanggao(String payment_id) {
        try {
            progress.CreateProgress();
            String amount = chongzhi_edit.getText().toString();
            System.out.println("===============" + amount);
            pety = payment_id;
            AsyncHttp.get(URLConstants.REALM_NAME_LL
                            + "/add_amount_recharge?user_id=" + user_id + "&user_name=" + user_name + "" +
                            "&amount=" + amount + "&fund_id=1&payment_id=" + payment_id + "&rebate_item_id=0",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject object = new JSONObject(arg1);
                                System.out.println("1=================================" + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    JSONObject obj = object.getJSONObject("data");
                                    AdvertDao1 data = new AdvertDao1();
                                    data.recharge_no = obj.getString("recharge_no");
                                    recharge_no = data.recharge_no;
                                    progress.CloseProgress();
                                    if (pety.equals("2")) {
                                        loadYue(recharge_no);
                                    } else if (pety.equals("3")) {
                                        loadzhidu(recharge_no);
                                    }
                                } else {
                                    progress.CloseProgress();
                                    Toast.makeText(MonneyChongZhiActivity.this, info, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, getApplicationContext());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    /**
     * 用户在线充值  支付宝
     *
     * @param
     */
    private void loadzhidu(String recharge_no) {
        try {

            String amount = chongzhi_edit.getText().toString().trim();
            //		String amount = "0.01";

            AsyncHttp.get(URLConstants.REALM_NAME_LL
                            + "/payment_sign?user_id=" + user_id + "&user_name=" + user_name + "" +
                            "&total_fee=" + amount + "&out_trade_no=" + recharge_no + "&payment_type=alipay",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject object = new JSONObject(arg1);
                                System.out.println("2=================================" + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    JSONObject obj = object.getJSONObject("data");
                                    PayProxy.NOTIFY_URL = notify_url = obj.getString("notify_url");
                                    return_url = obj.getString("return_url");
                                    System.out.println("return_url=================================" + return_url);
                                    progress.CloseProgress();
                                    handler.sendEmptyMessage(1);
                                } else {
                                    progress.CloseProgress();
                                    Toast.makeText(MonneyChongZhiActivity.this, info, Toast.LENGTH_SHORT).show();
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
     * 用户在线充值  余额支付
     *
     * @param
     */
    private void loadYue(String recharge_no) {
        try {

            AsyncHttp.get(URLConstants.REALM_NAME_LL
                            + "/payment_balance?user_id=" + user_id + "&user_name=" + user_name + "" +
                            "&order_no=" + recharge_no + "&paypassword=" + pwd + "",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject object = new JSONObject(arg1);
                                System.out.println("2=================================" + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    progress.CloseProgress();
                                    userloginqm();
                                } else {
                                    progress.CloseProgress();
                                    Toast.makeText(MonneyChongZhiActivity.this, info, Toast.LENGTH_SHORT).show();
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
     * 生成订单    微信支付1
     *
     * @param payment_id
     */
    private void loadweixinzf1(String payment_id) {
        try {
            progress.CreateProgress();
            String amount = chongzhi_edit.getText().toString();
            String monney = String.valueOf(Double.parseDouble(amount) * 100);
            System.out.println("0===============" + amount);

            AsyncHttp.get(URLConstants.REALM_NAME_LL
                            + "/add_amount_recharge?user_id=" + user_id + "&user_name=" + user_name + "" +
                            "&amount=" + amount + "&fund_id=1&payment_id=" + payment_id + "&rebate_item_id=0",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject object = new JSONObject(arg1);
                                System.out.println("0=================================" + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    JSONObject obj = object.getJSONObject("data");
                                    AdvertDao1 data = new AdvertDao1();
                                    data.recharge_no = obj.getString("recharge_no");
                                    recharge_no = data.recharge_no;
                                    System.out.println("0=================================" + data.recharge_no);
                                    progress.CloseProgress();
                                    loadweixinzf3(recharge_no);
                                } else {
                                    progress.CloseProgress();
                                    Toast.makeText(MonneyChongZhiActivity.this, info, Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, getApplicationContext());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 用户在线充值    微信支付3
     *
     * @param
     */
    private void loadweixinzf3(String recharge_no) {
        try {
            //			String amount = "0.01";
            String amount = chongzhi_edit.getText().toString().trim();
            String monney = String.valueOf(Double.parseDouble(amount) * 100);
            int num1 = 2;
            int num2 = num1 * 100;
            System.out.println("===============" + amount);
            AsyncHttp.get(URLConstants.REALM_NAME_LL
                            + "/payment_sign?user_id=" + user_id + "&user_name=" + user_name + "" +
                            "&total_fee=" + monney + "&out_trade_no=" + recharge_no + "&payment_type=weixin",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject object = new JSONObject(arg1);
                                System.out.println("weixin=================================" + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    JSONObject jsonObject = object.getJSONObject("data");
                                    String json = jsonObject.toString();
                                    mSignData = JSON.parseObject(json, WxSignData.class);
                                    progress.CloseProgress();
                                    handler.sendEmptyMessage(2);
                                } else {
                                    progress.CloseProgress();
                                    Toast.makeText(MonneyChongZhiActivity.this, info, Toast.LENGTH_SHORT).show();
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


    private void ali_pay() {
        String bizContent = "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\""
                + chongzhi_edit.getText().toString() + "\",\"subject\":\"充值\",\"body\":\"描述\",\"out_trade_no\":\"" + recharge_no + "\"}";
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(PayProxy.APPID, true, bizContent);
        PayProxy.payV2(this, handler, params);
    }

    private Handler handler = new Handler() {
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ali_pay();
                    break;
                case 2://微信支付
                    boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                    if (isPaySupported) {
                        api.registerApp(mSignData.getApp_id());
                        PayReq req = new PayReq();
                        req.appId = mSignData.getApp_id();
                        req.partnerId = mSignData.getMch_id();//商户id
                        req.prepayId = mSignData.getPrepay_id();// 7 预支付交易会话ID
                        req.nonceStr = mSignData.getNonce_str();// 3
                        req.timeStamp = mSignData.getTimestamp();// -1
                        req.packageValue = "Sign=WXPay"; //扩展字段
                        req.sign = mSignData.getSign();// -3
                        //3.调用微信支付sdk支付方法
                        flag = api.sendReq(req);
                    } else {

                    }

                    break;
                case PayProxy.SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(MonneyChongZhiActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        userloginqm();
                    } else {
                        //                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        Toast.makeText(MonneyChongZhiActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (adapter instanceof RechargeMoneyAdapter) {
            List<RechargeModel> datas = ((RechargeMoneyAdapter) adapter).getDatas();
            if (datas != null && datas.size() > position) {
                RechargeModel model = datas.get(position);
                chongzhi_edit.setText(model.getMoney());
                ((RechargeMoneyAdapter) adapter).setSelected(position);
            }
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
