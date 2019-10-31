package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CsoDashboardCombinedRequest {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("event_month")
    @Expose
    private String eventMonth;
    @SerializedName("event_year")
    @Expose
    private String eventYear;
    @SerializedName("countdown_date")
    @Expose
    private String countdownDate;

    public CsoDashboardCombinedRequest() {
    }

    public CsoDashboardCombinedRequest(String userId, String eventMonth, String eventYear, String countdownDate) {
        this.userId = userId;
        this.eventMonth = eventMonth;
        this.eventYear = eventYear;
        this.countdownDate = countdownDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventMonth() {
        return eventMonth;
    }

    public void setEventMonth(String eventMonth) {
        this.eventMonth = eventMonth;
    }

    public String getEventYear() {
        return eventYear;
    }

    public void setEventYear(String eventYear) {
        this.eventYear = eventYear;
    }

    public String getCountdownDate() {
        return countdownDate;
    }

    public void setCountdownDate(String countdownDate) {
        this.countdownDate = countdownDate;
    }

}
