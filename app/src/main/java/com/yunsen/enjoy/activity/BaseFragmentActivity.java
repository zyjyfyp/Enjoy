package com.yunsen.enjoy.activity;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.squareup.picasso.Picasso;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yunsen.enjoy.common.AppManager;
import com.yunsen.enjoy.common.DefaultRationale;
import com.yunsen.enjoy.common.PermissionSetting;

import java.util.List;


public abstract class BaseFragmentActivity extends AppCompatActivity {

    protected PermissionSetting mSetting;
    protected DefaultRationale mRationale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);

        // 修改状态栏颜色，4.4+生效
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //            setTranslucentStatus();
        //        }
        //        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        //        tintManager.setStatusBarTintEnabled(true);
        //        tintManager.setStatusBarTintResource(R.color.status_bar_bg);//通知栏所需颜色
        int layout = getLayout();
        if (layout != -1) {
            setContentView(layout);
        }
        initView();
        initData(savedInstanceState);
        requestData();
        initListener();
        mSetting = new PermissionSetting(this);
        mRationale = new DefaultRationale();
    }

    public void requestData() {

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

    public void requestPermission(String permissions, final int requestCode) {
        AndPermission.with(this)
                .permission(permissions)
                .rationale(mRationale)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        onRequestPermissionSuccess(requestCode);
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(BaseFragmentActivity.this, permissions)) {
                            mSetting.showSetting(permissions);
                        }
                    }
                })
                .start();
    }

    /**
     * 权限请求成功
     *
     * @param requestCode
     */
    protected void onRequestPermissionSuccess(int requestCode) {
    }

    ;

    protected void requestPermission(String[] permissions,final int requestCode) {
        AndPermission.with(this)
                .permission(permissions)
                .rationale(mRationale)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        onRequestPermissionSuccess(requestCode);
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(BaseFragmentActivity.this, permissions)) {
                            mSetting.showSetting(permissions);
                        }
                    }
                })
                .start();
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
