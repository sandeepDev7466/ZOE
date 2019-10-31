package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CsoDashboardCombinedResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private ResData resData;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public CsoDashboardCombinedResponse() {
    }

    public CsoDashboardCombinedResponse(String apiResKey, ResData resData, String resStatus) {
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

    public ResData getResData() {
        return resData;
    }

    public void setResData(ResData resData) {
        this.resData = resData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public class ResData {

        @SerializedName("calendar_data")
        @Expose
        private List<CalendarData> calendarData = null;
        @SerializedName("event_data")
        @Expose
        private List<EventData> eventData = null;

        @SerializedName("user_profile_pic")
        @Expose
        private String userProfilePic;

        @SerializedName("user_cover_pic")
        @Expose
        private String userCoverPic;

        public List<CalendarData> getCalendarData() {
            return calendarData;
        }

        public void setCalendarData(List<CalendarData> calendarData) {
            this.calendarData = calendarData;
        }

        public List<EventData> getEventData() {
            return eventData;
        }

        public void setEventData(List<EventData> eventData) {
            this.eventData = eventData;
        }

        public String getUserProfilePic() {
            return userProfilePic;
        }

        public void setUserProfilePic(String userProfilePic) {
            this.userProfilePic = userProfilePic;
        }

        public String getUserCoverPic() {
            return userCoverPic;
        }

        public void setUserCoverPic(String userCoverPic) {
            this.userCoverPic = userCoverPic;
        }
    }

    @Entity(tableName = "calendar_data")
    public static class CalendarData {


        @SerializedName("event_id")
        @Expose
        @ColumnInfo(name = "event_id")
        private String eventId;

        @NonNull
        @PrimaryKey
        @SerializedName("shift_id")
        @Expose
        @ColumnInfo(name = "shift_id")
        private String shiftId;

        @SerializedName("shift_date")
        @Expose
        @ColumnInfo(name = "shift_date")
        private String shiftDate;

        @SerializedName("yr")
        @Expose
        @ColumnInfo(name = "yr")
        private String yr;

        @SerializedName("mn")
        @Expose
        @ColumnInfo(name = "mn")
        private String mn;

        @SerializedName("dt")
        @Expose
        @ColumnInfo(name = "dt")
        private String dt;

        @SerializedName("shift_task")
        @Expose
        @ColumnInfo(name = "shift_task")
        private String shiftTask;

        @SerializedName("shift_task_name")
        @Expose
        @ColumnInfo(name = "shift_task_name")
        private String shiftTaskName;

        @SerializedName("shift_start_time")
        @Expose
        @ColumnInfo(name = "shift_start_time")
        private String shiftStartTime;

        @SerializedName("shift_end_time")
        @Expose
        @ColumnInfo(name = "shift_end_time")
        private String shiftEndTime;

        @SerializedName("event_heading")
        @Expose
        @ColumnInfo(name = "event_heading")
        private String eventHeading;

        public CalendarData(String eventId, String shiftId, String shiftDate, String yr, String mn, String dt, String shiftTask, String shiftStartTime, String shiftEndTime, String eventHeading) {
            super();
            this.eventId = eventId;
            this.shiftId = shiftId;
            this.shiftDate = shiftDate;
            this.yr = yr;
            this.mn = mn;
            this.dt = dt;
            this.shiftTask = shiftTask;
            this.shiftStartTime = shiftStartTime;
            this.shiftEndTime = shiftEndTime;
            this.eventHeading = eventHeading;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getShiftId() {
            return shiftId;
        }

        public void setShiftId(String shiftId) {
            this.shiftId = shiftId;
        }

        public String getShiftDate() {
            return shiftDate;
        }

        public void setShiftDate(String shiftDate) {
            this.shiftDate = shiftDate;
        }

        public String getYr() {
            return yr;
        }

        public void setYr(String yr) {
            this.yr = yr;
        }

        public String getMn() {
            return mn;
        }

        public void setMn(String mn) {
            this.mn = mn;
        }

        public String getDt() {
            return dt;
        }

        public void setDt(String dt) {
            this.dt = dt;
        }

        public String getShiftTask() {
            return shiftTask;
        }

        public void setShiftTask(String shiftTask) {
            this.shiftTask = shiftTask;
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

        public String getEventHeading() {
            return eventHeading;
        }

        public void setEventHeading(String eventHeading) {
            this.eventHeading = eventHeading;
        }

        public String getShiftTaskName() {
            return shiftTaskName;
        }

        public void setShiftTaskName(String shiftTaskName) {
            this.shiftTaskName = shiftTaskName;
        }
    }

    @Entity(tableName = "upcoming_event")
    public static class EventData {

        @NonNull
        @PrimaryKey
        @SerializedName("shift_id")
        @Expose
        @ColumnInfo(name = "shift_id")
        private String shiftId;

        @SerializedName("event_id")
        @Expose
        @ColumnInfo(name = "event_id")
        private String eventId;

        @SerializedName("event_heading")
        @Expose
        @ColumnInfo(name = "event_heading")
        private String eventHeading;

        @SerializedName("event_status")
        @Expose
        @ColumnInfo(name = "event_status")
        private String eventStatus;

        @SerializedName("event_add_date")
        @Expose
        @ColumnInfo(name = "event_add_date")
        private String eventAddDate;

        @SerializedName("event_register_start_date")
        @Expose
        @ColumnInfo(name = "event_register_start_date")
        private String eventRegisterStartDate;

        @SerializedName("event_register_end_date")
        @Expose
        @ColumnInfo(name = "event_register_end_date")
        private String eventRegisterEndDate;

        @SerializedName("shift_task")
        @Expose
        @ColumnInfo(name = "shift_task")
        private String shiftTask;

        @SerializedName("shift_task_name")
        @Expose
        @ColumnInfo(name = "shift_task_name")
        private String shiftTaskName;

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

        @SerializedName("shift_start_time_timer")
        @Expose
        @ColumnInfo(name = "shift_start_time_timer")
        private String shiftStartTimeTimer;

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getEventHeading() {
            return eventHeading;
        }

        public void setEventHeading(String eventHeading) {
            this.eventHeading = eventHeading;
        }

        public String getEventStatus() {
            return eventStatus;
        }

        public void setEventStatus(String eventStatus) {
            this.eventStatus = eventStatus;
        }

        public String getEventAddDate() {
            return eventAddDate;
        }

        public void setEventAddDate(String eventAddDate) {
            this.eventAddDate = eventAddDate;
        }

        public String getEventRegisterStartDate() {
            return eventRegisterStartDate;
        }

        public void setEventRegisterStartDate(String eventRegisterStartDate) {
            this.eventRegisterStartDate = eventRegisterStartDate;
        }

        public String getEventRegisterEndDate() {
            return eventRegisterEndDate;
        }

        public void setEventRegisterEndDate(String eventRegisterEndDate) {
            this.eventRegisterEndDate = eventRegisterEndDate;
        }

        public String getShiftId() {
            return shiftId;
        }

        public void setShiftId(String shiftId) {
            this.shiftId = shiftId;
        }

        public String getShiftTask() {
            return shiftTask;
        }

        public void setShiftTask(String shiftTask) {
            this.shiftTask = shiftTask;
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

        public String getShiftTaskName() {
            return shiftTaskName;
        }

        public void setShiftTaskName(String shiftTaskName) {
            this.shiftTaskName = shiftTaskName;
        }

        public String getShiftStartTimeTimer() {
            return shiftStartTimeTimer;
        }

        public void setShiftStartTimeTimer(String shiftStartTimeTimer) {
            this.shiftStartTimeTimer = shiftStartTimeTimer;
        }
    }
}