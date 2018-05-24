package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.URLConstants;

/**
 * Created by Administrator on 2018/4/20.
 */

public class CarModel {
    /**
     * id : 666
     * user_id : 1
     * user_name : administrator
     * channel_id : 7
     * title : 奔驰
     * call_index : 9567704101718240476
     * parent_id : 0
     * class_list : ,666,
     * class_layer : 1
     * sort_id : 99
     * link_url :
     * img_url : /upload/201804/10/201804101720016594.jpg
     * content :
     * seo_title :
     * seo_keywords :
     * seo_description :
     * update_time : 2018-04-10 17:20:03
     */

    private int id;
    private int user_id;
    private String user_name;
    private int channel_id;
    private String title;
    private String call_index;
    private int parent_id;
    private String class_list;
    private int class_layer;
    private int sort_id;
    private String link_url;
    private String img_url;
    private String content;
    private String seo_title;
    private String seo_keywords;
    private String seo_description;
    private String update_time;

    public CarModel() {
    }

    public CarModel(String title, String img_url) {
        this.title = title;
        this.img_url = img_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getCall_index() {
        return call_index;
    }

    public void setCall_index(String call_index) {
        this.call_index = call_index;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getClass_list() {
        return class_list;
    }

    public void setClass_list(String class_list) {
        this.class_list = class_list;
    }

    public int getClass_layer() {
        return class_layer;
    }

    public void setClass_layer(int class_layer) {
        this.class_layer = class_layer;
    }

    public int getSort_id() {
        return sort_id;
    }

    public void setSort_id(int sort_id) {
        this.sort_id = sort_id;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getImg_url() {
        if (img_url != null && img_url.startsWith("http")) {
            return img_url;
        } else {
            return URLConstants.REALM_URL + img_url;
        }
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSeo_title() {
        return seo_title;
    }

    public void setSeo_title(String seo_title) {
        this.seo_title = seo_title;
    }

    public String getSeo_keywords() {
        return seo_keywords;
    }

    public void setSeo_keywords(String seo_keywords) {
        this.seo_keywords = seo_keywords;
    }

    public String getSeo_description() {
        return seo_description;
    }

    public void setSeo_description(String seo_description) {
        this.seo_description = seo_description;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}