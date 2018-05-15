package com.yunsen.enjoy.model;

import java.util.List;

/**
 * Created by Administrator on 2018/5/14.
 */
public class AuthorizationModel {
    /**
     * id : 0
     * subscribe : 0
     * user_id : 0
     * user_name : 匿名
     * nick_name : zhang
     * sex : 女
     * avatar : http://thirdqq.qlogo.cn/qqapp/222222/942Fd
     * province : dfa
     * city : dfaf
     * country : ''a
     * oauth_name : qq
     * union_id : 123
     * open_id : 123
     * update_time : 2018-05-14 19:03:12
     */

    private int id;
    private int subscribe;
    private int user_id;
    private String user_name;
    private String nick_name;
    private String sex;
    private String avatar;
    private String province;
    private String city;
    private String country;
    private String oauth_name;
    private String union_id;
    private String open_id;
    private String update_time;
    /**
     * parent_id : 0
     * parent_name :
     * user_layer : 1
     * user_list : 61219,
     * sales_id : 0
     * sales_name :
     * sales_distance : 0
     * agency_id : 5
     * agency_name : 无代理级别
     * shops_name :
     * shops_id : 0
     * shops_distance : 0
     * store_name :
     * store_distance : 0
     * store_id : 0
     * group_id : 13
     * group_name : 普通会员
     * company_id : 0
     * company_name : 自营
     * department_id : 0
     * role_id : 0
     * position_id : 0
     * leader_id : 0
     * leader_name :
     * leader_list :
     * oldleader_id : 0
     * oldleader_name :
     * user_code : 111152699
     * salt : PB8D40
     * password : 38F80F615E543D9B
     * paypassword : 38F80F615E543D9B
     * real_name :
     * mobile : 13249089599
     * email : string
     * birthday : 2018-04-24 17:06:04
     * telphone : string
     * area : 东城区
     * street :
     * address : string
     * qq : string
     * weixin :
     * msn :
     * login_sign : 034E6EC942F08896517F4876B2E90B93
     * login_stamp : 1526294611
     * identity_card :
     * identity_card_a :
     * identity_card_b :
     * identity_card_t : 0
     * identity_province :
     * identity_city :
     * identity_area :
     * identity_address :
     * identity_name :
     * money : 0
     * card : 0
     * amount : 0
     * pension : 0
     * promotion : 0
     * reserve : 0
     * reserveb : 0
     * reserves : 0
     * training : 0
     * packet : 0
     * point : 51
     * exp : 0
     * exp_weal : 0
     * exp_invest : 0
     * exp_action : 0
     * exp_time : 0
     * exp_total : 0
     * is_system : 0
     * status : 0
     * grade : 0
     * upgrade_group : 0
     * upgrade_status : 0
     * reg_site :
     * reg_time : 2018-04-24 17:03:32
     * reg_ip : 116.24.64.8
     * accredit_id : 0
     * accredit_url :
     * accredit_no :
     * update_id : 61219
     * update_name : 13249089599
     * update_action :
     * update_type : 0
     * vip_card :
     * vip_time : null
     * write_id : 61219
     * write_name : 13249089599
     * write_time : 2018-04-24 17:03:32
     * write_action :
     * authen_id : 0
     * authen_name :
     * authen_time : null
     * authen_action :
     * authen_photo :
     * authen_table :
     * audit_id : 0
     * audit_name :
     * audit_time : null
     * audit_action :
     * receiver_id : 0
     * receiver_name :
     * receiver_time : 2018-04-24 17:06:04
     * receiver_action :
     * user_group_values : []
     * user_role_values : null
     * company : null
     * describe :
     */

    private int parent_id;
    private String parent_name;
    private int user_layer;
    private String user_list;
    private int sales_id;
    private String sales_name;
    private int sales_distance;
    private int agency_id;
    private String agency_name;
    private String shops_name;
    private int shops_id;
    private int shops_distance;
    private String store_name;
    private int store_distance;
    private int store_id;
    private int group_id;
    private String group_name;
    private int company_id;
    private String company_name;
    private int department_id;
    private int role_id;
    private int position_id;
    private int leader_id;
    private String leader_name;
    private String leader_list;
    private int oldleader_id;
    private String oldleader_name;
    private String user_code;
    private String salt;
    private String password;
    private String paypassword;
    private String real_name;
    private String mobile;
    private String email;
    private String birthday;
    private String telphone;
    private String area;
    private String street;
    private String address;
    private String qq;
    private String weixin;
    private String msn;
    private String login_sign;
    private String login_stamp;
    private String identity_card;
    private String identity_card_a;
    private String identity_card_b;
    private int identity_card_t;
    private String identity_province;
    private String identity_city;
    private String identity_area;
    private String identity_address;
    private String identity_name;
    private int money;
    private int card;
    private int amount;
    private int pension;
    private int promotion;
    private int reserve;
    private int reserveb;
    private int reserves;
    private int training;
    private int packet;
    private int point;
    private int exp;
    private int exp_weal;
    private int exp_invest;
    private int exp_action;
    private int exp_time;
    private int exp_total;
    private int is_system;
    private int status;
    private int grade;
    private int upgrade_group;
    private int upgrade_status;
    private String reg_site;
    private String reg_time;
    private String reg_ip;
    private int accredit_id;
    private String accredit_url;
    private String accredit_no;
    private int update_id;
    private String update_name;
    private String update_action;
    private int update_type;
    private String vip_card;
    private Object vip_time;
    private int write_id;
    private String write_name;
    private String write_time;
    private String write_action;
    private int authen_id;
    private String authen_name;
    private Object authen_time;
    private String authen_action;
    private String authen_photo;
    private String authen_table;
    private int audit_id;
    private String audit_name;
    private Object audit_time;
    private String audit_action;
    private int receiver_id;
    private String receiver_name;
    private String receiver_time;
    private String receiver_action;
    private Object user_role_values;
    private Object company;
    private String describe;
    private List<?> user_group_values;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
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

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOauth_name() {
        return oauth_name;
    }

    public void setOauth_name(String oauth_name) {
        this.oauth_name = oauth_name;
    }

    public String getUnion_id() {
        return union_id;
    }

    public void setUnion_id(String union_id) {
        this.union_id = union_id;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
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

    public int getSales_distance() {
        return sales_distance;
    }

    public void setSales_distance(int sales_distance) {
        this.sales_distance = sales_distance;
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

    public int getShops_distance() {
        return shops_distance;
    }

    public void setShops_distance(int shops_distance) {
        this.shops_distance = shops_distance;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public int getStore_distance() {
        return store_distance;
    }

    public void setStore_distance(int store_distance) {
        this.store_distance = store_distance;
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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public int getLeader_id() {
        return leader_id;
    }

    public void setLeader_id(int leader_id) {
        this.leader_id = leader_id;
    }

    public String getLeader_name() {
        return leader_name;
    }

    public void setLeader_name(String leader_name) {
        this.leader_name = leader_name;
    }

    public String getLeader_list() {
        return leader_list;
    }

    public void setLeader_list(String leader_list) {
        this.leader_list = leader_list;
    }

    public int getOldleader_id() {
        return oldleader_id;
    }

    public void setOldleader_id(int oldleader_id) {
        this.oldleader_id = oldleader_id;
    }

    public String getOldleader_name() {
        return oldleader_name;
    }

    public void setOldleader_name(String oldleader_name) {
        this.oldleader_name = oldleader_name;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPaypassword() {
        return paypassword;
    }

    public void setPaypassword(String paypassword) {
        this.paypassword = paypassword;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
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

    public int getIdentity_card_t() {
        return identity_card_t;
    }

    public void setIdentity_card_t(int identity_card_t) {
        this.identity_card_t = identity_card_t;
    }

    public String getIdentity_province() {
        return identity_province;
    }

    public void setIdentity_province(String identity_province) {
        this.identity_province = identity_province;
    }

    public String getIdentity_city() {
        return identity_city;
    }

    public void setIdentity_city(String identity_city) {
        this.identity_city = identity_city;
    }

    public String getIdentity_area() {
        return identity_area;
    }

    public void setIdentity_area(String identity_area) {
        this.identity_area = identity_area;
    }

    public String getIdentity_address() {
        return identity_address;
    }

    public void setIdentity_address(String identity_address) {
        this.identity_address = identity_address;
    }

    public String getIdentity_name() {
        return identity_name;
    }

    public void setIdentity_name(String identity_name) {
        this.identity_name = identity_name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPension() {
        return pension;
    }

    public void setPension(int pension) {
        this.pension = pension;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
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

    public int getTraining() {
        return training;
    }

    public void setTraining(int training) {
        this.training = training;
    }

    public int getPacket() {
        return packet;
    }

    public void setPacket(int packet) {
        this.packet = packet;
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

    public int getExp_total() {
        return exp_total;
    }

    public void setExp_total(int exp_total) {
        this.exp_total = exp_total;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getUpgrade_group() {
        return upgrade_group;
    }

    public void setUpgrade_group(int upgrade_group) {
        this.upgrade_group = upgrade_group;
    }

    public int getUpgrade_status() {
        return upgrade_status;
    }

    public void setUpgrade_status(int upgrade_status) {
        this.upgrade_status = upgrade_status;
    }

    public String getReg_site() {
        return reg_site;
    }

    public void setReg_site(String reg_site) {
        this.reg_site = reg_site;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getReg_ip() {
        return reg_ip;
    }

    public void setReg_ip(String reg_ip) {
        this.reg_ip = reg_ip;
    }

    public int getAccredit_id() {
        return accredit_id;
    }

    public void setAccredit_id(int accredit_id) {
        this.accredit_id = accredit_id;
    }

    public String getAccredit_url() {
        return accredit_url;
    }

    public void setAccredit_url(String accredit_url) {
        this.accredit_url = accredit_url;
    }

    public String getAccredit_no() {
        return accredit_no;
    }

    public void setAccredit_no(String accredit_no) {
        this.accredit_no = accredit_no;
    }

    public int getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(int update_id) {
        this.update_id = update_id;
    }

    public String getUpdate_name() {
        return update_name;
    }

    public void setUpdate_name(String update_name) {
        this.update_name = update_name;
    }

    public String getUpdate_action() {
        return update_action;
    }

    public void setUpdate_action(String update_action) {
        this.update_action = update_action;
    }

    public int getUpdate_type() {
        return update_type;
    }

    public void setUpdate_type(int update_type) {
        this.update_type = update_type;
    }

    public String getVip_card() {
        return vip_card;
    }

    public void setVip_card(String vip_card) {
        this.vip_card = vip_card;
    }

    public Object getVip_time() {
        return vip_time;
    }

    public void setVip_time(Object vip_time) {
        this.vip_time = vip_time;
    }

    public int getWrite_id() {
        return write_id;
    }

    public void setWrite_id(int write_id) {
        this.write_id = write_id;
    }

    public String getWrite_name() {
        return write_name;
    }

    public void setWrite_name(String write_name) {
        this.write_name = write_name;
    }

    public String getWrite_time() {
        return write_time;
    }

    public void setWrite_time(String write_time) {
        this.write_time = write_time;
    }

    public String getWrite_action() {
        return write_action;
    }

    public void setWrite_action(String write_action) {
        this.write_action = write_action;
    }

    public int getAuthen_id() {
        return authen_id;
    }

    public void setAuthen_id(int authen_id) {
        this.authen_id = authen_id;
    }

    public String getAuthen_name() {
        return authen_name;
    }

    public void setAuthen_name(String authen_name) {
        this.authen_name = authen_name;
    }

    public Object getAuthen_time() {
        return authen_time;
    }

    public void setAuthen_time(Object authen_time) {
        this.authen_time = authen_time;
    }

    public String getAuthen_action() {
        return authen_action;
    }

    public void setAuthen_action(String authen_action) {
        this.authen_action = authen_action;
    }

    public String getAuthen_photo() {
        return authen_photo;
    }

    public void setAuthen_photo(String authen_photo) {
        this.authen_photo = authen_photo;
    }

    public String getAuthen_table() {
        return authen_table;
    }

    public void setAuthen_table(String authen_table) {
        this.authen_table = authen_table;
    }

    public int getAudit_id() {
        return audit_id;
    }

    public void setAudit_id(int audit_id) {
        this.audit_id = audit_id;
    }

    public String getAudit_name() {
        return audit_name;
    }

    public void setAudit_name(String audit_name) {
        this.audit_name = audit_name;
    }

    public Object getAudit_time() {
        return audit_time;
    }

    public void setAudit_time(Object audit_time) {
        this.audit_time = audit_time;
    }

    public String getAudit_action() {
        return audit_action;
    }

    public void setAudit_action(String audit_action) {
        this.audit_action = audit_action;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_time() {
        return receiver_time;
    }

    public void setReceiver_time(String receiver_time) {
        this.receiver_time = receiver_time;
    }

    public String getReceiver_action() {
        return receiver_action;
    }

    public void setReceiver_action(String receiver_action) {
        this.receiver_action = receiver_action;
    }

    public Object getUser_role_values() {
        return user_role_values;
    }

    public void setUser_role_values(Object user_role_values) {
        this.user_role_values = user_role_values;
    }

    public Object getCompany() {
        return company;
    }

    public void setCompany(Object company) {
        this.company = company;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<?> getUser_group_values() {
        return user_group_values;
    }

    public void setUser_group_values(List<?> user_group_values) {
        this.user_group_values = user_group_values;
    }
}