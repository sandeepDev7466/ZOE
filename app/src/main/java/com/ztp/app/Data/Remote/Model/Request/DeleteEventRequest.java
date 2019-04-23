package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by htl-dev on 19-04-2019.
 */

public class DeleteEventRequest {

    @SerializedName("event_id")
    @Expose
    private String event_id;

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }


}
