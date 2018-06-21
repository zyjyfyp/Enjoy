package com.yunsen.enjoy.model.event;

/**
 * Created by Administrator on 2018/6/21.
 */

public class DiscoverEvent {
    private int eventId;
    private int position;

    public DiscoverEvent(int eventId, int position) {
        this.eventId = eventId;
        this.position = position;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
