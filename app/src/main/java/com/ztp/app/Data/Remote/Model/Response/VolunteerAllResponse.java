package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VolunteerAllResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<VolunteerResponse> resData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public List<VolunteerResponse> getResData() {
        return resData;
    }

    public void setResData(List<VolunteerResponse> resData) {
        this.resData = resData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    @Entity(tableName = "all_volunteer_response")
    public static class VolunteerResponse implements Serializable {

        @NonNull
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        private int id;

        @SerializedName("map_id")
        @Expose
        @ColumnInfo(name = "map_id")
        private String mapId;

        @SerializedName("user_id")
        @Expose
        @ColumnInfo(name = "user_id")
        private String userId;

        @SerializedName("event_id")
        @Expose
        @ColumnInfo(name = "event_id")
        private String eventId;

        @SerializedName("cso_id")
        @Expose
        @ColumnInfo(name = "cso_id")
        private String csoId;

        @SerializedName("shift_id")
        @Expose
        @ColumnInfo(name = "shift_id")
        private String shiftId;

        @SerializedName("attend_present")
        @Expose
        @ColumnInfo(name = "attend_present")
        private String attendPresent;

        @SerializedName("attend_in_time")
        @Expose
        @ColumnInfo(name = "attend_in_time")
        private String attendInTime;

        @SerializedName("attend_out_time")
        @Expose
        @ColumnInfo(name = "attend_out_time")
        private String attendOutTime;

        @SerializedName("attend_hours")
        @Expose
        @ColumnInfo(name = "attend_hours")
        private String attendHours;

        @SerializedName("attend_cso_grade")
        @Expose
        @ColumnInfo(name = "attend_cso_grade")
        private String attendCsoGrade;

        @SerializedName("attend_grade_date")
        @Expose
        @ColumnInfo(name = "attend_grade_date")
        private String attendGradeDate;

        @SerializedName("attend_cso_remarks")
        @Expose
        @ColumnInfo(name = "attend_cso_remarks")
        private String attendCsoRemarks;

        @SerializedName("attend_remarks_date")
        @Expose
        @ColumnInfo(name = "attend_remarks_date")
        private String attendRemarksDate;

        @SerializedName("attend_rate")
        @Expose
        @ColumnInfo(name = "attend_rate")
        private String attendRate;

        @SerializedName("attend_rate_remarks")
        @Expose
        @ColumnInfo(name = "attend_rate_remarks")
        private String attendRateRemarks;

        @SerializedName("attend_rate_date")
        @Expose
        @ColumnInfo(name = "attend_rate_date")
        private String attendRateDate;

        @SerializedName("map_status")
        @Expose
        @ColumnInfo(name = "map_status")
        private String mapStatus;

        @SerializedName("map_add_date")
        @Expose
        @ColumnInfo(name = "map_add_date")
        private String mapAddDate;

        @SerializedName("map_update_date")
        @Expose
        @ColumnInfo(name = "map_update_date")
        private String mapUpdateDate;

        @SerializedName("event_heading")
        @Expose
        @ColumnInfo(name = "event_heading")
        private String eventHeading;

        @SerializedName("shift_date_format")
        @Expose
        @ColumnInfo(name = "shift_date_format")
        private String shiftDate;

        @SerializedName("shift_start_time")
        @Expose
        @ColumnInfo(name = "shift_start_time")
        private String shiftStartTime;

        @SerializedName("shift_end_time")
        @Expose
        @ColumnInfo(name = "shift_end_time")
        private String shiftEndTime;

        @SerializedName("shift_rank")
        @Expose
        @ColumnInfo(name = "shift_rank")
        private String shiftRank;

        @SerializedName("shift_task")
        @Expose
        @ColumnInfo(name = "shift_task")
        private String shiftTask;

        @SerializedName("cso_email")
        @Expose
        @ColumnInfo(name = "cso_email")
        private String cso_email;

        @SerializedName("cso_f_name")
        @Expose
        @ColumnInfo(name = "cso_f_name")
        private String cso_f_name;

        @SerializedName("cso_l_name")
        @Expose
        @ColumnInfo(name = "cso_l_name")
        private String cso_l_name;

        @SerializedName("map_status_comment")
        @Expose
        @ColumnInfo(name = "map_status_comment")
        private String mapStatusComment;

        @SerializedName("map_rank_comment")
        @Expose
        @ColumnInfo(name = "map_rank_comment")
        private String mapRankComment;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMapId() {
            return mapId;
        }

        public void setMapId(String mapId) {
            this.mapId = mapId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getCsoId() {
            return csoId;
        }

        public void setCsoId(String csoId) {
            this.csoId = csoId;
        }

        public String getShiftId() {
            return shiftId;
        }

        public void setShiftId(String shiftId) {
            this.shiftId = shiftId;
        }

        public String getAttendPresent() {
            return attendPresent;
        }

        public void setAttendPresent(String attendPresent) {
            this.attendPresent = attendPresent;
        }

        public String getAttendInTime() {
            return attendInTime;
        }

        public void setAttendInTime(String attendInTime) {
            this.attendInTime = attendInTime;
        }

        public String getAttendOutTime() {
            return attendOutTime;
        }

        public void setAttendOutTime(String attendOutTime) {
            this.attendOutTime = attendOutTime;
        }

        public String getAttendHours() {
            return attendHours;
        }

        public void setAttendHours(String attendHours) {
            this.attendHours = attendHours;
        }

        public String getAttendCsoGrade() {
            return attendCsoGrade;
        }

        public void setAttendCsoGrade(String attendCsoGrade) {
            this.attendCsoGrade = attendCsoGrade;
        }

        public String getAttendGradeDate() {
            return attendGradeDate;
        }

        public void setAttendGradeDate(String attendGradeDate) {
            this.attendGradeDate = attendGradeDate;
        }

        public String getAttendCsoRemarks() {
            return attendCsoRemarks;
        }

        public void setAttendCsoRemarks(String attendCsoRemarks) {
            this.attendCsoRemarks = attendCsoRemarks;
        }

        public String getAttendRemarksDate() {
            return attendRemarksDate;
        }

        public void setAttendRemarksDate(String attendRemarksDate) {
            this.attendRemarksDate = attendRemarksDate;
        }

        public String getAttendRate() {
            return attendRate;
        }

        public void setAttendRate(String attendRate) {
            this.attendRate = attendRate;
        }

        public String getAttendRateRemarks() {
            return attendRateRemarks;
        }

        public void setAttendRateRemarks(String attendRateRemarks) {
            this.attendRateRemarks = attendRateRemarks;
        }

        public String getAttendRateDate() {
            return attendRateDate;
        }

        public void setAttendRateDate(String attendRateDate) {
            this.attendRateDate = attendRateDate;
        }

        public String getMapStatus() {
            return mapStatus;
        }

        public void setMapStatus(String mapStatus) {
            this.mapStatus = mapStatus;
        }

        public String getMapAddDate() {
            return mapAddDate;
        }

        public void setMapAddDate(String mapAddDate) {
            this.mapAddDate = mapAddDate;
        }

        public String getMapUpdateDate() {
            return mapUpdateDate;
        }

        public void setMapUpdateDate(String mapUpdateDate) {
            this.mapUpdateDate = mapUpdateDate;
        }

        public String getEventHeading() {
            return eventHeading;
        }

        public void setEventHeading(String eventHeading) {
            this.eventHeading = eventHeading;
        }

        public String getShiftDate() {
            return shiftDate;
        }

        public void setShiftDate(String shiftDate) {
            this.shiftDate = shiftDate;
        }

        public String getShiftStartTime() {
            return shiftStartTime;
        }

        public void setShiftStartTime(String shiftStartTime) {
            this.shiftStartTime = shiftStartTime;
        }

        public String getShiftEndTime() {
            return shiftEndTime;
        }

        public void setShiftEndTime(String shiftEndTime) {
            this.shiftEndTime = shiftEndTime;
        }

        public String getShiftRank() {
            return shiftRank;
        }

        public void setShiftRank(String shiftRank) {
            this.shiftRank = shiftRank;
        }

        public String getShiftTask() {
            return shiftTask;
        }

        public void setShiftTask(String shiftTask) {
            this.shiftTask = shiftTask;
        }

        public String getCso_email() {
            return cso_email;
        }

        public void setCso_email(String cso_email) {
            this.cso_email = cso_email;
        }

        public String getCso_f_name() {
            return cso_f_name;
        }

        public void setCso_f_name(String cso_f_name) {
            this.cso_f_name = cso_f_name;
        }

        public String getCso_l_name() {
            return cso_l_name;
        }

        public void setCso_l_name(String cso_l_name) {
            this.cso_l_name = cso_l_name;
        }

        public String getMapStatusComment() {
            return mapStatusComment;
        }

        public void setMapStatusComment(String mapStatusComment) {
            this.mapStatusComment = mapStatusComment;
        }

        public String getMapRankComment() {
            return mapRankComment;
        }

        public void setMapRankComment(String mapRankComment) {
            this.mapRankComment = mapRankComment;
        }
    }
}