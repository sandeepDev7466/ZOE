package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteShiftResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_message")
    @Expose
    private String resMessage;

    public DeleteShiftResponse() {
    }

    public DeleteShiftResponse(String apiResKey, String resStatus, String resMessage) {
        super();
        this.apiResKey = apiResKey;
        this.resStatus = resStatus;
        this.resMessage = resMessage;
    }

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public String getResMessage() {
        return resMessage;
    }

    public void setResMessage(String resMessage) {
        this.resMessage = resMessage;
    }

}