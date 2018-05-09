package com.yunsen.enjoy.utils;

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
    public static Map<String, Object> ConvertObjToMap(Object obj) {
        Map<String, Object> reMap = new HashMap<String, Object>();
        if (obj == null)
            return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                try {
                    Field f = obj.getClass().getDeclaredField(fields[i].getName());
                    Log.e(TAG, "ConvertObjToMap: "+f.toString() );
                    if (f.toString().startsWith("private")) {
                        f.setAccessible(true);
                        Object o = f.get(obj);
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
