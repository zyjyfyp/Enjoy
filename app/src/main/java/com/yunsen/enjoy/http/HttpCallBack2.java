package com.yunsen.enjoy.http;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/20.
 */

public interface HttpCallBack2<T> {
    public void onSuccess(T responseData, Object otherData);

    public void onError(Request request, Exception e);
}
