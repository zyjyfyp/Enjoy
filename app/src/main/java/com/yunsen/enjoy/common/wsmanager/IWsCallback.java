package com.yunsen.enjoy.common.wsmanager;

/**
 * Created by Administrator on 2018/6/9.
 */

public interface IWsCallback<T> {
    void onSuccess(T t);

    void onError(String msg, Request request, Action action);

    void onTimeout(Request request, Action action);
}