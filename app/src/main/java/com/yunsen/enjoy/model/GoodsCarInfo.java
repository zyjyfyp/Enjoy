package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/5/18.
 */

public class GoodsCarInfo {
    /**
     * id : 40801
     * user_id : 61219
     * user_name : 13249089599
     * sale_id : 0
     * sale_name : null
     * company_id : 96
     * company_name : 中安民生养老服务有限公司
     * title : 小软4D健康枕
     * img_url : /upload/201708/22/thumb_201708221354268145.jpg
     * article_id : 7883
     * goods_id : 17971
     * goods_no : 6945426100092
     * stock_quantity : 28974
     * market_price : 398
     * sell_price : 368
     * cost_price : 105
     * rebate_price : 0
     * cashing_packet : 368
     * cashing_point : 0
     * group_id : 13
     * group_price : 368
     * exchange_price : 0
     * exchange_point : 0
     * give_packet : 0
     * give_sinup_point : 0
     * give_sinin_point : 0
     * give_pension : 0
     * give_sinup_exp : 0
     * give_sinin_exp : 0
     * spec_text : 属性：无
     * spec_ids : ,0,
     * quantity : 1
     * update_time : 2018-05-18 11:01:52
     */
    private boolean isCheckGoods;
    private int id;
    private int user_id;
    private String user_name;
    private int sale_id;
    private Object sale_name;
    private int company_id;
    private String company_name;
    private String title;
    private String img_url;
    private int article_id;
    private int goods_id;
    private String goods_no;
    private int stock_quantity;
    private int market_price;
    private int sell_price;
    private int cost_price;
    private int rebate_price;
    private int cashing_packet;
    private int cashing_point;
    private int group_id;
    private int group_price;
    private int exchange_price;
    private int exchange_point;
    private int give_packet;
    private int give_sinup_point;
    private int give_sinin_point;
    private int give_pension;
    private int give_sinup_exp;
    private int give_sinin_exp;
    private String spec_text;
    private String spec_ids;
    private int quantity;
    private String update_time;

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

    public int getSale_id() {
        return sale_id;
    }

    public void setSale_id(int sale_id) {
        this.sale_id = sale_id;
    }

    public Object getSale_name() {
        return sale_name;
    }

    public void setSale_name(Object sale_name) {
        this.sale_name = sale_name;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        // TODO: 2018/5/18
        return "http://mobile.zams.cn"+img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_no() {
        return goods_no;
    }

    public void setGoods_no(String goods_no) {
        this.goods_no = goods_no;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public int getMarket_price() {
        return market_price;
    }

    public void setMarket_price(int market_price) {
        this.market_price = market_price;
    }

    public int getSell_price() {
        return sell_price;
    }

    public void setSell_price(int sell_price) {
        this.sell_price = sell_price;
    }

    public int getCost_price() {
        return cost_price;
    }

    public void setCost_price(int cost_price) {
        this.cost_price = cost_price;
    }

    public int getRebate_price() {
        return rebate_price;
    }

    public void setRebate_price(int rebate_price) {
        this.rebate_price = rebate_price;
    }

    public int getCashing_packet() {
        return cashing_packet;
    }

    public void setCashing_packet(int cashing_packet) {
        this.cashing_packet = cashing_packet;
    }

    public int getCashing_point() {
        return cashing_point;
    }

    public void setCashing_point(int cashing_point) {
        this.cashing_point = cashing_point;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getGroup_price() {
        return group_price;
    }

    public void setGroup_price(int group_price) {
        this.group_price = group_price;
    }

    public int getExchange_price() {
        return exchange_price;
    }

    public void setExchange_price(int exchange_price) {
        this.exchange_price = exchange_price;
    }

    public int getExchange_point() {
        return exchange_point;
    }

    public void setExchange_point(int exchange_point) {
        this.exchange_point = exchange_point;
    }

    public int getGive_packet() {
        return give_packet;
    }

    public void setGive_packet(int give_packet) {
        this.give_packet = give_packet;
    }

    public int getGive_sinup_point() {
        return give_sinup_point;
    }

    public void setGive_sinup_point(int give_sinup_point) {
        this.give_sinup_point = give_sinup_point;
    }

    public int getGive_sinin_point() {
        return give_sinin_point;
    }

    public void setGive_sinin_point(int give_sinin_point) {
        this.give_sinin_point = give_sinin_point;
    }

    public int getGive_pension() {
        return give_pension;
    }

    public void setGive_pension(int give_pension) {
        this.give_pension = give_pension;
    }

    public int getGive_sinup_exp() {
        return give_sinup_exp;
    }

    public void setGive_sinup_exp(int give_sinup_exp) {
        this.give_sinup_exp = give_sinup_exp;
    }

    public int getGive_sinin_exp() {
        return give_sinin_exp;
    }

    public void setGive_sinin_exp(int give_sinin_exp) {
        this.give_sinin_exp = give_sinin_exp;
    }

    public String getSpec_text() {
        return spec_text;
    }

    public void setSpec_text(String spec_text) {
        this.spec_text = spec_text;
    }

    public String getSpec_ids() {
        return spec_ids;
    }

    public void setSpec_ids(String spec_ids) {
        this.spec_ids = spec_ids;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public boolean isCheckGoods() {
        return isCheckGoods;
    }

    public void setCheckGoods(boolean checkGoods) {
        isCheckGoods = checkGoods;
    }
}