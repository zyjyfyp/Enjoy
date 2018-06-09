package com.yunsen.enjoy.common.wsmanager;

import com.alibaba.fastjson.JSON;

/**
 * Created by Administrator on 2018/6/9.
 */

public class Codec {

    public static Response decoder(String text) {
        Response parseObject = JSON.parseObject(text, Response.class);
        return parseObject;
    }

}
