package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VolunteerDashboardCombineResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private ResData resData;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @Ignore

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

        @SerializedName("event_data")
        @Expose
        private List<EventData> eventData = null;

        @SerializedName("user_profile_pic")
        @Expose
        private String userProfilePic;

        @SerializedName("user_cover_pic")
        @Expose
        private String userCoverPic;

        @SerializedName("vol_rank")
        @Expose
        private String volRank;

        @SerializedName("vol_hours")
        @Expose
        private String volHours;

        @SerializedName("user_zipcode")
        @Expose
        private String userZipCode;

        @Ignore
        public ResData() {
        }

        public ResData(List<EventData> eventData) {
            super();
            this.eventData = eventData;
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

        public String getVolRank() {
            return volRank;
        }

        public void setVolRank(String volRank) {
            this.volRank = volRank;
        }

        public String getVolHours() {
            return volHours;
        }

        public void setVolHours(String volHours) {
            this.volHours = volHours;
        }

        public String getUserZipCode() {
            return userZipCode;
        }

        public void setUserZipCode(String userZipCode) {
            this.userZipCode = userZipCode;
        }
    }

    @Entity(tableName = "volunteer_upcoming_events")
    public static class EventData implements Serializable {

        @PrimaryKey(autoGenerate = true)
        @NonNull
        @ColumnInfo(name = "id")
        private int id;

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

        @SerializedName("shift_id")
        @Expose
        @ColumnInfo(name = "shift_id")
        private String shiftId;

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

        @SerializedName("shift_task")
        @Expose
        @ColumnInfo(name = "shift_task")
        private String shift_task;

        @SerializedName("shift_task_name")
        @Expose
        @ColumnInfo(name = "shift_task_name")
        private String shift_task_name;

        @SerializedName("event_latitude")
        @Expose
        @ColumnInfo(name = "event_latitude")
        private String eventLatitude;

        @SerializedName("event_longitude")
        @Expose
        @ColumnInfo(name = "event_longitude")
        private String eventLongitude;

        @SerializedName("shift_start_time_timer")
        @Expose
        @ColumnInfo(name = "shift_start_time_timer")
        private String shiftStartTimeTimer;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public String getShift_task() {
            return shift_task;
        }

        public void setShift_task(String shift_task) {
            this.shift_task = shift_task;
        }

        public String getEventLatitude() {
            return eventLatitude;
        }

        public void setEventLatitude(String eventLatitude) {
            this.eventLatitude = eventLatitude;
        }

        public String getEventLongitude() {
            return eventLongitude;
        }

        public void setEventLongitude(String eventLongitude) {
            this.eventLongitude = eventLongitude;
        }

        public String getShift_task_name() {
            return shift_task_name;
        }

        public void setShift_task_name(String shift_task_name) {
            this.shift_task_name = shift_task_name;
        }

        public String getShiftStartTimeTimer() {
            return shiftStartTimeTimer;
        }

        public void setShiftStartTimeTimer(String shiftStartTimeTimer) {
            this.shiftStartTimeTimer = shiftStartTimeTimer;
        }
    }
}