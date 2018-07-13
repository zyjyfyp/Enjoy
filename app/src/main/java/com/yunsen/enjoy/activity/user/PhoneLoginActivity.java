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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.UserForgotPasswordActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.SpUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.utils.Validator;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.GlideCircleTransform;
import com.yunsen.enjoy.widget.MyPopupWindowMenu;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import okhttp3.Request;

public class PhoneLoginActivity extends BaseFragmentActivity implements OnClickListener {
    private Button btn_login;
    private EditText et_pwd, userphone;
    private Button img_title_register;
    private TextView imgbtn_findpwd;
    private MessageDigest md;
    private String status, rnd, name, password, mm, mi;
    private String inStr;
    private DialogProgress progress;
    private String strUrlone, strUrltwo;
    private MyPopupWindowMenu popupWindowMenu;
    private String strUr2 = URLConstants.REALM_NAME_LL + "/get_apk_version?browser=android";
    private String URL;
    boolean zhuangtai = false;
    public static Bitmap bitmap;


    @Override
    public int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        progress = new DialogProgress(PhoneLoginActivity.this);
        popupWindowMenu = new MyPopupWindowMenu(this);
        ImageView loginImage = (ImageView) findViewById(R.id.login_img);
        Glide.with(this)
                .load(R.mipmap.login_icon)
                .transform(new GlideCircleTransform(this))
                .into(loginImage);
        initdata();
    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        if (requestCode == Constants.READ_PHONE_STATE) {
            Intent intent = new Intent(PhoneLoginActivity.this, UserRegisterActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
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
                    Toast.makeText(PhoneLoginActivity.this, str, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(PhoneLoginActivity.this, "用户名错误", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    try {
                        strUrltwo = URLConstants.REALM_URL + "/mi/login.ashx?yth="
                                + processParam(name) + "&pwd=" + mi + "&msisdn=";
                    } catch (UnsupportedEncodingException e) {

                        e.printStackTrace();
                    }

                    AsyncHttp.get(strUrltwo, new AsyncHttpResponseHandler() {
                        public void onSuccess(int arg0, String arg1) {
                            try {
                                System.out.println("=================4==" + arg1);
                                JSONObject object = new JSONObject(arg1);
                                int s = object.getInt("status");
                                System.out.println("s:  " + s);
                                if (s == 0) {
                                    String str = object.getString("msg");
                                    progress.CloseProgress();
                                    Message message2 = new Message();
                                    message2.what = 1;
                                    message2.obj = str;
                                    handler.sendMessage(message2);
                                } else {
                                    handler.sendEmptyMessage(0);
                                }
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {

                            super.onFailure(arg0, arg1);
                            Toast.makeText(PhoneLoginActivity.this, "连接超时",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }, PhoneLoginActivity.this);

                    break;

                default:
                    break;
            }

        }

    };


    private void initdata() {
        userphone = (EditText) findViewById(R.id.et_user_phone);
        img_title_register = (Button) findViewById(R.id.img_title_registre);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_pwd = (EditText) findViewById(R.id.et_user_pwd);
        imgbtn_findpwd = (TextView) findViewById(R.id.wenhao);
        img_title_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        imgbtn_findpwd.setOnClickListener(this);
        TextView img_menu = (TextView) findViewById(R.id.iv_fanhui);
        img_menu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });
    }

    // 字符串上传服务器 乱码 问题的解决方法
    private String processParam(String temp)
            throws UnsupportedEncodingException {
        return URLEncoder.encode(temp, "UTF-8");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_title_registre:
                requestPermission(new String[]{Permission.READ_PHONE_STATE, Permission.READ_PHONE_STATE}, Constants.READ_PHONE_STATE);
                break;
            case R.id.btn_login:
                name = userphone.getText().toString().trim();
                password = et_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(PhoneLoginActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!Validator.isMobile(name)) {
                    ToastUtils.makeTextShort("请输入正确的电话号码");
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(PhoneLoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    ToastUtils.makeTextShort("请输入至少6位数的密码");
                } else {
                    progress.CreateProgress();
                    requestUserLogin(name, password);
                }
                break;
            case R.id.wenhao:// 找回密码
                Intent intent2 = new Intent(PhoneLoginActivity.this, UserForgotPasswordActivity.class);
                intent2.putExtra("type", "1");
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    /**
     * 登录
     *
     * @param name
     * @param pwd
     */
    private void requestUserLogin(final String name, final String pwd) {
        HttpProxy.getUserLogin(name, pwd, new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo responseData) {
                if (btn_login != null) {
                    SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString(SpConstants.INPUT_USER_PWD, pwd);
                    edit.putString(SpConstants.INPUT_USER_NAME, name);
                    edit.commit();
                    btn_login.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progress.CloseProgress();
                            EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                            UIHelper.showHomeActivity(PhoneLoginActivity.this);
                            setResult(RESULT_OK);
                            finish();
                        }
                    }, 1000);
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                if (e instanceof DataException) {
                    ToastUtils.makeTextShort(e.getMessage());
                }
                progress.CloseProgress();
            }
        });
    }

    private static final String TAG = "PhoneLoginActivity";

    public void init() {
        try {
            this.md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
