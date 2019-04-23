package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEventDetailRequest {

    @SerializedName("event_id")
    @Expose
    private String eventId;

    public GetEventDetailRequest() {
    }

    public GetEventDetailRequest(String eventId) {
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