package com.yunsen.enjoy.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.DeviceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import okhttp3.Request;

/**
 * 引导页的界面
 */
public class GuideActivity extends AppCompatActivity {
    private ImageView i0;
    private MyHandler handler;
    private SharedPreferences mSp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        handler = new MyHandler(this);
        setContentView(R.layout.item01);
        i0 = (ImageView) findViewById(R.id.i0);
        i0.setVisibility(View.VISIBLE);
        handler.sendEmptyMessageDelayed(0, 3000);
//        Glide.with(this)
//                .load(R.drawable.zams_qdy)
//                .into(i0);
        mSp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        needLogin();//判断是否需要重新登录 防止同一个账号同时使用在多台设备上
    }

    private void needLogin() {
        if (AccountUtils.hasLogin() && AccountUtils.hasBoundPhone()) {
            String userName = mSp.getString(SpConstants.USER_NAME, "");
            final String loginSign = mSp.getString(SpConstants.LOGIN_SIGN, "");
            HttpProxy.getUserInfo(userName, new HttpCallBack<UserInfo>() {
                @Override
                public void onSuccess(UserInfo responseData) {
                    String login_sign = responseData.getLogin_sign();
                    if (!loginSign.equals(login_sign)) {
                        appLogin();
                    }
                }

                @Override
                public void onError(Request request, Exception e) {

                }
            });


        }
    }

    private void appLogin() {
        String userName = mSp.getString(SpConstants.INPUT_USER_NAME, "");
        String pwd = mSp.getString(SpConstants.INPUT_USER_PWD, "");
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)) { //如果是第三方登录的 犹如不知道用户名和密码需要重新手动登录
            AccountUtils.clearData();
            mSp.edit().clear().commit();
            return;
        }
        HttpProxy.getUserLogin(userName, pwd, new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo responseData) {

            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }


    public static class MyHandler extends Handler {
        private WeakReference<GuideActivity> weakReference;

        public MyHandler(GuideActivity activity) {
            this.weakReference = new WeakReference<GuideActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final GuideActivity act = weakReference.get();
            if (act != null) {
                switch (msg.what) {
                    case 0:
                        SharedPreferences preferences = act.getSharedPreferences(SpConstants.SP_GUIDE, Activity.MODE_PRIVATE);
                        String versionName = preferences.getString(SpConstants.APP_VERSION_NAME, "");
                        String currentVersionName = DeviceUtil.getAppVersionName(act);
                        // 如果程序已经进入
                        if (!TextUtils.isEmpty(versionName) && versionName.equals(currentVersionName)) {
//                            act.getgaoguan(); //todo 跳过广告判断
                            Intent intent = new Intent(act, MainActivity.class);
                            act.startActivity(intent);
                            act.finish();
                        } else {
                            Intent intent = new Intent(act, Guide2Activity.class);
                            act.startActivity(intent);
                            act.finish();
                        }
                        break;

                    default:
                        break;
                }
            }
        }
    }


    /**
     * 判断是否有广告
     */
    private void getgaoguan() {
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/?advert_id=15", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, String arg1) {
                super.onSuccess(arg0, arg1);
                try {
                    System.out.println("-----------------" + arg1);
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    if ("y".equals(status)) {
                        Intent intent = new Intent(GuideActivity.this, SecondActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable arg0, String arg1) {
                super.onFailure(arg0, arg1);
                System.out.println("异常-----------------" + arg1);
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, GuideActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }


}