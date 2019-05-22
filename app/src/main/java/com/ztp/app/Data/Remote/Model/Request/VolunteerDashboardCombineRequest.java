
package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VolunteerDashboardCombineRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("list_date")
    @Expose
    private String listDate;

    public VolunteerDashboardCombineRequest() {
    }

    public VolunteerDashboardCombineRequest(String userId, String listDate) {
        super();
        this.userId = userId;
        this.listDate = listDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getListDate() {
        return listDate;
    }

    public void setListDate(String listDate) {
        this.listDate = listDate;
    }

}