package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/4/29.
 */


public  class DatatypeBean {
    private int article_id;
    private int channel_id;
    private int datatype_id;
    private String datatype_title;
    private String datatype_list;
    private String update_time;

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public int getDatatype_id() {
        return datatype_id;
    }

    public void setDatatype_id(int datatype_id) {
        this.datatype_id = datatype_id;
    }

    public String getDatatype_title() {
        return datatype_title;
    }

    public void setDatatype_title(String datatype_title) {
        this.datatype_title = datatype_title;
    }

    public String getDatatype_list() {
        return datatype_list;
    }

    public void setDatatype_list(String datatype_list) {
        this.datatype_list = datatype_list;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}