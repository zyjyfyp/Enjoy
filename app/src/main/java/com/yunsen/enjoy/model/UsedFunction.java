package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/6/20.
 */

public class UsedFunction {
    private int imgResId;
    private String title;

    public UsedFunction(int imgResId, String title) {
        this.imgResId = imgResId;
        this.title = title;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
