package com.yunsen.enjoy.http;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/20.
 */

public interface HttpCallBack<T> {
    public void onSuccess(T responseData);

    public void onError(Request request, Exception e);
}
