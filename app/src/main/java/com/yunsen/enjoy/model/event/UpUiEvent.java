package com.yunsen.enjoy.model.event;

/**
 * Created by Administrator on 2018/4/24.
 */

public class UpUiEvent {
    private int eventId;

    public UpUiEvent(int eventId) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
