package com.yunsen.enjoy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.yunsen.enjoy.http.URLConstants;

/**
 * Created by Administrator on 2018/7/12/012.
 */

public class MyApplyCarBean implements Parcelable {
    /**
     * id : 1021
     * article_id : 14837
     * title : 大众捷达 2013款 1.6L 自动舒适型
     * type : 1
     * first_payment : 1.79
     * all_payment : 5.67
     * term : 36
     * monthly_supply : 1447
     * brand_id : 0
     * brand_name : null
     * ctype_id : 1698
     * ctype_name : null
     * user_id : 6783
     * user_name : 13249089599
     * real_name : 赵先生
     * mobile : 13249846735
     * identity_card : 412724199102024459
     * identity_card_a : /upload/201805/17/201805170908092482.jpg
     * identity_card_b : /upload/201805/17/201805170908234589.jpg
     * bank_flow : /upload/201805/17/201805170908506314.jpg
     * status : 0
     * add_time : 2018-05-17 09:09:02
     * update_time : 2018-05-17 09:09:02
     */

    private int id;
    private int article_id;
    private String title;
    private int type;
    private double first_payment;
    private double all_payment;
    private int term;
    private int monthly_supply;
    private int brand_id;
    private Object brand_name;
    private int ctype_id;
    private Object ctype_name;
    private int user_id;
    private String user_name;
    private String real_name;
    private String mobile;
    private String identity_card;
    private String identity_card_a;
    private String identity_card_b;
    private String bank_flow;
    @JSONField(name = "status")
    private int statusX;
    private String add_time;
    private String update_time;
    private String img_url;

    public MyApplyCarBean() {
    }

    protected MyApplyCarBean(Parcel in) {
        id = in.readInt();
        article_id = in.readInt();
        title = in.readString();
        type = in.readInt();
        first_payment = in.readDouble();
        all_payment = in.readDouble();
        term = in.readInt();
        monthly_supply = in.readInt();
        brand_id = in.readInt();
        ctype_id = in.readInt();
        user_id = in.readInt();
        user_name = in.readString();
        real_name = in.readString();
        mobile = in.readString();
        identity_card = in.readString();
        identity_card_a = in.readString();
        identity_card_b = in.readString();
        bank_flow = in.readString();
        statusX = in.readInt();
        add_time = in.readString();
        update_time = in.readString();
        img_url = in.readString();
    }

    public static final Creator<MyApplyCarBean> CREATOR = new Creator<MyApplyCarBean>() {
        @Override
        public MyApplyCarBean createFromParcel(Parcel in) {
            return new MyApplyCarBean(in);
        }

        @Override
        public MyApplyCarBean[] newArray(int size) {
            return new MyApplyCarBean[size];
        }
    };

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getFirst_payment() {
        return first_payment;
    }

    public void setFirst_payment(double first_payment) {
        this.first_payment = first_payment;
    }

    public double getAll_payment() {
        return all_payment;
    }

    public void setAll_payment(double all_payment) {
        this.all_payment = all_payment;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getMonthly_supply() {
        return monthly_supply;
    }

    public void setMonthly_supply(int monthly_supply) {
        this.monthly_supply = monthly_supply;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public Object getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(Object brand_name) {
        this.brand_name = brand_name;
    }

    public int getCtype_id() {
        return ctype_id;
    }

    public void setCtype_id(int ctype_id) {
        this.ctype_id = ctype_id;
    }

    public Object getCtype_name() {
        return ctype_name;
    }

    public void setCtype_name(Object ctype_name) {
        this.ctype_name = ctype_name;
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

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdentity_card() {
        return identity_card;
    }

    public void setIdentity_card(String identity_card) {
        this.identity_card = identity_card;
    }

    public String getIdentity_card_a() {
        if (identity_card_a != null && identity_card_a.startsWith("http")) {
            return identity_card_a;
        } else {
            return URLConstants.REALM_URL + identity_card_a;
        }
    }

    public void setIdentity_card_a(String identity_card_a) {
        this.identity_card_a = identity_card_a;
    }

    public String getIdentity_card_b() {
        if (identity_card_b != null && identity_card_b.startsWith("http")) {
            return identity_card_b;
        } else {
            return URLConstants.REALM_URL + identity_card_b;
        }
    }

    public void setIdentity_card_b(String identity_card_b) {
        this.identity_card_b = identity_card_b;
    }

    public String getBank_flow() {
        if (bank_flow != null && bank_flow.startsWith("http")) {
            return bank_flow;
        } else {
            return URLConstants.REALM_URL + bank_flow;
        }
    }

    public void setBank_flow(String bank_flow) {
        this.bank_flow = bank_flow;
    }

    public int getStatusX() {
        return statusX;
    }

    public void setStatusX(int statusX) {
        this.statusX = statusX;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(article_id);
        dest.writeString(title);
        dest.writeInt(type);
        dest.writeDouble(first_payment);
        dest.writeDouble(all_payment);
        dest.writeInt(term);
        dest.writeInt(monthly_supply);
        dest.writeInt(brand_id);
        dest.writeInt(ctype_id);
        dest.writeInt(user_id);
        dest.writeString(user_name);
        dest.writeString(real_name);
        dest.writeString(mobile);
        dest.writeString(identity_card);
        dest.writeString(identity_card_a);
        dest.writeString(identity_card_b);
        dest.writeString(bank_flow);
        dest.writeInt(statusX);
        dest.writeString(add_time);
        dest.writeString(update_time);
        dest.writeString(img_url);
    }
}
