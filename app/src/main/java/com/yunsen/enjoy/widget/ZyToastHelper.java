package com.yunsen.enjoy.widget;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.utils.DeviceUtil;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/6/14.
 * 弹幕Tosat
 */

public class ZyToastHelper implements View.OnClickListener {
    private Context mContext;
    private ImageView mImageView;
    private TextView mToastTv;
    private Toast mToast;

    public ZyToastHelper(Context context) {
        mContext = context;
        mToast = new Toast(context);
        initView();
    }


    private void initView() {
        int xOffset = DeviceUtil.getScreenWidth() / 3;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.toast_layout, null);
        mImageView = ((ImageView) rootView.findViewById(R.id.toast_img));
        mToastTv = ((TextView) rootView.findViewById(R.id.toast_tv));
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setView(rootView);
        mToast.setGravity(Gravity.TOP, -xOffset, 200);
        rootView.setOnClickListener(this);
        try {
            Object mTN;
            mTN = getField(mToast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams != null
                        && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    //显示与隐藏动画
                    params.windowAnimations = R.style.ClickToast;
                    //Toast可点击
                    params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

                    //设置viewgroup宽高
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT; //设置Toast宽度
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT; //设置高度
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToast(String message) {
        mToastTv.setText(message);
        mToast.show();
    }

    private static final String TAG = "ZyToastHelper";

    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick: " + mToastTv.getText().toString());
    }


    /**
     * 反射字段
     *
     * @param object    要反射的对象
     * @param fieldName 要反射的字段名称
     */
    private static Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }
}
