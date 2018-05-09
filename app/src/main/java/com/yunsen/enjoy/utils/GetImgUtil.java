package com.yunsen.enjoy.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.PullImageResult;
import com.yunsen.enjoy.model.event.PullImageEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

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
                params.put("base64",s);
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
}
