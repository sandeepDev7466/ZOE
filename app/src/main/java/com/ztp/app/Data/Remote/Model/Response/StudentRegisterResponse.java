package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentRegisterResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_data")
    @Expose
    private ResData resData;
    @SerializedName("res_message")
    @Expose
    private String resMessage;

    public StudentRegisterResponse() {
    }

    public StudentRegisterResponse(String apiResKey, String resStatus, ResData resData, String resMessage) {
        this.apiResKey = apiResKey;
        this.resStatus = resStatus;
        this.resData = resData;
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

    public ResData getResData() {
        return resData;
    }

    public void setResData(ResData resData) {
        this.resData = resData;
    }

    public String getResMessage() {
        return resMessage;
    }

    public void setResMessage(String resMessage) {
        this.resMessage = resMessage;
    }

    public class ResData {

        @SerializedName("user_id")
        @Expose
        private String userId;

        @SerializedName("phone_otp")
        @Expose
        private String phoneOtp;

        public ResData() {
        }

        public ResData(String userId, String phoneOtp) {
            this.userId = userId;
            this.phoneOtp = phoneOtp;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPhoneOtp() {
            return phoneOtp;
        }

        public void setPhoneOtp(String phoneOtp) {
            this.phoneOtp = phoneOtp;
        }
    }

}