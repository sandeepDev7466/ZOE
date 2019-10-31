package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

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

    @Entity(tableName = "school")
    public static class School {

        @PrimaryKey
        @NonNull
        @SerializedName("school_id")
        @Expose
        @ColumnInfo(name = "school_id")
        private String schoolId;

        @SerializedName("school_name")
        @Expose
        @ColumnInfo(name = "school_name")
        private String schoolName;

        @SerializedName("user_id")
        @Expose
        @ColumnInfo(name = "user_id")
        private String userId;

        public School(String schoolId, String schoolName) {
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

        @Override
        public String toString() {
            return schoolName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
