package com.yunsen.enjoy.model.event;

/**
 * Created by Administrator on 2018/5/10.
 */

public class PullImageEvent {
    private int evenId;
    private String imgUrl;

    public PullImageEvent(int evenId, String imgUrl) {
        this.evenId = evenId;
        this.imgUrl = imgUrl;
    }

    public int getEvenId() {
        return evenId;
    }

    public void setEvenId(int evenId) {
        this.evenId = evenId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
