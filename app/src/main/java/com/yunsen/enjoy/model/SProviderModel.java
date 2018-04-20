package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/4/20.
 */

public class SProviderModel {
    private String imgUrl;
    private String title;

    public SProviderModel() {
    }

    public SProviderModel(String imgUrl, String title) {
        this.imgUrl = imgUrl;
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
