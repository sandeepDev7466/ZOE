package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GradeResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_data")
    @Expose
    private List<Grade> resData = null;

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

    public List<Grade> getResData() {
        return resData;
    }

    public void setResData(List<Grade> resData) {
        this.resData = resData;
    }

    public class Grade {

        @SerializedName("setting_id")
        @Expose
        private String settingId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("grade_id")
        @Expose
        private String gradeId;
        @SerializedName("grade_number")
        @Expose
        private String gradeNumber;
        @SerializedName("grade_name")
        @Expose
        private String gradeName;
        @SerializedName("grade_date")
        @Expose
        private String gradeDate;
        @SerializedName("req_hours")
        @Expose
        private String reqHours;
        @SerializedName("status")
        @Expose
        private String status;

        public String getSettingId() {
            return settingId;
        }

        public void setSettingId(String settingId) {
            this.settingId = settingId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getGradeId() {
            return gradeId;
        }

        public void setGradeId(String gradeId) {
            this.gradeId = gradeId;
        }

        public String getGradeNumber() {
            return gradeNumber;
        }

        public void setGradeNumber(String gradeNumber) {
            this.gradeNumber = gradeNumber;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }

        public String getGradeDate() {
            return gradeDate;
        }

        public void setGradeDate(String gradeDate) {
            this.gradeDate = gradeDate;
        }

        public String getReqHours() {
            return reqHours;
        }

        public void setReqHours(String reqHours) {
            this.reqHours = reqHours;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
