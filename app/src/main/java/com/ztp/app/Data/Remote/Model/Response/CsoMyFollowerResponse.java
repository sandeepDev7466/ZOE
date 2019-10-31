package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CsoMyFollowerResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<SeeFollower> resData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public List<SeeFollower> getResData() {
        return resData;
    }

    public void setResData(List<SeeFollower> resData) {
        this.resData = resData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    @Entity(tableName = "see_follower")
    public static class SeeFollower {

        @PrimaryKey(autoGenerate = true)
        @NonNull
        private int id;

        @SerializedName("user_id")
        @Expose
        @ColumnInfo(name = "user_id")
        private String userId;

        @SerializedName("user_f_name")
        @Expose
        @ColumnInfo(name = "user_f_name")
        private String userFName;

        @SerializedName("user_l_name")
        @Expose
        @ColumnInfo(name = "user_l_name")
        private String userLName;

        @SerializedName("user_email")
        @Expose
        @ColumnInfo(name = "user_email")
        private String userEmail;

        @SerializedName("user_phone")
        @Expose
        @ColumnInfo(name = "user_phone")
        private String userPhone;

        @SerializedName("user_city")
        @Expose
        @ColumnInfo(name = "user_city")
        private String userCity;

        @SerializedName("user_grad_date")
        @Expose
        @ColumnInfo(name = "user_grad_date")
        private String userGradDate;

        @SerializedName("user_hours_req")
        @Expose
        @ColumnInfo(name = "user_hours_req")
        private String userHoursReq;

        @SerializedName("user_rank")
        @Expose
        @ColumnInfo(name = "user_rank")
        private String userRank;

        @SerializedName("user_hours")
        @Expose
        @ColumnInfo(name = "user_hours")
        private String userHours;

        @SerializedName("user_grad_date_format")
        @Expose
        @ColumnInfo(name = "user_grad_date_format")
        private String userGradDateFormat;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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