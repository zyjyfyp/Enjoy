package com.yunsen.enjoy.model.request;

/**
 * Created by Administrator on 2018/5/10.
 */

public class WatchCarModel {
    private String user_id;//用户id,
    private String user_name;//用户名,
    private String article_id;//;//内容id,
    private String goods_id;//;//商品id,
    private String payment_id;//;//支付方式,5
    private String express_id;//配送方式,7
    private String is_invoice;//是否需要发票,0
    private String accept_name;//收货人,
    private String province;//省,
    private String city;//市,
    private String area;//区,
    private String address;//地址,
    private String telphone;//联系电话,
    private String mobile;//手机,
    private String email;//电子邮件,
    private String post_code;//邮编,
    private String message;//看车时间
    private String invoice_title;//发票抬头

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public void setExpress_id(String express_id) {
        this.express_id = express_id;
    }

    public void setIs_invoice(String is_invoice) {
        this.is_invoice = is_invoice;
    }

    public void setAccept_name(String accept_name) {
        this.accept_name = accept_name;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setInvoice_title(String invoice_title) {
        this.invoice_title = invoice_title;
    }
}
