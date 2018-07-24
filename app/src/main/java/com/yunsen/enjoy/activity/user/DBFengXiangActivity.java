package com.yunsen.enjoy.activity.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.ApkVersionInfo;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.widget.DialogProgress;

import java.util.concurrent.ExecutionException;

import okhttp3.Request;

/**
 * 分享
 *
 * @author Administrator
 */
public class DBFengXiangActivity extends BaseFragmentActivity implements OnClickListener {

    private SharedPreferences spPreferences;
    private ImageButton btn_wechat;
    private ImageButton img_btn_tencent, btn_wx_friend, btn_sms;
    private Button btn_holdr;
    private String mImagUrl;
    private Bitmap mShareBitmap = null;
    private int mShareType = 0;
    private String mUserId;
    private String mShareUrl = "";
    private String mShareTitle = "";
    private String mShareDescription = "";
    private DialogProgress progress;

    @Override
    public int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_fenxiang_time;
    }

    @Override
    protected void initView() {
        progress = new DialogProgress(DBFengXiangActivity.this);
        btn_wechat = (ImageButton) findViewById(R.id.img_btn_wechat);
        btn_wx_friend = (ImageButton) findViewById(R.id.img_btn_wx_friend);
        btn_sms = (ImageButton) findViewById(R.id.img_btn_sms);
        img_btn_tencent = (ImageButton) findViewById(R.id.img_btn_tencent);
        btn_holdr = (Button) findViewById(R.id.btn_holdr);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserId = spPreferences.getString(SpConstants.USER_ID, "");
        Intent intent = getIntent();
        mImagUrl = intent.getStringExtra(Constants.SHARE_IMG_URL);
        mShareType = intent.getIntExtra(Constants.SHARE_TYPE, 0);
        String shareUrl = intent.getStringExtra(Constants.SHARE_URL);
        if (shareUrl != null) {
            mShareUrl = shareUrl;
        }
        if (TextUtils.isEmpty(mImagUrl)) {
            mShareBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon_1);
        } else {
            mImagUrl = getImgUrl(mImagUrl); //处理分享的图片URL
            new AsyncTask<String, Nullable, Bitmap>() {
                @Override
                protected Bitmap doInBackground(String... strings) {
                    FutureTarget<Bitmap> into = Glide.with(DBFengXiangActivity.this)
                            .load(mImagUrl)
                            .asBitmap()
                            .into(300, 300);
                    Bitmap bitmap = null;
                    try {
                        bitmap = into.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return bitmap;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    mShareBitmap = bitmap;
                }
            }.execute(mImagUrl);
        }

        switch (mShareType) {
            case Constants.SHARE_APP_INFO: // 分享app
                mShareTitle = getResources().getString(R.string.app_name);
                mShareUrl = URLConstants.REALM_URL + "/appshare/" + mUserId + ".html" +
                        "?unionid=" + AccountUtils.getUnionid() + "&shareid=" + mUserId + "&from=android";
                requestAppVersion();
                break;
            default:
                String shareTitle = intent.getStringExtra(Constants.SHARE_TITLE);
                if (!TextUtils.isEmpty(shareTitle)) {
                    mShareTitle = shareTitle;
                }
                String shareDescription = intent.getStringExtra(Constants.SHARE_DESCRIPTION);
                if (!TextUtils.isEmpty(shareDescription)) {
                    mShareDescription = shareDescription;
                }
                break;
        }
    }

    @Override
    protected void initListener() {
        btn_holdr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        // 新浪
        img_btn_tencent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
                Toast.makeText(DBFengXiangActivity.this, "功能还未开发，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });

        // 微信
        btn_wechat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                wxHyShare(SendMessageToWX.Req.WXSceneSession);
            }
        });
        // 朋友圈
        btn_wx_friend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                wxHyShare(SendMessageToWX.Req.WXSceneTimeline);
            }
        });

        // 短信
        btn_sms.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                smsShare();
            }
        });
    }

    @Override
    public void requestData() {

    }


    /**
     * 分享到微信 或朋友圈
     *
     * @param shareType
     */
    public void wxHyShare(int shareType) {
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        WXWebpageObject webPage = new WXWebpageObject();
        webPage.webpageUrl = mShareUrl;
        WXMediaMessage msg = new WXMediaMessage(webPage);
        msg.title = mShareTitle;
        msg.description = mShareDescription;
        //图片加载是使用的ImageLoader.loadImageSync() 同步方法
        //并且还要创建图片的缩略图，因为微信限制了图片的大小
        if (TextUtils.isEmpty(mImagUrl)) {
            mShareBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon_1);
        } else if (mShareBitmap == null) {
            mShareBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon_1);
        }
        Bitmap thumbBmp = Bitmap.createScaledBitmap(mShareBitmap, 120, 120, true);
        msg.setThumbImage(thumbBmp);
        thumbBmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        //好友
        req.scene = shareType == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        // 调用api接口发送数据到微信
        api.sendReq(req);
    }

    /**
     * 短信分享
     */
    public void smsShare() {
        Uri uri = Uri.parse("smsto:");
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", mShareDescription + mShareUrl);
        startActivity(it);
    }

    private static final String TAG = "DBFengXiangActivity";


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    /**
     * 处理图片url
     *
     * @param imgUrl
     * @return
     */
    private String getImgUrl(String imgUrl) {
        if (imgUrl != null && imgUrl.startsWith("http")) {
            return imgUrl;
        } else {
            String trim = "";
            if (imgUrl != null) {
                trim = imgUrl.trim();
            }
            return URLConstants.REALM_URL + trim;
        }
    }

    private void requestAppVersion() {
        HttpProxy.getApkVersion(new HttpCallBack<ApkVersionInfo>() {
            @Override
            public void onSuccess(ApkVersionInfo responseData) {
                if (responseData == null) {
                    responseData = new ApkVersionInfo();
                }
                String content = responseData.getContent();
                if (TextUtils.isEmpty(content)) {
                    content = "一款放心的买车App~";
                }
                mShareDescription = content;
            }

            @Override
            public void onError(Request request, Exception e) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fanhui:
                finish();
                break;
            default:
                break;
        }
    }
}
