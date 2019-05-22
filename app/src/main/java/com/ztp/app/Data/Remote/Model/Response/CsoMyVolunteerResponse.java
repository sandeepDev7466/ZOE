package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CsoMyVolunteerResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<ResData> resData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public CsoMyVolunteerResponse() {
    }

    public CsoMyVolunteerResponse(String apiResKey, List<ResData> resData, String resStatus) {
        super();
        this.apiResKey = apiResKey;
        this.resData = resData;
        this.resStatus = resStatus;
    }

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public List<ResData> getResData() {
        return resData;
    }

    public void setResData(List<ResData> resData) {
        this.resData = resData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
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
        @SerializedName("user_email")
        @Expose
        private String userEmail;
        @SerializedName("user_phone")
        @Expose
        private String userPhone;
        @SerializedName("user_city")
        @Expose
        private String userCity;
        @SerializedName("user_grad_date")
        @Expose
        private String userGradDate;
        @SerializedName("user_hours_req")
        @Expose
        private String userHoursReq;
        @SerializedName("user_rank")
        @Expose
        private String userRank;
        @SerializedName("user_hours")
        @Expose
        private String userHours;
        @SerializedName("user_grad_date_format")
        @Expose
        private String userGradDateFormat;

        public ResData() {
        }

        public ResData(String userId, String userFName, String userLName, String userEmail, String userPhone, String userCity, String userGradDate, String userHoursReq, String userRank, String userHours, String userGradDateFormat) {
            super();
            this.userId = userId;
            this.userFName = userFName;
            this.userLName = userLName;
            this.userEmail = userEmail;
            this.userPhone = userPhone;
            this.userCity = userCity;
            this.userGradDate = userGradDate;
            this.userHoursReq = userHoursReq;
            this.userRank = userRank;
            this.userHours = userHours;
            this.userGradDateFormat = userGradDateFormat;
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

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserCity() {
            return userCity;
        }

        public void setUserCity(String userCity) {
            this.userCity = userCity;
        }

        public String getUserGradDate() {
            return userGradDate;
        }

        public void setUserGradDate(String userGradDate) {
            this.userGradDate = userGradDate;
        }

        public String getUserHoursReq() {
            return userHoursReq;
        }

        public void setUserHoursReq(String userHoursReq) {
            this.userHoursReq = userHoursReq;
        }

        public String getUserRank() {
            return userRank;
        }

        public void setUserRank(String userRank) {
            this.userRank = userRank;
        }

        public String getUserHours() {
            return userHours;
        }

        public void setUserHours(String userHours) {
            this.userHours = userHours;
        }

        public String getUserGradDateFormat() {
            return userGradDateFormat;
        }

        public void setUserGradDateFormat(String userGradDateFormat) {
            this.userGradDateFormat = userGradDateFormat;
        }

    }
}