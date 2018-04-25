package com.yunsen.enjoy.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.Utils;
import com.yunsen.enjoy.widget.DialogProgress;

import org.json.JSONException;
import org.json.JSONObject;

public class UserLoginWayActivity extends AppCompatActivity implements
        OnClickListener {
    private DialogProgress progress;
    public static String kahao;
    private String nickname, sex, province, city, country;
    // private SharedPreferences spPreferences_qq;
    private SharedPreferences spPreferences_login;
    public static boolean isWXLogin = false;
    public static IWXAPI mWxApi;
    public static String WX_CODE = "";
    public static String mAppid;
    public static QQAuth mQQAuth;
    private UserInfo mInfo;
    private Tencent mTencent;
    private final String APP_ID = "1105738127";
    // private final String APP_ID = "222222";// 测试时使用，真正发布的时候要换成自己的APP_ID
    public static Bitmap bitmap;
    public static String oauth_name = "";
    public static boolean panduan = false;
    public static boolean panduan_tishi = false;
    public static boolean jiemian = false;
    public static Handler handler1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user_login_way);
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        mWxApi.registerApp(Constants.APP_ID);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // spPreferences_qq = getSharedPreferences("longuserset_qq",
        // MODE_PRIVATE);
        spPreferences_login = getSharedPreferences("longuserset_login",
                MODE_PRIVATE);
        try {
            jiemian = true;// 判断界面是否打开

            progress = new DialogProgress(UserLoginWayActivity.this);

            initdata();
            handler1 = new Handler() {
                public void dispatchMessage(Message msg) {
                    switch (msg.what) {
                        case 0:
                            break;
                        case 1:
                            finish();
                            break;

                        default:
                            break;
                    }
                }
            };
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        // SharedPreferences spPreferences = getSharedPreferences("longuserset",
        // MODE_PRIVATE);
        // user_name = spPreferences.getString("user_name", "");
        // if (nickname == null && bitmap == null) {
        // panduan = false;
        // finish();
        // }

    }

    @Override
    protected void onStart() {
        final Context context = UserLoginWayActivity.this;
        final Context ctxContext = context.getApplicationContext();
        mAppid = APP_ID;
        mQQAuth = QQAuth.createInstance(mAppid, ctxContext);
        mTencent = Tencent.createInstance(mAppid, UserLoginWayActivity.this);
        super.onStart();
    }

    Handler handler = new Handler() {

        public void dispatchMessage(android.os.Message msg) {

            switch (msg.what) {
                case 0:
                    break;
                case -1:
                    progress.CloseProgress();
                    break;
                case 10:

                    break;
                case 1:
                    String str = (String) msg.obj;
                    Toast.makeText(UserLoginWayActivity.this, str, Toast.LENGTH_SHORT).show();
                    break;
                case 7:

                    break;
                default:
                    break;
            }

        }

        ;

    };

    private void initdata() {
        TextView item0 = (TextView) findViewById(R.id.item0);
        TextView item1 = (TextView) findViewById(R.id.item1);
        // TextView item2 = (TextView) findViewById(R.id.item2);
        TextView item4 = (TextView) findViewById(R.id.item4);
        item0.setOnClickListener(this);
        item1.setOnClickListener(this);
        item4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.item0://
                Intent intent3 = new Intent(UserLoginWayActivity.this,
                        PhoneLoginActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.item1://
                try {
                    onClickLogin();
                    // Intent intent = new
                    // Intent(UserLoginActivity.this,QQLoginActivity.class);
                    // startActivity(intent);

                } catch (Exception e) {

                    e.printStackTrace();
                }
                break;
            case R.id.item4://
                finish();
                break;
            default:
                break;
        }
    }

    private void onClickLogin() {
        try {
            SharedPreferences spPreferences_tishi = getSharedPreferences(
                    "longuserset_tishi", MODE_PRIVATE);
            String weixin = spPreferences_tishi.getString("weixin", "");
            if (!weixin.equals("")) {
                spPreferences_tishi.edit().clear().commit();
                UserLoginActivity.panduan_tishi = false;
            }
            System.out.println("=================weixin==" + weixin);
            oauth_name = "qq";
            if (!mQQAuth.isSessionValid()) {
                try {

                    IUiListener listener = new BaseUiListener() {
                        @Override
                        protected void doComplete(JSONObject values) {
                            updateUserInfo();
                        }
                    };
                    mQQAuth.login(this, "all", listener);
                    // mTencent.loginWithOEM(this,
                    // "all",listener,"10000144","10000144","xxxx");
                    mTencent.login(this, "all", listener);
                    // finish();
                } catch (Exception e) {

                    e.printStackTrace();
                }
            } else {
                mQQAuth.logout(this);
                updateUserInfo();
                // finish();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            System.out.println("response===============" + response);
            try {
                String access_token = ((JSONObject) response)
                        .getString("access_token");
                String openid = ((JSONObject) response).getString("openid");
                String ret = ((JSONObject) response).getString("ret");
                String oauth_openid = ((JSONObject) response)
                        .getString("openid");
                // System.out.println("access_token==============="+access_token);
                Editor editor = spPreferences_login.edit();
                editor.putString("access_token", access_token);
                editor.putString("unionid", openid);
                editor.putString("sex", ret);
                editor.putString("oauth_openid", oauth_openid);
                editor.commit();
            } catch (JSONException e) {

                e.printStackTrace();
            }
            // Util.showResultDialog(UserLoginActivity.this,
            // response.toString(),"登录成功");
            //
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            Toast.makeText(UserLoginWayActivity.this, "onError: "
                    + e.errorDetail, Toast.LENGTH_SHORT);
//			Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            // Util.toastMessage(UserLoginActivity.this, "用户取消");//onCancel:
//			Util.dismissDialog();
        }
    }

    private void updateUserInfo() {
        try {
            SharedPreferences spPreferences = getSharedPreferences(
                    "longuserset_ptye", MODE_PRIVATE);
            Editor editor = spPreferences.edit();
            editor.putString("ptye", "qq");
            editor.commit();
            panduan = true;
            panduan_tishi = true;

            if (mQQAuth != null && mQQAuth.isSessionValid()) {
                try {
                    IUiListener listener = new IUiListener() {
                        @Override
                        public void onError(UiError e) {

                        }

                        @Override
                        public void onComplete(final Object response) {
                            try {
                                // Message msg = new Message();
                                // msg.obj = response;
                                // msg.what = 0;
                                // mHandler.sendMessage(msg);
                                new Thread() {

                                    @Override
                                    public void run() {
                                        JSONObject json = (JSONObject) response;
                                        if (json.has("figureurl")) {
                                            bitmap = null;
                                            try {
                                                nickname = json
                                                        .getString("nickname");
                                                sex = json.getString("gender");
                                                province = json
                                                        .getString("province");
                                                city = json.getString("city");
                                                System.out
                                                        .println("nickname==========1====="
                                                                + nickname);

                                                bitmap = GetImgUtil.getImage(json.getString("figureurl_qq_2"));
                                                String headimgurl2 = Utils.bitmaptoString(bitmap);
                                                Editor editor = spPreferences_login
                                                        .edit();
                                                editor.putString("nickname",
                                                        nickname);
                                                editor.putString("headimgurl2",
                                                        headimgurl2);
                                                editor.putString("sex", sex);
                                                editor.putString("province",
                                                        province);
                                                editor.putString("city", city);
                                                editor.putString("country",
                                                        country);
                                                editor.commit();
                                                System.out
                                                        .println("bitmap==============="
                                                                + bitmap);

                                                // SharedPreferences
                                                // spPreferences_tishi =
                                                // getSharedPreferences("longuserset_tishi",
                                                // MODE_PRIVATE);
                                                // spPreferences_tishi.edit().clear().commit();

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            // Message msg = new Message();
                                            // msg.obj = bitmap;
                                            // msg.what = 1;
                                            // mHandler.sendMessage(msg);
                                            // finish();

                                            if (nickname != null
                                                    && bitmap != null) {
                                                // SharedPreferences
                                                // spPreferences_3_wx =
                                                // getSharedPreferences("longuserset_3_wx",
                                                // MODE_PRIVATE);
                                                // spPreferences_3_wx.edit().clear().commit();
                                                finish();
                                                UserLoginActivity.handler1
                                                        .sendEmptyMessage(1);
                                            } else {

                                                // SharedPreferences
                                                // spPreferences_3_wx =
                                                // getSharedPreferences("longuserset_3_wx",
                                                // MODE_PRIVATE);
                                                // spPreferences_3_wx.edit().clear().commit();
                                                finish();
                                                UserLoginActivity.handler1
                                                        .sendEmptyMessage(1);
                                            }
                                        }
                                    }

                                }.start();
                                System.out.println("bitmap2==============="
                                        + bitmap);
                                finish();
                            } catch (Exception e) {

                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onCancel() {
                        }
                    };
                    System.out.println("2===============");
                    mInfo = new UserInfo(this, mQQAuth.getQQToken());
                    mInfo.getUserInfo(listener);

                } catch (Exception e) {

                    e.printStackTrace();
                }

            } else {
                // mUserInfo.setText("");
                // mUserInfo.setVisibility(android.view.View.GONE);
                // mUserLogo.setVisibility(android.view.View.GONE);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}
