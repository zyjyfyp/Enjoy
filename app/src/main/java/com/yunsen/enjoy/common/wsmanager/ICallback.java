package com.yunsen.enjoy.common.wsmanager;

/**
 * Created by Administrator on 2018/6/9.
 */

public interface ICallback<T> {

    void onSuccess(T t);

    void onFail(String msg);

}