package com.yunsen.enjoy.common.wsmanager;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2018/6/9.
 */

public class Response {

    @JSONField(name = "resp_event")
    private int respEvent;

    @JSONField(name = "seq_id")
    private String seqId;

    private String action;
    private String resp;

    public int getRespEvent() {
        return respEvent;
    }

    public void setRespEvent(int respEvent) {
        this.respEvent = respEvent;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }
}