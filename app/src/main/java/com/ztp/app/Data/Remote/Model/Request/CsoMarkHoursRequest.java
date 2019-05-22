package com.ztp.app.Data.Remote.Model.Request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CsoMarkHoursRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_device")
    @Expose
    private String userDevice;
    @SerializedName("map_id")
    @Expose
    private String mapId;
    @SerializedName("attend_hours")
    @Expose
    private String attendHours;
    @SerializedName("vol_id")
    @Expose
    private String volId;

    public CsoMarkHoursRequest() {
    }

    public CsoMarkHoursRequest(String userId, String userType, String userDevice, String mapId, String attendHours, String volId) {
        super();
        this.userId = userId;
        this.userType = userType;
        this.userDevice = userDevice;
        this.mapId = mapId;
        this.attendHours = attendHours;
        this.volId = volId;
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

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getAttendHours() {
        return attendHours;
    }

    public void setAttendHours(String attendHours) {
        this.attendHours = attendHours;
    }

    public String getVolId() {
        return volId;
    }

    public void setVolId(String volId) {
        this.volId = volId;
    }
}
