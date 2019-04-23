package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteShiftRequest {

    @SerializedName("shift_id")
    @Expose
    private String shiftId;

    public DeleteShiftRequest() {
    }

    public DeleteShiftRequest(String shiftId) {
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