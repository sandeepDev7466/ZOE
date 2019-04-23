package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private ResData resData;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    @SerializedName("res_message")
    @Expose
    private String resMessage;

    public LoginResponse() {
    }

    public LoginResponse(String apiResKey, ResData resData, String resStatus, String resMessage) {
        this.apiResKey = apiResKey;
        this.resData = resData;
        this.resStatus = resStatus;
        this.resMessage = resMessage;
    }

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public ResData getResData() {
        return resData;
    }

    public void setResData(ResData resData) {
        this.resData = resData;
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

    public class ResData {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("user_f_name")
        @Expose
        private String userFName;
        @SerializedName("user_l_name")
        @Expose
        private String userLName;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("user_phone")
        @Expose
        private String userPhone;

        public ResData() {
        }

        public ResData(String userId, String userFName, String userLName, String userType, String userPhone) {
            super();
            this.userId = userId;
            this.userFName = userFName;
            this.userLName = userLName;
            this.userType = userType;
            this.userPhone = userPhone;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserFName() {
            return userFName;
        }

        public void setUserFName(String userFName) {
            this.userFName = userFName;
        }

        public String getUserLName() {
            return userLName;
        }

        public void setUserLName(String userLName) {
            this.userLName = userLName;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

    }

}