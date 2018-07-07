package com.yunsen.enjoy.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.AchieveInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/6.
 */

public class AchieveInfoResponse extends RestApiResponse{

    /**
     * data : {"listModel":[{"id":201,"user_code":"1208","parent_id":200,"parent_name":"13680304436","user_layer":3,"user_list":",199,200,201,","sales_id":200,"sales_name":"13316989009","agency_id":5,"agency_name":"无代理级别","shops_name":"","shops_id":0,"store_name":"","store_id":0,"group_id":13,"group_name":"普通会员","user_name":"13502883181","mobile":"13502883181","email":"","avatar":"http://thirdwx.qlogo.cn/mmopen/rE6YWO50ngCLdiahZFhthVWajy2Fbg1RXMpQTMQkWNgiaOmewlX138hGOicjIwlhlT1XfvNZLbn8oHbx399TIK1KVQdIJOGiaq2ia/132","nick_name":"哈里","reg_ip":"113.96.219.247","identity_card":"3330219197011080065","identity_card_a":"/upload/201806/26/201806262031518737.jpg","identity_card_b":"/upload/201806/26/201806262032029364.jpg","real_name":"范咪咪","sex":"男","birthday":"2018-06-26 00:00:00","telphone":"","province":"","city":"","area":"","address":"","qq":"","weixin":"","login_sign":"8D4477FB9D5B651040DFD7EAC5FB80AF","login_stamp":"1530016051","amount":300,"pension":10,"packet":188,"reserve":0,"reserveb":0,"reserves":0,"point":0,"exp":0,"exp_weal":0,"exp_invest":0,"exp_action":0,"exp_time":0,"status":0,"reg_time":"2018-06-26 20:27:32","update_time":"2018-07-04 19:45:11","audit_time":null,"company":null}],"listOrderCount":[{"add_time":"2018-07-04 22:15:43","datatype":"订单","nick_name":"哈里","user_code":"1208","real_amount":100,"paycount":12},{"add_time":"2018-07-04 20:38:18","datatype":"订单","nick_name":"哈里","user_code":"1208","real_amount":100,"paycount":12},{"add_time":"2018-07-04 20:30:16","datatype":"订单","nick_name":"哈里","user_code":"1208","real_amount":100,"paycount":12},{"add_time":"2018-07-04 20:21:06","datatype":"订单","nick_name":"哈里","user_code":"1208","real_amount":100,"paycount":12},{"add_time":"2018-07-04 20:14:49","datatype":"订单","nick_name":"哈里","user_code":"1208","real_amount":100,"paycount":12},{"add_time":"2018-07-04 20:09:47","datatype":"订单","nick_name":"哈里","user_code":"1208","real_amount":100,"paycount":12},{"add_time":"2018-07-04 19:58:43","datatype":"订单","nick_name":"哈里","user_code":"1208","real_amount":100,"paycount":12}],"listcumulative_income":[{"consumer_name":"哈里","consumer_id":"1208","month_income":84}]}
     */

    private AchieveInfoBean data;

    public AchieveInfoBean getData() {
        return data;
    }

    public void setData(AchieveInfoBean data) {
        this.data = data;
    }

}
