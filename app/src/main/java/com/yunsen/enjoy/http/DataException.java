package com.yunsen.enjoy.http;

/**
 * Created by Administrator on 2018/5/7.
 */

public class DataException extends Exception {
    private String message;

    public DataException() {
    }

    public DataException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
