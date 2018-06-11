package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.URLConstants;

/**
 * Created by Administrator on 2018/6/11.
 */

public class HeightFilterBean {
    /**
     * id : 1698
     * parent_id : 0
     * channel_id : 7
     * title : 两厢轿车
     * img_url :
     * icon_url : /upload/201805/17/201805171828389332.png
     * content :
     */

    private int id;
    private int parent_id;
    private int channel_id;
    private String title;
    private String img_url;
    private String icon_url;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        if (img_url != null && img_url.startsWith("http")) {
            return img_url;
        } else {
            String trim = "";
            if (img_url != null) {
                trim = img_url.trim();
            }
            return URLConstants.REALM_URL + trim;
        }
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getIcon_url() {
        if (icon_url != null && icon_url.startsWith("http")) {
            return icon_url;
        } else {
            String trim = "";
            if (icon_url != null) {
                trim = icon_url.trim();
            }
            return URLConstants.REALM_URL + trim;
        }
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}