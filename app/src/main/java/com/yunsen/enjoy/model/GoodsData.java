package com.yunsen.enjoy.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.URLConstants;

import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */
public class GoodsData {
    /**
     * id : 10284
     * company_id : 11873
     * company_name : 深圳齐善食品有限公司
     * channel_id : 7
     * channel_name : goods
     * category_id : 595
     * category_title :
     * brand_id : 0
     * brand_title :
     * code : 5957711081653130194
     * title : 齐善食品_散装豆干 素肉豆腐干 酱香味 26g
     * subtitle : 小吃零食 齐善素食 美味爽口
     * call_index : goods201612291547242720
     * link_url :
     * img_url : /upload/201701/04/201701041416224379.jpg
     * seo_title :
     * seo_keywords :
     * seo_description :
     * summary :
     * content : <img alt="" src="/upload/201611/08/201611081654060702.jpg" /><img alt="" src="/upload/201611/08/201611081654068983.jpg" /><img alt="" src="/upload/201611/08/201611081654096796.jpg" /><img alt="" src="/upload/201611/08/201611081654117421.jpg" />
     * mcontent : <img alt="" src="/upload/201611/08/201611081654060702.jpg" /><img alt="" src="/upload/201611/08/201611081654068983.jpg" /><img alt="" src="/upload/201611/08/201611081654096796.jpg" /><img alt="" src="/upload/201611/08/201611081654117421.jpg" />
     * sort_id : <img alt="" src="/upload/201611/08/201611081654060702.jpg" /><img alt="" src="/upload/201611/08/201611081654068983.jpg" /><img alt="" src="/upload/201611/08/201611081654096796.jpg" /><img alt="" src="/upload/201611/08/201611081654117421.jpg" />
     * click : 120
     * status : 0
     * is_msg : 0
     * is_top : 0
     * is_red : 0
     * is_hot : 0
     * is_slide : 0
     * is_sys : 1
     * user_id : 118
     * user_name : 姚小娟
     * province : 广东省
     * city : 深圳市
     * area : 南山区
     * street : 所有乡村
     * address : 所有省份,所有城市,所有区县
     * start_time : null
     * end_time : null
     * add_time : 2016-11-08 16:55:12
     * update_time : 2017-08-17 17:34:55
     * comment : 1
     * sell_price : 0
     * market_price : 0
     * first_payment : 0
     * monthly_supply : 0
     * term : 0
     * cashing_packet : 0
     * cashing_point : 0
     * albums : [{"id":43910,"article_id":10284,"thumb_path":"/upload/201612/15/thumb_201612151928555185.jpg","original_path":"/upload/201612/15/201612151928555185.jpg","remark":"","add_time":"2018-04-23 15:22:39","update_time":"2017-08-17 17:34:55"},{"id":43992,"article_id":10284,"thumb_path":"/upload/201612/30/thumb_201612301746299271.jpg","original_path":"/upload/201612/30/201612301746299271.jpg","remark":"","add_time":"2018-04-23 15:22:39","update_time":"2017-08-17 17:34:55"}]
     * activity : null
     */
    private int imgRes;
    private int id;
    private int company_id;
    private String company_name;
    private int channel_id;
    private String channel_name;
    private int category_id;
    private String category_title;
    private int brand_id;
    private String brand_title;
    @JSONField(name = "code")
    private String codeX;
    private String title;
    private String subtitle;
    private String call_index;
    private String link_url;
    private String img_url;
    private String seo_title;
    private String seo_keywords;
    private String seo_description;
    private String summary;
    private String content;
    private String mcontent;
    private String sort_id;
    private int click;
    @JSONField(name = "status")
    private int statusX;
    private int is_msg;
    private int is_top;
    private int is_red;
    private int is_hot;
    private int is_slide;
    private int is_sys;
    private int user_id;
    private String user_name;
    private String province;
    private String city;
    private String area;
    private String street;
    private String address;
    private Object start_time;
    private Object end_time;
    private String add_time;
    private String update_time;
    private int comment;
    private int sell_price;
    private int market_price;
    private int first_payment;
    private int monthly_supply;
    private int term;
    private int cashing_packet;
    private int cashing_point;
    private Object activity;
    private List<AlbumsBean> albums;

    public GoodsData() {
    }

    public GoodsData(String img_url, String title) {
        this.img_url = img_url;
        this.title = title;
    }

    public GoodsData(int imgRes) {
        this.imgRes = imgRes;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_title() {
        return brand_title;
    }

    public void setBrand_title(String brand_title) {
        this.brand_title = brand_title;
    }

    public String getCodeX() {
        return codeX;
    }

    public void setCodeX(String codeX) {
        this.codeX = codeX;
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

    public String getCall_index() {
        return call_index;
    }

    public void setCall_index(String call_index) {
        this.call_index = call_index;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getImg_url() {
        if (img_url != null && img_url.startsWith("http")) {
            return img_url;
        } else {
            String trim = "";
            if (img_url != null) {
                trim = img_url.trim();
            }
            return URLConstants.REALM_URL + trim;
        }
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

    public String getSort_id() {
        return sort_id;
    }

    public void setSort_id(String sort_id) {
        this.sort_id = sort_id;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getStatusX() {
        return statusX;
    }

    public void setStatusX(int statusX) {
        this.statusX = statusX;
    }

    public int getIs_msg() {
        return is_msg;
    }

    public void setIs_msg(int is_msg) {
        this.is_msg = is_msg;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public int getIs_red() {
        return is_red;
    }

    public void setIs_red(int is_red) {
        this.is_red = is_red;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public int getIs_slide() {
        return is_slide;
    }

    public void setIs_slide(int is_slide) {
        this.is_slide = is_slide;
    }

    public int getIs_sys() {
        return is_sys;
    }

    public void setIs_sys(int is_sys) {
        this.is_sys = is_sys;
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

    public Object getStart_time() {
        return start_time;
    }

    public void setStart_time(Object start_time) {
        this.start_time = start_time;
    }

    public Object getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Object end_time) {
        this.end_time = end_time;
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

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getSell_price() {
        return sell_price;
    }

    public void setSell_price(int sell_price) {
        this.sell_price = sell_price;
    }

    public int getMarket_price() {
        return market_price;
    }

    public void setMarket_price(int market_price) {
        this.market_price = market_price;
    }

    public int getFirst_payment() {
        return first_payment;
    }

    public void setFirst_payment(int first_payment) {
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

    public Object getActivity() {
        return activity;
    }

    public void setActivity(Object activity) {
        this.activity = activity;
    }

    public List<AlbumsBean> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumsBean> albums) {
        this.albums = albums;
    }

}