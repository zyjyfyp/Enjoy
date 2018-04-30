package com.yunsen.enjoy.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.AppManager;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {
    /**
     * 关于引导页的界面
     */
    private ViewPager viewPager;
    private ArrayList<View> pageViews;
    SharedPreferences preferences;
    private ImageView i0;
    private ViewPager i1;

    private Handler handler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    preferences = getSharedPreferences("guide",
                            Activity.MODE_PRIVATE);
                    // 如果程序已经进入
                    if (preferences.getString("flow", "").equals("yes")) {
                        getgaoguan();
                    } else {
                        i0.setVisibility(View.GONE);
                        Intent intent = new Intent(GuideActivity.this, Guide2Activity.class);
                        Editor editor = preferences.edit();
                        editor.putString("flow", "yes");
                        editor.commit();
                        startActivity(intent);
                        finish();
                    }
                    break;

                default:
                    break;
            }
        }

        ;
    };

    //当Activity被销毁时会调用onDestory方法
    @Override
    protected void onDestroy() {
        super.onDestroy();
        BitmapDrawable bd1 = (BitmapDrawable) i0.getBackground();
        i0.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
        bd1.setCallback(null);
        bd1.getBitmap().recycle();
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
                    if (status.equals("y")) {
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
                AppManager.getAppManager().finishActivity();
            }
        }, GuideActivity.this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item01);
        i0 = (ImageView) findViewById(R.id.i0);
        //		i0.setBackgroundResource(R.drawable.zams_qdy);
        Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.zams_qdy);
        BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
        i0.setBackgroundDrawable(bd);
        i1 = (ViewPager) findViewById(R.id.i1);
        i0.setVisibility(View.VISIBLE);
        i1.setVisibility(View.GONE);
        i0.postDelayed(new Runnable() {

            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 3000);
    }


}