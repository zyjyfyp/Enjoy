package com.yunsen.enjoy.model;

import java.util.List;

/**
 * Created by Administrator on 2018/4/29.
 */


public  class CarDetails {
    private int id;
    private int channel_id;
    private String channel_ids;
    private int category_id;
    private String category_ids;
    private String category_title;
    private int brand_id;
    private Object brand_title;
    private String code;
    private String title;
    private String subtitle;
    private String call_index;
    private String link_url;
    private String img_url;
    private String imgh_url;
    private String imgs_url;
    private String seo_title;
    private String seo_keywords;
    private String seo_description;
    private String tags;
    private String summary;
    private String content;
    private String mcontent;
    private int sort_id;
    private int click;
    private int status;
    private int is_msg;
    private int is_top;
    private int is_red;
    private int is_hot;
    private int is_slide;
    private int is_sys;
    private int is_post;
    private int user_id;
    private String user_name;
    private int company_id;
    private String company_name;
    private String province;
    private String city;
    private String area;
    private String street;
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
    private Object address;
    private int express_id;
    private int min_quantity;
    private Object unit;
    private Object packing;
    private int weight;
    private String validity;
    private int templet_id;
    private Object start_time;
    private Object end_time;
    private Object start_sinup;
    private Object end_sinup;
    private String add_time;
    private String update_time;
    /**
     * goods_id : 8212
     * article_id : 14837
     * goods_no : SD0-0
     * stock_quantity : 0
     * market_price : 6.57
     * sell_price : 5.98
     * first_payment : 1.79
     * monthly_supply : 1447
     * term : 36
     * rebate_price : 5.67
     * cost_price : 0.01
     * exchange_price : 0
     * exchange_point : 0
     * cashing_packet : 0.57
     * cashing_point : 0
     * give_packet : 0
     * give_pension : 1.13
     * give_sinup_point : 0
     * give_sinin_point : 0
     * give_sinup_exp : 0
     * give_sinin_exp : 0
     * spec_text : 属性：无
     * spec_ids : ,0,
     * is_default : 1
     * is_lock : 0
     * item_time : 2018-04-19 17:59:32
     * default_group_price : {"article_id":0,"goods_id":0,"group_id":0,"price":0,"is_default":0}
     * group_price : [{"article_id":14837,"goods_id":8212,"group_id":13,"price":0.01,"is_default":1}]
     * default_activity_price : {"article_id":0,"goods_id":0,"people":0,"is_default":0,"price":0}
     * activity_price : []
     */

    private DefaultSpecItemBean default_spec_item;
    private Object group_price;
    /**
     * article_id : 14837
     * share_total_point : 1000
     * share_click_point : 1
     * share_times_point : 10
     * share_total_packet : 10
     * share_click_packet : 0.01
     * share_times_packet : 0.1
     * author_total_point : 1000
     * author_click_point : 1
     * author_times_point : 10
     * author_total_packet : 10
     * author_click_packet : 0.01
     * author_times_packet : 0.1
     * read_total_point : 1000
     * read_click_point : 1
     * read_times_point : 10
     * read_total_packet : 10
     * read_click_packet : 0.01
     * read_times_packet : 0.1
     */

    private ShareBean share;
    /**
     * article_id : 14837
     * gift_id : 0
     * gift_title :
     */

    private GiftBean gift;
    private Object activity;
    /**
     * id : 0
     * article_id : 0
     * is_default : 0
     * people : 0
     * price_discount : 0
     * brokerage_discount : 0
     */

    private DefaultActivityItemBean default_activity_item;
    /**
     * id : 46567
     * article_id : 14837
     * thumb_path : /upload/201804/04/thumb_201804041428275263.jpg
     * original_path : /upload/201804/04/201804041428275263.jpg
     * remark :
     * add_time : 2018-04-29 11:38:00
     * update_time : 2018-04-19 17:59:32
     */

    private List<AlbumsBean> albums;
    private List<?> attach;
    /**
     * goods_id : 8212
     * article_id : 14837
     * goods_no : SD0-0
     * stock_quantity : 0
     * market_price : 6.57
     * sell_price : 5.98
     * first_payment : 1.79
     * monthly_supply : 1447
     * term : 36
     * rebate_price : 5.67
     * cost_price : 0.01
     * exchange_price : 0
     * exchange_point : 0
     * cashing_packet : 0.57
     * cashing_point : 0
     * give_packet : 0
     * give_pension : 1.13
     * give_sinup_point : 0
     * give_sinin_point : 0
     * give_sinup_exp : 0
     * give_sinin_exp : 0
     * spec_text : 属性：无
     * spec_ids : ,0,
     * is_default : 1
     * is_lock : 0
     * item_time : 2018-04-19 17:59:32
     * default_group_price : null
     * group_price : [{"article_id":14837,"goods_id":8212,"group_id":13,"price":0.01,"is_default":1}]
     * default_activity_price : null
     * activity_price : []
     */

    private List<SpecItemBean> spec_item;
    private List<?> param;
    private List<?> steps;
    /**
     * article_id : 14837
     * channel_id : 7
     * category_id : 1698
     * category_title : 两厢轿车
     * category_list : ,1698,
     * update_time : 2018-04-19 17:59:32
     */

    private List<CategoryBean> category;
    /**
     * article_id : 14837
     * channel_id : 7
     * datatype_id : 7
     * datatype_title : 超值
     * datatype_list : ,7,
     * update_time : 2018-04-19 17:59:32
     */

    private List<DatatypeBean> datatype;
    private List<?> author;
    private List<?> activity_award;
    private List<?> activity_item;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_ids() {
        return channel_ids;
    }

    public void setChannel_ids(String channel_ids) {
        this.channel_ids = channel_ids;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_ids() {
        return category_ids;
    }

    public void setCategory_ids(String category_ids) {
        this.category_ids = category_ids;
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

    public Object getBrand_title() {
        return brand_title;
    }

    public void setBrand_title(Object brand_title) {
        this.brand_title = brand_title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public int getSort_id() {
        return sort_id;
    }

    public void setSort_id(int sort_id) {
        this.sort_id = sort_id;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getIs_post() {
        return is_post;
    }

    public void setIs_post(int is_post) {
        this.is_post = is_post;
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

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
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

    public Object getUnit() {
        return unit;
    }

    public void setUnit(Object unit) {
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

    public DefaultSpecItemBean getDefault_spec_item() {
        return default_spec_item;
    }

    public void setDefault_spec_item(DefaultSpecItemBean default_spec_item) {
        this.default_spec_item = default_spec_item;
    }

    public Object getGroup_price() {
        return group_price;
    }

    public void setGroup_price(Object group_price) {
        this.group_price = group_price;
    }

    public ShareBean getShare() {
        return share;
    }

    public void setShare(ShareBean share) {
        this.share = share;
    }

    public GiftBean getGift() {
        return gift;
    }

    public void setGift(GiftBean gift) {
        this.gift = gift;
    }

    public Object getActivity() {
        return activity;
    }

    public void setActivity(Object activity) {
        this.activity = activity;
    }

    public DefaultActivityItemBean getDefault_activity_item() {
        return default_activity_item;
    }

    public void setDefault_activity_item(DefaultActivityItemBean default_activity_item) {
        this.default_activity_item = default_activity_item;
    }

    public List<AlbumsBean> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumsBean> albums) {
        this.albums = albums;
    }

    public List<?> getAttach() {
        return attach;
    }

    public void setAttach(List<?> attach) {
        this.attach = attach;
    }

    public List<SpecItemBean> getSpec_item() {
        return spec_item;
    }

    public void setSpec_item(List<SpecItemBean> spec_item) {
        this.spec_item = spec_item;
    }

    public List<?> getParam() {
        return param;
    }

    public void setParam(List<?> param) {
        this.param = param;
    }

    public List<?> getSteps() {
        return steps;
    }

    public void setSteps(List<?> steps) {
        this.steps = steps;
    }

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public List<DatatypeBean> getDatatype() {
        return datatype;
    }

    public void setDatatype(List<DatatypeBean> datatype) {
        this.datatype = datatype;
    }

    public List<?> getAuthor() {
        return author;
    }

    public void setAuthor(List<?> author) {
        this.author = author;
    }

    public List<?> getActivity_award() {
        return activity_award;
    }

    public void setActivity_award(List<?> activity_award) {
        this.activity_award = activity_award;
    }

    public List<?> getActivity_item() {
        return activity_item;
    }

    public void setActivity_item(List<?> activity_item) {
        this.activity_item = activity_item;
    }

    public static class DefaultSpecItemBean {
        private int goods_id;
        private int article_id;
        private String goods_no;
        private int stock_quantity;
        private double market_price;
        private double sell_price;
        private double first_payment;
        private int monthly_supply;
        private int term;
        private double rebate_price;
        private double cost_price;
        private int exchange_price;
        private int exchange_point;
        private double cashing_packet;
        private int cashing_point;
        private int give_packet;
        private double give_pension;
        private int give_sinup_point;
        private int give_sinin_point;
        private int give_sinup_exp;
        private int give_sinin_exp;
        private String spec_text;
        private String spec_ids;
        private int is_default;
        private int is_lock;
        private String item_time;
        /**
         * article_id : 0
         * goods_id : 0
         * group_id : 0
         * price : 0
         * is_default : 0
         */

        private DefaultPriceBean default_group_price;
        /**
         * article_id : 0
         * goods_id : 0
         * people : 0
         * is_default : 0
         * price : 0
         */

        private DefaultPriceBean default_activity_price;
        /**
         * article_id : 14837
         * goods_id : 8212
         * group_id : 13
         * price : 0.01
         * is_default : 1
         */

        private List<GroupPriceBean> group_price;
        private List<?> activity_price;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getArticle_id() {
            return article_id;
        }

        public void setArticle_id(int article_id) {
            this.article_id = article_id;
        }

        public String getGoods_no() {
            return goods_no;
        }

        public void setGoods_no(String goods_no) {
            this.goods_no = goods_no;
        }

        public int getStock_quantity() {
            return stock_quantity;
        }

        public void setStock_quantity(int stock_quantity) {
            this.stock_quantity = stock_quantity;
        }

        public double getMarket_price() {
            return market_price;
        }

        public void setMarket_price(double market_price) {
            this.market_price = market_price;
        }

        public double getSell_price() {
            return sell_price;
        }

        public void setSell_price(double sell_price) {
            this.sell_price = sell_price;
        }

        public double getFirst_payment() {
            return first_payment;
        }

        public void setFirst_payment(double first_payment) {
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

        public int getExchange_price() {
            return exchange_price;
        }

        public void setExchange_price(int exchange_price) {
            this.exchange_price = exchange_price;
        }

        public int getExchange_point() {
            return exchange_point;
        }

        public void setExchange_point(int exchange_point) {
            this.exchange_point = exchange_point;
        }

        public double getCashing_packet() {
            return cashing_packet;
        }

        public void setCashing_packet(double cashing_packet) {
            this.cashing_packet = cashing_packet;
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

        public double getGive_pension() {
            return give_pension;
        }

        public void setGive_pension(double give_pension) {
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

        public String getSpec_text() {
            return spec_text;
        }

        public void setSpec_text(String spec_text) {
            this.spec_text = spec_text;
        }

        public String getSpec_ids() {
            return spec_ids;
        }

        public void setSpec_ids(String spec_ids) {
            this.spec_ids = spec_ids;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public int getIs_lock() {
            return is_lock;
        }

        public void setIs_lock(int is_lock) {
            this.is_lock = is_lock;
        }

        public String getItem_time() {
            return item_time;
        }

        public void setItem_time(String item_time) {
            this.item_time = item_time;
        }

        public DefaultPriceBean getDefault_group_price() {
            return default_group_price;
        }

        public void setDefault_group_price(DefaultPriceBean default_group_price) {
            this.default_group_price = default_group_price;
        }

        public DefaultPriceBean getDefault_activity_price() {
            return default_activity_price;
        }

        public void setDefault_activity_price(DefaultPriceBean default_activity_price) {
            this.default_activity_price = default_activity_price;
        }

        public List<GroupPriceBean> getGroup_price() {
            return group_price;
        }

        public void setGroup_price(List<GroupPriceBean> group_price) {
            this.group_price = group_price;
        }

        public List<?> getActivity_price() {
            return activity_price;
        }

        public void setActivity_price(List<?> activity_price) {
            this.activity_price = activity_price;
        }



    }










}