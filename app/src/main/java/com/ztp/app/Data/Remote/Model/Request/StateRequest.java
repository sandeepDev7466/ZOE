package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateRequest {

    @SerializedName("country_id")
    @Expose
    private String countryId;

    public StateRequest() {
    }

    public StateRequest(String countryId) {
        super();
        this.countryId = countryId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

}