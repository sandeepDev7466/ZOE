package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateUserTimezoneRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("login_timezone")
    @Expose
    private String loginTimezone;
    @SerializedName("login_daylight")
    @Expose
    private String loginDaylight;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginTimezone() {
        return loginTimezone;
    }

    public void setLoginTimezone(String loginTimezone) {
        this.loginTimezone = loginTimezone;
    }

    public String getLoginDaylight() {
        return loginDaylight;
    }

    public void setLoginDaylight(String loginDaylight) {
        this.loginDaylight = loginDaylight;
    }

}