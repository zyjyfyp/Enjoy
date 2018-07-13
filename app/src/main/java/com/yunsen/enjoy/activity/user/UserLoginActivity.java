package com.yunsen.enjoy.activity.user;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orhanobut.logger.Logger;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.MainActivity;
import com.yunsen.enjoy.activity.mine.PersonCenterActivity;
import com.yunsen.enjoy.common.AppContext;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.PermissionSetting;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.http.down.UpdateApkThread;
import com.yunsen.enjoy.model.AuthorizationModel;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.SpUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.GlideCircleTransform;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import okhttp3.Request;

import static com.yunsen.enjoy.activity.user.LoginActivity.initOpenidAndToken;

public class UserLoginActivity extends AppCompatActivity implements OnClickListener {
    private static final int PHONE_LOGIN_REQUEST = 1;
    private Button btn_login;
    private DialogProgress progress;
    private String nickname, headimgurl, access_token, sex, unionid, province, city, country, oauth_openid;
    private SharedPreferences spPreferences_weixin;
    private SharedPreferences spPreferences_login;
    public static boolean isWXLogin = false;
    private IWXAPI mWxApi;
    public static String WX_CODE = "";
    public static Bitmap bitmap;
    public static String oauth_name = "";
    public static boolean panduan_tishi = false;
    public static boolean wx_fanhui = false;
    public static Handler handler1;
    public static boolean zhuangtai = false;
    private String strUr2 = URLConstants.REALM_NAME_LL + "/get_apk_version?browser=android";
    private String URL;
    SharedPreferences spPreferences_qq;
    Editor editor;
    private AlertDialog dialog;
    private static Tencent mTencent;
    private boolean isServerSideLogin;
    private UserInfo mInfo;
    private static final String TAG = "UserLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weixin_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mWxApi = WXAPIFactory.createWXAPI(this, null);
        mWxApi.registerApp(Constants.APP_ID);
        mTencent = Tencent.createInstance(Constants.APP_QQ_ID, AppContext.getInstance());
        spPreferences_weixin = getSharedPreferences("longuserset_weixin", MODE_PRIVATE);
        spPreferences_login = getSharedPreferences("longuserset_login", MODE_PRIVATE);
        ImageView loginImage= (ImageView) findViewById(R.id.login_img);
        Glide.with(this)
                .load(R.mipmap.login_icon)
                .transform(new GlideCircleTransform(this))
                .into(loginImage);
        try {
            progress = new DialogProgress(UserLoginActivity.this);
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
        wx_fanhui = true;//分享微信返回APP
        if (AccountUtils.mWeiXiHasLogin) {
            finish();
        }
        if (!zhuangtai) {
            updata();
        }


    }

    public void userxinxi() {
        try {
            String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                    + Constants.APP_ID + "&secret=" + Constants.APP_SECRET + "&code=" + WX_CODE +
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

                ;
            }, UserLoginActivity.this);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void userxinxill(String ACCESS_TOKEN, String openid) {

        try {
            access_token = ACCESS_TOKEN;
            String accessTokenUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + ACCESS_TOKEN + "&openid=" + openid + "";
            System.out.println("======22=============" + accessTokenUrl);
            AsyncHttp.get(accessTokenUrl, new AsyncHttpResponseHandler() {
                public void onSuccess(int arg0, String arg1) {
                    System.out.println("======输出2=============" + arg1);
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
                        editor = spPreferences_login.edit();
                        editor.putString(SpConstants.NICK_NAME, nickname);
                        editor.putString("headimgurl", headimgurl);
                        editor.putString("access_token", access_token);
                        editor.putString("unionid", unionid);
                        editor.putString("sex", sex);
                        editor.putString("province", province);
                        editor.putString("city", city);
                        editor.putString("country", country);
                        editor.putString("oauth_openid", oauth_openid);
                        editor.commit();
                        EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                    spPreferences_qq = getSharedPreferences("longuserset_3_qq", MODE_PRIVATE);
                    spPreferences_qq.edit().clear().commit();
                    isWXLogin = false;
                    finish();
                }

                ;
            }, UserLoginActivity.this);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {

        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    dialog();
                    break;
                case -1:
                    progress.CloseProgress();
                    break;
                case 10:

                    break;
                case 1:
                    String str = (String) msg.obj;
                    Toast.makeText(UserLoginActivity.this, str, Toast.LENGTH_SHORT).show();
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
        btn_login = (Button) findViewById(R.id.btn_login);
        Button phoneLoginBtn = (Button) findViewById(R.id.phone_login_btn);
        btn_login.setOnClickListener(this);
        phoneLoginBtn.setOnClickListener(this);
        TextView img_menu = (TextView) findViewById(R.id.img_menu);
        img_menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://微信登录
                isWXLogin = true;
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wei_xin_log_in";
                mWxApi.sendReq(req);
                break;
            case R.id.phone_login_btn://qq登录
//                Intent intent = new Intent(this, LoginActivity.class);
//                startActivityForResult(intent, PHONE_LOGIN_REQUEST);
                UIHelper.showPhoneLoginActivity(this);
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

    IUiListener loginListener = new UserLoginActivity.BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            initOpenidAndToken(values);
            updateUserInfo();
        }

    };

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
                                    String figureurl_qq_1 = json.getString("figureurl_qq_1");
                                    SharedPreferences spPreferences_login = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = spPreferences_login.edit();
                                    editor.putString(SpConstants.NICK_NAME, nickname);
                                    editor.putString(SpConstants.HEAD_IMG_URL_2, figureurl_qq_2);
                                    editor.putString(SpConstants.HEAD_IMG_URL, figureurl_qq_1);
                                    editor.putString(SpConstants.SEX, sex);
                                    editor.putString(SpConstants.PROVINCE, province);
                                    editor.putString(SpConstants.CITY, city);
                                    editor.putString(SpConstants.COUNTRY, country);
                                    editor.putString(SpConstants.LOGIN_FLAG, SpConstants.QQ_LOGIN);
                                    editor.commit();
                                    /**
                                     * 第三方授权
                                     */
                                    requestBundlePhone(SpConstants.QQ_LOGIN);
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
            mInfo = new com.tencent.connect.UserInfo(UserLoginActivity.this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);
        }
    }

    /**
     * QQ第三方登录
     */
    private void requestBundlePhone(final String loginType) {
        HttpProxy.requestBindPhone(new HttpCallBack<AuthorizationModel>() {
            @Override
            public void onSuccess(AuthorizationModel responseData) {
                SpUtils.saveUserInfo(responseData, loginType);
                EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                finish();
            }

            @Override
            public void onError(Request request, Exception e) {
                Log.e(TAG, "onError: " + e);
                EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                finish();
            }
        });
    }


    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
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
            Toast.makeText(UserLoginActivity.this, "onError: " + e.errorDetail, Toast.LENGTH_SHORT);
        }

        @Override
        public void onCancel() {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHONE_LOGIN_REQUEST:
                    finish();
                    break;
            }
        } else if (resultCode == 2) {
            /**
             * 第三方授权 微信第三方授权成功
             */
            Log.e(TAG, "onActivityResult: 第三方授权 微信第三方授权开始");
            requestBundlePhone(SpConstants.WEI_XIN);
        }
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
                        String c_version = getAppVersionName(getApplicationContext()).trim().replaceAll("\\.", "");
                        float server_version = Float.parseFloat(file_version.replaceAll("\\.", ""));//服务器
                        float client_version = Float.parseFloat(c_version);//当前

                        Logger.e("服务器:" + server_version + "/当前:" + client_version);
                        if (server_version > client_version) {
                            //						Toast.makeText(UserLoginActivity.this, "提示更新", Toast.LENGTH_SHORT).show();
                            Message message = new Message();
                            message.what = 0;
                            handler.sendMessage(message);
                        } else {
                            //					Toast.makeText(UserLoginActivity.this, "没有提示更新", Toast.LENGTH_SHORT).show();
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

    protected void downLoadApk() {
        final ProgressDialog pd; // 进度条对话框
        pd = new ProgressDialog(UserLoginActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.setCanceledOnTouchOutside(true);
        pd.setProgressNumberFormat(null);
        zhuangtai = true;
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
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
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");// 编者按：此处Android应为android，否则造成安装不了
        UserLoginActivity.this.startActivity(intent);
    }

    // 程序版本更新
    private void dialog() {

        AlertDialog.Builder builder = new Builder(UserLoginActivity.this);
        builder.setMessage("检查到最新版本，是否要更新！");
        builder.setTitle("提示:新版本");
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (UpdateApkThread.IsLoading()) {
                    Toast.makeText(UserLoginActivity.this, "正在下载...", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {

                    AndPermission.with(UserLoginActivity.this)
                            .permission(Permission.Group.STORAGE)
                            .onGranted(new Action() {
                                @Override
                                public void onAction(List<String> permissions) {
                                    String filePath = Environment.getExternalStorageDirectory() + "/ss";
                                    new UpdateApkThread(URL, filePath, "zams.apk", UserLoginActivity.this).start();
                                }
                            })
                            .onDenied(new Action() {
                                @Override
                                public void onAction(List<String> permissions) {
                                    new PermissionSetting(UserLoginActivity.this).showSettingStorage(permissions);
                                }
                            }).start();
                }
            }
        });
        builder.setNegativeButton("以后再说",
                new DialogInterface.OnClickListener()

                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();
        dialog.show();
    }

    // 获取当前程序的版本信息

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {

            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
    }
}
