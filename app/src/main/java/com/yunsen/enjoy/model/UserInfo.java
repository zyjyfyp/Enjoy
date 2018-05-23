package com.yunsen.enjoy.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2018/5/7.
 */

public class UserInfo {
    /**
     * id : 6781
     * user_code : 113882699
     * parent_id : 0
     * parent_name :
     * user_layer : 1
     * user_list : 6781,
     * sales_id : 0
     * sales_name :
     * agency_id : 5
     * agency_name : 无代理级别
     * shops_name :
     * shops_id : 0
     * store_name :
     * store_id : 0
     * group_id : 13
     * group_name : 普通会员
     * company_id : 0
     * user_name : 13249089599
     * mobile : 13249089599
     * email :
     * avatar :
     * nick_name :
     * reg_ip : 116.24.67.175
     * identity_card :
     * identity_card_a :
     * identity_card_b :
     * real_name :
     * sex :
     * birthday : 2018-05-07 15:21:33
     * telphone :
     * province :
     * city :
     * area :
     * street :
     * address :
     * qq :
     * weixin :
     * login_sign : C3232BC5DE9FC654AA680069C5A778F8
     * login_stamp : 1525675612
     * amount : 0
     * promotion : 0
     * pension : 10
     * packet : 188
     * reserve : 0
     * reserveb : 0
     * reserves : 0
     * point : 0
     * exp : 0
     * exp_weal : 0
     * exp_invest : 0
     * exp_action : 0
     * exp_time : 0
     * vip_card :
     * is_system : 0
     * status : 0
     * reg_time : 2018-05-07 11:00:13
     * update_time : 2018-05-07 11:00:13
     * authen_time : null
     * audit_time : null
     * company : null
     */

    private int id;
    private String user_code;
    private int parent_id;
    private String parent_name;
    private int user_layer;
    private String user_list;
    private int sales_id;
    private String sales_name;
    private int agency_id;
    private String agency_name;
    private String shops_name;
    private int shops_id;
    private String store_name;
    private int store_id;
    private int group_id;
    private String group_name;
    private int company_id;
    private String user_name;
    private String mobile;
    private String email;
    private String avatar;
    private String nick_name;
    private String reg_ip;
    private String identity_card;
    private String identity_card_a;
    private String identity_card_b;
    private String real_name;
    private String sex;
    private String birthday;
    private String telphone;
    private String province;
    private String city;
    private String area;
    private String street;
    private String address;
    private String qq;
    private String weixin;
    private String login_sign;
    private String login_stamp;
    private double amount;
    private double promotion;
    private double pension;
    private double packet;
    private double reserve;
    private double reserveb;
    private double reserves;
    private double point;
    private int exp;
    private int exp_weal;
    private int exp_invest;
    private int exp_action;
    private int exp_time;
    private String vip_card;
    private int is_system;
    @JSONField(name = "status")
    private int statusX;
    private String reg_time;
    private String update_time;
    private Object authen_time;
    private Object audit_time;
    private CompanyModel company;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public int getUser_layer() {
        return user_layer;
    }

    public void setUser_layer(int user_layer) {
        this.user_layer = user_layer;
    }

    public String getUser_list() {
        return user_list;
    }

    public void setUser_list(String user_list) {
        this.user_list = user_list;
    }

    public int getSales_id() {
        return sales_id;
    }

    public void setSales_id(int sales_id) {
        this.sales_id = sales_id;
    }

    public String getSales_name() {
        return sales_name;
    }

    public void setSales_name(String sales_name) {
        this.sales_name = sales_name;
    }

    public int getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(int agency_id) {
        this.agency_id = agency_id;
    }

    public String getAgency_name() {
        return agency_name;
    }

    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    public String getShops_name() {
        return shops_name;
    }

    public void setShops_name(String shops_name) {
        this.shops_name = shops_name;
    }

    public int getShops_id() {
        return shops_id;
    }

    public void setShops_id(int shops_id) {
        this.shops_id = shops_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getReg_ip() {
        return reg_ip;
    }

    public void setReg_ip(String reg_ip) {
        this.reg_ip = reg_ip;
    }

    public String getIdentity_card() {
        return identity_card;
    }

    public void setIdentity_card(String identity_card) {
        this.identity_card = identity_card;
    }

    public String getIdentity_card_a() {
        return identity_card_a;
    }

    public void setIdentity_card_a(String identity_card_a) {
        this.identity_card_a = identity_card_a;
    }

    public String getIdentity_card_b() {
        return identity_card_b;
    }

    public void setIdentity_card_b(String identity_card_b) {
        this.identity_card_b = identity_card_b;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getLogin_sign() {
        return login_sign;
    }

    public void setLogin_sign(String login_sign) {
        this.login_sign = login_sign;
    }

    public String getLogin_stamp() {
        return login_stamp;
    }

    public void setLogin_stamp(String login_stamp) {
        this.login_stamp = login_stamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPromotion() {
        return promotion;
    }

    public void setPromotion(double promotion) {
        this.promotion = promotion;
    }

    public double getPension() {
        return pension;
    }

    public void setPension(double pension) {
        this.pension = pension;
    }

    public double getPacket() {
        return packet;
    }

    public void setPacket(double packet) {
        this.packet = packet;
    }

    public double getReserve() {
        return reserve;
    }

    public void setReserve(double reserve) {
        this.reserve = reserve;
    }

    public double getReserveb() {
        return reserveb;
    }

    public void setReserveb(double reserveb) {
        this.reserveb = reserveb;
    }

    public double getReserves() {
        return reserves;
    }

    public void setReserves(double reserves) {
        this.reserves = reserves;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExp_weal() {
        return exp_weal;
    }

    public void setExp_weal(int exp_weal) {
        this.exp_weal = exp_weal;
    }

    public int getExp_invest() {
        return exp_invest;
    }

    public void setExp_invest(int exp_invest) {
        this.exp_invest = exp_invest;
    }

    public int getExp_action() {
        return exp_action;
    }

    public void setExp_action(int exp_action) {
        this.exp_action = exp_action;
    }

    public int getExp_time() {
        return exp_time;
    }

    public void setExp_time(int exp_time) {
        this.exp_time = exp_time;
    }

    public String getVip_card() {
        return vip_card;
    }

    public void setVip_card(String vip_card) {
        this.vip_card = vip_card;
    }

    public int getIs_system() {
        return is_system;
    }

    public void setIs_system(int is_system) {
        this.is_system = is_system;
    }

    public int getStatusX() {
        return statusX;
    }

    public void setStatusX(int statusX) {
        this.statusX = statusX;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public Object getAuthen_time() {
        return authen_time;
    }

    public void setAuthen_time(Object authen_time) {
        this.authen_time = authen_time;
    }

    public Object getAudit_time() {
        return audit_time;
    }

    public void setAudit_time(Object audit_time) {
        this.audit_time = audit_time;
    }

    public CompanyModel getCompany() {
        return company;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }
}
