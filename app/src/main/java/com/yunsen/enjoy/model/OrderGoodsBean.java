package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.URLConstants;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/5/29.
 */

public class OrderGoodsBean {
    /**
     * order_id : 27660
     * article_id : 1005
     * article_title : 内容测试
     * goods_no : SD0-0
     * goods_id : 10217
     * img_url : /upload/201805/28/201805281116120873.jpg
     * spec_ids : ,0,
     * spec_text : 属性：无
     * market_price : 0
     * sell_price : 0
     * real_price : 0
     * rebate_price : 0
     * cost_price : 0
     * is_exchange_price : 0
     * exchange_price : 0
     * is_exchange_point : 0
     * exchange_point : 0
     * is_cashing_packet : 0
     * cashing_packet : 0
     * is_cashing_point : 0
     * cashing_point : 0
     * give_packet : 0
     * give_pension : 0
     * give_sinup_point : 0
     * give_sinin_point : 0
     * give_sinup_exp : 0
     * give_sinin_exp : 0
     * quantity : 0
     * group_price : 0
     * group_id : 0
     * activity_no :
     * activity_min : 0
     * activity_price : 0
     * brokerage_price : 0
     * activity_people : 0
     * activity_member : 0
     * is_foreman : 0
     * foreman_id : 0
     * foreman_name :
     * timer_time : 2018-05-29 17:07:32
     * start_time : 2018-05-29 16:07:32
     * end_time : 2018-05-29 17:07:32
     * start_sinup : 2018-05-29 16:07:32
     * end_sinup : 2018-05-29 17:07:32
     * value_type : 0
     * allow_group :
     * allow_sex :
     * allow_min_grade : 0
     * allow_max_grade : 0
     * allow_min_age : 0
     * allow_max_age : 0
     * game_tmpl :
     * game_online : 0
     * game_rank : 0
     * game_wheel : 0
     * game_let : 0
     * award_quantity : 0
     * award_rate : 0
     * award_status : 0
     * award_no :
     */

    private int order_id;
    private int article_id;
    private String article_title;
    private String goods_no;
    private int goods_id;
    private String img_url;
    private String spec_ids;
    private String spec_text;
    private int market_price;
    private double sell_price;
    private double real_price;
    private double rebate_price;
    private double cost_price;
    private double is_exchange_price;
    private double exchange_price;
    private int is_exchange_point;
    private int exchange_point;
    private int is_cashing_packet;
    private int cashing_packet;
    private int is_cashing_point;
    private int cashing_point;
    private int give_packet;
    private int give_pension;
    private int give_sinup_point;
    private int give_sinin_point;
    private int give_sinup_exp;
    private int give_sinin_exp;
    private int quantity;
    private int group_price;
    private int group_id;
    private String activity_no;
    private int activity_min;
    private double activity_price;
    private double brokerage_price;
    private int activity_people;
    private int activity_member;
    private int is_foreman;
    private int foreman_id;
    private String foreman_name;
    private String timer_time;
    private String start_time;
    private String end_time;
    private String start_sinup;
    private String end_sinup;
    private int value_type;
    private String allow_group;
    private String allow_sex;
    private int allow_min_grade;
    private int allow_max_grade;
    private int allow_min_age;
    private int allow_max_age;
    private String game_tmpl;
    private int game_online;
    private int game_rank;
    private int game_wheel;
    private int game_let;
    private int award_quantity;
    private int award_rate;
    private int award_status;
    private String award_no;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getGoods_no() {
        return goods_no;
    }

    public void setGoods_no(String goods_no) {
        this.goods_no = goods_no;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
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

    public String getSpec_ids() {
        return spec_ids;
    }

    public void setSpec_ids(String spec_ids) {
        this.spec_ids = spec_ids;
    }

    public String getSpec_text() {
        return spec_text;
    }

    public void setSpec_text(String spec_text) {
        this.spec_text = spec_text;
    }

    public int getMarket_price() {
        return market_price;
    }

    public void setMarket_price(int market_price) {
        this.market_price = market_price;
    }

    public double getSell_price() {
        BigDecimal bg = new BigDecimal(sell_price);
        sell_price = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return sell_price;
    }

    public void setSell_price(int sell_price) {
        this.sell_price = sell_price;
    }


    public void setExchange_price(int exchange_price) {
        this.exchange_price = exchange_price;
    }

    public int getIs_exchange_point() {
        return is_exchange_point;
    }

    public void setIs_exchange_point(int is_exchange_point) {
        this.is_exchange_point = is_exchange_point;
    }

    public int getExchange_point() {
        return exchange_point;
    }

    public void setExchange_point(int exchange_point) {
        this.exchange_point = exchange_point;
    }

    public int getIs_cashing_packet() {
        return is_cashing_packet;
    }

    public void setIs_cashing_packet(int is_cashing_packet) {
        this.is_cashing_packet = is_cashing_packet;
    }

    public int getCashing_packet() {
        return cashing_packet;
    }

    public void setCashing_packet(int cashing_packet) {
        this.cashing_packet = cashing_packet;
    }

    public int getIs_cashing_point() {
        return is_cashing_point;
    }

    public void setIs_cashing_point(int is_cashing_point) {
        this.is_cashing_point = is_cashing_point;
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

    public int getGive_pension() {
        return give_pension;
    }

    public void setGive_pension(int give_pension) {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getGroup_price() {
        return group_price;
    }

    public void setGroup_price(int group_price) {
        this.group_price = group_price;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getActivity_no() {
        return activity_no;
    }

    public void setActivity_no(String activity_no) {
        this.activity_no = activity_no;
    }

    public int getActivity_min() {
        return activity_min;
    }

    public void setActivity_min(int activity_min) {
        this.activity_min = activity_min;
    }

    public void setSell_price(double sell_price) {
        this.sell_price = sell_price;
    }

    public double getReal_price() {
        return real_price;
    }

    public void setReal_price(double real_price) {
        this.real_price = real_price;
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

    public double getIs_exchange_price() {
        return is_exchange_price;
    }

    public void setIs_exchange_price(double is_exchange_price) {
        this.is_exchange_price = is_exchange_price;
    }

    public double getExchange_price() {
        return exchange_price;
    }

    public void setExchange_price(double exchange_price) {
        this.exchange_price = exchange_price;
    }

    public double getActivity_price() {
        return activity_price;
    }

    public void setActivity_price(double activity_price) {
        this.activity_price = activity_price;
    }

    public double getBrokerage_price() {
        return brokerage_price;
    }

    public void setBrokerage_price(double brokerage_price) {
        this.brokerage_price = brokerage_price;
    }

    public void setBrokerage_price(int brokerage_price) {
        this.brokerage_price = brokerage_price;
    }

    public int getActivity_people() {
        return activity_people;
    }

    public void setActivity_people(int activity_people) {
        this.activity_people = activity_people;
    }

    public int getActivity_member() {
        return activity_member;
    }

    public void setActivity_member(int activity_member) {
        this.activity_member = activity_member;
    }

    public int getIs_foreman() {
        return is_foreman;
    }

    public void setIs_foreman(int is_foreman) {
        this.is_foreman = is_foreman;
    }

    public int getForeman_id() {
        return foreman_id;
    }

    public void setForeman_id(int foreman_id) {
        this.foreman_id = foreman_id;
    }

    public String getForeman_name() {
        return foreman_name;
    }

    public void setForeman_name(String foreman_name) {
        this.foreman_name = foreman_name;
    }

    public String getTimer_time() {
        return timer_time;
    }

    public void setTimer_time(String timer_time) {
        this.timer_time = timer_time;
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

    public String getStart_sinup() {
        return start_sinup;
    }

    public void setStart_sinup(String start_sinup) {
        this.start_sinup = start_sinup;
    }

    public String getEnd_sinup() {
        return end_sinup;
    }

    public void setEnd_sinup(String end_sinup) {
        this.end_sinup = end_sinup;
    }

    public int getValue_type() {
        return value_type;
    }

    public void setValue_type(int value_type) {
        this.value_type = value_type;
    }

    public String getAllow_group() {
        return allow_group;
    }

    public void setAllow_group(String allow_group) {
        this.allow_group = allow_group;
    }

    public String getAllow_sex() {
        return allow_sex;
    }

    public void setAllow_sex(String allow_sex) {
        this.allow_sex = allow_sex;
    }

    public int getAllow_min_grade() {
        return allow_min_grade;
    }

    public void setAllow_min_grade(int allow_min_grade) {
        this.allow_min_grade = allow_min_grade;
    }

    public int getAllow_max_grade() {
        return allow_max_grade;
    }

    public void setAllow_max_grade(int allow_max_grade) {
        this.allow_max_grade = allow_max_grade;
    }

    public int getAllow_min_age() {
        return allow_min_age;
    }

    public void setAllow_min_age(int allow_min_age) {
        this.allow_min_age = allow_min_age;
    }

    public int getAllow_max_age() {
        return allow_max_age;
    }

    public void setAllow_max_age(int allow_max_age) {
        this.allow_max_age = allow_max_age;
    }

    public String getGame_tmpl() {
        return game_tmpl;
    }

    public void setGame_tmpl(String game_tmpl) {
        this.game_tmpl = game_tmpl;
    }

    public int getGame_online() {
        return game_online;
    }

    public void setGame_online(int game_online) {
        this.game_online = game_online;
    }

    public int getGame_rank() {
        return game_rank;
    }

    public void setGame_rank(int game_rank) {
        this.game_rank = game_rank;
    }

    public int getGame_wheel() {
        return game_wheel;
    }

    public void setGame_wheel(int game_wheel) {
        this.game_wheel = game_wheel;
    }

    public int getGame_let() {
        return game_let;
    }

    public void setGame_let(int game_let) {
        this.game_let = game_let;
    }

    public int getAward_quantity() {
        return award_quantity;
    }

    public void setAward_quantity(int award_quantity) {
        this.award_quantity = award_quantity;
    }

    public int getAward_rate() {
        return award_rate;
    }

    public void setAward_rate(int award_rate) {
        this.award_rate = award_rate;
    }

    public int getAward_status() {
        return award_status;
    }

    public void setAward_status(int award_status) {
        this.award_status = award_status;
    }

    public String getAward_no() {
        return award_no;
    }

    public void setAward_no(String award_no) {
        this.award_no = award_no;
    }
}