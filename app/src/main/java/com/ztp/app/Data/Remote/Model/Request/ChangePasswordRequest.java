package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {

    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("user_pass_old")
    @Expose
    private String user_pass_old;
    @SerializedName("user_pass_new")
    @Expose
    private String user_pass_new;
    @SerializedName("user_device")
    @Expose
    private String user_device;
    @SerializedName("user_type")
    @Expose
    private String user_type;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String user_id,String user_pass_old,String user_pass_new,String user_device,String user_type) {
        super();
        this.user_id = user_id;
        this.user_pass_old = user_pass_old;
        this.user_pass_new = user_pass_new;
        this.user_device = user_device;
        this.user_type = user_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pass_old() {
        return user_pass_old;
    }

    public void setUser_pass_old(String user_pass_old) {
        this.user_pass_old = user_pass_old;
    }

    public String getUser_pass_new() {
        return user_pass_new;
    }

    public void setUser_pass_new(String user_pass_new) {
        this.user_pass_new = user_pass_new;
    }

    public String getUser_device() {
        return user_device;
    }

    public void setUser_device(String user_device) {
        this.user_device = user_device;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}