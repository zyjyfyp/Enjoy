package com.yunsen.enjoy.model.request;

/**
 * Created by Administrator on 2018/6/29.
 */

public class ApplySaleAfterModel {
    String order_no;//商品订单编号
    String datatype;//售后类型
    String user_id;//用户id
    String user_name;//用户名
    String user_mobile;//手机号码
    String user_address;//收货地址
    String cause_desc;//售后原因
    String img_url;//图片路径
    String return_way;//配送方式

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
}
