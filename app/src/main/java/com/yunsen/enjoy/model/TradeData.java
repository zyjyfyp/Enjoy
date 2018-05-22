package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.URLConstants;

/**
 * Created by Administrator on 2018/5/10.
 */

public  class TradeData {
    private int id;
    private int parent_id;
    private String title;
    private String img_url;
    private String icon_url;

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
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }
}