package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSearchShiftListRequest {

    @SerializedName("event_id")
    @Expose
    private String event_id;
    @SerializedName("user_id")
    @Expose
    private String user_id;

    public GetSearchShiftListRequest() {
    }

    public GetSearchShiftListRequest(String event_id, String user_id) {
        super();
        this.event_id = event_id;
        this.user_id = user_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}