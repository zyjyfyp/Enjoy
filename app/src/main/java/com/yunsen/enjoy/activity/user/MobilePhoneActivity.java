package com.yunsen.enjoy.activity.user;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.model.response.UserInfoResponse;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.SpUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.utils.Utils;
import com.yunsen.enjoy.utils.Validator;
import com.yunsen.enjoy.widget.DialogProgress;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 绑定手机
 */
public class MobilePhoneActivity extends AppCompatActivity implements OnClickListener {

    private EditText userpwd, userphone, et_user_yz;
    private Button btn_register, get_yz;
    private String name, phone, pwd, yz;
    private String str, hengyuName;
    private DialogProgress progress;
    private String strUrl;
    private String yanzhengma;
    String headimgurl2 = "";
    private String oauth_name = "";
    String headimgurl, access_token, sex, unionid;
    String province = "";
    String city = "";
    String country = "";
    String nickname = "";
    private SharedPreferences mSp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mobile_phone);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initdata();
        mSp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        TextView img_menu = (TextView) findViewById(R.id.iv_fanhui);
        img_menu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

    }

    Handler handler = new Handler() {

        public void dispatchMessage(android.os.Message msg) {

            switch (msg.what) {
                case 0:
                    String strhengyuname = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), strhengyuname, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new UpUiEvent(EventConstants.APP_LOGIN));
                    progress.CloseProgress();
                    finish();
                    break;
                case 1:
                    String strmsg = (String) msg.obj;
                    Toast
                            .makeText(getApplicationContext(), strmsg, Toast.LENGTH_SHORT)
                            .show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "验证码已发送", Toast.LENGTH_SHORT).show();
                    new Thread() {
                        public void run() {
                            for (int i = 120; i >= 0; i--) {
                                if (i == 0) {
                                    handler.sendEmptyMessage(4);
                                } else {
                                    Message msg = new Message();
                                    msg.arg1 = i;
                                    msg.what = 5;
                                    handler.sendMessage(msg);

                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {

                                        e.printStackTrace();
                                    }

                                }
                            }
                        }

                        ;
                    }.start();
                    break;
                case 3:

                    Toast.makeText(getApplicationContext(), "验证码已下发", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    get_yz.setEnabled(true);
                    get_yz.setText("获取验证码");
                    break;
                case 5:
                    get_yz.setEnabled(false);
                    get_yz.setText(msg.arg1 + "s");
                    break;
                default:
                    break;
            }
        }

        ;

    };
    private static final String TAG = "MobilePhoneActivity";

    private void initdata() {
        et_user_yz = (EditText) findViewById(R.id.et_user_yz);
        userpwd = (EditText) findViewById(R.id.et_user_pwd);
        get_yz = (Button) findViewById(R.id.get_yz);
        userphone = (EditText) findViewById(R.id.et_user_phone);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        get_yz.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_yz:
                phone = userphone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.makeTextShort("手机号码不能为空");
                } else if (phone.length() < 11) {
                    ToastUtils.makeTextShort("手机号码少于11位");
                } else {
                    if (Validator.isMobile(phone)) {
                        strUrl = URLConstants.REALM_NAME_LL + "/user_oauth_smscode?mobile=" + phone + "";
                        AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int arg0, String arg1) {
                                super.onSuccess(arg0, arg1);
                                System.out.println("=============" + arg1);
                                try {
                                    JSONObject object = new JSONObject(arg1);
                                    String result = object.getString("status");//
                                    String info = object.getString("info");// info
                                    if ("y".equals(result)) {
                                        yanzhengma = object.getString("data");
                                        handler.sendEmptyMessage(2);
                                    } else {
                                        Toast.makeText(MobilePhoneActivity.this, info, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, getApplicationContext());
                    } else {
                        Toast.makeText(MobilePhoneActivity.this, "手机号码不正确", Toast.LENGTH_SHORT)
                                .show();
                    }
                }

                break;
            case R.id.btn_register:
                yz = et_user_yz.getText().toString().trim();
                phone = userphone.getText().toString().trim();
                pwd = userpwd.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(MobilePhoneActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT)
                            .show();
                } else if (phone.length() < 11) {
                    Toast.makeText(MobilePhoneActivity.this, "手机号码少于11位", Toast.LENGTH_SHORT)
                            .show();
                } else if ("".equals(yz)) {
                    Toast.makeText(MobilePhoneActivity.this, "验证码不能为空", Toast.LENGTH_SHORT)
                            .show();
                } else if ("".equals(pwd)) {
                    Toast.makeText(MobilePhoneActivity.this, "密码不能为空", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    progress = new DialogProgress(MobilePhoneActivity.this);
                    progress.CreateProgress();
                    new Thread() {
                        public void run() {
                            headimgurl2 = mSp.getString("headimgurl2", "");
                            nickname = mSp.getString("nickname", "");
                            unionid = mSp.getString("unionid", "");
                            access_token = mSp.getString("access_token", "");
                            sex = mSp.getString("sex", "");
                            String oauth_openid = mSp.getString("oauth_openid", "");
                            province = getIntent().getStringExtra("province");
                            city = getIntent().getStringExtra("city");
                            country = getIntent().getStringExtra("area");
                            if (province == null) {
                                province = "";
                            }
                            if (city == null) {
                                city = "";
                            }
                            if (country == null) {
                                country = "";
                            }

                            if (!"".equals(headimgurl2)) {
                                if (headimgurl2.startsWith("http")) {
                                    Log.e(TAG, "run: headimgurl2=" + headimgurl2);
                                } else {
                                    Bitmap bitmap = Utils.stringtoBitmap(headimgurl2);
                                    headimgurl = Utils.savePhoto(bitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));
                                }
                            } else {
                                headimgurl = mSp.getString("headimgurl", "");
                            }

                            if ("1".equals(sex)) {
                                sex = "男";
                            } else {
                                sex = "女";
                            }

                            oauth_name = mSp.getString(SpConstants.OAUTH_NAME, "");
                            String strUrl = URLConstants.REALM_NAME_LL
                                    + "/user_oauth_bind_0217?mobile="
                                    + phone + "&password=" + pwd
                                    + "&code=" + yz + "&nick_name="
                                    + nickname + "" + "&sex=" + sex
                                    + "&avatar=" + headimgurl
                                    + "&province=" + province
                                    + "&city=" + city + "&country="
                                    + country + "&oauth_name="
                                    + oauth_name + ""
                                    + "&oauth_unionid=" + unionid
                                    + "&oauth_openid=" + oauth_openid
                                    + "";
                            Log.e(TAG, "注册" + strUrl);

                            AsyncHttp.get(strUrl,
                                    new AsyncHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int arg0, String arg1) {
                                            super.onSuccess(arg0, arg1);
                                            try {
                                                JSONObject jsonObject = new JSONObject(arg1);
                                                String status = jsonObject.getString("status");
                                                if ("n".equals(status)) {
                                                    str = jsonObject.getString("info");
                                                    progress.CloseProgress();
                                                    Message message = new Message();
                                                    message.what = 1;
                                                    message.obj = str;
                                                    handler.sendMessage(message);
                                                } else if ("y".equals(status)) {
                                                    UserInfoResponse userInfoResponse = JSON.parseObject(arg1, UserInfoResponse.class);
                                                    SpUtils.saveUserInfo(userInfoResponse.getData());
                                                    progress.CloseProgress();
                                                    Message message = new Message();
                                                    message.what = 0;
                                                    message.obj = hengyuName;
                                                    handler.sendMessage(message);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Throwable arg0, String arg1) {
                                            super.onFailure(arg0, arg1);
                                        }
                                    }, getApplicationContext());
                        }
                    }.start();
                }

                break;

            default:
                break;
        }
    }

}
