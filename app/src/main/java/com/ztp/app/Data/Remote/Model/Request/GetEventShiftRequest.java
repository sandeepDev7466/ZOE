package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEventShiftRequest {

    @SerializedName("event_id")
    @Expose
    private String eventId;

    public GetEventShiftRequest() {
    }

    public GetEventShiftRequest(String eventId) {
        super();
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

}