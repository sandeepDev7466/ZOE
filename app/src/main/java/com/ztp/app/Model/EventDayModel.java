package com.ztp.app.Model;

import com.applandeo.materialcalendarview.EventDay;

public class EventDayModel
{
    private EventDay eventDay;
    private String eventId;
    private String eventHeading;

    public EventDayModel() {
    }

    public EventDayModel(EventDay eventDay, String eventId, String eventHeading) {
        this.eventDay = eventDay;
        this.eventId = eventId;
        this.eventHeading = eventHeading;
    }

    public EventDay getEventDay() {
        return eventDay;
    }

    public void setEventDay(EventDay eventDay) {
        this.eventDay = eventDay;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventHeading() {
        return eventHeading;
    }

    public void setEventHeading(String eventHeading) {
        this.eventHeading = eventHeading;
    }
}
