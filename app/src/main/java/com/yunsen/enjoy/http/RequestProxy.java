package com.yunsen.enjoy.http;


import android.util.Log;

import com.yunsen.enjoy.model.AdvertList;
import com.yunsen.enjoy.model.SearchParam;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/20.
 */

public class RequestProxy {
    private static final String TAG = "RequestProxy";

    /**
     * 获取首页广告
     */
    public static void getHomeAdvertList() {
        HttpClient.get(URLConstants.HOME_ADV_URL, null, new HttpResponseHandler<AdvertList>() {
            @Override
            public void onSuccess(AdvertList response) {
                super.onSuccess(response);
                Log.e(TAG, "onSuccess: " + response.toString());
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                Log.e(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

}
