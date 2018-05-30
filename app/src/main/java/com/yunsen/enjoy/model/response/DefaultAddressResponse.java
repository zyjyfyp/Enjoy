package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.AddressInfo;

/**
 * Created by Administrator on 2018/5/30.
 */

public class DefaultAddressResponse extends RestApiResponse {

    /**
     * data : {"id":14049,"user_id":6783,"user_name":"13249089599","user_accept_name":"姓名","user_address":"详细地址","user_mobile":"15173630564","user_telphone":null,"user_email":null,"user_post_code":null,"province":"黑龙江","city":"鸡西","area":"鸡冠区","street":"","is_default":1,"add_time":"2018-05-30 10:57:31","update_time":"2018-05-30 10:57:31"}
     */

    private AddressInfo data;

    public AddressInfo getData() {
        return data;
    }

    public void setData(AddressInfo data) {
        this.data = data;
    }

}
