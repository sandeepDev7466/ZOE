package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PotentialFriendsRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("user_type")
    @Expose
    private String userType;

    @SerializedName("user_device")
    @Expose
    private String userDevice;

    public PotentialFriendsRequest(String userId, String userType, String userDevice) {
        this.userId = userId;
        this.userType = userType;
        this.userDevice = userDevice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserDevice() {
        return userDevice;
    }

    public void setUserDevice(String userDevice) {
        this.userDevice = userDevice;
    }
}