package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/4/27.
 */

public class CarServiceModel {
    private int imgResId;
    private String title;
    private String content;

    public CarServiceModel() {
    }

    public CarServiceModel(int imgResId, String title, String content) {
        this.imgResId = imgResId;
        this.title = title;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
