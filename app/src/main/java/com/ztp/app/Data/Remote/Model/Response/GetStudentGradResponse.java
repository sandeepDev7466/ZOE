package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetStudentGradResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<ResData> resData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

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
        @SerializedName("school_id")
        @Expose
        private String schoolId;
        @SerializedName("grade_id")
        @Expose
        private String gradeId;
        @SerializedName("graduation_date")
        @Expose
        private String graduationDate;
        @SerializedName("sem_end_date")
        @Expose
        private String semEndDate;
        @SerializedName("user_current_gpa")
        @Expose
        private String userCurrentGpa;
        @SerializedName("user_target_gpa")
        @Expose
        private String userTargetGpa;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getGradeId() {
            return gradeId;
        }

        public void setGradeId(String gradeId) {
            this.gradeId = gradeId;
        }

        public String getGraduationDate() {
            return graduationDate;
        }

        public void setGraduationDate(String graduationDate) {
            this.graduationDate = graduationDate;
        }

        public String getSemEndDate() {
            return semEndDate;
        }

        public void setSemEndDate(String semEndDate) {
            this.semEndDate = semEndDate;
        }

        public String getUserCurrentGpa() {
            return userCurrentGpa;
        }

        public void setUserCurrentGpa(String userCurrentGpa) {
            this.userCurrentGpa = userCurrentGpa;
        }

        public String getUserTargetGpa() {
            return userTargetGpa;
        }

        public void setUserTargetGpa(String userTargetGpa) {
            this.userTargetGpa = userTargetGpa;
        }

    }
}
