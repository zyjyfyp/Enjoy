package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/6/28.
 */

public class ResetTypeModel {
    private String title;
    private boolean isCheck;
    private int id;

    public ResetTypeModel(String title, boolean isCheck) {
        this.title = title;
        this.isCheck = isCheck;
    }

    public ResetTypeModel(String title, boolean isCheck, int id) {
        this.title = title;
        this.isCheck = isCheck;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
