package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchoolResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_data")
    @Expose
    private List<School> schoolData = null;

    public SchoolResponse() {
    }

    public SchoolResponse(String apiResKey, String resStatus, List<School> schoolData) {
        super();
        this.apiResKey = apiResKey;
        this.resStatus = resStatus;
        this.schoolData = schoolData;
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

    public List<School> getSchoolData() {
        return schoolData;
    }

    public void setSchoolData(List<School> schoolData) {
        this.schoolData = schoolData;
    }

    public class School {

        @SerializedName("school_id")
        @Expose
        private String schoolId;
        @SerializedName("school_name")
        @Expose
        private String schoolName;

        public School() {
        }

        public School(String schoolId, String schoolName) {
            super();
            this.schoolId = schoolId;
            this.schoolName = schoolName;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

    }
}
