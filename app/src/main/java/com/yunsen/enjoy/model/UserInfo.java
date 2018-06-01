package com.yunsen.enjoy.model;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/5/7.
 */

public class UserInfo {

    /**
     * id : 6783
     * user_code : 115109499
     * parent_id : 0
     * parent_name :
     * user_layer : 1
     * user_list : 6783,
     * sales_id : 0
     * sales_name :
     * agency_id : 5
     * agency_name : 无代理级别
     * shops_name :
     * shops_id : 0
     * store_name :
     * store_id : 0
     * group_id : 19
     * group_name : 商品供应商
     * company_id : 6783
     * user_name : 13249089599
     * mobile : 13249089599
     * email : string
     * avatar : /upload/201805/26/201805261720281015.jpg
     * nick_name : 现在
     * reg_ip : 116.24.66.68
     * identity_card :
     * identity_card_a :
     * identity_card_b :
     * real_name :
     * sex : 男
     * birthday : 2018-05-10 00:01:44
     * telphone : string
     * province : 北京
     * city : 北京市内
     * area : 宣武区
     * street :
     * address : string
     * qq : string
     * weixin :
     * login_sign : 9ED26F26FB3365AAC031098E24589754
     * login_stamp : 1527580146
     * amount : 100
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
     * reg_time : 2018-05-08 10:00:42
     * update_time : 2018-05-08 10:00:42
     * authen_time : null
     * audit_time : null
     * company : {"id":21935,"user_id":6783,"user_name":"13249089599","shop_name":"","shop_style":"","is_system":0,"group_id":19,"agency_layer":5,"agency_id":0,"store_id":0,"shops_id":0,"parent_id":0,"company_layer":0,"company_list":null,"distance":0,"recommend_id":0,"recommend_name":null,"trade_id":0,"name":"企业名称2","content":"企业介绍","artperson":"\"\"","contact":"\u201c\u201d","tel":"\u201c\u201d","regtime":"2018-05-09","nature":"\u201c\u201d","post_code":"\u201c\u201d","email":"\u201c\u201d","mobile":"联系人电话","address":"地址","sort_id":0,"logo_url":"企业logo","img_url":"\u201c\u201d","seo_title":"\u201c\u201d","seo_keywords":"\u201c\u201d","seo_description":"\u201c\u201d","province":"\u201c\u201d","city":"\u201c\u201d","area":"\u201c\u201d","street":null,"add_time":"2018-05-17 18:14:12","update_time":"2018-05-24 09:17:06","lng":0,"lat":0,"advantage":"企业优势","idcard":"","idcard_a":"\u201c\u201d","idcard_b":"\u201c\u201d","license":"工商营业执照","accredit":"\u201c\u201d","aptitude":"\u201c\u201d","revenue_card":"税务","organi_card":"组织机构代码证","status":0,"is_lock":0,"is_red":0,"brand_card":"\u201c\u201d","bank_licence":"\u201c\u201d","trade_aptitude":"\u201c\u201d","account_name":"\u201c\u201d","bank_name":"\u201c\u201d","bank_account":"\u201c\u201d","registeredid":"\u201c\u201d","datatype":"Supply","service_time":"09:00-10:00","settle_time":1,"service":[{"user_id":6783,"service_id":1,"service_list":",1,","update_time":"2018-05-24 09:17:06"}]}
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
    private int promotion;
    private int pension;
    private double packet;
    private double reserve;
    private int reserveb;
    private int reserves;
    private int point;
    private int exp;
    private int exp_weal;
    private int exp_invest;
    private int exp_action;
    private int exp_time;
    private String vip_card;
    private int is_system;
    private int status;
    private String reg_time;
    private String update_time;
    private Object authen_time;
    private Object audit_time;
    private CompanyBean company;

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

    public String getAmountStr() {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(amount);
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public int getPension() {
        return pension;
    }

    public void setPension(int pension) {
        this.pension = pension;
    }


    public int getReserveb() {
        return reserveb;
    }

    public void setReserveb(int reserveb) {
        this.reserveb = reserveb;
    }

    public int getReserves() {
        return reserves;
    }

    public void setReserves(int reserves) {
        this.reserves = reserves;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public CompanyBean getCompany() {
        return company;
    }

    public void setCompany(CompanyBean company) {
        this.company = company;
    }

    public static class CompanyBean {
        /**
         * id : 21935
         * user_id : 6783
         * user_name : 13249089599
         * shop_name :
         * shop_style :
         * is_system : 0
         * group_id : 19
         * agency_layer : 5
         * agency_id : 0
         * store_id : 0
         * shops_id : 0
         * parent_id : 0
         * company_layer : 0
         * company_list : null
         * distance : 0
         * recommend_id : 0
         * recommend_name : null
         * trade_id : 0
         * name : 企业名称2
         * content : 企业介绍
         * artperson : ""
         * contact : “”
         * tel : “”
         * regtime : 2018-05-09
         * nature : “”
         * post_code : “”
         * email : “”
         * mobile : 联系人电话
         * address : 地址
         * sort_id : 0
         * logo_url : 企业logo
         * img_url : “”
         * seo_title : “”
         * seo_keywords : “”
         * seo_description : “”
         * province : “”
         * city : “”
         * area : “”
         * street : null
         * add_time : 2018-05-17 18:14:12
         * update_time : 2018-05-24 09:17:06
         * lng : 0
         * lat : 0
         * advantage : 企业优势
         * idcard :
         * idcard_a : “”
         * idcard_b : “”
         * license : 工商营业执照
         * accredit : “”
         * aptitude : “”
         * revenue_card : 税务
         * organi_card : 组织机构代码证
         * status : 0
         * is_lock : 0
         * is_red : 0
         * brand_card : “”
         * bank_licence : “”
         * trade_aptitude : “”
         * account_name : “”
         * bank_name : “”
         * bank_account : “”
         * registeredid : “”
         * datatype : Supply
         * service_time : 09:00-10:00
         * settle_time : 1
         * service : [{"user_id":6783,"service_id":1,"service_list":",1,","update_time":"2018-05-24 09:17:06"}]
         */

        private int id;
        private int user_id;
        private String user_name;
        private String shop_name;
        private String shop_style;
        private int is_system;
        private int group_id;
        private int agency_layer;
        private int agency_id;
        private int store_id;
        private int shops_id;
        private int parent_id;
        private int company_layer;
        private Object company_list;
        private int distance;
        private int recommend_id;
        private Object recommend_name;
        private int trade_id;
        private String name;
        private String content;
        private String artperson;
        private String contact;
        private String tel;
        private String regtime;
        private String nature;
        private String post_code;
        private String email;
        private String mobile;
        private String address;
        private int sort_id;
        private String logo_url;
        private String img_url;
        private String seo_title;
        private String seo_keywords;
        private String seo_description;
        private String province;
        private String city;
        private String area;
        private Object street;
        private String add_time;
        private String update_time;
        private int lng;
        private int lat;
        private String advantage;
        private String idcard;
        private String idcard_a;
        private String idcard_b;
        private String license;
        private String accredit;
        private String aptitude;
        private String revenue_card;
        private String organi_card;
        private int status;
        private int is_lock;
        private int is_red;
        private String brand_card;
        private String bank_licence;
        private String trade_aptitude;
        private String account_name;
        private String bank_name;
        private String bank_account;
        private String registeredid;
        private String datatype;
        private String service_time;
        private int settle_time;
        private List<ServiceBean> service;

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

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_style() {
            return shop_style;
        }

        public void setShop_style(String shop_style) {
            this.shop_style = shop_style;
        }

        public int getIs_system() {
            return is_system;
        }

        public void setIs_system(int is_system) {
            this.is_system = is_system;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public int getAgency_layer() {
            return agency_layer;
        }

        public void setAgency_layer(int agency_layer) {
            this.agency_layer = agency_layer;
        }

        public int getAgency_id() {
            return agency_id;
        }

        public void setAgency_id(int agency_id) {
            this.agency_id = agency_id;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public int getShops_id() {
            return shops_id;
        }

        public void setShops_id(int shops_id) {
            this.shops_id = shops_id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getCompany_layer() {
            return company_layer;
        }

        public void setCompany_layer(int company_layer) {
            this.company_layer = company_layer;
        }

        public Object getCompany_list() {
            return company_list;
        }

        public void setCompany_list(Object company_list) {
            this.company_list = company_list;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getRecommend_id() {
            return recommend_id;
        }

        public void setRecommend_id(int recommend_id) {
            this.recommend_id = recommend_id;
        }

        public Object getRecommend_name() {
            return recommend_name;
        }

        public void setRecommend_name(Object recommend_name) {
            this.recommend_name = recommend_name;
        }

        public int getTrade_id() {
            return trade_id;
        }

        public void setTrade_id(int trade_id) {
            this.trade_id = trade_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getArtperson() {
            return artperson;
        }

        public void setArtperson(String artperson) {
            this.artperson = artperson;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getRegtime() {
            return regtime;
        }

        public void setRegtime(String regtime) {
            this.regtime = regtime;
        }

        public String getNature() {
            return nature;
        }

        public void setNature(String nature) {
            this.nature = nature;
        }

        public String getPost_code() {
            return post_code;
        }

        public void setPost_code(String post_code) {
            this.post_code = post_code;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getSort_id() {
            return sort_id;
        }

        public void setSort_id(int sort_id) {
            this.sort_id = sort_id;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
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

        public Object getStreet() {
            return street;
        }

        public void setStreet(Object street) {
            this.street = street;
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

        public int getLng() {
            return lng;
        }

        public void setLng(int lng) {
            this.lng = lng;
        }

        public int getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }

        public String getAdvantage() {
            return advantage;
        }

        public void setAdvantage(String advantage) {
            this.advantage = advantage;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getIdcard_a() {
            return idcard_a;
        }

        public void setIdcard_a(String idcard_a) {
            this.idcard_a = idcard_a;
        }

        public String getIdcard_b() {
            return idcard_b;
        }

        public void setIdcard_b(String idcard_b) {
            this.idcard_b = idcard_b;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getAccredit() {
            return accredit;
        }

        public void setAccredit(String accredit) {
            this.accredit = accredit;
        }

        public String getAptitude() {
            return aptitude;
        }

        public void setAptitude(String aptitude) {
            this.aptitude = aptitude;
        }

        public String getRevenue_card() {
            return revenue_card;
        }

        public void setRevenue_card(String revenue_card) {
            this.revenue_card = revenue_card;
        }

        public String getOrgani_card() {
            return organi_card;
        }

        public void setOrgani_card(String organi_card) {
            this.organi_card = organi_card;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIs_lock() {
            return is_lock;
        }

        public void setIs_lock(int is_lock) {
            this.is_lock = is_lock;
        }

        public int getIs_red() {
            return is_red;
        }

        public void setIs_red(int is_red) {
            this.is_red = is_red;
        }

        public String getBrand_card() {
            return brand_card;
        }

        public void setBrand_card(String brand_card) {
            this.brand_card = brand_card;
        }

        public String getBank_licence() {
            return bank_licence;
        }

        public void setBank_licence(String bank_licence) {
            this.bank_licence = bank_licence;
        }

        public String getTrade_aptitude() {
            return trade_aptitude;
        }

        public void setTrade_aptitude(String trade_aptitude) {
            this.trade_aptitude = trade_aptitude;
        }

        public String getAccount_name() {
            return account_name;
        }

        public void setAccount_name(String account_name) {
            this.account_name = account_name;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_account() {
            return bank_account;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }

        public String getRegisteredid() {
            return registeredid;
        }

        public void setRegisteredid(String registeredid) {
            this.registeredid = registeredid;
        }

        public String getDatatype() {
            return datatype;
        }

        public void setDatatype(String datatype) {
            this.datatype = datatype;
        }

        public String getService_time() {
            return service_time;
        }

        public void setService_time(String service_time) {
            this.service_time = service_time;
        }

        public int getSettle_time() {
            return settle_time;
        }

        public void setSettle_time(int settle_time) {
            this.settle_time = settle_time;
        }

        public List<ServiceBean> getService() {
            return service;
        }

        public void setService(List<ServiceBean> service) {
            this.service = service;
        }

    }
}
