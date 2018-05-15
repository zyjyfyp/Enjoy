package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.AuthorizationModel;

/**
 * Created by Administrator on 2018/5/14.
 */

public class AuthorizationResponse extends RestApiResponse {

    /**
     * data : {"id":0,"subscribe":0,"user_id":0,"user_name":"匿名","nick_name":"zhang","sex":"女","avatar":"http://thirdqq.qlogo.cn/qqapp/222222/942Fd","province":"dfa","city":"dfaf","country":"''a","oauth_name":"qq","union_id":"123","open_id":"123","update_time":"2018-05-14 19:03:12"}
     */

    private AuthorizationModel data;

    public AuthorizationModel getData() {
        return data;
    }

    public void setData(AuthorizationModel data) {
        this.data = data;
    }


}
