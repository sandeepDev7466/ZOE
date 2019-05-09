package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteShiftRequest {
    @SerializedName("shift_id")
    @Expose
    private String shift_id;
    public String getShiftId() {
        return shift_id;
    }
    public void setShiftId(String shift_id) {
        this.shift_id = shift_id;
    }
}