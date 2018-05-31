package com.yunsen.enjoy.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/5/22.
 */

public class DefaultSpecItemBean {
    private int goods_id;
    private int article_id;
    private String goods_no;
    private int stock_quantity;
    private double market_price;
    private double sell_price;
    private double first_payment;
    private int monthly_supply;
    private int term;
    private double rebate_price;
    private double cost_price;
    private double exchange_price;
    private int exchange_point;
    private double cashing_packet;
    private int cashing_point;
    private int give_packet;
    private double give_pension;
    private int give_sinup_point;
    private int give_sinin_point;
    private int give_sinup_exp;
    private int give_sinin_exp;
    private String spec_text;
    private String spec_ids;
    private int is_default;
    private int is_lock;
    private String item_time;
    /**
     * article_id : 0
     * goods_id : 0
     * group_id : 0
     * price : 0
     * is_default : 0
     */

    private DefaultPriceBean default_group_price;
    /**
     * article_id : 0
     * goods_id : 0
     * people : 0
     * is_default : 0
     * price : 0
     */

    private DefaultPriceBean default_activity_price;
    /**
     * article_id : 14837
     * goods_id : 8212
     * group_id : 13
     * price : 0.01
     * is_default : 1
     */

    private List<GroupPriceBean> group_price;
    private List<?> activity_price;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
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

    public double getMarket_price() {
        BigDecimal bg = new BigDecimal(market_price);
        market_price = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return market_price;
    }

    public String getMarkePriceStr() {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(market_price);
    }

    public void setMarket_price(double market_price) {
        this.market_price = market_price;
    }

    public double getSell_price() {
        BigDecimal bg = new BigDecimal(sell_price);
        sell_price = bg.setScale(2, BigDecimal.ROUND_FLOOR).doubleValue();
        return sell_price;
    }

    public String getSellPriceStr() {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(sell_price);
    }

    public void setSell_price(double sell_price) {
        this.sell_price = sell_price;
    }

    public double getFirst_payment() {
        return first_payment;
    }

    public void setFirst_payment(double first_payment) {
        this.first_payment = first_payment;
    }

    public int getMonthly_supply() {
        return monthly_supply;
    }

    public void setMonthly_supply(int monthly_supply) {
        this.monthly_supply = monthly_supply;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public double getRebate_price() {
        return rebate_price;
    }

    public void setRebate_price(double rebate_price) {
        this.rebate_price = rebate_price;
    }

    public double getCost_price() {
        return cost_price;
    }

    public void setCost_price(double cost_price) {
        this.cost_price = cost_price;
    }

    public double getExchange_price() {
        return exchange_price;
    }

    public String getExchange_priceStr() {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(exchange_price);
    }

    public void setExchange_price(double exchange_price) {
        this.exchange_price = exchange_price;
    }

    public int getExchange_point() {
        return exchange_point;
    }

    public void setExchange_point(int exchange_point) {
        this.exchange_point = exchange_point;
    }

    public double getCashing_packet() {
        return cashing_packet;
    }

    public String getCashing_packetStr() {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(cashing_packet);
    }

    public void setCashing_packet(double cashing_packet) {
        this.cashing_packet = cashing_packet;
    }

    public int getCashing_point() {
        return cashing_point;
    }

    public void setCashing_point(int cashing_point) {
        this.cashing_point = cashing_point;
    }

    public int getGive_packet() {
        return give_packet;
    }

    public void setGive_packet(int give_packet) {
        this.give_packet = give_packet;
    }

    public double getGive_pension() {
        return give_pension;
    }

    public void setGive_pension(double give_pension) {
        this.give_pension = give_pension;
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

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public int getIs_lock() {
        return is_lock;
    }

    public void setIs_lock(int is_lock) {
        this.is_lock = is_lock;
    }

    public String getItem_time() {
        return item_time;
    }

    public void setItem_time(String item_time) {
        this.item_time = item_time;
    }

    public DefaultPriceBean getDefault_group_price() {
        return default_group_price;
    }

    public void setDefault_group_price(DefaultPriceBean default_group_price) {
        this.default_group_price = default_group_price;
    }

    public DefaultPriceBean getDefault_activity_price() {
        return default_activity_price;
    }

    public void setDefault_activity_price(DefaultPriceBean default_activity_price) {
        this.default_activity_price = default_activity_price;
    }

    public List<GroupPriceBean> getGroup_price() {
        return group_price;
    }

    public void setGroup_price(List<GroupPriceBean> group_price) {
        this.group_price = group_price;
    }

    public List<?> getActivity_price() {
        return activity_price;
    }

    public void setActivity_price(List<?> activity_price) {
        this.activity_price = activity_price;
    }


}