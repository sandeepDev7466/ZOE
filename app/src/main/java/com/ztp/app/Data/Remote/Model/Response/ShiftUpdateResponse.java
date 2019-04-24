package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by htl-dev on 24-04-2019.
 */

public class ShiftUpdateResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_data")
    @Expose
    private ShiftAddResponse.ResData resData;


    public ShiftUpdateResponse() {
    }

    public ShiftUpdateResponse(String apiResKey, String resStatus, ShiftAddResponse.ResData resData) {
        super();
        this.apiResKey = apiResKey;
        this.resStatus = resStatus;
        this.resData = resData;
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

    public ShiftAddResponse.ResData getResData() {
        return resData;
    }

    public void setResData(ShiftAddResponse.ResData resData) {
        this.resData = resData;
    }

    public class ResData {

        @SerializedName("user_id")
        @Expose
        private String userId;

        public ResData() {
        }

        public ResData(String userId) {
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

}
