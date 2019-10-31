package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VolunteerTargetResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<TargetData> targetData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public List<TargetData> getTargetData() {
        return targetData;
    }

    public void setTargetData(List<TargetData> targetData) {
        this.targetData = targetData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    @Entity(tableName = "target_data")
    public static class TargetData {

        @NonNull
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        private int id;

        @SerializedName("vol_hours")
        @Expose
        @ColumnInfo(name = "vol_hours")
        private String vol_hours;

        @SerializedName("vol_hours_req")
        @Expose
        @ColumnInfo(name = "vol_hours_req")
        private String vol_hours_req;

        @SerializedName("vol_current_gpa")
        @Expose
        @ColumnInfo(name = "vol_current_gpa")
        private String vol_current_gpa;

        @SerializedName("vol_target_gpa")
        @Expose
        @ColumnInfo(name = "vol_target_gpa")
        private String vol_target_gpa;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVol_hours() {
            return vol_hours;
        }

        public void setVol_hours(String vol_hours) {
            this.vol_hours = vol_hours;
        }

        public String getVol_hours_req() {
            return vol_hours_req;
        }

        public void setVol_hours_req(String vol_hours_req) {
            this.vol_hours_req = vol_hours_req;
        }

        public String getVol_current_gpa() {
            return vol_current_gpa;
        }

        public void setVol_current_gpa(String vol_current_gpa) {
            this.vol_current_gpa = vol_current_gpa;
        }

        public String getVol_target_gpa() {
            return vol_target_gpa;
        }

        public void setVol_target_gpa(String vol_target_gpa) {
            this.vol_target_gpa = vol_target_gpa;
        }

    }
}