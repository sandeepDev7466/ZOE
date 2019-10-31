package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VolunteerMarkHoursRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_type")
    @Expose
    private String user_type;
    @SerializedName("user_device")
    @Expose
    private String user_device;
    @SerializedName("map_id")
    @Expose
    private String map_id;
    @SerializedName("attend_in_time")
    @Expose
    private String attend_in_time;

    @SerializedName("attend_out_time")
    @Expose
    private String attend_out_time;
    @SerializedName("map_status")
    @Expose
    private String map_status;

    public VolunteerMarkHoursRequest() {
    }

    public VolunteerMarkHoursRequest(String userId, String user_device, String map_id, String attend_in_time, String attend_out_time, String map_status) {
        super();
        this.userId = userId;
        this.user_device = user_device;
        this.map_id = map_id;
        this.attend_in_time = attend_in_time;
        this.attend_out_time = attend_out_time;
        this.map_status = map_status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getMap_id() {
        return map_id;
    }

    public void setMap_id(String map_id) {
        this.map_id = map_id;
    }

    public String getAttend_in_time() {
        return attend_in_time;
    }

    public void setAttend_in_time(String attend_in_time) {
        this.attend_in_time = attend_in_time;
    }

    public String getAttend_out_time() {
        return attend_out_time;
    }

    public void setAttend_out_time(String attend_out_time) {
        this.attend_out_time = attend_out_time;
    }

    public String getMap_status() {
        return map_status;
    }

    public void setMap_status(String map_status) {
        this.map_status = map_status;
    }
}