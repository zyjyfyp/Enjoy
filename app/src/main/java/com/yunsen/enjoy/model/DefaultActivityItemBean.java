package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/4/29.
 */

public  class DefaultActivityItemBean {
    private int id;
    private int article_id;
    private int is_default;
    private int people;
    private int price_discount;
    private int brokerage_discount;

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

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getPrice_discount() {
        return price_discount;
    }

    public void setPrice_discount(int price_discount) {
        this.price_discount = price_discount;
    }

    public int getBrokerage_discount() {
        return brokerage_discount;
    }

    public void setBrokerage_discount(int brokerage_discount) {
        this.brokerage_discount = brokerage_discount;
    }
}

