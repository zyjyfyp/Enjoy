package com.yunsen.enjoy.http.down;


import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/6/25.
 */

public class ApkInstallService extends Service {
    private static final String TAG = "ApkInstallService";
    private static final int APP_INSTALL_ID = 8;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String filePath = Environment.getExternalStorageDirectory() + "/ss";
        File file = new File(filePath, "zams.apk");
        openAPKFile(file);
        Log.e(TAG, "onStartCommand: " + filePath);
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 打开安装包
     *
     * @param
     * @param apkFile
     */
    public void openAPKFile(File apkFile) {
        // 核心是下面几句代码
        if (null != apkFile) {
            String cmd = "chmod 777 " + apkFile.getPath();
            try {
                Runtime.getRuntime().exec(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri uri = Uri.fromFile(apkFile);
            //安装程序
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            //启动安装程序
            try {
                //兼容7.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(this, this.getPackageName() + ".fileProvider", apkFile);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    //兼容8.0
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        boolean hasInstallPermission = this.getPackageManager().canRequestPackageInstalls();
                        if (!hasInstallPermission) {
                            startInstallPermissionSettingActivity();
                            return;
                        }
                    }
                } else {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                if (this.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                    this.startActivity(intent);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                Log.e(TAG, "openAPKFile: " + e.getMessage());
            }
            ApkInstallService.this.startActivity(intent);
        }
        stopSelf();
    }


    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

}
