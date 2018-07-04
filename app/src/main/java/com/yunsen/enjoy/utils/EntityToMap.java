package com.yunsen.enjoy.utils;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/9.
 */

public class EntityToMap {
    /**
     * 实体类对象转换成Map
     *
     * @param obj
     * @return
     */
    private static final String TAG = "EntityToMap";

    public static Map<String, String> ConvertObjToMap(Object obj) {
        Map<String, String> reMap = new HashMap<String, String>();
        if (obj == null)
            return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                try {
                    Field f = obj.getClass().getDeclaredField(fields[i].getName());
//                    Log.e(TAG, "ConvertObjToMap: " + f.toString());
                    if (f.toString().startsWith("private")) {
                        f.setAccessible(true);
                        String o = (String) f.get(obj);
                        if (TextUtils.isEmpty(o)) {
                            o = "";
                        }
                        reMap.put(fields[i].getName(), o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return reMap;
    }
}
