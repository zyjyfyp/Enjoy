package com.yunsen.enjoy.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.PullImageResult;
import com.yunsen.enjoy.model.event.PullImageEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import okhttp3.Request;

public class GetImgUtil {
    private static final String TAG = "GetImgUtil";

    // 获取网络图片的数据
    public static Bitmap getImage(String picturepath) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(picturepath);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL
                    .openConnection();
            // 设置超时时间为5秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(5 * 1000);
            // 连接设置获得数据流
            conn.setDoInput(true);
            // 不使用缓存
            conn.setUseCaches(false);
            // 这句可有可无，没有影响
            // conn.connect();
            // 得到数据流
            InputStream is = conn.getInputStream();
            // 解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            // 关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    /**
     * 从sp加载图片 SpConstants.SP_LONG_USER_SET_USER  SpConstants.USER_IMG
     */
    public static void loadLocationImg(final Activity activity, final ImageView imgView) {
        new AsyncTask<Nullable, Nullable, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Nullable... activities) {
                SharedPreferences sp = activity.getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
                String userImg = sp.getString(SpConstants.USER_IMG, "");
                if (TextUtils.isEmpty(userImg)) {
                    return null;
                }
                Bitmap bitmap = Utils.stringtoBitmap(userImg);
                return Utils.toRoundBitmap(bitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imgView.setImageBitmap(bitmap);
                }
            }
        }.execute();

    }

    public static void pullUserIcon(final Activity activity, Uri selectedImage) {
        final Uri imgUri = selectedImage;
        new AsyncTask<Nullable, Nullable, String>() {
            @Override
            protected String doInBackground(Nullable... nullables) {
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(activity)
                            .load(imgUri)
                            .asBitmap()
                            .into(200, 200)
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                String imgStr = Utils.bitmaptoString(bitmap);
                return imgStr;
            }

            @Override
            protected void onPostExecute(String s) {
                SharedPreferences sp = activity.getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString(SpConstants.USER_IMG, s);
                edit.commit();

                HttpProxy.putUserIcon(activity, SpConstants.OK, new HttpCallBack<String>() {
                    @Override
                    public void onSuccess(String responseData) {
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        ToastUtils.makeTextShort("上传失败");
                    }
                });
            }
        }.execute();
    }

    public static void pullImageBase4(final Activity activity, Uri selectedImage, int type) {
        final Uri imgUri = selectedImage;
        new AsyncTask<Integer, Nullable, String>() {
            int mType;

            @Override
            protected String doInBackground(Integer... type) {
                mType = type[0];
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(activity)
                            .load(imgUri)
                            .asBitmap()
                            .into(200, 200)
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                String imgStr = BitmapUtil.bitmapToBase64(bitmap);

                return imgStr;
            }

            @Override
            protected void onPostExecute(String s) {
                HashMap<String, String> params = new HashMap<>();
                params.put("base64", s);
                HttpProxy.getPullImageBase64(s, new HttpCallBack<PullImageResult>() {
                    @Override
                    public void onSuccess(PullImageResult responseData) {
                        String img_url = responseData.getImg_url();
                        EventBus.getDefault().post(new PullImageEvent(mType, img_url));
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        ToastUtils.makeTextShort("上传失败");
                    }
                });
            }
        }.execute(type);
    }

    public static void FTPPushImage(List<String> photoList, final String userCode, final Handler handler) {
        new AsyncTask<List<String>, Nullable, Boolean>() {
            @Override
            protected Boolean doInBackground(List<String>[] lists) {
                List<String> list = lists[0];
                FTPClient client = new FTPClient();
                try {
                    client.connect("139.159.215.36", 210);
                    client.login("ddek3", "ddek3210.");
//                    SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                    String remotePathTmp = "upload/phone/" + userCode;//路径
                    try {
                        client.createDirectory(remotePathTmp);//客户端创建目录
                    } catch (Exception e) {
                    }

                    client.changeDirectory(remotePathTmp);
                    for (int i = 0; i < list.size(); i++) {
                        final int fIndex = i;
                        String pathname = list.get(i);
                        File file = new File(pathname);
                        int startIndex = pathname.lastIndexOf("/") + 1;
                        String filePath = pathname.substring(startIndex);
                        final String fFilePath = "/upload/phone/" + userCode + "/" + filePath;
                        Log.e(TAG, "doInBackground: filePath=" + fFilePath);
                        FileInputStream fis = new FileInputStream(file);
                        try {
                            client.upload(filePath, fis, 0, 0, new FTPDataTransferListener() {
                                @Override
                                public void started() {

                                }

                                @Override
                                public void transferred(int i) {
                                    Log.e(TAG, "transferred: " + i);
                                }

                                @Override
                                public void completed() {
                                    Log.d(TAG, ": 完成");
                                    Message obtain = Message.obtain();
                                    obtain.what = Constants.SUCCESS;
                                    obtain.arg1 = fIndex;
                                    obtain.obj = fFilePath;
                                    handler.sendMessage(obtain);
                                }

                                @Override
                                public void aborted() {
                                    Log.e(TAG, "aborted: ");
                                }

                                @Override
                                public void failed() {
                                    Log.d(TAG, "failed: 失败");
                                    Message obtain = Message.obtain();
                                    obtain.what = Constants.FAILED;
                                    obtain.arg1 = fIndex;
                                    obtain.obj = fFilePath;
                                    handler.sendMessage(obtain);
                                }
                            });
                        } catch (FTPDataTransferException e) {
                            e.printStackTrace();
                            Log.e(TAG, "doInBackground: FTPDataTransferException=" + e.getMessage());
                        } catch (FTPAbortedException e) {
                            e.printStackTrace();
                            Log.e(TAG, "doInBackground: FTPAbortedException=" + e.getMessage());
                        }
                    }
                    client.disconnect(true);//exit
                } catch (IllegalStateException e) {
                    e.printStackTrace();//非法状态异常
                    Log.e(TAG, "doInBackground: IllegalStateException" + e.getMessage());

                } catch (FTPIllegalReplyException e) {
                    e.printStackTrace();//非法回复异常
                    Log.e(TAG, "doInBackground: FTPIllegalReplyException" + e.getMessage());

                } catch (FTPException e) {
                    e.printStackTrace();//异常
                    Log.e(TAG, "doInBackground: FTPException " + e.getMessage());

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "doInBackground: IOException" + e.getMessage());
                }
                return null;
            }
        }.execute(photoList);

    }
}
