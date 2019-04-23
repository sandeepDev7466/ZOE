package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by htl-dev on 12-04-2019.
 */

public class TimeZoneResponse {


    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_data")
    @Expose
    private List<Timezone> resData = null;

    public TimeZoneResponse() {
    }

    public TimeZoneResponse(String apiResKey, String resStatus, List<Timezone> resData) {
        super();
        this.apiResKey = apiResKey;
        this.resStatus = resStatus;
        this.resData = resData;
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

    public List<Timezone> getResData() {
        return resData;
    }

    public void setResData(List<Timezone> resData) {
        this.resData = resData;
    }


    @Entity(tableName = "timezone")
    public static class Timezone {

        @PrimaryKey(autoGenerate = true)
        @NonNull
        @ColumnInfo(name = "id")
        private int id;

        @SerializedName("timezone_id")
        @Expose
        @ColumnInfo(name = "timezone_id")
        private String timezoneId;

        @SerializedName("timezone_code")
        @Expose
        @ColumnInfo(name = "timezone_code")
        private String timezoneCode;

        @SerializedName("timezone_name")
        @Expose
        @ColumnInfo(name = "timezone_name")
        private String timezoneName;


        @SerializedName("timezone_hours")
        @Expose
        @ColumnInfo(name = "timezone_hours")
        private String timezone_hours;


        public Timezone() {
        }

        public Timezone(int id, String timezoneId, String timezoneCode, String timezoneName, String timezone_hours) {
            this.id = id;
            this.timezoneId = timezoneId;
            this.timezoneCode = timezoneCode;
            this.timezoneName = timezoneName;
            this.timezone_hours = timezone_hours;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTimezoneId() {
            return timezoneId;
        }

        public void setTimezoneId(String timezoneId) {
            this.timezoneId = timezoneId;
        }

        public String getTimezoneCode() {
            return timezoneCode;
        }

        public void setTimezoneCode(String timezoneCode) {
            this.timezoneCode = timezoneCode;
        }

        public String getTimezoneName() {
            return timezoneName;
        }

        public void setTimezoneName(String timezoneName) {
            this.timezoneName = timezoneName;
        }

        public String getTimezone_hours() {
            return timezone_hours;
        }

        public void setTimezone_hours(String timezone_hours) {
            this.timezone_hours = timezone_hours;
        }


        @Override
        public String toString() {
            return timezoneName;
        }


    }



}
