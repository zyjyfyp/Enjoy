package com.yunsen.enjoy.http.down;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateApkThread  {
    private String downloadUrl;
    private File saveFile;
    private String fileName;
    private Context mContext;
    private NotificationManager notificationManager;// 状态栏通知管理类
    private RemoteViews notificationViews;// 状态栏通知显示的view
    private final int notificationID = 1;// 通知的id
    private final int updateProgress = 1;// 更新状态栏的下载进度
    private final int downloadSuccess = 2;// 下载成功
    private final int downloadError = 3;// 下载失败
    ProgressDialog downLoadDialog;
    private static boolean mIsLoading = false;
    private Notification.Builder mNoticeBuilder;
    private int mProgress = 0;

    public UpdateApkThread(String downloadUrl, String fileName, Context context) {
        this.mContext = context;
        mIsLoading = true;
        this.downloadUrl = downloadUrl;
        String filePath = context.getFilesDir().getAbsolutePath();
        this.saveFile = new File(filePath);
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.fileName = fileName;
        showDialog();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == updateProgress) {// 更新下载进度
                notificationViews.setTextViewText(R.id.progressTv, "已下载" + mProgress + "%");
                notificationViews.setProgressBar(R.id.progressBar, 100, mProgress, false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //8.0以上弹出通知状态栏
                    String channelID = "0";
                    String channelName = "channel_name";
                    NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
                    if (notificationManager != null) {
                        notificationManager.createNotificationChannel(channel);
                        mNoticeBuilder.setChannelId(channelID);
                    }
                }
                notificationManager.notify(notificationID, mNoticeBuilder.build());
                handler.sendEmptyMessageDelayed(updateProgress, 500);
            } else if (msg.what == downloadSuccess) {// 下载完成
                mIsLoading = false;
                File file = new File(saveFile, Constants.APK_NAME);
                notificationViews.setTextViewText(R.id.progressTv, "下载完成，点击安装");
                notificationViews.setProgressBar(R.id.progressBar, 100, 100, false);
                PendingIntent intent = getApkInstallService(file);
                mNoticeBuilder.setAutoCancel(false);
                mNoticeBuilder.setContentIntent(intent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //8.0以上弹出通知状态栏
                    String channelID = "0";
                    String channelName = "channel_name";
                    NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
                    if (notificationManager != null) {
                        notificationManager.createNotificationChannel(channel);
                        mNoticeBuilder.setChannelId(channelID);
                        mNoticeBuilder.build();
                    }
                }
                notificationManager.notify(notificationID, mNoticeBuilder.build());

                if (downLoadDialog != null && downLoadDialog.isShowing()) {
                    downLoadDialog.dismiss();
                    mContext.startService(new Intent(mContext, ApkInstallService.class));
                }
                Log.e(TAG, "handleMessage: 下载完成  安装");
            } else if (msg.what == downloadError) {// 下载失败
                mIsLoading = false;
                Toast.makeText(mContext, "网络异常，下载失败", Toast.LENGTH_SHORT);
                if (downLoadDialog != null && downLoadDialog.isShowing()) {
                    downLoadDialog.dismiss();
                }
                notificationManager.cancel(notificationID);
            }
        }
    };

    private void showDialog() {
        // 进度条对话框
        downLoadDialog = new ProgressDialog(mContext);
        downLoadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        downLoadDialog.setMessage("正在下载更新");
        downLoadDialog.setCanceledOnTouchOutside(true);
        downLoadDialog.setProgressNumberFormat(null);
        downLoadDialog.setMax(100);
        downLoadDialog.show();
    }


    /**
     * @author wangqian@iliveasia.com
     * @Description: 初始化通知栏
     */
    private void initNofication() {
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNoticeBuilder = new Notification.Builder(mContext);
        mNoticeBuilder.setSmallIcon(R.mipmap.app_icon_1);// 设置通知消息的图标
        mNoticeBuilder.setContentTitle("正在下载...");// 设置通知消息的标题
        notificationViews = new RemoteViews(mContext.getPackageName(), R.layout.down_notification);
        notificationViews.setImageViewResource(R.id.download_icon, R.mipmap.app_icon_1);
        mNoticeBuilder.setContent(notificationViews);
        Message message = Message.obtain();
        message.what = updateProgress;
        message.arg1 = mProgress;
        handler.sendMessageDelayed(message, 500);
    }

    private static final String TAG = "UpdateApkThread";

    public static boolean IsLoading() {
        return mIsLoading;
    }

    private PendingIntent getApkInstallService(File apkFile) {
        PendingIntent intent = PendingIntent.getService(mContext, 1, new Intent(mContext, ApkInstallService.class), PendingIntent.FLAG_UPDATE_CURRENT);
        return intent;
    }

    public void startDownLoadFile() {
        initNofication();
        ZyDownLoadUtil.get().download(downloadUrl, saveFile.getAbsolutePath(), fileName, new ZyDownLoadUtil.OnDownloadListener() {
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
}
