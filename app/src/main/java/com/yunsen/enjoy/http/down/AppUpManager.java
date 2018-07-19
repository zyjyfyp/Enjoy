package com.yunsen.enjoy.http.down;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.AppContext;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.PermissionSetting;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.PgyAppVersion;
import com.yunsen.enjoy.ui.DialogUtils;
import com.yunsen.enjoy.ui.interfaces.OnLeftOnclickListener;
import com.yunsen.enjoy.ui.interfaces.OnRightOnclickListener;
import com.yunsen.enjoy.utils.ToastUtils;
import java.lang.ref.WeakReference;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/7/19/019.
 * 版本更新
 */

public class AppUpManager {
    private static final String TAG = "AppUpManager";
    private static AppUpManager manager;
    private AlertDialog mDiscoverNewVersionDialog;
    private final int updateprogress = 1;
    private final int updateProgress = 1;// 更新状态栏的下载进度
    private final int downloadSuccess = 2;// 下载成功
    private final int downloadError = 3;// 下载失败
    private static MyHandler handler;
    private int mProgress = 0;
    private ProgressDialog downLoadDialog;
    private RemoteViews notificationViews;
    private NotificationManager notificationManager;
    private Notification.Builder mNoticBuilder;
    private Context mContext = AppContext.getInstance();

    private AppUpManager() {
    }

    public static AppUpManager getInstance() {
        if (manager == null) {
            synchronized (AppUpManager.class) {
                if (manager == null) {
                    manager = new AppUpManager();
                    handler = new MyHandler(manager);
                }
            }
        }
        return manager;
    }

    /**
     * 开始检查新版本
     *
     * @param act
     * @param byHand
     */
    public void startCheckUpdate(Activity act, final boolean byHand) {
        final Activity fAct = act;
        HttpProxy.getPGYApkVersion(new HttpCallBack<PgyAppVersion>() {
            @Override
            public void onSuccess(PgyAppVersion responseData) {
                //有新版本
                if (responseData != null && responseData.isBuildHaveNewVersion())
                    showDiscoverNewVersion(fAct, responseData);
                else {
                    if (byHand) {
                        ToastUtils.makeTextShort("当前是最新版本");
                    }
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                if (byHand) {
                    ToastUtils.makeTextShort("当前是最新版本");
                }
            }
        });
    }

    /**
     * 显示发现新版本
     *
     * @param act
     * @param version
     */
    public void showDiscoverNewVersion(Activity act, PgyAppVersion version) {
        final Activity fAct = act;
        final String fUrl = version.getDownloadURL();
        if (mDiscoverNewVersionDialog != null && mDiscoverNewVersionDialog.isShowing()) {
            mDiscoverNewVersionDialog.dismiss();
        }
        mDiscoverNewVersionDialog = DialogUtils.createYesAndNoDialog(act, "新版本" + version.getBuildVersion(),
                "取消", "确认", new OnLeftOnclickListener() {
                    @Override
                    public void onLeftClick() {
                    }
                }, new OnRightOnclickListener() {
                    @Override
                    public void onRightClick(int... index) {
//                        AndPermission.with(fAct)
//                                .permission(Permission.Group.STORAGE)
//                                .onGranted(new Action() {
//                                    @Override
//                                    public void onAction(List<String> permissions) {
                        //检查本地版本是下载最新版本
                        startDownLoadFile(fAct, fUrl);
//                                    }
//                                })
//                                .onDenied(new Action() {
//                                    @Override
//                                    public void onAction(List<String> permissions) {
//                                        new PermissionSetting(fAct).showSettingStorage(permissions);
//                                    }
//                                }).start();
                    }
                });
        mDiscoverNewVersionDialog.setMessage(version.getBuildUpdateDescription() + "");
        mDiscoverNewVersionDialog.show();
    }

    /**
     * 下载的对话框
     *
     * @param act
     */
    private void showDialog(Activity act) {
        // 进度条对话框
        if (downLoadDialog == null) {
            downLoadDialog = new ProgressDialog(act);
            downLoadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            downLoadDialog.setMessage("正在下载更新");
            downLoadDialog.setCanceledOnTouchOutside(true);
            downLoadDialog.setProgressNumberFormat(null);
            downLoadDialog.setMax(100);
        }
        if (!downLoadDialog.isShowing() && !act.isFinishing()) {
            downLoadDialog.show();
        }
    }

    /**
     * 下载的通知
     */
    private void initNotification(Activity act) {
        notificationManager = (NotificationManager) act.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationViews = new RemoteViews(act.getPackageName(), R.layout.down_notification);
        notificationViews.setImageViewResource(R.id.download_icon, R.mipmap.app_icon_1);
        mNoticBuilder = new Notification.Builder(act)
                .setSmallIcon(R.mipmap.app_icon_1)// 设置通知消息的图标
                .setContentTitle("正在下载...")// 设置通知消息的标题
                .setContent(notificationViews);
        Message message = Message.obtain();
        message.what = updateProgress;
        message.arg1 = mProgress;
        handler.sendMessageDelayed(message, 500);
    }

    private void startDownLoadFile(Activity act, String downloadUrl) {
        showDialog(act);
        initNotification(act);
        ZyDownLoadUtil.get().download(downloadUrl, act.getFilesDir().toString(), Constants.APK_NAME, new ZyDownLoadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                handler.removeMessages(updateProgress);
                handler.sendEmptyMessage(downloadSuccess);
            }

            @Override
            public void onDownloading(int progress) {
                mProgress = progress;
                if (downLoadDialog != null && downLoadDialog.isShowing()) {
                    downLoadDialog.setProgress(progress);
                }
            }

            @Override
            public void onDownloadFailed() {
                handler.removeMessages(updateProgress);
                handler.sendEmptyMessage(downloadError);
            }
        });
    }

    private PendingIntent getApkInstallService() {
        PendingIntent intent = PendingIntent.getService(mContext, 1, new Intent(mContext, ApkInstallService.class), PendingIntent.FLAG_UPDATE_CURRENT);
        return intent;
    }

    private static class MyHandler extends Handler {
        WeakReference<AppUpManager> wrf;
        private int notificationID = 1;

        public MyHandler(AppUpManager manager) {
            wrf = new WeakReference<AppUpManager>(manager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AppUpManager appUpManager = wrf.get();
            if (appUpManager == null) {
                return;
            }
            if (msg.what == appUpManager.updateprogress) {// 更新下载进度
                appUpManager.notificationViews.setTextViewText(R.id.progressTv, "已下载" + appUpManager.mProgress + "%");
                appUpManager.notificationViews.setProgressBar(R.id.progressBar, 100, appUpManager.mProgress, false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //8.0以上弹出通知状态栏
                    String channelID = "0";
                    String channelName = "channel_name";
                    NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
                    if (appUpManager.notificationManager != null) {
                        appUpManager.notificationManager.createNotificationChannel(channel);
                        appUpManager.mNoticBuilder.setChannelId(channelID);
                    }
                }
                appUpManager.notificationManager.notify(notificationID, appUpManager.mNoticBuilder.build());
                handler.sendEmptyMessageDelayed(appUpManager.updateProgress, 500);
            } else if (msg.what == appUpManager.downloadSuccess) {// 下载完成
                appUpManager.notificationViews.setTextViewText(R.id.progressTv, "下载完成，点击安装");
                appUpManager.notificationViews.setProgressBar(R.id.progressBar, 100, 100, false);
                PendingIntent intent = appUpManager.getApkInstallService();
                appUpManager.mNoticBuilder.setAutoCancel(false);
                appUpManager.mNoticBuilder.setContentIntent(intent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //8.0以上弹出通知状态栏
                    String channelID = "0";
                    String channelName = "channel_name";
                    NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
                    if (appUpManager.notificationManager != null) {
                        appUpManager.notificationManager.createNotificationChannel(channel);
                        appUpManager.mNoticBuilder.setChannelId(channelID);
                        appUpManager.mNoticBuilder.build();
                    }
                }
                appUpManager.notificationManager.notify(notificationID, appUpManager.mNoticBuilder.build());

                if (appUpManager.downLoadDialog != null && appUpManager.downLoadDialog.isShowing()) {
                    appUpManager.downLoadDialog.dismiss();
                    appUpManager.mContext.startService(new Intent(appUpManager.mContext, ApkInstallService.class));
                }
                Log.e(TAG, "handleMessage: 下载完成 安装");
            } else if (msg.what == appUpManager.downloadError) {// 下载失败
                ToastUtils.makeTextShort("网络异常，下载失败");
                if (appUpManager.downLoadDialog != null && appUpManager.downLoadDialog.isShowing()) {
                    appUpManager.downLoadDialog.dismiss();
                }
                appUpManager.notificationManager.cancel(notificationID);
            }
        }
    }

    /**
     * 在onDestory回收资源
     */
    public void recyclerData() {
        if (mDiscoverNewVersionDialog != null) {
            if (mDiscoverNewVersionDialog.isShowing()) {
                mDiscoverNewVersionDialog.dismiss();
            }
            mDiscoverNewVersionDialog = null;

        }
        if (downLoadDialog != null) {
            if (downLoadDialog.isShowing()) {
                downLoadDialog.dismiss();
            }
            downLoadDialog = null;
        }

    }
}


