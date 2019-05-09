package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostVolunteerRequest {

    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("user_type")
    @Expose
    private String user_type;
    @SerializedName("user_device")
    @Expose
    private String user_device;
    @SerializedName("event_id")
    @Expose
    private String event_id;
    @SerializedName("shift_id")
    @Expose
    private String shift_id;
    @SerializedName("cso_id")
    @Expose
    private String cso_id;

    public PostVolunteerRequest() {
    }

    public PostVolunteerRequest(String user_id, String user_type, String user_device, String event_id,String shift_id,String cso_id) {
        super();
        this.user_id = user_id;
        this.user_type = user_type;
        this.user_device = user_device;
        this.event_id =event_id;
        this.shift_id =shift_id;
        this.cso_id =cso_id;

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

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getShift_id() {
        return shift_id;
    }

    public void setShift_id(String shift_id) {
        this.shift_id = shift_id;
    }

    public String getCso_id() {
        return cso_id;
    }

    public void setCso_id(String cso_id) {
        this.cso_id = cso_id;
    }
}