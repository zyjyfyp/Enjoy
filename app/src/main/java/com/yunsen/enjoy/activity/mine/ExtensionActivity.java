package com.yunsen.enjoy.activity.mine;

import android.graphics.Bitmap;
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
import com.yunsen.enjoy.utils.BitmapUtil;

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

    @Override
    public int getLayout() {
        return R.layout.activity_extension;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("面对面推广");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        new AsyncTask<Nullable, Nullable, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Nullable... nullables) {
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapUtil.createQRCode("你好啊！", 350);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                super.onPostExecute(result);
                if (result != null) {
                    qrCodeImg.setImageBitmap(result);
                }

            }
        }.execute();
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
