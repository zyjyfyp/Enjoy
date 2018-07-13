package com.yunsen.enjoy.model.event;

/**
 * Created by Administrator on 2018/5/29.
 */

public class UpFilterReqEvent {
    private int eventId;
    private String type;
    private int currentIndex;
    public UpFilterReqEvent(int eventId, String type) {
        this.eventId = eventId;
        this.type = type;
    }

    public UpFilterReqEvent(int eventId, int currentIndex) {
        this.eventId = eventId;
        this.currentIndex = currentIndex;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}
