package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.URLConstants;

/**
 * Created by Administrator on 2018/5/10.
 */

public  class PullImageResult {
    private String img_url;

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
}