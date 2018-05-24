package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/5/24.
 */

public class ClassifyBean {
    /**
     * id : 1698
     * parent_id : 0
     * channel_id : 7
     * title : 两厢轿车
     * content :
     * img_url :
     */

    private int id;
    private int parent_id;
    private int channel_id;
    private String title;
    private String content;
    private String img_url;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}