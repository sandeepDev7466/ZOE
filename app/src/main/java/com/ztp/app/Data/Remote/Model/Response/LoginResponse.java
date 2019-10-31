package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_message")
    @Expose
    private String resMessage;
    @SerializedName("res_data")
    @Expose
    private ResData resData;
    @SerializedName("res_query_string")
    @Expose
    private String resQueryString;

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

    public ResData getResData() {
        return resData;
    }

    public void setResData(ResData resData) {
        this.resData = resData;
    }

    public String getResQueryString() {
        return resQueryString;
    }

    public void setResQueryString(String resQueryString) {
        this.resQueryString = resQueryString;
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
        @SerializedName("phone_valid")
        @Expose
        private String phoneValid;
        @SerializedName("user_status")
        @Expose
        private String userStatus;
        @SerializedName("user_profile_pic")
        @Expose
        private String userProfilePic;
        @SerializedName("user_cover_pic")
        @Expose
        private String userCoverPic;
        @SerializedName("user_timezone")
        @Expose
        private String userTimezone;
        @SerializedName("user_daylight")
        @Expose
        private String userDayLight;

        public ResData() {
        }

        public ResData(String userId, String userFName, String userLName, String userType, String userPhone, String phoneValid, String userStatus, String userProfilePic, String userCoverPic) {
            super();
            this.userId = userId;
            this.userFName = userFName;
            this.userLName = userLName;
            this.userType = userType;
            this.userPhone = userPhone;
            this.phoneValid = phoneValid;
            this.userStatus = userStatus;
            this.userProfilePic = userProfilePic;
            this.userCoverPic = userCoverPic;
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

        public String getPhoneValid() {
            return phoneValid;
        }

        public void setPhoneValid(String phoneValid) {
            this.phoneValid = phoneValid;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getUserProfilePic() {
            return userProfilePic;
        }

        public void setUserProfilePic(String userProfilePic) {
            this.userProfilePic = userProfilePic;
        }

        public String getUserCoverPic() {
            return userCoverPic;
        }

        public void setUserCoverPic(String userCoverPic) {
            this.userCoverPic = userCoverPic;
        }

        public String getUserTimezone() {
            return userTimezone;
        }

        public void setUserTimezone(String userTimezone) {
            this.userTimezone = userTimezone;
        }

        public String getUserDayLight() {
            return userDayLight;
        }

        public void setUserDayLight(String userDayLight) {
            this.userDayLight = userDayLight;
        }
    }

}