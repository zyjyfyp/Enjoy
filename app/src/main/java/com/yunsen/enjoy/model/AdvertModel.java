package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.URLConstants;

/**
 * Created by Administrator on 2018/4/20.
 */

public class AdvertModel {

    /**
     * id : 94
     * aid : 13
     * terminal_id : 2
     * category_id : 0
     * article_id : 0
     * title : 乐享新车
     * start_time : 2016-08-24 21:09:22
     * end_time : 2019-08-24 21:09:22
     * ad_url : /upload/201608/24/201608242109447736.jpg
     * link_url : /pages/goods/goods_show?id=14837
     * remark :
     * sort_id : 1
     * status : 0
     * user_id : 0
     * user_name : null
     * company_id : 0
     * company_name : 自营
     * add_time : 2016-08-24 21:09:46
     * update_time : 2017-03-28 09:20:36
     */

    private int id;
    private int aid;
    private int terminal_id;
    private int category_id;
    private int article_id;
    private String title;
    private String start_time;
    private String end_time;
    private String ad_url;
    private String link_url;
    private String remark;
    private int sort_id;
    private int status;
    private int user_id;
    private String user_name;
    private int company_id;
    private String company_name;
    private String add_time;
    private String update_time;
    private int rseImg;

    public AdvertModel() {
    }

    public AdvertModel(int rseImg, String ad_url) {
        this.rseImg = rseImg;
        this.ad_url = ad_url;
    }

    public int getRseImg() {
        return rseImg;
    }

    public void setRseImg(int rseImg) {
        this.rseImg = rseImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(int terminal_id) {
        this.terminal_id = terminal_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getAd_url() {
        if (ad_url != null && ad_url.matches("https")) {
            return ad_url;
        } else {
            return URLConstants.REALM_URL + ad_url;
        }
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSort_id() {
        return sort_id;
    }

    public void setSort_id(int sort_id) {
        this.sort_id = sort_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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

    @Override
    public String toString() {
        return "Advert{" +
                "id=" + id +
                ", aid=" + aid +
                ", terminal_id=" + terminal_id +
                ", category_id=" + category_id +
                ", article_id=" + article_id +
                ", title='" + title + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", ad_url='" + ad_url + '\'' +
                ", link_url='" + link_url + '\'' +
                ", remark='" + remark + '\'' +
                ", sort_id=" + sort_id +
                ", status=" + status +
                ", user_id=" + user_id +
                ", user_name=" + user_name +
                ", company_id=" + company_id +
                ", company_name='" + company_name + '\'' +
                ", add_time='" + add_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}

