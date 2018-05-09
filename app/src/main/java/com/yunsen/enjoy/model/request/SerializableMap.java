package com.yunsen.enjoy.model.request;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/9.
 */

public class SerializableMap implements Serializable {
    private Map<String, String> maps;

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public void put(String key, String value) {
        maps.put(key, value);
    }

    public String get(String key) {
        return maps.get(key);
    }
}
