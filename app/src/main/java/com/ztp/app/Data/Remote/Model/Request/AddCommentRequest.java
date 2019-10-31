package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCommentRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("map_id")
    @Expose
    private String mapId;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_device")
    @Expose
    private String userDevice;
    @SerializedName("map_status")
    @Expose
    private String mapStatus;
    @SerializedName("map_status_comment")
    @Expose
    private String mapStatusComment;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
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

    public String getMapStatus() {
        return mapStatus;
    }

    public void setMapStatus(String mapStatus) {
        this.mapStatus = mapStatus;
    }

    public String getMapStatusComment() {
        return mapStatusComment;
    }

    public void setMapStatusComment(String mapStatusComment) {
        this.mapStatusComment = mapStatusComment;
    }

}