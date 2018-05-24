package com.yunsen.enjoy.model.event;

/**
 * Created by Administrator on 2018/5/24.
 */

public class UpHomeUiEvent {
    private int eventId;
    private long currentTime;

    public UpHomeUiEvent(int eventId, long currentTime) {
        this.eventId = eventId;
        this.currentTime = currentTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
