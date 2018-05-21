package com.yunsen.enjoy.activity.user;

/**
 * Created by Administrator on 2018/5/14.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orhanobut.logger.Logger;
import com.tencent.connect.auth.QQAuth;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.MainActivity;
import com.yunsen.enjoy.activity.mine.PersonCenterActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.AuthorizationModel;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.utils.SpUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.utils.Validator;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;


/**
 * Created by tiansj on 15/7/31.
 */
public class LoginActivity extends BaseFragmentActivity {

    private static final int GET_CODE_ID = 10;
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.imgPhone)
    ImageView imgPhone;
    @Bind(R.id.phone_edt)
    EditText phoneEdt;
    @Bind(R.id.imgCancel)
    ImageView imgCancel;
    @Bind(R.id.layoutPhone)
    RelativeLayout layoutPhone;
    @Bind(R.id.imgCode)
    ImageView imgCode;
    @Bind(R.id.pwd_edt)
    EditText pwdEdt;
    @Bind(R.id.rl_1)
    RelativeLayout rl1;
    @Bind(R.id.register_tv)
    TextView registerTv;
    @Bind(R.id.forget_pwd_tv)
    TextView forgetPwdTv;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.qq_login_img)
    ImageView qqLoginImg;
    @Bind(R.id.weixin_loing_img)
    ImageView weixinLoingImg;
    private int mTime = 60;

    private Handler sHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    private IWXAPI mWxApi;
    private String access_token;
    private String nickname, headimgurl, unionid, sex, province, city, country, oauth_openid;
    private boolean mIsWXLogin;
    private static Tencent mTencent;
    private com.tencent.connect.UserInfo mInfo;
    private boolean isServerSideLogin;
    private QQAuth mQQAuth;
    private String URL;
    private String strUr2 = URLConstants.REALM_NAME_LL + "/get_apk_version?browser=android";


    @Override
    public int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_login2;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        mWxApi.registerApp(Constants.APP_ID);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //        mQQAuth = QQAuth.createInstance(Constants.APP_QQ_ID, AppContext.getInstance());
        mTencent = Tencent.createInstance(Constants.APP_QQ_ID, this);
    }


    @Override
    protected void initListener() {
    }


    @OnClick({R.id.action_back, R.id.register_tv, R.id.forget_pwd_tv, R.id.login_btn, R.id.qq_login_img, R.id.weixin_loing_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.register_tv:
                UIHelper.showUserRegisterActivity(this);
                break;
            case R.id.forget_pwd_tv:
                UIHelper.showForgetPwdActivity(this);
                break;
            case R.id.login_btn:
                userLogin();
                break;
            case R.id.qq_login_img:
                qqLogin();
                break;
            case R.id.weixin_loing_img:
                mIsWXLogin = true;
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo";
                mWxApi.sendReq(req);
                break;
        }
    }

    /**
     * QQ登录
     */
    private void qqLogin() {

        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
            isServerSideLogin = false;
        } else {
            if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                mTencent.logout(this);
                mTencent.login(this, "all", loginListener);
                isServerSideLogin = false;
                return;
            }
            mTencent.logout(this);
            updateUserInfo();
        }
    }


    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            initOpenidAndToken(values);
            updateUserInfo();
        }

    };

    /**
     * initOpenid 和 Token
     *
     * @param jsonObject
     */
    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Log.e(TAG, "onComplete111: " + response);
            try {
                String access_token = ((JSONObject) response).getString("access_token");
                String openid = ((JSONObject) response).getString("openid");
                String ret = ((JSONObject) response).getString("ret");
                String oauth_openid = ((JSONObject) response).getString("openid");
                SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("access_token", access_token);
                editor.putString("unionid", openid);
                editor.putString("sex", ret);
                editor.putString("oauth_openid", oauth_openid);
                editor.putString(SpConstants.OAUTH_UNIONID, oauth_openid);
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
            Toast.makeText(LoginActivity.this, "onError: " + e.errorDetail, Toast.LENGTH_SHORT);
        }

        @Override
        public void onCancel() {
        }
    }


    public void updateUserInfo() {
        SharedPreferences spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = spPreferences.edit();
        editor.putString(SpConstants.LOGIN_FLAG, SpConstants.QQ_LOGIN);
        editor.putBoolean(SpConstants.PAN_DUAN, true);
        editor.putBoolean(SpConstants.PAN_DUAN_TI_SHI, true);
        editor.commit();
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {
                @Override
                public void onError(UiError e) {
                }

                @Override
                public void onComplete(final Object response) {
                    Log.e(TAG, "onComplete: " + response);
                    new Thread() {
                        @Override
                        public void run() {
                            JSONObject json = (JSONObject) response;
                            if (json.has("figureurl")) {
                                try {
                                    nickname = json.getString("nickname");
                                    sex = json.getString("gender");
                                    province = json.getString("province");
                                    city = json.getString("city");
                                    String figureurl_qq_2 = json.getString("figureurl_qq_2");
                                    SharedPreferences spPreferences_login = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = spPreferences_login.edit();
                                    editor.putString(SpConstants.NICK_NAME, nickname);
                                    editor.putString(SpConstants.HEAD_IMG_URL_2, figureurl_qq_2);
                                    editor.putString(SpConstants.SEX, sex);
                                    editor.putString(SpConstants.PROVINCE, province);
                                    editor.putString(SpConstants.CITY, city);
                                    editor.putString(SpConstants.COUNTRY, country);
                                    editor.putString(SpConstants.LOGIN_FLAG, SpConstants.QQ_LOGIN);
                                    editor.commit();
                                    /**
                                     * 第三方授权
                                     */
                                    requestBundlePhone();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }.start();
                }

                @Override
                public void onCancel() {
                }
            };
            mInfo = new com.tencent.connect.UserInfo(LoginActivity.this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);
        }
    }

    /**
     * 账号和密码验证
     */
    private void userLogin() {
        String name = phoneEdt.getText().toString();
        String pwd = pwdEdt.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.makeTextShort("请输入电话号码");
        } else if (Validator.isMobile(pwd)) {
            ToastUtils.makeTextShort("请输入正确的电话号码");
        } else if (TextUtils.isEmpty(pwd)) {
            ToastUtils.makeTextShort("请输入密码");
        } else if (pwd.length() < 6) {
            ToastUtils.makeTextShort("请输入至少6位数的密码");
        } else {
            requestUserLogin(name, pwd);
        }
    }

    /**
     * 登录
     *
     * @param name
     * @param pwd
     */
    private void requestUserLogin(String name, String pwd) {
        HttpProxy.getUserLogin(name, pwd, new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo responseData) {
                SpUtils.saveUserInfo(responseData);
                EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                UIHelper.showHomeActivity(LoginActivity.this);
                finish();
            }

            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.makeTextShort("登录失败");
            }
        });
    }

    /**
     * QQ第三方登录
     */
    private void requestBundlePhone() {
        HttpProxy.requestBindPhone(new HttpCallBack<AuthorizationModel>() {
            @Override
            public void onSuccess(AuthorizationModel responseData) {
                SpUtils.saveUserInfo(responseData, SpConstants.QQ_LOGIN);
                EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                finish();
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }


    public void userxinxi() {
        try {
            String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                    + Constants.APP_ID + "&secret=" + Constants.APP_SECRET + "&code=" + Constants.WX_Code +
                    "&grant_type=authorization_code";
            System.out.println("======11=============" + accessTokenUrl);
            AsyncHttp.get(accessTokenUrl, new AsyncHttpResponseHandler() {
                public void onSuccess(int arg0, String arg1) {
                    try {
                        JSONObject object = new JSONObject(arg1);
                        String access_token = object.getString("access_token");
                        String openid = object.getString("openid");
                        userxinxill(access_token, openid);
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }

            }, LoginActivity.this);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void userxinxill(String ACCESS_TOKEN, String openid) {
        access_token = ACCESS_TOKEN;
        String accessTokenUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + ACCESS_TOKEN + "&openid=" + openid + "";
        AsyncHttp.get(accessTokenUrl, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                try {
                    JSONObject object = new JSONObject(arg1);
                    nickname = object.getString(SpConstants.NICK_NAME);
                    headimgurl = object.getString("headimgurl");
                    unionid = object.getString("unionid");
                    sex = object.getString("sex");
                    province = object.getString("province");
                    city = object.getString("city");
                    country = object.getString("country");
                    oauth_openid = object.getString("openid");
                    SharedPreferences spPreferences_login = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
                    SharedPreferences.Editor editor = spPreferences_login.edit();
                    editor.putString(SpConstants.NICK_NAME, nickname);
                    editor.putString("headimgurl", headimgurl);
                    editor.putString("access_token", access_token);
                    editor.putString("unionid", unionid);
                    editor.putString("sex", sex);
                    editor.putString("province", province);
                    editor.putString("city", city);
                    editor.putString("country", country);
                    editor.putString("oauth_openid", oauth_openid);
                    editor.putString(SpConstants.LOGIN_FLAG, SpConstants.WEI_XIN);
                    editor.commit();
                    EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
            }
        }, LoginActivity.this);
    }

    private static final String TAG = "LoginActivity";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: requestCode=" + requestCode + "resultCode=" + resultCode);
        Log.e(TAG, "onActivityResult: " + data);
        mTencent.onActivityResult(requestCode, resultCode, data);
    }

    private void updata() {
        try {
            /**
             * 版本2
             */
            AsyncHttp.get(strUr2, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int arg0, String arg1) {

                    super.onSuccess(arg0, arg1);
                    System.out.println("首页版本==============" + arg1);
                    try {
                        JSONObject jsonObject = new JSONObject(arg1);
                        JSONObject jsob = jsonObject.getJSONObject("data");
                        String file_version = jsob.getString("file_version");
                        String link_url = jsob.getString("link_url");
                        String file_path = jsob.getString("file_path");
                        String id = jsob.getString("id");
                        URL = URLConstants.REALM_NAME_HTTP + file_path;
                        System.out.println("首页版本URL==============" + URL);
                        String c_version = DeviceUtil.getAppVersionName(getApplicationContext()).trim().replaceAll("\\.", "");
                        float server_version = Float.parseFloat(file_version.replaceAll("\\.", ""));//服务器
                        float client_version = Float.parseFloat(c_version);//当前

                        Logger.e("服务器:" + server_version + "/当前:" + client_version);
                        if (server_version > client_version) {
                            dialog();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, getApplicationContext());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 程序版本更新
    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        // builder.setMessage(updatainfo);
        builder.setMessage("检查到最新版本，是否要更新！");
        builder.setTitle("提示:新版本");
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                downLoadApk();
            }
        });
        builder.setNegativeButton("以后再说",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    protected void downLoadApk() {
        final ProgressDialog pd; // 进度条对话框
        pd = new ProgressDialog(LoginActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.setCanceledOnTouchOutside(false);
        pd.setProgressNumberFormat(null);
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("=====URL==============" + URL);
                    File file = getFileFromServer(URL, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); // 结束掉进度条对话框
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // 安装apk
    protected void installApk(File file) {
        // TODO: 2018/4/25 zyjy 升级标记
        MainActivity.zhuangtai = false;
        UserLoginActivity.zhuangtai = false;
        PersonCenterActivity.zhuangtai = false;
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");// 编者按：此处Android应为android，否则造成安装不了
        LoginActivity.this.startActivity(intent);
    }

    public static File getFileFromServer(String path, ProgressDialog pd)
            throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            java.net.URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            // 获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(),
                    "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                // 获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (sHandler != null) {
            sHandler.removeMessages(GET_CODE_ID);
        }
        sHandler = null;
    }
}
