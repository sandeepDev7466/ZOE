package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateVolunteerHoursRequest {

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
    @SerializedName("attend_hours_vol")
    @Expose
    private String attendHoursVol;

    public UpdateVolunteerHoursRequest() {
    }

    public UpdateVolunteerHoursRequest(String userId, String userType, String userDevice, String mapId, String attendHoursVol) {
        super();
        this.userId = userId;
        this.userType = userType;
        this.userDevice = userDevice;
        this.mapId = mapId;
        this.attendHoursVol = attendHoursVol;
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

    public String getAttendHoursVol() {
        return attendHoursVol;
    }

    public void setAttendHoursVol(String attendHoursVol) {
        this.attendHoursVol = attendHoursVol;
    }
}