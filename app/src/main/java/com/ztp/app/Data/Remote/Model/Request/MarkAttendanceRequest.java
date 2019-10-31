package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarkAttendanceRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("shift_id")
    @Expose
    private String shiftId;
    @SerializedName("log_track_time")
    @Expose
    private String logTrackTime;
    @SerializedName("log_latitude")
    @Expose
    private String logLatitude;
    @SerializedName("log_longitude")
    @Expose
    private String logLongitude;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public String getLogTrackTime() {
        return logTrackTime;
    }

    public void setLogTrackTime(String logTrackTime) {
        this.logTrackTime = logTrackTime;
    }

    public String getLogLatitude() {
        return logLatitude;
    }

    public void setLogLatitude(String logLatitude) {
        this.logLatitude = logLatitude;
    }

    public String getLogLongitude() {
        return logLongitude;
    }

    public void setLogLongitude(String logLongitude) {
        this.logLongitude = logLongitude;
    }

}