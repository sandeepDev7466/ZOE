package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by htl-dev on 19-04-2019.
 */

public class DuplicateEventRequest {

    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("event_id")
    @Expose
    private String event_id;
    @SerializedName("user_type")
    @Expose
    private String user_type;
    @SerializedName("user_device")
    @Expose
    private String user_device;

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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_device() {
        return user_device;
    }

    public void setUser_device(String user_device) {
        this.user_device = user_device;
    }
}
