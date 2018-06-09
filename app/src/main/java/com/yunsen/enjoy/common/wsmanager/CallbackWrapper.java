package com.yunsen.enjoy.common.wsmanager;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by Administrator on 2018/6/9.
 */


public class CallbackWrapper {

    private final IWsCallback tempCallback;
    private final ScheduledFuture timeoutTask;
    private final Action action;
    private final Request request;


    public CallbackWrapper(IWsCallback tempCallback, ScheduledFuture timeoutTask, Action action, Request request) {
        this.tempCallback = tempCallback;
        this.timeoutTask = timeoutTask;
        this.action = action;
        this.request = request;
    }


    public IWsCallback getTempCallback() {
        return tempCallback;
    }


    public ScheduledFuture getTimeoutTask() {
        return timeoutTask;
    }


    public Action getAction() {
        return action;
    }


    public Request getRequest() {
        return request;
    }
}