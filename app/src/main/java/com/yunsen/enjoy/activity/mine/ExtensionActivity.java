package com.yunsen.enjoy.activity.mine;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.zxing.WriterException;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.utils.BitmapUtil;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/6.
 */

public class ExtensionActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.qr_code_img)
    ImageView qrCodeImg;
    private String mShareUrl;
    private String mUserId;

    @Override
    public int getLayout() {
        return R.layout.activity_extension;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("面对面推广");
        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserId = sp.getString(SpConstants.USER_ID, "");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mShareUrl = URLConstants.REALM_URL + "/appshare/" + mUserId + ".html";
        final String path = getCacheDir().toString() + "enjoy";
        new AsyncTask<String, Nullable, Boolean>() {

            @Override
            protected Boolean doInBackground(String... str) {
                boolean flag = false;
                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon_1);
                flag = BitmapUtil.createQRImage(mShareUrl, 450, 450, icon, str[0]);
                return flag;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (result) {
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    qrCodeImg.setImageBitmap(bitmap);
                }

            }
        }.execute(path);
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.action_back, R.id.qr_code_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.qr_code_img:
                break;
        }
    }
}
