package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CSOAllResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<CSOAllRequest> resData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public CSOAllResponse() {
    }

    public CSOAllResponse(String apiResKey, List<CSOAllRequest> resData, String resStatus) {
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

    public List<CSOAllRequest> getResData() {
        return resData;
    }

    public void setResData(List<CSOAllRequest> resData) {
        this.resData = resData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    @Entity(tableName = "cso_all_request")
    public static class CSOAllRequest {

        @PrimaryKey(autoGenerate = true)
        @NonNull
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

        @SerializedName("attend_hours_vol")
        @Expose
        @ColumnInfo(name = "attend_hours_vol")
        private String attendHoursVol;

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

        @SerializedName("attend_rank")
        @Expose
        @ColumnInfo(name = "attend_rank")
        private String attendRank;

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

        @SerializedName("map_rank_comment")
        @Expose
        @ColumnInfo(name = "map_rank_comment")
        private String mapRankComment;

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

        @SerializedName("shift_date")
        @Expose
        @ColumnInfo(name = "shift_date")
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

        @SerializedName("shift_task_name")
        @Expose
        @ColumnInfo(name = "shift_task_name")
        private String shiftTaskName;

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

        public String getAttendRank() {
            return attendRank;
        }

        public void setAttendRank(String attendRank) {
            this.attendRank = attendRank;
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

        public String getMapRankComment() {
            return mapRankComment;
        }

        public void setMapRankComment(String mapRankComment) {
            this.mapRankComment = mapRankComment;
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

        public String getShiftTaskName() {
            return shiftTaskName;
        }

        public void setShiftTaskName(String shiftTaskName) {
            this.shiftTaskName = shiftTaskName;
        }

        public String getAttendHoursVol() {
            return attendHoursVol;
        }

        public void setAttendHoursVol(String attendHoursVol) {
            this.attendHoursVol = attendHoursVol;
        }
    }
}