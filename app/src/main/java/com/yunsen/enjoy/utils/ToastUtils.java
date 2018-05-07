package com.yunsen.enjoy.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/5/7.
 */

public class ToastUtils {

    private static Toast toast;

    public static void init(Context ctx) {
        toast = Toast.makeText(ctx, "", Toast.LENGTH_SHORT);
    }

    public static void makeTextShort(String info) {
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(info);
        toast.show();
    }

    public static void makeTextLong(String info) {
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setText(info);
        toast.show();
    }

    public static void makeTextShort(int resId) {
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(resId);
        toast.show();
    }

    public static void makeTextLong(int resId) {
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setText(resId);
        toast.show();
    }


}
