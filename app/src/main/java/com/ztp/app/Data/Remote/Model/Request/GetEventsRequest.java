package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEventsRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;

    public GetEventsRequest() {
    }

    public GetEventsRequest(String userId) {
        super();
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}