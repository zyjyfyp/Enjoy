package com.yunsen.enjoy.model.request;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/5/10.
 */

public class ApplyCarModel implements Parcelable {
    private String article_id;// 商品id,
    private String title;// 标题,
    private String type;// 1,
    private String first_payment;// 首付金额,
    private String all_payment;// 所有全额,
    private String term;// 期限,
    private String monthly_supply;// 月供金额,
    private String ctype_id;// 车型id,
    private String user_id;// 用户id,
    private String user_name;// 用户名,
    private String mobile;// 手机号码,
    private String real_name;// 真实姓名,
    private String identity_card;// 身份证号,
    private String identity_card_a;// 身份证正面,
    private String identity_card_b;// 身份证背面,
    private String bank_flow;// 银行流水

    public ApplyCarModel() {
    }

    protected ApplyCarModel(Parcel in) {
        article_id = in.readString();
        title = in.readString();
        type = in.readString();
        first_payment = in.readString();
        all_payment = in.readString();
        term = in.readString();
        monthly_supply = in.readString();
        ctype_id = in.readString();
        user_id = in.readString();
        user_name = in.readString();
        mobile = in.readString();
        real_name = in.readString();
        identity_card = in.readString();
        identity_card_a = in.readString();
        identity_card_b = in.readString();
        bank_flow = in.readString();
    }

    public static final Creator<ApplyCarModel> CREATOR = new Creator<ApplyCarModel>() {
        @Override
        public ApplyCarModel createFromParcel(Parcel in) {
            return new ApplyCarModel(in);
        }

        @Override
        public ApplyCarModel[] newArray(int size) {
            return new ApplyCarModel[size];
        }
    };

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFirst_payment(String first_payment) {
        this.first_payment = first_payment;
    }

    public void setAll_payment(String all_payment) {
        this.all_payment = all_payment;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setMonthly_supply(String monthly_supply) {
        this.monthly_supply = monthly_supply;
    }

    public void setCtype_id(String ctype_id) {
        this.ctype_id = ctype_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public void setIdentity_card(String identity_card) {
        this.identity_card = identity_card;
    }

    public void setIdentity_card_a(String identity_card_a) {
        this.identity_card_a = identity_card_a;
    }

    public void setIdentity_card_b(String identity_card_b) {
        this.identity_card_b = identity_card_b;
    }

    public void setBank_flow(String bank_flow) {
        this.bank_flow = bank_flow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(article_id);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(first_payment);
        dest.writeString(all_payment);
        dest.writeString(term);
        dest.writeString(monthly_supply);
        dest.writeString(ctype_id);
        dest.writeString(user_id);
        dest.writeString(user_name);
        dest.writeString(mobile);
        dest.writeString(real_name);
        dest.writeString(identity_card);
        dest.writeString(identity_card_a);
        dest.writeString(identity_card_b);
        dest.writeString(bank_flow);
    }
}
