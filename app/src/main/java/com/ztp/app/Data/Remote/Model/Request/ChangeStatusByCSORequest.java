package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeStatusByCSORequest {

    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("user_type")
    @Expose
    private String user_type;
    @SerializedName("user_device")
    @Expose
    private String user_device;
    @SerializedName("map_id")
    @Expose
    private String map_id;

    @SerializedName("map_status")
    @Expose
    private String map_status;


    public ChangeStatusByCSORequest() {
    }

    public ChangeStatusByCSORequest(String user_id, String user_type, String user_device, String map_id, String map_status) {
        super();
        this.user_id = user_id;
        this.user_type = user_type;
        this.user_device = user_device;
        this.map_id = map_id;
        this.map_status = map_status;
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

    public String getMap_id() {
        return map_id;
    }

    public void setMap_id(String map_id) {
        this.map_id = map_id;
    }

    public String getMap_status() {
        return map_status;
    }

    public void setMap_status(String map_status) {
        this.map_status = map_status;
    }
}