package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.utils.Validator;
import com.yunsen.enjoy.widget.DialogProgress;

import org.json.JSONException;
import org.json.JSONObject;

public class UserForgotPasswordActivity extends AppCompatActivity implements
        OnClickListener {

    private Button img_title_login;
    private EditText userpwd, userphone, et_user_yz;
    private Button btn_register, get_yz;
    private String name, phone, postbox, pwd, pwdagain, insertdata, yz,
            shoujihao;
    private String str, hengyuName;
    private DialogProgress progress;
    private String strUrl;
    private TextView regise_tip, tv_title;
    private String yanzhengma;
    private LinearLayout ll_zhuchexieyi;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wangji_pass);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initdata();

        tv_title = (TextView) findViewById(R.id.tv_title);
        String type_num = getIntent().getStringExtra("type");
        System.out.println("=================type_num==" + type_num);
        if (type_num != null) {
            if (type_num.equals("1")) {
                type = "password";
                tv_title.setText("找回用户密码");
            } else if (type_num.equals("2")) {
                type = "paypassword";
                tv_title.setText("找回支付密码");
            } else {

            }
        } else {

        }

        //		ImageView img_menu = (ImageView) findViewById(R.id.img_menu);
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
                    Toast.makeText(getApplicationContext(), strhengyuname,
                            Toast.LENGTH_SHORT).show();
                    progress.CloseProgress();
                    // Intent intent = new Intent(UserRegisterActivity.this,
                    // UserLoginActivity.class);
                    // startActivity(intent);
                    finish();
                    break;
                case 1:
                    String strmsg = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), strmsg, Toast.LENGTH_SHORT)
                            .show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "验证码已发送",
                            Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "验证码已下发",
                            Toast.LENGTH_SHORT).show();
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

    private void initdata() {
        try {
            // ll_zhuchexieyi.setVisibility(View.GONE);
            LinearLayout ll_buju = (LinearLayout) findViewById(R.id.ll_buju);
            //			ll_buju.setBackgroundResource(R.drawable.denglu_beijing);
            et_user_yz = (EditText) findViewById(R.id.et_user_yz);
            get_yz = (Button) findViewById(R.id.get_yz);
            // img_title_login = (Button) findViewById(R.id.img_title_login);
            // username = (EditText) findViewById(R.id.et_user_name);
            userphone = (EditText) findViewById(R.id.et_user_phone);
            // userpostbox = (EditText) findViewById(R.id.et_user_postbox);
            userpwd = (EditText) findViewById(R.id.et_user_pwd);
            // userpwdagain = (EditText) findViewById(R.id.et_user_pwd_again);
            btn_register = (Button) findViewById(R.id.btn_register);
            // img_title_login.setOnClickListener(this);
            btn_register.setOnClickListener(this);
            get_yz.setOnClickListener(this);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {


            switch (v.getId()) {
                case R.id.regise_tip:
					Intent intent4 = new Intent(UserForgotPasswordActivity.this,
							Webview1.class);
					intent4.putExtra("userxy", "5997");
					startActivity(intent4);
                    break;
                case R.id.get_yz:
                    phone = userphone.getText().toString().trim();
                    if (phone.equals("")) {
                        Toast.makeText(UserForgotPasswordActivity.this, "手机号码不能为空",
                                Toast.LENGTH_SHORT).show();
                    } else if (phone.length() < 11) {
                        Toast.makeText(UserForgotPasswordActivity.this,
                                "手机号码少于11位", Toast.LENGTH_SHORT).show();
                    } else {
                        // phone = userphone.getText().toString().trim();
                        if (Validator.isMobile(phone)) {

                            strUrl = URLConstants.REALM_URL
                                    + "/user_passmis_smscode?mobile=" + phone + "";

                            AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int arg0, String arg1) {
                                    super.onSuccess(arg0, arg1);
                                    System.out.println("=============" + arg1);
                                    try {
                                        JSONObject object = new JSONObject(arg1);
                                        String result = object.getString("status");//
                                        String info = object.getString("info");// info
                                        if (result.equals("y")) {
                                            yanzhengma = object.getString("data");
                                            handler.sendEmptyMessage(2);
                                        } else {
                                            Toast.makeText(
                                                    UserForgotPasswordActivity.this,
                                                    info, Toast.LENGTH_SHORT).show();
                                            // handler.sendEmptyMessage(3);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, getApplicationContext());
                        } else {
                            Toast.makeText(UserForgotPasswordActivity.this,
                                    "手机号码不正确", Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;
//                case R.id.img_title_login:
//                    int index = 0;
//					Intent intent = new Intent(UserForgotPasswordActivity.this,
//							UserLoginActivity.class);
//					intent.putExtra("login", index);
//					startActivity(intent);
//					finish();
//                    break;
                case R.id.btn_register:
                    yz = et_user_yz.getText().toString().trim();
                    // name = username.getText().toString().trim();
                    phone = userphone.getText().toString().trim();
                    // postbox = userpostbox.getText().toString().trim();
                    pwd = userpwd.getText().toString().trim();
                    // pwdagain = userpwdagain.getText().toString().trim();
                    // SimpleDateFormat formatter = new SimpleDateFormat(
                    // "yyyy年MM月dd日   HH:mm:ss");
                    // Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
                    // insertdata = formatter.format(curDate);

                    System.out.println("1==================" + yz);
                    System.out.println("2==================" + yanzhengma);
                    if (phone.equals("")) {
                        Toast.makeText(UserForgotPasswordActivity.this, "手机号码不能为空",
                                Toast.LENGTH_SHORT).show();
                    } else if (phone.length() < 11) {
                        Toast.makeText(UserForgotPasswordActivity.this,
                                "手机号码少于11位", Toast.LENGTH_SHORT).show();
                    } else if (yz.equals("")) {
                        Toast.makeText(UserForgotPasswordActivity.this, "验证码不能为空",
                                Toast.LENGTH_SHORT).show();
                    }
                    // else if (!yz.equals(yanzhengma)) {
                    // Toast.makeText(UserRegisterActivity.this, "验证码不正确",
                    // 200).show();
                    // }
                    // else if (yz.length() < 6 ) {
                    // Toast.makeText(UserForgotPasswordActivity.this, "验证码少于六位",
                    // 200).show();
                    // }
                    else if (pwd.equals("")) {
                        Toast.makeText(UserForgotPasswordActivity.this, "密码不能为空",
                                Toast.LENGTH_SHORT).show();
                    } else if (pwd.length() < 8) {
                        Toast.makeText(UserForgotPasswordActivity.this, "密码不得小于8位",
                                Toast.LENGTH_SHORT).show();
                    } else if (!(userpwd.getText().toString().length() < 20 && userpwd
                            .getText().toString().length() >= 8)) {
                        Toast.makeText(UserForgotPasswordActivity.this,
                                "密码在8-20位之间", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            progress = new DialogProgress(
                                    UserForgotPasswordActivity.this);
                            progress.CreateProgress();
                            new Thread() {
                                public void run() {
                                    try {
                                        // System.out.println("=================11=="
                                        // + phone);
                                        // System.out.println("=================12=="
                                        // + pwd);
                                        // System.out.println("=================13=="
                                        // + postbox);
                                        // String type =
                                        // getIntent().getStringExtra("type");
                                        // System.out.println("=================13=="
                                        // + type);
                                        strUrl = URLConstants.REALM_URL
                                                + "/mobile_update_password?user_name="
                                                + phone + "&mobile=" + phone
                                                + "&code=" + yz + "&newpassword="
                                                + pwd + "&type=" + type + "";
                                        System.out.println("注册" + strUrl);

                                        AsyncHttp.get(strUrl,
                                                new AsyncHttpResponseHandler() {
                                                    @Override
                                                    public void onSuccess(int arg0,
                                                                          String arg1) {

                                                        // method stub
                                                        super.onSuccess(arg0, arg1);
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(
                                                                    arg1);
                                                            System.out
                                                                    .println("=================1=="
                                                                            + arg1);
                                                            String status = jsonObject
                                                                    .getString("status");
                                                            String info = jsonObject
                                                                    .getString("info");
                                                            if (status.equals("n")) {
                                                                System.out
                                                                        .println("=================2==");
                                                                str = jsonObject
                                                                        .getString("info");
                                                                String no = jsonObject
                                                                        .getString("info");
                                                                // // str =
                                                                // jsonObject.getString("info");
                                                                // NewDataToast.makeText(getApplicationContext(),
                                                                // no,false,
                                                                // 0).show();
                                                                progress.CloseProgress();
                                                                Message message = new Message();
                                                                message.what = 1;
                                                                message.obj = str;
                                                                handler.sendMessage(message);
                                                            } else if (status
                                                                    .equals("y")) {
                                                                try {
                                                                    System.out
                                                                            .println("=================3=="
                                                                                    + info);
                                                                    hengyuName = jsonObject
                                                                            .getString("info");
                                                                    // NewDataToast.makeText(getApplicationContext(),
                                                                    // info,false,
                                                                    // 0).show();
                                                                    System.out
                                                                            .println("=================4==");
                                                                    Log.v("data1",
                                                                            hengyuName
                                                                                    + "");

                                                                    SharedPreferences spPreferences = getSharedPreferences(
                                                                            "longuserset_user",
                                                                            MODE_PRIVATE);
                                                                    Editor editor = spPreferences
                                                                            .edit();
                                                                    editor.putBoolean(
                                                                            "save",
                                                                            true);
                                                                    editor.putString(
                                                                            "user_name",
                                                                            userphone
                                                                                    .getText()
                                                                                    .toString());
                                                                    editor.putString(
                                                                            "pwd",
                                                                            userpwd.getText()
                                                                                    .toString());
                                                                    editor.commit();

                                                                    progress.CloseProgress();
                                                                    Message message = new Message();
                                                                    message.what = 0;
                                                                    message.obj = hengyuName;
                                                                    handler.sendMessage(message);
                                                                    // Intent intent
                                                                    // = new
                                                                    // Intent(UserRegisterActivity.this,
                                                                    // HomeActivity.class);
                                                                    // startActivity(intent);
                                                                    // finish();
                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        } catch (JSONException e) {

                                                            // catch block
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(
                                                            Throwable arg0,
                                                            String arg1) {

                                                        // method stub
                                                        super.onFailure(arg0, arg1);
                                                        System.out
                                                                .println("=================arg0=="
                                                                        + arg0);
                                                        System.out
                                                                .println("=================arg1=="
                                                                        + arg1);
                                                    }
                                                }, getApplicationContext());

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                ;
                            }.start();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    break;

                default:
                    break;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
