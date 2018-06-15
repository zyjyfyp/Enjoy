package com.yunsen.enjoy.model.event;

/**
 * Created by Administrator on 2018/6/14.
 */

public class UpNoticeUi {
    private int eventId;
    private String message;

    public UpNoticeUi(int eventId, String message) {
        this.eventId = eventId;
        this.message = message;
    }

    public int getEventId() {
        return eventId;
    }

    public String getMessage() {
        return message;
    }
}
