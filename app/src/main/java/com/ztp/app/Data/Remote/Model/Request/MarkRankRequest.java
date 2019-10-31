package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarkRankRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_device")
    @Expose
    private String userDevice;
    @SerializedName("vol_id")
    @Expose
    private String volId;
    @SerializedName("attend_rank")
    @Expose
    private String attendRank;
    @SerializedName("map_id")
    @Expose
    private String mapId;
    @SerializedName("map_rank_comment")
    @Expose
    private String mapRankComment;

    public MarkRankRequest() {
    }

    public MarkRankRequest(String userId, String userType, String userDevice, String volId, String attendRank, String mapId,String mapRankComment) {
        this.userId = userId;
        this.userType = userType;
        this.userDevice = userDevice;
        this.volId = volId;
        this.attendRank = attendRank;
        this.mapId = mapId;
        this.mapRankComment = mapRankComment;
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

    public String getVolId() {
        return volId;
    }

    public void setVolId(String volId) {
        this.volId = volId;
    }

    public String getAttendRank() {
        return attendRank;
    }

    public void setAttendRank(String attendRank) {
        this.attendRank = attendRank;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getMapRankComment() {
        return mapRankComment;
    }

    public void setMapRankComment(String mapRankComment) {
        this.mapRankComment = mapRankComment;
    }
}

