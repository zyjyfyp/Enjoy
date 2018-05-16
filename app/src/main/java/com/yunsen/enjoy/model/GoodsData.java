package com.yunsen.enjoy.model;

import com.alibaba.fastjson.annotation.JSONField;
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
    /**
     * channel_ids : ,22,7,
     * category_ids : ,2749,1698,595,
     * brand_title : null
     * imgh_url : https://ju918.com/upload/201608/18/thumb_201608181829333287.jpg
     * imgs_url : /upload/201608/18/thumb_201608181829333287.jpg
     * tags :
     * content : null
     * mcontent : null
     * sort_id : 109
     * is_post : 0
     * lng : 0,0
     * lat : 0,0
     * value_type : 0
     * allow_group :
     * allow_sex :
     * allow_min_level : 0
     * allow_max_level : 0
     * allow_min_age : 0
     * allow_max_age : 0
     * game_tmpl :
     * game_online : 0
     * game_rank : 0
     * game_wheel : 0
     * game_let : 0
     * express_id : 0
     * min_quantity : 0
     * unit : 件
     * packing : null
     * weight : 0
     * validity : 2018-05-16 17:38:00
     * templet_id : 0
     * start_time : null
     * end_time : null
     * start_sinup : null
     * end_sinup : null
     * fields : null
     * albums : null
     * attach : null
     */

    private String channel_ids;
    private String category_ids;
    @JSONField(name = "brand_title")
    private Object brand_titleX;
    private String imgh_url;
    private String imgs_url;
    private String tags;
    @JSONField(name = "content")
    private Object contentX;
    @JSONField(name = "mcontent")
    private Object mcontentX;
    @JSONField(name = "sort_id")
    private int sort_idX;
    private int is_post;
    private String lng;
    private String lat;
    private int value_type;
    private String allow_group;
    private String allow_sex;
    private int allow_min_level;
    private int allow_max_level;
    private int allow_min_age;
    private int allow_max_age;
    private String game_tmpl;
    private int game_online;
    private int game_rank;
    private int game_wheel;
    private int game_let;
    private int express_id;
    private int min_quantity;
    private String unit;
    private Object packing;
    private int weight;
    private String validity;
    private int templet_id;
    @JSONField(name = "start_time")
    private Object start_timeX;
    @JSONField(name = "end_time")
    private Object end_timeX;
    private Object start_sinup;
    private Object end_sinup;
    private Object fields;
    @JSONField(name = "albums")
    private Object albumsX;
    private Object attach;

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

    public String getChannel_ids() {
        return channel_ids;
    }

    public void setChannel_ids(String channel_ids) {
        this.channel_ids = channel_ids;
    }

    public String getCategory_ids() {
        return category_ids;
    }

    public void setCategory_ids(String category_ids) {
        this.category_ids = category_ids;
    }

    public Object getBrand_titleX() {
        return brand_titleX;
    }

    public void setBrand_titleX(Object brand_titleX) {
        this.brand_titleX = brand_titleX;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Object getContentX() {
        return contentX;
    }

    public void setContentX(Object contentX) {
        this.contentX = contentX;
    }

    public Object getMcontentX() {
        return mcontentX;
    }

    public void setMcontentX(Object mcontentX) {
        this.mcontentX = mcontentX;
    }

    public int getSort_idX() {
        return sort_idX;
    }

    public void setSort_idX(int sort_idX) {
        this.sort_idX = sort_idX;
    }

    public int getIs_post() {
        return is_post;
    }

    public void setIs_post(int is_post) {
        this.is_post = is_post;
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

    public int getAllow_min_level() {
        return allow_min_level;
    }

    public void setAllow_min_level(int allow_min_level) {
        this.allow_min_level = allow_min_level;
    }

    public int getAllow_max_level() {
        return allow_max_level;
    }

    public void setAllow_max_level(int allow_max_level) {
        this.allow_max_level = allow_max_level;
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

    public int getExpress_id() {
        return express_id;
    }

    public void setExpress_id(int express_id) {
        this.express_id = express_id;
    }

    public int getMin_quantity() {
        return min_quantity;
    }

    public void setMin_quantity(int min_quantity) {
        this.min_quantity = min_quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Object getPacking() {
        return packing;
    }

    public void setPacking(Object packing) {
        this.packing = packing;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public int getTemplet_id() {
        return templet_id;
    }

    public void setTemplet_id(int templet_id) {
        this.templet_id = templet_id;
    }

    public Object getStart_timeX() {
        return start_timeX;
    }

    public void setStart_timeX(Object start_timeX) {
        this.start_timeX = start_timeX;
    }

    public Object getEnd_timeX() {
        return end_timeX;
    }

    public void setEnd_timeX(Object end_timeX) {
        this.end_timeX = end_timeX;
    }

    public Object getStart_sinup() {
        return start_sinup;
    }

    public void setStart_sinup(Object start_sinup) {
        this.start_sinup = start_sinup;
    }

    public Object getEnd_sinup() {
        return end_sinup;
    }

    public void setEnd_sinup(Object end_sinup) {
        this.end_sinup = end_sinup;
    }

    public Object getFields() {
        return fields;
    }

    public void setFields(Object fields) {
        this.fields = fields;
    }

    public Object getAlbumsX() {
        return albumsX;
    }

    public void setAlbumsX(Object albumsX) {
        this.albumsX = albumsX;
    }

    public Object getAttach() {
        return attach;
    }

    public void setAttach(Object attach) {
        this.attach = attach;
    }
}