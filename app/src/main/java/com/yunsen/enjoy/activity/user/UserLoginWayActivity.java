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
import com.yunsen.enjoy.common.AppContext;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.Utils;
import com.yunsen.enjoy.widget.DialogProgress;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class UserLoginWayActivity extends AppCompatActivity implements
        OnClickListener {
    private DialogProgress progress;
    public static String kahao;
    public static IWXAPI mWxApi;
    public static String WX_CODE = "";
    private String mAppid = Constants.APP_QQ_ID;
    private UserInfo mInfo;
    private Tencent mTencent;
    public static Bitmap bitmap;
    public static String oauth_name = "";
    public static boolean panduan_tishi = false;
    public static boolean jiemian = false;
    public static Handler handler1;
    private SharedPreferences mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user_login_way);
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        mWxApi.registerApp(Constants.APP_ID);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mSp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);

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
    protected void onStart() {
        final Context context = UserLoginWayActivity.this;
        final Context ctxContext = context.getApplicationContext();
        Constants.QQauth = QQAuth.createInstance(mAppid, ctxContext);
        mTencent = Tencent.createInstance(mAppid, AppContext.getInstance());
        super.onStart();
    }

    private void initdata() {
        TextView item0 = (TextView) findViewById(R.id.item0);
        TextView item1 = (TextView) findViewById(R.id.item1);
        TextView item4 = (TextView) findViewById(R.id.item4);
        item0.setOnClickListener(this);
        item1.setOnClickListener(this);
        item4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.item0://
                Intent intent3 = new Intent(UserLoginWayActivity.this, PhoneLoginActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.item1://
                onClickLogin();
                break;
            case R.id.item4://
                finish();
                break;
            default:
                break;
        }
    }

    private void onClickLogin() {
        SharedPreferences spPreferences_tishi = getSharedPreferences("longuserset_tishi", MODE_PRIVATE);
        String weixin = spPreferences_tishi.getString("weixin", "");
        if (!weixin.equals("")) {
            spPreferences_tishi.edit().clear().commit();
            UserLoginActivity.panduan_tishi = false;
        }
        oauth_name = "qq";
        if (!Constants.QQauth.isSessionValid()) {
            try {
                IUiListener listener = new BaseUiListener() {
                    @Override
                    protected void doComplete(JSONObject values) {
                        updateUserInfo();
                    }
                };
                Constants.QQauth.login(this, "all", listener);
                mTencent.login(this, "all", listener);
            } catch (Exception e) {

                e.printStackTrace();
            }
        } else {
            Constants.QQauth.logout(this);
            updateUserInfo();
        }

    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            System.out.println("response===============" + response);
            try {
                String access_token = ((JSONObject) response).getString("access_token");
                String openid = ((JSONObject) response).getString("openid");
                String ret = ((JSONObject) response).getString("ret");
                String oauth_openid = ((JSONObject) response).getString("openid");
                // System.out.println("access_token==============="+access_token);
                Editor editor = mSp.edit();
                editor.putString(SpConstants.ACCESS_TOKEN, access_token);
                editor.putString(SpConstants.UNION_ID, openid);
                editor.putString(SpConstants.SEX, ret);
                editor.putString(SpConstants.OAUTH_OPEN_ID, oauth_openid);
                editor.commit();
            } catch (JSONException e) {

                e.printStackTrace();
            }
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            Toast.makeText(UserLoginWayActivity.this, "onError: " + e.errorDetail, Toast.LENGTH_SHORT);
        }

        @Override
        public void onCancel() {
            // Util.toastMessage(UserLoginActivity.this, "用户取消");//onCancel:
//			Util.dismissDialog();
        }
    }

    private void updateUserInfo() {
        try {
            panduan_tishi = true;

            if (Constants.QQauth != null && Constants.QQauth.isSessionValid()) {
                try {
                    IUiListener listener = new IUiListener() {
                        @Override
                        public void onError(UiError e) {

                        }

                        @Override
                        public void onComplete(final Object response) {
                            new Thread() {

                                @Override
                                public void run() {
                                    JSONObject json = (JSONObject) response;
                                    if (json.has("figureurl")) {
                                        bitmap = null;
                                        String nickname = null;
                                        try {
                                            nickname = json.getString("nickname");
                                            String sex = json.getString("gender");
                                            String province = json.getString("province");
                                            String city = json.getString("city");
                                            String country = json.getString("country");
                                            System.out.println("nickname==========1=====" + nickname);
                                            bitmap = GetImgUtil.getImage(json.getString("figureurl_qq_2"));
                                            String headimgurl2 = Utils.bitmaptoString(bitmap);
                                            Editor editor = mSp.edit();
                                            editor.putString(SpConstants.NICK_NAME, nickname);
                                            editor.putString("headimgurl2", headimgurl2);
                                            editor.putString("sex", sex);
                                            editor.putString("province", province);
                                            editor.putString("city", city);
                                            editor.putString("country", country);
                                            editor.commit();
                                            EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        if (nickname != null && bitmap != null) {
                                            finish();
                                            UserLoginActivity.handler1.sendEmptyMessage(1);
                                        } else {
                                            finish();
                                            UserLoginActivity.handler1.sendEmptyMessage(1);
                                        }
                                    } else {
                                        finish();
                                    }
                                }

                            }.start();
                        }

                        @Override
                        public void onCancel() {
                        }
                    };
                    mInfo = new UserInfo(this, Constants.QQauth.getQQToken());
                    mInfo.getUserInfo(listener);
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}
