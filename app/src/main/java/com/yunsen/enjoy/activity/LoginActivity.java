package com.yunsen.enjoy.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * Created by tiansj on 15/7/31.
 */
public class LoginActivity extends BaseFragmentActivity {

    private static final int GET_CODE_ID = 10;
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.imgCancel)
    ImageView imgCancel;
    @Bind(R.id.imgCode)
    ImageView imgCode;
    @Bind(R.id.code)
    EditText code;
    @Bind(R.id.btnGetCode)
    Button btnGetCode;
    @Bind(R.id.btnSure)
    Button btnSure;
    private int mTime = 60;

    private Handler sHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mTime == 0) {
                btnGetCode.setText("获取验证码");
                btnGetCode.setClickable(true);
            } else {
                btnGetCode.setClickable(false);
                btnGetCode.setText("重新获取(" + mTime-- + ")");
                sHandler.sendEmptyMessageDelayed(GET_CODE_ID, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("乐享汽车绑定手机");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    private static final String TAG = "LoginActivity";

    @Override
    protected void initListener() {
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, "beforeTextChanged: " + s);
                if (s != null & s.toString().length() == 0) {
                    imgCancel.setVisibility(View.GONE);
                } else {
                    imgCancel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, "beforeTextChanged: " + s + " count =" + count);
                if (s != null && s.toString().length() == 4) {
                    btnSure.setTextColor(getResources().getColor(R.color.white));
                    btnSure.setBackground(getResources().getDrawable(R.drawable.orange_button));
                } else {
                    btnSure.setTextColor(getResources().getColor(R.color.color_888888));
                    btnSure.setBackground(getResources().getDrawable(R.drawable.login_yzm_oval_shape_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.action_back, R.id.imgCancel, R.id.btnGetCode, R.id.btnSure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.imgCancel:
                phone.setText("");
                imgCancel.setVisibility(View.GONE);
                break;
            case R.id.btnGetCode://获取验证码
                Log.e(TAG, "onClick: 获取验证码");
                if (phone.getText().toString().length() == 11) {
                    sHandler.sendEmptyMessageDelayed(GET_CODE_ID, 1000);
                    sendCode("86", phone.getText().toString());
                }
                break;
            case R.id.btnSure: //登录
                submitCode("86", phone.getText().toString(), code.getText().toString());
                break;
        }
    }

    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    Log.e(TAG, "afterEvent: " + result + "data=" + data);
                } else {
                    // TODO 处理错误的结果
                    Log.e(TAG, "afterEvent: data=" + data);
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    //     data={phone=15111628556, country=86}
                } else {
                    // TODO 处理错误的结果
                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (sHandler != null) {
            sHandler.removeMessages(GET_CODE_ID);
        }
        sHandler = null;
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    }
}
