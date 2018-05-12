package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/5/12.
 */

public class ImgAndTextModel {
    private int resId;
    private String text;
    private int id;

    public ImgAndTextModel(int resId, String text, int id) {
        this.resId = resId;
        this.text = text;
        this.id = id;
    }

    public ImgAndTextModel(int resId, String text) {
        this.resId = resId;
        this.text = text;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
