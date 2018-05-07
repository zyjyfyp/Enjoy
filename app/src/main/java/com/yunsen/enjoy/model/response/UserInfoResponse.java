package com.yunsen.enjoy.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.UserInfo;

/**
 * Created by Administrator on 2018/5/7.
 */

public class UserInfoResponse extends RestApiResponse {


    /**
     * data : {"id":6781,"user_code":"113882699","parent_id":0,"parent_name":"","user_layer":1,"user_list":"6781,","sales_id":0,"sales_name":"","agency_id":5,"agency_name":"无代理级别","shops_name":"","shops_id":0,"store_name":"","store_id":0,"group_id":13,"group_name":"普通会员","company_id":0,"user_name":"13249089599","mobile":"13249089599","email":"","avatar":"","nick_name":"","reg_ip":"116.24.67.175","identity_card":"","identity_card_a":"","identity_card_b":"","real_name":"","sex":"","birthday":"2018-05-07 15:21:33","telphone":"","province":"","city":"","area":"","street":"","address":"","qq":"","weixin":"","login_sign":"C3232BC5DE9FC654AA680069C5A778F8","login_stamp":"1525675612","amount":0,"promotion":0,"pension":10,"packet":188,"reserve":0,"reserveb":0,"reserves":0,"point":0,"exp":0,"exp_weal":0,"exp_invest":0,"exp_action":0,"exp_time":0,"vip_card":"","is_system":0,"status":0,"reg_time":"2018-05-07 11:00:13","update_time":"2018-05-07 11:00:13","authen_time":null,"audit_time":null,"company":null}
     */

    private UserInfo data;

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }


}
