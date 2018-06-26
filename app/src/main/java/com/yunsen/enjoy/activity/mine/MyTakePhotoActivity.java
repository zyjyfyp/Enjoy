package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.view.Window;

import com.yunsen.enjoy.R;

import org.devio.takephoto.app.TakePhotoActivity;


/**
 * Created by Administrator on 2018/6/26.
 */

public class MyTakePhotoActivity extends TakePhotoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_take_photo);        getTakePhoto().onPickMultiple(9);
        getTakePhoto().onPickMultiple(9);

    }
}
