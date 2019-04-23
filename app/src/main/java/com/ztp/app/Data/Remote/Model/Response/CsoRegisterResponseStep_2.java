package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CsoRegisterResponseStep_2 {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public CsoRegisterResponseStep_2() {
    }

    public CsoRegisterResponseStep_2(String apiResKey, String resStatus) {
        super();
        this.apiResKey = apiResKey;
        this.resStatus = resStatus;
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

}