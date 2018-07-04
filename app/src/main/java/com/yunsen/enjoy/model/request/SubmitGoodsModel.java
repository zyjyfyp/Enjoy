package com.yunsen.enjoy.model.request;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/7/3.
 */

public class SubmitGoodsModel implements Parcelable {
    private String user_id;// 用户id
    private String channel_name;//      频道名称
    private String category_id;//     类别id
    private String title;//      内容标题
    private String subtitle;//      短标题
    private String brand_id;//      品牌id
    private String link_url;//
    private String img_url;//      图片url
    private String imgh_url;//      图片url
    private String imgs_url;//      图片url
    private String seo_title;//  seo标题
    private String seo_keywords;//  seo关键词
    private String seo_description;//  seo描述
    private String province;//  省
    private String city;//  市
    private String area;//  区
    private String street;//  街道
    private String lng;//  精度
    private String lat;//  维度
    private String address;//  地址
    private String summary;//  简介
    private String content;//  内容
    private String mcontent;//  手机端内容
    private String start_time;//  开始时间
    private String end_time;//  结束时间
    private String min_quantity;//  最少起批量
    private String unit;//  商品单位
    private String stock_quantity;//  库存数量
    private String vtype;//  音频类型
    private String video_url;//  音频路径
    private String albums;//  相册

    public SubmitGoodsModel() {
    }

    protected SubmitGoodsModel(Parcel in) {
        user_id = in.readString();
        channel_name = in.readString();
        category_id = in.readString();
        title = in.readString();
        subtitle = in.readString();
        brand_id = in.readString();
        link_url = in.readString();
        img_url = in.readString();
        imgh_url = in.readString();
        imgs_url = in.readString();
        seo_title = in.readString();
        seo_keywords = in.readString();
        seo_description = in.readString();
        province = in.readString();
        city = in.readString();
        area = in.readString();
        street = in.readString();
        lng = in.readString();
        lat = in.readString();
        address = in.readString();
        summary = in.readString();
        content = in.readString();
        mcontent = in.readString();
        start_time = in.readString();
        end_time = in.readString();
        min_quantity = in.readString();
        unit = in.readString();
        stock_quantity = in.readString();
        vtype = in.readString();
        video_url = in.readString();
        albums = in.readString();
    }

    public static final Creator<SubmitGoodsModel> CREATOR = new Creator<SubmitGoodsModel>() {
        @Override
        public SubmitGoodsModel createFromParcel(Parcel in) {
            return new SubmitGoodsModel(in);
        }

        @Override
        public SubmitGoodsModel[] newArray(int size) {
            return new SubmitGoodsModel[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getImgh_url() {
        return imgh_url;
    }

    public void setImgh_url(String imgh_url) {
        this.imgh_url = imgh_url;
    }

    public String getImgs_url() {
        return imgs_url;
    }

    public void setImgs_url(String imgs_url) {
        this.imgs_url = imgs_url;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMcontent() {
        return mcontent;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent;
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

    public String getMin_quantity() {
        return min_quantity;
    }

    public void setMin_quantity(String min_quantity) {
        this.min_quantity = min_quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(String stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public String getVtype() {
        return vtype;
    }

    public void setVtype(String vtype) {
        this.vtype = vtype;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getAlbums() {
        return albums;
    }

    public void setAlbums(String albums) {
        this.albums = albums;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(channel_name);
        dest.writeString(category_id);
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeString(brand_id);
        dest.writeString(link_url);
        dest.writeString(img_url);
        dest.writeString(imgh_url);
        dest.writeString(imgs_url);
        dest.writeString(seo_title);
        dest.writeString(seo_keywords);
        dest.writeString(seo_description);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(area);
        dest.writeString(street);
        dest.writeString(lng);
        dest.writeString(lat);
        dest.writeString(address);
        dest.writeString(summary);
        dest.writeString(content);
        dest.writeString(mcontent);
        dest.writeString(start_time);
        dest.writeString(end_time);
        dest.writeString(min_quantity);
        dest.writeString(unit);
        dest.writeString(stock_quantity);
        dest.writeString(vtype);
        dest.writeString(video_url);
        dest.writeString(albums);
    }
}