package com.yunsen.enjoy.model;

public class SpListData {

    public String id;
    public String title;
    public String sub_title;
    public String img_url;
    public String market_price;
    public String sell_price;
    public String cashing_packet;
    public String category_id;

    public String getCashing_packet() {
        return cashing_packet;
    }

    public void setCashing_packet(String cashing_packet) {
        this.cashing_packet = cashing_packet;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }


}
