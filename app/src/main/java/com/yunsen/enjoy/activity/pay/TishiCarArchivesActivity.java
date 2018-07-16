package com.yunsen.enjoy.activity.pay;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.DialogProgress;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 钱包支付（输入密码）
 *
 * @author
 */
public class TishiCarArchivesActivity extends Activity implements OnClickListener {
    private TextView btnConfirm;//
    private TextView btnCancle, tv_yue;//
    public Activity mContext;
    String user_name, user_id, pwd;
    private EditText zhidupess;
    private DialogProgress progress;
    private SharedPreferences spPreferences;
    String login_sign, amount;
    public static String yue_zhuangtai;
    public static String order_type = "0";
    public static String province, city, area, user_address, accept_name, user_mobile;
    public static String recharge_no, order_no, datetime, sell_price, give_pension, article_id;
    public static String huodong_type = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tishi_carxing);
        spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        user_name = spPreferences.getString(SpConstants.USER_NAME, "");
        user_id = spPreferences.getString("user_id", "");
        progress = new DialogProgress(TishiCarArchivesActivity.this);
        order_no = getIntent().getStringExtra("order_no");
        useryue();
        initUI();
    }


    protected void initUI() {
        zhidupess = (EditText) findViewById(R.id.et_user_pwd);
        btnConfirm = (TextView) findViewById(R.id.btnConfirm);//
        btnCancle = (TextView) findViewById(R.id.btnCancle);//
        btnCancle.setOnClickListener(this);//
        btnConfirm.setOnClickListener(this);//
    }


    /**
     * 点击触发事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm://取消
                yue_zhuangtai = "1";
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.btnCancle://
                pwd = zhidupess.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(TishiCarArchivesActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    String yue = getIntent().getStringExtra("yue");
                    String jubi = getIntent().getStringExtra("jubi");
                    String order_yue = getIntent().getStringExtra("order_yue");
                    if (yue != null) {
                        userloginqm();
                    } else if (jubi != null) {
                        userloginqm();
                    } else if (order_yue != null) {
                        userloginqm();
                    } else {
                        ShouhuoOK(order_no);
                    }
                }
                break;

            default:
                break;
        }
    }

    /**
     * 判断当前聚币与余额的值
     *
     * @param
     */
    private void useryue() {
        String strUrlone = URLConstants.REALM_NAME_LL + "/get_user_model?username=" + user_name + "";
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                try {
                    System.out.println("当前聚币与余额的值-------------" + arg1);
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    if (status.equals("y")) {
                        JSONObject obj = object.getJSONObject("data");
                        amount = obj.getString("amount");
                        String cardMoney = obj.getString("card");
                        String point = obj.getString("point");
                        String jubi = getIntent().getStringExtra("jubi");
                        String title = getIntent().getStringExtra("title");
                        boolean isCard = getIntent().getBooleanExtra(Constants.IS_CARD_MONEY, false);

                        tv_yue = (TextView) findViewById(R.id.tv_yue);
                        if (title != null) {
                            tv_yue.setText("提示");
                        } else {
                            if (isCard) {
                                tv_yue.setText("您的剩余储值卡为¥" + cardMoney);
                            }else {
                                tv_yue.setText("您剩余的余额为¥" + amount);
                            }
                        }
                    } else {
                    }
                } catch (
                        JSONException e)

                {

                    e.printStackTrace();
                }
            }

            ;
        }, null);

    }

    /**
     * 获取登录签名
     *
     * @param
     */
    private void userloginqm() {
        try {
            String strUrlone = URLConstants.REALM_NAME_LL + "/get_user_model?username=" + user_name + "";
            AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
                public void onSuccess(int arg0, String arg1) {
                    try {
                        JSONObject object = new JSONObject(arg1);
                        String status = object.getString("status");
                        if (status.equals("y")) {
                            JSONObject obj = object.getJSONObject("data");
                            UserRegisterllData data = new UserRegisterllData();
                            data.login_sign = obj.getString("login_sign");
                            amount = obj.getString("amount");
                            login_sign = data.login_sign;
                            loadYue(order_no, login_sign);
                        } else {
                            ToastUtils.makeTextShort("支付异常");
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }

                ;
            }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 余额支付
     *
     * @param login_sign
     * @param
     */
    private void loadYue(final String order_no, String login_sign) {
        try {
            AsyncHttp.get(URLConstants.REALM_NAME_HTTP + "/api/payment/balance/index.aspx?action=payment&user_id=" + user_id + "&user_name=" + user_name + "" +
                            "&user_sign=" + login_sign + "&paypassword=" + pwd + "&trade_no=" + order_no + "",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            try {
                                JSONObject object = new JSONObject(arg1);
                                System.out.println("2=================================" + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    progress.CloseProgress();
                                    ToastUtils.makeTextShort("支付成功");
                                    EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                                    setResult(1);
                                    finish();
                                } else {
                                    progress.CloseProgress();
                                    Toast.makeText(TishiCarArchivesActivity.this, info, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {
                            super.onFailure(arg0, arg1);
                            Toast.makeText(TishiCarArchivesActivity.this, "异常", Toast.LENGTH_SHORT).show();
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 确认收货
     *
     * @param order_no
     * @param
     */
    public void ShouhuoOK(String order_no) {
        progress.CreateProgress();
        AsyncHttp.get(URLConstants.REALM_NAME_LL
                        + "/update_order_complete?user_id=" + user_id + "&user_name=" + user_name + "" +
                        "&trade_no=" + order_no + "&paypassword=" + pwd + "",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject object = new JSONObject(arg1);
                            System.out.println("arg1================================" + arg1);
                            String status = object.getString("status");
                            String info = object.getString("info");
                            if (status.equals("y")) {
                                progress.CloseProgress();
                                //									  order_type = getIntent().getStringExtra("order_type");
                                order_type = "2";//确认收货状态
                                System.out.println("order_type=============2====================" + order_type);
                                Toast.makeText(TishiCarArchivesActivity.this, "确认订单完成", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                progress.CloseProgress();
                                Toast.makeText(TishiCarArchivesActivity.this, info, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, TishiCarArchivesActivity.this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            setResult(RESULT_OK);
        }
        return super.onKeyDown(keyCode, event);
    }
}