package com.yunsen.enjoy.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 */


public class OrderDataBean {
    /**
     * id : 27660
     * order_no : L18052916073273294
     * trade_no : T18052916073273294
     * user_id : 6783
     * user_name : 13249089599
     * real_name :
     * user_avatar : /upload/201805/26/201805261720281015.jpg
     * sale_id : 0
     * sale_name :
     * pay_id : 0
     * pay_name :
     * consigner_id : 0
     * consigner_name : null
     * share_id : 0
     * share_name : null
     * company_id : 6784
     * company_name : 企业名称
     * payment_id : 5
     * payment_fee : 0
     * payment_status : 1
     * payment_time : null
     * rebate_status : 1
     * rebate_time : null
     * express_id : 7
     * express_no :
     * express_fee : 0
     * express_status : 1
     * express_type : 1
     * express_time : null
     * accept_name : 13249089599
     * accept_no :
     * post_code :
     * telphone : 13249089599
     * mobile : 13249089599
     * email : string
     * province : 广东省
     * city : 珠海市
     * area : 香洲区
     * street :
     * address : 地址
     * message : 2018-05-29
     * remark :
     * is_invoice : 0
     * invoice_title :
     * invoice_taxes : 0
     * is_exchange_price : 0
     * exchange_price_total : 0
     * is_exchange_point : 0
     * exchange_point_total : 0
     * is_cashing_packet : 0
     * cashing_packet_total : 0
     * is_cashing_point : 0
     * cashing_point_total : 0
     * give_packet_total : 0
     * give_pension_total : 0
     * give_sinup_point_total : 0
     * give_sinin_point_total : 0
     * give_sinup_exp_total : 0
     * give_sinin_exp_total : 0
     * payable_amount : 0
     * real_amount : 0
     * market_price_total : 0
     * sell_price_total : 0
     * cost_price_total : 0
     * rebate_price_total : 0
     * status : 2
     * datatype : 11
     * platform_id : 2
     * expenses_id : 6
     * settlement_amount : 0
     * settlement_status : 1
     * settlement_time : null
     * settlement_date : null
     * add_time : 2018-05-29 16:07:32
     * update_time : 2018-05-29 16:07:32
     * confirm_time : 2018-05-29 16:07:32
     * complete_time : null
     * order_goods : [{"order_id":27660,"article_id":1005,"article_title":"内容测试","goods_no":"SD0-0","goods_id":10217,"img_url":"/upload/201805/28/201805281116120873.jpg","spec_ids":",0,","spec_text":"属性：无","market_price":0,"sell_price":0,"real_price":0,"rebate_price":0,"cost_price":0,"is_exchange_price":0,"exchange_price":0,"is_exchange_point":0,"exchange_point":0,"is_cashing_packet":0,"cashing_packet":0,"is_cashing_point":0,"cashing_point":0,"give_packet":0,"give_pension":0,"give_sinup_point":0,"give_sinin_point":0,"give_sinup_exp":0,"give_sinin_exp":0,"quantity":0,"group_price":0,"group_id":0,"activity_no":"","activity_min":0,"activity_price":0,"brokerage_price":0,"activity_people":0,"activity_member":0,"is_foreman":0,"foreman_id":0,"foreman_name":"","timer_time":"2018-05-29 17:07:32","start_time":"2018-05-29 16:07:32","end_time":"2018-05-29 17:07:32","start_sinup":"2018-05-29 16:07:32","end_sinup":"2018-05-29 17:07:32","value_type":0,"allow_group":"","allow_sex":"","allow_min_grade":0,"allow_max_grade":0,"allow_min_age":0,"allow_max_age":0,"game_tmpl":"","game_online":0,"game_rank":0,"game_wheel":0,"game_let":0,"award_quantity":0,"award_rate":0,"award_status":0,"award_no":""}]
     * order_rebate : null
     * order_upgrade : null
     */

    private int id;
    private String order_no;
    private String trade_no;
    private int user_id;
    private String user_name;
    private String real_name;
    private String user_avatar;
    private int sale_id;
    private String sale_name;
    private int pay_id;
    private String pay_name;
    private int consigner_id;
    private Object consigner_name;
    private int share_id;
    private Object share_name;
    private int company_id;
    private String company_name;
    private int payment_id;
    private int payment_fee;
    private int payment_status;
    private Object payment_time;
    private int rebate_status;
    private Object rebate_time;
    private int express_id;
    private String express_no;
    private int express_fee;
    private int express_status;
    private int express_type;
    private Object express_time;
    private String accept_name;
    private String accept_no;
    private String post_code;
    private String telphone;
    private String mobile;
    private String email;
    private String province;
    private String city;
    private String area;
    private String street;
    private String address;
    private String message;
    private String remark;
    private int is_invoice;
    private String invoice_title;
    private int invoice_taxes;
    private double is_exchange_price;
    private double exchange_price_total;
    private int is_exchange_point;
    private double exchange_point_total;
    private int is_cashing_packet;
    private double cashing_packet_total;
    private int is_cashing_point;
    private double cashing_point_total;
    private double give_packet_total;
    private double give_pension_total;
    private int give_sinup_point_total;
    private int give_sinin_point_total;
    private double give_sinup_exp_total;
    private double give_sinin_exp_total;
    private double payable_amount;
    private double real_amount;
    private double market_price_total;
    private double sell_price_total;
    private double cost_price_total;
    private double rebate_price_total;
    @JSONField(name = "status")
    private int statusX;
    private int datatype;
    private int platform_id;
    private int expenses_id;
    private int settlement_amount;
    private int settlement_status;
    private Object settlement_time;
    private Object settlement_date;
    private String add_time;
    private String update_time;
    private String confirm_time;
    private Object complete_time;
    private Object order_rebate;
    private Object order_upgrade;
    private List<OrderGoodsBean> order_goods;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
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

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public int getSale_id() {
        return sale_id;
    }

    public void setSale_id(int sale_id) {
        this.sale_id = sale_id;
    }

    public String getSale_name() {
        return sale_name;
    }

    public void setSale_name(String sale_name) {
        this.sale_name = sale_name;
    }

    public int getPay_id() {
        return pay_id;
    }

    public void setPay_id(int pay_id) {
        this.pay_id = pay_id;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public int getConsigner_id() {
        return consigner_id;
    }

    public void setConsigner_id(int consigner_id) {
        this.consigner_id = consigner_id;
    }

    public Object getConsigner_name() {
        return consigner_name;
    }

    public void setConsigner_name(Object consigner_name) {
        this.consigner_name = consigner_name;
    }

    public int getShare_id() {
        return share_id;
    }

    public void setShare_id(int share_id) {
        this.share_id = share_id;
    }

    public Object getShare_name() {
        return share_name;
    }

    public void setShare_name(Object share_name) {
        this.share_name = share_name;
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

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getPayment_fee() {
        return payment_fee;
    }

    public void setPayment_fee(int payment_fee) {
        this.payment_fee = payment_fee;
    }

    public int getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(int payment_status) {
        this.payment_status = payment_status;
    }

    public Object getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(Object payment_time) {
        this.payment_time = payment_time;
    }

    public int getRebate_status() {
        return rebate_status;
    }

    public void setRebate_status(int rebate_status) {
        this.rebate_status = rebate_status;
    }

    public Object getRebate_time() {
        return rebate_time;
    }

    public void setRebate_time(Object rebate_time) {
        this.rebate_time = rebate_time;
    }

    public int getExpress_id() {
        return express_id;
    }

    public void setExpress_id(int express_id) {
        this.express_id = express_id;
    }

    public String getExpress_no() {
        return express_no;
    }

    public void setExpress_no(String express_no) {
        this.express_no = express_no;
    }

    public int getExpress_fee() {
        return express_fee;
    }

    public void setExpress_fee(int express_fee) {
        this.express_fee = express_fee;
    }

    public int getExpress_status() {
        return express_status;
    }

    public void setExpress_status(int express_status) {
        this.express_status = express_status;
    }

    public int getExpress_type() {
        return express_type;
    }

    public void setExpress_type(int express_type) {
        this.express_type = express_type;
    }

    public Object getExpress_time() {
        return express_time;
    }

    public void setExpress_time(Object express_time) {
        this.express_time = express_time;
    }

    public String getAccept_name() {
        return accept_name;
    }

    public void setAccept_name(String accept_name) {
        this.accept_name = accept_name;
    }

    public String getAccept_no() {
        return accept_no;
    }

    public void setAccept_no(String accept_no) {
        this.accept_no = accept_no;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIs_invoice() {
        return is_invoice;
    }

    public void setIs_invoice(int is_invoice) {
        this.is_invoice = is_invoice;
    }

    public String getInvoice_title() {
        return invoice_title;
    }

    public void setInvoice_title(String invoice_title) {
        this.invoice_title = invoice_title;
    }

    public int getInvoice_taxes() {
        return invoice_taxes;
    }

    public void setInvoice_taxes(int invoice_taxes) {
        this.invoice_taxes = invoice_taxes;
    }

    public double getIs_exchange_price() {
        return is_exchange_price;
    }

    public void setIs_exchange_price(double is_exchange_price) {
        this.is_exchange_price = is_exchange_price;
    }

    public double getExchange_price_total() {
        return exchange_price_total;
    }

    public void setExchange_price_total(double exchange_price_total) {
        this.exchange_price_total = exchange_price_total;
    }

    public int getIs_exchange_point() {
        return is_exchange_point;
    }

    public void setIs_exchange_point(int is_exchange_point) {
        this.is_exchange_point = is_exchange_point;
    }

    public double getExchange_point_total() {
        return exchange_point_total;
    }

    public void setExchange_point_total(double exchange_point_total) {
        this.exchange_point_total = exchange_point_total;
    }

    public int getIs_cashing_packet() {
        return is_cashing_packet;
    }

    public void setIs_cashing_packet(int is_cashing_packet) {
        this.is_cashing_packet = is_cashing_packet;
    }

    public double getCashing_packet_total() {
        return cashing_packet_total;
    }

    public void setCashing_packet_total(double cashing_packet_total) {
        this.cashing_packet_total = cashing_packet_total;
    }

    public int getIs_cashing_point() {
        return is_cashing_point;
    }

    public void setIs_cashing_point(int is_cashing_point) {
        this.is_cashing_point = is_cashing_point;
    }

    public double getCashing_point_total() {
        return cashing_point_total;
    }

    public void setCashing_point_total(double cashing_point_total) {
        this.cashing_point_total = cashing_point_total;
    }

    public double getGive_packet_total() {
        return give_packet_total;
    }

    public void setGive_packet_total(double give_packet_total) {
        this.give_packet_total = give_packet_total;
    }

    public double getGive_pension_total() {
        return give_pension_total;
    }

    public void setGive_pension_total(double give_pension_total) {
        this.give_pension_total = give_pension_total;
    }

    public int getGive_sinup_point_total() {
        return give_sinup_point_total;
    }

    public void setGive_sinup_point_total(int give_sinup_point_total) {
        this.give_sinup_point_total = give_sinup_point_total;
    }

    public int getGive_sinin_point_total() {
        return give_sinin_point_total;
    }

    public void setGive_sinin_point_total(int give_sinin_point_total) {
        this.give_sinin_point_total = give_sinin_point_total;
    }

    public double getGive_sinup_exp_total() {
        return give_sinup_exp_total;
    }

    public void setGive_sinup_exp_total(double give_sinup_exp_total) {
        this.give_sinup_exp_total = give_sinup_exp_total;
    }

    public double getGive_sinin_exp_total() {
        return give_sinin_exp_total;
    }

    public void setGive_sinin_exp_total(double give_sinin_exp_total) {
        this.give_sinin_exp_total = give_sinin_exp_total;
    }

    public double getPayable_amount() {
        return payable_amount;
    }

    public void setPayable_amount(double payable_amount) {
        this.payable_amount = payable_amount;
    }

    public double getReal_amount() {
        return real_amount;
    }

    public void setReal_amount(double real_amount) {
        this.real_amount = real_amount;
    }

    public double getMarket_price_total() {
        BigDecimal bg = new BigDecimal(market_price_total);
        market_price_total = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return market_price_total;
    }

    public void setMarket_price_total(double market_price_total) {
        this.market_price_total = market_price_total;
    }

    public double getSell_price_total() {
        return sell_price_total;
    }

    public void setSell_price_total(double sell_price_total) {
        this.sell_price_total = sell_price_total;
    }

    public double getCost_price_total() {
        return cost_price_total;
    }

    public void setCost_price_total(double cost_price_total) {
        this.cost_price_total = cost_price_total;
    }

    public double getRebate_price_total() {
        return rebate_price_total;
    }

    public void setRebate_price_total(double rebate_price_total) {
        this.rebate_price_total = rebate_price_total;
    }

    public int getStatusX() {
        return statusX;
    }

    public void setStatusX(int statusX) {
        this.statusX = statusX;
    }

    public int getDatatype() {
        return datatype;
    }

    public void setDatatype(int datatype) {
        this.datatype = datatype;
    }

    public int getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(int platform_id) {
        this.platform_id = platform_id;
    }

    public int getExpenses_id() {
        return expenses_id;
    }

    public void setExpenses_id(int expenses_id) {
        this.expenses_id = expenses_id;
    }

    public int getSettlement_amount() {
        return settlement_amount;
    }

    public void setSettlement_amount(int settlement_amount) {
        this.settlement_amount = settlement_amount;
    }

    public int getSettlement_status() {
        return settlement_status;
    }

    public void setSettlement_status(int settlement_status) {
        this.settlement_status = settlement_status;
    }

    public Object getSettlement_time() {
        return settlement_time;
    }

    public void setSettlement_time(Object settlement_time) {
        this.settlement_time = settlement_time;
    }

    public Object getSettlement_date() {
        return settlement_date;
    }

    public void setSettlement_date(Object settlement_date) {
        this.settlement_date = settlement_date;
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

    public String getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(String confirm_time) {
        this.confirm_time = confirm_time;
    }

    public Object getComplete_time() {
        return complete_time;
    }

    public void setComplete_time(Object complete_time) {
        this.complete_time = complete_time;
    }

    public Object getOrder_rebate() {
        return order_rebate;
    }

    public void setOrder_rebate(Object order_rebate) {
        this.order_rebate = order_rebate;
    }

    public Object getOrder_upgrade() {
        return order_upgrade;
    }

    public void setOrder_upgrade(Object order_upgrade) {
        this.order_upgrade = order_upgrade;
    }

    public List<OrderGoodsBean> getOrder_goods() {
        return order_goods;
    }

    public void setOrder_goods(List<OrderGoodsBean> order_goods) {
        this.order_goods = order_goods;
    }


}

