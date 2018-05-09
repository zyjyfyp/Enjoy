package com.yunsen.enjoy.model.request;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/5/9.
 */

public class ApplyFacilitatorModel implements Parcelable{
    private String user_id;//  用户名ID：user_id
    private String user_name;//  用户名：user_name
    private String trade_id;//   行业Id：trade_id
    private String name;//   企业名称：name
    private String content;//   企业介绍：content
    private String artperson;//   法人代表：artperson
    private String contact;//   联系人：contact
    private String mobile;//   联系人电话：mobile
    private String tel;//   企业电话：tel
    private String nature;//   企业性质：nature
    private String post_code;//   邮编：post_code
    private String email;//   电子邮件：email
    private String address;//   地址：address
    private String sort_id;//   排序：sort_id
    private String logo_url;//   企业logo：logo_url
    private String img_url;//   企业图片：img_url
    private String seo_title;//   seo标题：seo_title
    private String seo_keywords;//   seo关键字：seo_keywords
    private String seo_description;//   seo描述：seo_description
    private String province;//   省份：province
    private String city;//   城市：city
    private String area;//   区县：area
    private String regtime;//   注册时间：regtime
    private String lng;//   经度：lng
    private String lat;//   纬度：lat
    private String advantage;//   企业优势：advantage
    private String idcard_a;//   法人身份证(正面)：idcard_a
    private String idcard_b;//   法人身份证(反面)：idcard_b
    private String license;//   工商营业执照：license
    private String accredit;//   厂家授权或者厂家合同：accredit
    private String aptitude;//   企业资质：aptitude
    private String revenue_card;//   税务：revenue_card
    private String organi_card;//   组织机构代码证：organi_card
    private String brand_card;//   品牌注册证：brand_card
    private String licence_card;//   开户行许许可证：licence_card
    private String trade_aptitude;//   行业资质证明文件：trade_aptitude
    private String account_name;//   企业开户名称：account_name
    private String bank_name;//   企业开户银行：bank_name
    private String bank_account;//   企业银行账号：bank_account
    private String registeredid;//   工商执照注册号：registeredid
    private String service_time;//   服务时间：service_time
    private String service_ids;//   服务项目ID：service_ids，如：1,2,3

    public ApplyFacilitatorModel() {
    }

    protected ApplyFacilitatorModel(Parcel in) {
        user_id = in.readString();
        user_name = in.readString();
        trade_id = in.readString();
        name = in.readString();
        content = in.readString();
        artperson = in.readString();
        contact = in.readString();
        mobile = in.readString();
        tel = in.readString();
        nature = in.readString();
        post_code = in.readString();
        email = in.readString();
        address = in.readString();
        sort_id = in.readString();
        logo_url = in.readString();
        img_url = in.readString();
        seo_title = in.readString();
        seo_keywords = in.readString();
        seo_description = in.readString();
        province = in.readString();
        city = in.readString();
        area = in.readString();
        regtime = in.readString();
        lng = in.readString();
        lat = in.readString();
        advantage = in.readString();
        idcard_a = in.readString();
        idcard_b = in.readString();
        license = in.readString();
        accredit = in.readString();
        aptitude = in.readString();
        revenue_card = in.readString();
        organi_card = in.readString();
        brand_card = in.readString();
        licence_card = in.readString();
        trade_aptitude = in.readString();
        account_name = in.readString();
        bank_name = in.readString();
        bank_account = in.readString();
        registeredid = in.readString();
        service_time = in.readString();
        service_ids = in.readString();
    }

    public static final Creator<ApplyFacilitatorModel> CREATOR = new Creator<ApplyFacilitatorModel>() {
        @Override
        public ApplyFacilitatorModel createFromParcel(Parcel in) {
            return new ApplyFacilitatorModel(in);
        }

        @Override
        public ApplyFacilitatorModel[] newArray(int size) {
            return new ApplyFacilitatorModel[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(String trade_id) {
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSort_id() {
        return sort_id;
    }

    public void setSort_id(String sort_id) {
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

    public String getRegtime() {
        return regtime;
    }

    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
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

    public String getBrand_card() {
        return brand_card;
    }

    public void setBrand_card(String brand_card) {
        this.brand_card = brand_card;
    }

    public String getLicence_card() {
        return licence_card;
    }

    public void setLicence_card(String licence_card) {
        this.licence_card = licence_card;
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

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getService_ids() {
        return service_ids;
    }

    public void setService_ids(String service_ids) {
        this.service_ids = service_ids;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(user_name);
        dest.writeString(trade_id);
        dest.writeString(name);
        dest.writeString(content);
        dest.writeString(artperson);
        dest.writeString(contact);
        dest.writeString(mobile);
        dest.writeString(tel);
        dest.writeString(nature);
        dest.writeString(post_code);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(sort_id);
        dest.writeString(logo_url);
        dest.writeString(img_url);
        dest.writeString(seo_title);
        dest.writeString(seo_keywords);
        dest.writeString(seo_description);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(area);
        dest.writeString(regtime);
        dest.writeString(lng);
        dest.writeString(lat);
        dest.writeString(advantage);
        dest.writeString(idcard_a);
        dest.writeString(idcard_b);
        dest.writeString(license);
        dest.writeString(accredit);
        dest.writeString(aptitude);
        dest.writeString(revenue_card);
        dest.writeString(organi_card);
        dest.writeString(brand_card);
        dest.writeString(licence_card);
        dest.writeString(trade_aptitude);
        dest.writeString(account_name);
        dest.writeString(bank_name);
        dest.writeString(bank_account);
        dest.writeString(registeredid);
        dest.writeString(service_time);
        dest.writeString(service_ids);
    }
}
