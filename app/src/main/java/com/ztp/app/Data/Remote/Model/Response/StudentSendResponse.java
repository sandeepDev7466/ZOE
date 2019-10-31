package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentSendResponse
{
    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<ResDatum> resData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public List<ResDatum> getResData() {
        return resData;
    }

    public void setResData(List<ResDatum> resData) {
        this.resData = resData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }


    public class ResDatum {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("stu_id")
        @Expose
        private String stuId;
        @SerializedName("map_status")
        @Expose
        private String mapStatus;
        @SerializedName("user_f_name")
        @Expose
        private String userFName;
        @SerializedName("user_l_name")
        @Expose
        private String userLName;
        @SerializedName("user_email")
        @Expose
        private String userEmail;
        @SerializedName("map_add_date")
        @Expose
        private String mapAddDate;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStuId() {
            return stuId;
        }

        public void setStuId(String stuId) {
            this.stuId = stuId;
        }

        public String getMapStatus() {
            return mapStatus;
        }

        public void setMapStatus(String mapStatus) {
            this.mapStatus = mapStatus;
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

        public String getMapAddDate() {
            return mapAddDate;
        }

        public void setMapAddDate(String mapAddDate) {
            this.mapAddDate = mapAddDate;
        }

    }
}




