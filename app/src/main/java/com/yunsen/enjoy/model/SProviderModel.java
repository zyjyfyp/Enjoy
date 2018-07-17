package com.yunsen.enjoy.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.yunsen.enjoy.http.URLConstants;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */

public  class SProviderModel {
    /**
     * id : 20933
     * user_name : 13316989009
     * group_id : 19
     * agency_layer : 5
     * parent_id : 0
     * user_id : 4782
     * recommend_id : 0
     * recommend_name : null
     * name : 深圳南山源兴科技大厦服务商
     * content : null
     * contact : 马先生
     * tel :
     * regtime :
     * nature : null
     * post_code : null
     * email : null
     * mobile : 13316989009
     * artperson : 马先生
     * img_url : /upload/201805/05/201805052127027884.png
     * sort_id : 99
     * seo_title :
     * seo_keywords :
     * seo_description :
     * province : 广东省
     * city : 深圳市
     * area : 南山区
     * lng : 0
     * lat : 0
     * address : 源兴科技大厦南座
     * advantage :
     * idcard :
     * idcard_a :
     * idcard_b :
     * license :
     * accredit :
     * aptitude :
     * revenue_card :
     * organi_card :
     * status : 0
     * brand_card :
     * bank_licence :
     * trade_aptitude :
     * trade_id : 275
     * account_name :
     * bank_name :
     * bank_account :
     * registeredid :
     * logo_url :
     * datatype : Supply
     * distance : 0
     * update_time : 2018-05-05 21:27:08
     * add_time : 2018-05-05 21:25:28
     * article : []
     */

    private int id;
    private String user_name;
    private int group_id;
    private int agency_layer;
    private int parent_id;
    private int user_id;
    private int recommend_id;
    private Object recommend_name;
    private String name;
    private String content;
    private String contact;
    private String tel;
    private String regtime;
    private Object nature;
    private String post_code;
    private Object email;
    private String mobile;
    private String artperson;
    private String img_url;
    private int sort_id;
    private String seo_title;
    private String seo_keywords;
    private String seo_description;
    private String province;
    private String city;
    private String area;
    private int lng;
    private int lat;
    private String address;
    private String advantage;
    private String idcard;
    private String idcard_a;
    private String idcard_b;
    private String license;
    private String accredit;
    private String aptitude;
    private String revenue_card;
    private String organi_card;
    @JSONField(name = "status")
    private int statusX;
    private String brand_card;
    private String bank_licence;
    private String trade_aptitude;
    private int trade_id;
    private String account_name;
    private String bank_name;
    private String bank_account;
    private String registeredid;
    private String logo_url;
    private String datatype;
    private int distance;
    private String update_time;
    private String add_time;
    private List<?> article;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public Object getNature() {
        return nature;
    }

    public void setNature(Object nature) {
        this.nature = nature;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getArtperson() {
        return artperson;
    }

    public void setArtperson(String artperson) {
        this.artperson = artperson;
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

    public int getSort_id() {
        return sort_id;
    }

    public void setSort_id(int sort_id) {
        this.sort_id = sort_id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getStatusX() {
        return statusX;
    }

    public void setStatusX(int statusX) {
        this.statusX = statusX;
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

    public int getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(int trade_id) {
        this.trade_id = trade_id;
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

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public List<?> getArticle() {
        return article;
    }

    public void setArticle(List<?> article) {
        this.article = article;
    }
}
