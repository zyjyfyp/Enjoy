package com.yunsen.enjoy.http.down;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.yunsen.enjoy.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateApkThread extends Thread {
    private String downloadUrl;
    private File saveFile;
    private String fileName;
    private Context context;
    private NotificationManager notificationManager;// 状态栏通知管理类
    private Notification notification;// 状态栏通知
    private RemoteViews notificationViews;// 状态栏通知显示的view
    private Timer timer;// 定时器，用于更新下载进度
    private TimerTask task;// 定时器执行的任务

    private final int notificationID = 1;// 通知的id
    private final int updateProgress = 1;// 更新状态栏的下载进度
    private final int downloadSuccess = 2;// 下载成功
    private final int downloadError = 3;// 下载失败
    private DownLoadUtil downLoadUtil;
    ProgressDialog downLoadDialog;
    private static boolean mIsLoading = false;

    public UpdateApkThread(String downloadUrl, String fileLocation, String fileName, Context context) {
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        this.downloadUrl = downloadUrl;
        this.saveFile = new File(fileLocation);
        this.context = context;
        this.fileName = fileName;
        showDialog();
    }

    private void showDialog() {
//        MainFragment.zhuangtai = true;
        // 进度条对话框
        downLoadDialog = new ProgressDialog(context);
        downLoadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        downLoadDialog.setMessage("正在下载更新");
        downLoadDialog.setCanceledOnTouchOutside(true);
        downLoadDialog.setProgressNumberFormat(null);
        downLoadDialog.show();
    }

    @Override
    public void run() {
        super.run();
        try {
            initNofication();
            handlerTask();
            downLoadUtil = new DownLoadUtil();
            int downSize = downLoadUtil.downloadUpdateFile(downloadUrl, saveFile, fileName, callback);
            if (downSize == downLoadUtil.getRealSize() && downSize != 0) {
                downLoadDialog.setMax(downSize);
                handler.sendEmptyMessage(downloadSuccess);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author wangqian@iliveasia.com
     * @Description: 初始化通知栏
     */
    private void initNofication() {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.icon = R.mipmap.app_icon_1;// 设置通知消息的图标
        notification.tickerText = "正在下载...";// 设置通知消息的标题
        notificationViews = new RemoteViews(context.getPackageName(), R.layout.down_notification);
        notificationViews.setImageViewResource(R.id.download_icon, R.mipmap.app_icon_1);
    }

    /**
     * @author wangqian@iliveasia.com
     * @Description: 定时通知handler去显示下载百分比
     */
    private void handlerTask() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(updateProgress);
            }
        };
        timer.schedule(task, 500, 500);
    }

    private static final String TAG = "UpdateApkThread";
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == updateProgress) {// 更新下载进度
                int fileSize = downLoadUtil.getRealSize();
                int totalReadSize = downLoadUtil.getTotalSize();
                if (totalReadSize > 0) {
                    float size = (float) totalReadSize * 100 / (float) fileSize;
                    DecimalFormat format = new DecimalFormat("0.00");
                    String progress = format.format(size);
                    notificationViews.setTextViewText(R.id.progressTv, "已下载" + progress + "%");
                    notificationViews.setProgressBar(R.id.progressBar, 100, (int) size, false);
                    notification.contentView = notificationViews;
                    notificationManager.notify(notificationID, notification);
                    if (downLoadDialog != null && downLoadDialog.isShowing()) {
                        downLoadDialog.setProgress((int) size);
                    }
                }
            } else if (msg.what == downloadSuccess) {// 下载完成
                mIsLoading = false;
                notificationViews.setTextViewText(R.id.progressTv, "下载完成");
                notificationViews.setProgressBar(R.id.progressBar, 100, 100, false);
                notification.contentView = notificationViews;
                notification.tickerText = "下载完成";
                notificationManager.notify(notificationID, notification);
                if (timer != null && task != null) {
                    timer.cancel();
                    task.cancel();
                    timer = null;
                    task = null;
                }
                File file = new File(saveFile + "/zams.apk");
                if (downLoadDialog != null && downLoadDialog.isShowing()) {
                    downLoadDialog.dismiss();
                    openAPKFile(file.getAbsolutePath());
                }
                Log.e(TAG, "handleMessage: 下载完成安装");


                Intent intent = getNoticeItntent(file);
                // PendingIntent 通知栏跳转
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notification.contentIntent = pendingIntent;
                notification.contentView.setTextViewText(R.id.progressTv, "下载完成，点击安装");
                notificationManager.notify(notificationID, notification);

            } else if (msg.what == downloadError) {// 下载失败
                mIsLoading = false;
                if (timer != null && task != null) {
                    timer.cancel();
                    task.cancel();
                    timer = null;
                    task = null;
                }
                Toast.makeText(context, "网络异常，下载失败", Toast.LENGTH_SHORT);
                if (downLoadDialog != null && downLoadDialog.isShowing()) {
                    downLoadDialog.dismiss();
                }
                notificationManager.cancel(notificationID);
            }
        }

    };


    /**
     * 下载回调
     */
    DownloadFileCallback callback = new DownloadFileCallback() {

        @Override
        public void downloadError(String msg) {
            handler.sendEmptyMessage(downloadError);
        }
    };


    public static boolean IsLoading() {
        return mIsLoading;
    }

    private Intent getNoticeItntent(File apkFile) {
        Intent installIntent = null;
        //兼容7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            installIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", apkFile);
            installIntent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                return intent;
            }
        } else {
            Uri uri = Uri.fromFile(apkFile);
            installIntent = new Intent(Intent.ACTION_VIEW);
            installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        return installIntent;
    }


    /**
     * 打开安装包
     *
     * @param
     * @param fileUri
     */
    public void openAPKFile(String fileUri) {
        // 核心是下面几句代码
        if (null != fileUri) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                File apkFile = new File(fileUri);
                //兼容7.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", apkFile);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    //兼容8.0
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        boolean hasInstallPermission = context.getPackageManager().canRequestPackageInstalls();
                        if (!hasInstallPermission) {
                            startInstallPermissionSettingActivity();
                            return;
                        }
                    }
                } else {
                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                    context.startActivity(intent);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                Log.e(TAG, "openAPKFile: " + e.getMessage());
            }
        }
    }


    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
