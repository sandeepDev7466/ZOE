package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnlinkUserRequest {

    @SerializedName("cso_id")
    @Expose
    private String csoId;
    @SerializedName("vol_id")
    @Expose
    private String volId;
    @SerializedName("user_device")
    @Expose
    private String userDevice;

    public UnlinkUserRequest(String csoId, String volId, String userDevice) {
        this.csoId = csoId;
        this.volId = volId;
        this.userDevice = userDevice;
    }

    public String getCsoId() {
        return csoId;
    }

    public void setCsoId(String csoId) {
        this.csoId = csoId;
    }

    public String getVolId() {
        return volId;
    }

    public void setVolId(String volId) {
        this.volId = volId;
    }

    public String getUserDevice() {
        return userDevice;
    }

    public void setUserDevice(String userDevice) {
        this.userDevice = userDevice;
    }

}