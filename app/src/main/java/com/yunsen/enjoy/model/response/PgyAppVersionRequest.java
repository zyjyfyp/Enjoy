package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.model.PgyAppVersion;

/**
 * Created by Administrator on 2018/7/19/019.
 */

public class PgyAppVersionRequest {

    /**
     * code : 0
     * message :
     * data : {"buildBuildVersion":"6","downloadURL":"https://www.pgyer.com/app/installUpdate/db97532dee6fd88104a694fc929c43c0?sig=8g0JNI%2Fg%2BXiLgSyM1R2HYVMQ8BRzxps96jQfS0IwkAYRkmXJVSwACgVL01wWl%2Ffy","buildHaveNewVersion":true,"buildVersionNo":"101","buildVersion":"1.0.1","buildShortcutUrl":"https://www.pgyer.com/UP9Q","buildUpdateDescription":""}
     */

    private int code;
    private String message;
    private PgyAppVersion data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PgyAppVersion getData() {
        return data;
    }

    public void setData(PgyAppVersion data) {
        this.data = data;
    }


}
