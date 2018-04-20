package com.yunsen.enjoy.http;


import com.yunsen.enjoy.model.SearchParam;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/20.
 */

public class RequestProxy {

    /**
     * 获取首页广告
     *
     * @param param
     * @param httpResponseHandler
     */
    public static void getHomeAdvertList(SearchParam param, HttpResponseHandler httpResponseHandler) {
            HttpClient.get(URLConstants.HOME_ADV_URL, null, new HttpResponseHandler(){
                @Override
                public void onSuccess(RestApiResponse response) {
                    super.onSuccess(response);
                }

                @Override
                public void onFailure(Request request, Exception e) {
                    super.onFailure(request, e);
                }
            });
    }

}
