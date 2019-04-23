package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetShiftDetailRequest {

    @SerializedName("shift_id")
    @Expose
    private String shiftId;

    public GetShiftDetailRequest() {
    }

    public GetShiftDetailRequest(String shiftId) {
        super();
        this.shiftId = shiftId;
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }
}