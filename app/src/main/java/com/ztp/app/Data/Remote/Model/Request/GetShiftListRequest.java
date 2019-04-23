package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by htl-dev on 17-04-2019.
 */

public class GetShiftListRequest {

    @SerializedName("event_id")
    @Expose
    private String event_id;

    public GetShiftListRequest() {
    }

    public GetShiftListRequest(String event_id) {
        super();
        this.event_id = event_id;
    }

    public String getEventId() {
        return event_id;
    }

    public void setEventId(String event_id) {
        this.event_id = event_id;
    }


}
