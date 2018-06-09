package com.yunsen.enjoy.common.wsmanager;

/**
 * Created by Administrator on 2018/6/9.
 */

public class ChildResponse {

    private int code;
    private String msg;
    private String data;

    public boolean isOK() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

//省略get set方法
}