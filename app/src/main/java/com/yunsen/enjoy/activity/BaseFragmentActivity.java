package com.yunsen.enjoy.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.AppManager;


public abstract class BaseFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);

        // 修改状态栏颜色，4.4+生效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.status_bar_bg);//通知栏所需颜色
        getLayout();
        setContentView(getLayout());
        initView();
        initData(savedInstanceState);
        initListener();
    }


    public abstract int getLayout();

    protected abstract void initView();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initListener();



    @TargetApi(19)
    protected void setTranslucentStatus() {
        Window window = getWindow();
        // Translucent status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // Translucent navigation bar
//        window.setFlags(
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Picasso.with(this).resumeTag(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Picasso.with(this).pauseTag(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Picasso.with(this).cancelTag(this);
        // 结束Activity从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

}
