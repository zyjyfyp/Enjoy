package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.URLConstants;

/**
 * Created by Administrator on 2018/4/23.
 */

public class AlbumsBean {
    /**
     * id : 43910
     * article_id : 10284
     * thumb_path : /upload/201612/15/thumb_201612151928555185.jpg
     * original_path : /upload/201612/15/201612151928555185.jpg
     * remark :
     * add_time : 2018-04-23 15:22:39
     * update_time : 2017-08-17 17:34:55
     */

    private int id;
    private int article_id;
    private String thumb_path;
    private String original_path;
    private String remark;
    private String add_time;
    private String update_time;
    private int resId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getThumb_path() {
        if (thumb_path != null && thumb_path.startsWith("http")) {
            return thumb_path;
        } else {
            return URLConstants.REALM_URL + thumb_path;
        }
    }

    public void setThumb_path(String thumb_path) {
        this.thumb_path = thumb_path;
    }

    public String getOriginal_path() {
        if (original_path != null && original_path.startsWith("http")) {
            return original_path;
        } else {
            return URLConstants.REALM_URL + original_path;
        }
    }

    public void setOriginal_path(String original_path) {
        this.original_path = original_path;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}