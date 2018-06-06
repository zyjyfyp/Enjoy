package com.yunsen.enjoy.activity.user;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.Webview1;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.Register_Va;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.utils.Validator;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.MyPopupWindowMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRegisterActivity extends AppCompatActivity implements OnClickListener {

    private EditText userpwd, userphone, et_user_yz;
    private Button btn_register, get_yz;
    private String phone, pwd, yz;
    private String str, hengyuName;
    private DialogProgress progress;
    private String strUrl;
    private MyPopupWindowMenu popupWindowMenu;
    private TextView regise_tip;
    public Handler mHandler = new MyHandler(this);
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zhuce);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        popupWindowMenu = new MyPopupWindowMenu(this);
        initData();
        TextView img_menu = (TextView) findViewById(R.id.iv_fanhui);
        img_menu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

    }

    static class MyHandler extends Handler {
        WeakReference<UserRegisterActivity> mActivityReference;

        MyHandler(UserRegisterActivity activity) {
            mActivityReference = new WeakReference<UserRegisterActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final UserRegisterActivity activity = mActivityReference.get();
            if (activity != null) {

                switch (msg.what) {
                    case 0:
                        String strhengyuname = (String) msg.obj;
                        Toast.makeText(activity, strhengyuname, Toast.LENGTH_SHORT).show();
                        activity.progress.CloseProgress();
                        activity.finish();
                        break;
                    case 1:
                        String strmsg = (String) msg.obj;
                        Toast.makeText(activity, strmsg, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        if (activity.time > 0) {
                            sendEmptyMessageDelayed(2, 1000);
                            activity.get_yz.setText(activity.time + "s");
                            activity.time--;
                        } else {
                            activity.get_yz.setEnabled(true);
                            activity.get_yz.setText("获取验证码");
                        }
                        break;
                    default:
                        break;
                }


            }
        }
    }

    private LayoutInflater mLayoutInflater;
    private View popView;
    private PopupWindow mPopupWindow;

    private void initPopupWindow() {
        mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        popView = mLayoutInflater.inflate(R.layout.register_tip, null);
        mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        // mPopupWindow.setBackgroundDrawable(new
        // BitmapDrawable());//必须设置background才能消失
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.color.ban_louming));
        mPopupWindow.setOutsideTouchable(true);
        // 自定义动画
        // mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
        // 使用系统动画
        mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
        mPopupWindow.update();
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        final TextView lglottery_pop_closed = (TextView) popView
                .findViewById(R.id.register_tip);
        Map<String, String> params = new HashMap<String, String>();
        params.put("act", "GetwxzRegForIOS");
        params.put("yth", "test");

        AsyncHttp.post_1(URLConstants.REALM_URL + "/mi/getdata.ashx", params,
                new AsyncHttpResponseHandler() {
                    // AsyncHttp.post_1(RealmName.REALM_NAME_LL+"/user_invite_code",
                    // params,new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject object = new JSONObject(arg1);
                            lglottery_pop_closed.setText(object
                                    .getString("msg"));
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                });
    }

    private void showPopupWindow(View view) {
        if (!mPopupWindow.isShowing()) {
            // mPopupWindow.showAsDropDown(view,0,0);
            // 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
            // 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            //			mPopupWindow.showAsDropDown(lay);
        }
    }

    public float[] bubbleSort(float[] args) {// 冒泡排序算法
        for (int i = 0; i < args.length - 1; i++) {
            for (int j = i + 1; j < args.length; j++) {
                if (args[i] < args[j]) {
                    float temp = args[i];
                    args[i] = args[j];
                    args[j] = temp;
                }
            }
        }
        return args;
    }

    private void initData() {
        regise_tip = (TextView) findViewById(R.id.regise_tip);
        et_user_yz = (EditText) findViewById(R.id.et_user_yz);
        get_yz = (Button) findViewById(R.id.get_yz);
        userphone = (EditText) findViewById(R.id.et_user_phone);
        userpwd = (EditText) findViewById(R.id.et_user_pwd);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        get_yz.setOnClickListener(this);
        regise_tip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regise_tip:
                UIHelper.showUserAgreement(UserRegisterActivity.this);
                break;
            case R.id.get_yz:
                phone = userphone.getText().toString().trim();
                if (phone.equals("")) {
                    Toast.makeText(UserRegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT)
                            .show();
                } else if (phone.length() < 11) {
                    Toast.makeText(UserRegisterActivity.this, "手机号少于11位", Toast.LENGTH_SHORT).show();
                } else {
                    if (Validator.isMobile(phone)) {
                        strUrl = URLConstants.REALM_ACCOUNT_URL + "/user_verify_smscode?mobile=" + phone + "";
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
                                        ToastUtils.makeTextShort("验证码已发送");
                                        time = 59;
                                        get_yz.setEnabled(false);
                                        mHandler.sendEmptyMessageDelayed(2, 1000);
                                    } else {
                                        Toast.makeText(UserRegisterActivity.this, info, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, UserRegisterActivity.this);

                    } else {
                        ToastUtils.makeTextShort("手机号码不正确");
                    }
                }

                break;
            case R.id.btn_register:
                yz = et_user_yz.getText().toString().trim();
                phone = userphone.getText().toString().trim();
                final String pwd = userpwd.getText().toString();
                this.pwd = pwd.trim();
                if (phone.equals("")) {
                    Toast.makeText(UserRegisterActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT)
                            .show();
                } else if (phone.length() < 11) {
                    Toast.makeText(UserRegisterActivity.this, "手机号码少于11位", Toast.LENGTH_SHORT)
                            .show();
                } else if (TextUtils.isEmpty(yz)) {
                    Toast.makeText(UserRegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT)
                            .show();
                } else if ((TextUtils.isEmpty(this.pwd))) {
                    Toast.makeText(UserRegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT)
                            .show();
                } else if (pwd.length() < 6) {
                    Toast.makeText(UserRegisterActivity.this, "密码不得小于6位", Toast.LENGTH_SHORT)
                            .show();
                } else if (!(pwd.length() <= 20 && pwd.length() >= 6)) {
                    Toast.makeText(UserRegisterActivity.this, "密码在6-20位之间", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    try {
                        progress = new DialogProgress(UserRegisterActivity.this);
                        progress.CreateProgress();
                        new Thread() {
                            public void run() {
                                strUrl = URLConstants.REALM_ACCOUNT_URL
                                        + "/user_register?site=mobile&code="
                                        + yz + "&username=" + phone
                                        + "&password=" + UserRegisterActivity.this.pwd + "&mobile="
                                        + phone + "";
                                System.out.println("注册" + strUrl);

                                AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int arg0,
                                                          String arg1) {
                                        super.onSuccess(arg0, arg1);
                                        try {
                                            JSONObject jsonObject = new JSONObject(arg1);
                                            String status = jsonObject.getString("status");
                                            String info = jsonObject.getString("info");
                                            if (status.equals("n")) {
                                                System.out.println("=================2==");
                                                str = jsonObject.getString("info");
                                                progress.CloseProgress();
                                                Message message = Message.obtain();
                                                message.what = 1;
                                                message.obj = str;
                                                sendMessage(message);
                                            } else if (status.equals("y")) {
                                                hengyuName = jsonObject.getString("info");
                                                // TODO: 2018/5/7  zyjy what?
                                                SharedPreferences spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
                                                Editor editor = spPreferences.edit();
                                                editor.putBoolean("save", true);
                                                editor.putString("user_name", userphone.getText().toString());
                                                editor.putString("pwd", pwd);
                                                editor.commit();

                                                progress.CloseProgress();
                                                Message message = Message.obtain();
                                                message.what = 0;
                                                message.obj = hengyuName;
                                                mHandler.sendMessage(message);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, getApplicationContext());
                            }
                        }.start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    /*
     * searchFile 查找文件并加入到ArrayList 当中去
     *
     * @String keyword 查找的关键词
     *
     * @File filepath 查找的目录
     */
    ArrayList<Register_Va> bookList = new ArrayList<Register_Va>();

    private void searchFile(String keyword, File filepath) {

        // 判断SD卡是否存在
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File[] files = filepath.listFiles();

            if (files.length > 0) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // 如果目录可读就执行（一定要加，不然会挂掉）
                        if (file.canRead()) {
                            searchFile(keyword, file); // 如果是目录，递归查找
                        }
                    } else {
                        // 判断是文件，则进行文件名判断
                        try {
                            if (file.getName().indexOf(keyword) > -1
                                    || file.getName().indexOf(
                                    keyword.toUpperCase()) > -1) {
                                Register_Va va = new Register_Va();
                                /*
                                 * HashMap<> rowItem = new HashMap<String,
								 * Object>(); rowItem.put("number", index); //
								 * 加入序列号 rowItem.put("bookName",
								 * file.getName());// 加入名称 rowItem.put("path",
								 * file.getPath()); // 加入路径 rowItem.put("size",
								 * file.length()); // 加入文件大小 rowItem.put("time",
								 * file.lastModified()+"f");
								 */
                                System.out.println(file.getName() + "-"
                                        + file.getPath() + "-"
                                        + file.lastModified());
                                va.setPath(file.getPath());
                                va.setTime(Float.parseFloat(file.lastModified()
                                        + "f"));
                                bookList.add(va);
                            }
                        } catch (Exception e) {
                            Toast.makeText(this, "查找发生错误", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }
            }
        }
    }

    protected void dialog(String hengyucode) {
        AlertDialog.Builder builder = new Builder(this);
        builder.setMessage("请牢记云盟号:" + hengyucode);
        builder.setTitle("提示:注册成功");
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("menu");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        if (0 == popupWindowMenu.currentState && popupWindowMenu.isShowing()) {
            popupWindowMenu.dismiss(); // 对话框消失
            popupWindowMenu.currentState = 1; // 标记状态，已消失
        } else {
            popupWindowMenu.showAtLocation(findViewById(R.id.layout),
                    Gravity.BOTTOM, 0, 0);
            popupWindowMenu.currentState = 0; // 标记状态，显示中
        }
        return false; // true--显示系统自带菜单；false--不显示。
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
