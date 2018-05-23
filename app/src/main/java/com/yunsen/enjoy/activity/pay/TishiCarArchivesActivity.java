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
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.DialogProgress;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Request;

/**
 * 钱包支付（输入密码）
 *
 * @author
 */
public class TishiCarArchivesActivity extends BaseFragmentActivity implements OnClickListener {
    private TextView btnConfirm;//
    private TextView btnCancle, tv_yue;//
    String user_name, user_id, pwd;
    private EditText zhidupess;
    private DialogProgress progress;
    private SharedPreferences spPreferences;
    String login_sign, amount;
    public static String order_type = "0";
    public static String province, city, area, user_address, accept_name, user_mobile;
    public static String recharge_no, datetime, sell_price, article_id;
    private String mOrderNo;

    @Override
    public int getLayout() {
        return R.layout.activity_tishi_carxing;
    }

    @Override
    protected void initView() {
        spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        user_name = spPreferences.getString(SpConstants.USER_NAME, "");
        user_id = spPreferences.getString(SpConstants.USER_ID, "");
        progress = new DialogProgress(TishiCarArchivesActivity.this);
        mOrderNo = getIntent().getStringExtra("order_no");
        useryue();
        initUI();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }


    protected void initUI() {
        zhidupess = (EditText) findViewById(R.id.et_user_pwd);
        btnConfirm = (TextView) findViewById(R.id.btnConfirm);//
        btnConfirm.setOnClickListener(this);//
        btnCancle = (TextView) findViewById(R.id.btnCancle);//
        btnCancle.setOnClickListener(this);//
    }


    /**
     * 点击触发事件
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnConfirm://取消
                finishToOrderActivity();
                break;
            case R.id.btnCancle://
                pwd = zhidupess.getText().toString().trim();
                if (pwd.equals("")) {
                    Toast.makeText(TishiCarArchivesActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    String yue = getIntent().getStringExtra("yue");
                    String jubi = getIntent().getStringExtra("jubi");
                    String order_yue = getIntent().getStringExtra("order_yue");
                    if (yue != null) {
//                        userloginqm();
                        loadYue(mOrderNo, null);
                    } else if (jubi != null) {
//                        userloginqm();
                        loadYue(mOrderNo, null);

                    } else if (order_yue != null) {
//                        userloginqm();
                        loadYue(mOrderNo, null);
                    } else {
                        ShouhuoOK(mOrderNo);
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
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    if (status.equals("y")) {
                        JSONObject obj = object.getJSONObject("data");
                        amount = obj.getString("amount");
                        String point = obj.getString("point");
                        String jubi = getIntent().getStringExtra("jubi");
                        String title = getIntent().getStringExtra("title");
                        tv_yue = (TextView) findViewById(R.id.tv_yue);
                        if (title != null) {
                            tv_yue.setText("提示");
                        } else {
                            if (jubi != null) {
                                tv_yue.setText("您剩余的福利为￥" + point);
                            } else {
                                tv_yue.setText("您剩余的余额为￥" + amount);
                            }
                        }
                    } else {
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            ;
        }, null);

    }

    /**
     * 获取登录签名
     * todo 登录签名是否可用去掉？
     *
     * @param
     */
    private void userloginqm() {
        String strUrlone = URLConstants.REALM_NAME_LL + "/get_user_model?username=" + user_name + "";
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                try {
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    if ("y".equals(status)) {
                        JSONObject obj = object.getJSONObject("data");
                        login_sign = obj.getString("login_sign");
                        amount = obj.getString("amount");
                        loadYue(mOrderNo, login_sign);
                    } else {
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            ;
        }, null);


    }

    /**
     * 余额支付
     *
     * @param login_sign
     * @param
     */
    private void loadYue(String order_no, String login_sign) {
        HttpProxy.getBalanceBuyGoods(pwd, order_no, new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean responseData) {
                ToastUtils.makeTextShort("支付成功");
                finishTishiActivity();
            }

            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.makeTextShort("支付失败");
                finishToOrderActivity();
            }
        });
    }

    private void finishTishiActivity() {
        UIHelper.showMyOrderXqActivity(TishiCarArchivesActivity.this, mOrderNo);
        setResult(RESULT_OK);
        finish();
    }

    private void finishToOrderActivity(){
        UIHelper.showOrderActivity(this,"1");
        setResult(RESULT_OK);
        finish();
    }

    /**
     * 确认收货
     *
     * @param order_no
     * @param
     */
    public void ShouhuoOK(String order_no) {
        progress.CreateProgress();
        System.out.println("order_no=================================" + order_no);
        System.out.println("login_sign=================================" + pwd);
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
            UIHelper.showOrderActivity(this,"1");
            setResult(RESULT_OK);
        }
        return super.onKeyDown(keyCode, event);
    }
}