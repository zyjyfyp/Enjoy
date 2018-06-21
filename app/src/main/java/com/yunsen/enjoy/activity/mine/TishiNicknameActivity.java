package com.yunsen.enjoy.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.utils.Validator;
import com.yunsen.enjoy.widget.DialogProgress;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import okhttp3.Request;

/**
 * 修改性别 ,修改邮箱地址 type==2
 *
 * @author
 */
public class TishiNicknameActivity extends Activity implements OnClickListener {
    private TextView btnConfirm;//
    private TextView btnCancle;//
    public Activity mContext;
    String user_name, user_id, nichen;
    private EditText zhidupess;
    private DialogProgress progress;
    private SharedPreferences spPreferences;
    String login_sign;
    private int mActType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tishi_gender);
        spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        user_name = spPreferences.getString(SpConstants.USER_NAME, "");
        user_id = spPreferences.getString(SpConstants.USER_ID, "");
        progress = new DialogProgress(TishiNicknameActivity.this);
        initUI();
    }


    protected void initUI() {
        Intent intent = getIntent();
        mActType = intent.getIntExtra(Constants.ACT_TYPE_KEY, 0);

        zhidupess = (EditText) findViewById(R.id.et_user_pwd);
        btnConfirm = (TextView) findViewById(R.id.btnConfirm);//
        btnConfirm.setOnClickListener(this);//
        btnCancle = (TextView) findViewById(R.id.btnCancle);//
        btnCancle.setOnClickListener(this);//
        if (Constants.EMIL == mActType) {
            zhidupess.setHint("请输入你的邮箱！");
        } else if (Constants.ONLINE_QQ == mActType) {
            zhidupess.setHint("请输入你的QQ!");
        }
    }


    /**
     * 点击触发事件
     */
    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btnConfirm://取消
                finish();
                break;
            case R.id.btnCancle://
                nichen = zhidupess.getText().toString().trim();
                System.out.println("nichen-------------" + nichen);
                if (Constants.EMIL == mActType) {
                    if (TextUtils.isEmpty(nichen)) {
                        Toast.makeText(TishiNicknameActivity.this, "请输入修改的邮箱", Toast.LENGTH_SHORT).show();
                    } else if (!Validator.isEmail(nichen)) {
                        Toast.makeText(TishiNicknameActivity.this, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
                    } else {
                        userloginqm();
                    }
                } else if (Constants.ONLINE_QQ == mActType) {
                    if (TextUtils.isEmpty(nichen)) {
                        Toast.makeText(TishiNicknameActivity.this, "请输入修改的QQ", Toast.LENGTH_SHORT).show();
                    } else {
                        userloginqm();
                    }
                } else {
                    if (TextUtils.isEmpty(nichen)) {
                        Toast.makeText(TishiNicknameActivity.this, "请输入修改的昵称", Toast.LENGTH_SHORT).show();
                    } else {
                        userloginqm();
                    }
                }
                break;

            default:
                break;
        }
    }

    /**
     * 获取登录签名
     *
     * @param
     */
    private void userloginqm() {
        progress.CreateProgress();
        String strUrlone = URLConstants.REALM_ACCOUNT_URL + "/get_user_model?username=" + user_name + "";
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                try {
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    if (status.equals("y")) {
                        JSONObject obj = object.getJSONObject("data");
                        UserRegisterllData data = new UserRegisterllData();
                        data.login_sign = obj.getString("login_sign");
                        login_sign = data.login_sign;
                        if (mActType == Constants.EMIL) {
                            changeEmil();
                        } else if (mActType == Constants.ONLINE_QQ) {
                            changeQQ();
                        } else {
                            loadusersex(login_sign);
                        }
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                progress.CloseProgress();
            }
        }, null);
    }

    /**
     * 改变QQ号码
     */
    private void changeQQ() {
        HttpProxy.changeQQData(user_id, user_name, login_sign, nichen, new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean responseData) {
                if (responseData) {
                    Toast.makeText(TishiNicknameActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(TishiNicknameActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    finish();
                }
                progress.CloseProgress();
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    /**
     * 改变邮箱
     */
    private void changeEmil() {
        HttpProxy.changeEmilData(user_id, user_name, login_sign, nichen, new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean responseData) {
                if (responseData) {
                    Toast.makeText(TishiNicknameActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(TishiNicknameActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    finish();
                }
                progress.CloseProgress();
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    /**
     * 修改昵称
     *
     * @param login_sign
     */
    private void loadusersex(String login_sign) {
        try {
            AsyncHttp.get(URLConstants.REALM_ACCOUNT_URL
                            + "/user_update_field?user_id=" + user_id + "&user_name=" + user_name + "" +
                            "&field=nick_name&value=" + nichen + "&sign=" + login_sign + "",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            try {
                                JSONObject object = new JSONObject(arg1);
                                System.out.println("2=================================" + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    finish();
                                } else {
                                    Toast.makeText(TishiNicknameActivity.this, info, Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFinish() {
                            progress.CloseProgress();
                        }
                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}