package com.yunsen.enjoy.model.request;

/**
 * Created by Administrator on 2018/6/29.
 */

public class ApplySaleAfterModel {
    private String order_no;//商品订单编号
    private String datatype;//售后类型
    private String user_id;//用户id
    private String user_name;//用户名
    private String user_mobile;//手机号码
    private String user_address;//收货地址
    private String cause_desc;//售后原因
    private String img_url;//图片路径
    private String return_way = "1";//配送方式

    public ApplySaleAfterModel(String user_id, String user_name, String user_mobile, String user_address) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_mobile = user_mobile;
        this.user_address = user_address;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public void setCause_desc(String cause_desc) {
        this.cause_desc = cause_desc;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setReturn_way(String return_way) {
        this.return_way = return_way;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }
}
