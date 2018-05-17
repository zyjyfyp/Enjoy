package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/5/17.
 */

public class GradeFlagModel {
    private String text;
    private int id;
    private boolean hasCheck;

    public GradeFlagModel(String text, int id, boolean hasCheck) {
        this.text = text;
        this.id = id;
        this.hasCheck = hasCheck;
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

    public boolean isHasCheck() {
        return hasCheck;
    }

    public void setHasCheck(boolean hasCheck) {
        this.hasCheck = hasCheck;
    }
}
