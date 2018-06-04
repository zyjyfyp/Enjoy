package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/6/4.
 */

public class BindCardTypeBean {
    private String name;
    private boolean isCheck;

    public BindCardTypeBean(String name, boolean isCheck) {
        this.name = name;
        this.isCheck = isCheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
