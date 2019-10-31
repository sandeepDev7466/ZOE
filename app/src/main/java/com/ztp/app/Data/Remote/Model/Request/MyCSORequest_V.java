package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCSORequest_V {

    @SerializedName("user_id")
    @Expose
    private String userId;

    public MyCSORequest_V() {
    }

    public MyCSORequest_V(String userId) {
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
