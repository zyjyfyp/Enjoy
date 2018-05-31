package com.yunsen.enjoy.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * 引导页的界面
 */
public class GuideActivity extends AppCompatActivity {
    SharedPreferences preferences;
    private ImageView i0;
    private MyHandler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        handler = new MyHandler(this);
        setContentView(R.layout.item01);
        i0 = (ImageView) findViewById(R.id.i0);
        i0.setVisibility(View.VISIBLE);
        handler.sendEmptyMessageDelayed(0, 3000);
        Glide.with(this)
                .load(R.drawable.zams_qdy)
                .into(i0);
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
                        act.preferences = act.getSharedPreferences(SpConstants.SP_GUIDE, Activity.MODE_PRIVATE);
                        // 如果程序已经进入
                        if (act.preferences.getString("flow", "").equals("yes")) {
                            act.getgaoguan();
                        } else {
                            Intent intent = new Intent(act, Guide2Activity.class);
                            Editor editor = act.preferences.edit();
                            editor.putString("flow", "yes");
                            editor.commit();
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