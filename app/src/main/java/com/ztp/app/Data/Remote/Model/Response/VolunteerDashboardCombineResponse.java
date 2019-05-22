package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    public VolunteerDashboardCombineResponse() {
    }

    public VolunteerDashboardCombineResponse(String apiResKey, ResData resData, String resStatus) {
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

        @SerializedName("event_data")
        @Expose
        private List<EventData> eventData = null;

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
    }

    public class EventData {

        @SerializedName("event_id")
        @Expose
        private String eventId;
        @SerializedName("shift_id")
        @Expose
        private String shiftId;
        @SerializedName("shift_date")
        @Expose
        private String shiftDate;
        @SerializedName("shift_start_time")
        @Expose
        private String shiftStartTime;
        @SerializedName("shift_end_time")
        @Expose
        private String shiftEndTime;
        @SerializedName("event_heading")
        @Expose
        private String eventHeading;
        @SerializedName("shift_day")
        @Expose
        private String shiftDay;
        @SerializedName("shift_month")
        @Expose
        private String shiftMonth;
        @SerializedName("shift_day_num")
        @Expose
        private String shiftDayNum;
        @SerializedName("shift_start_time_s")
        @Expose
        private String shiftStartTimeS;
        @SerializedName("shift_end_time_s")
        @Expose
        private String shiftEndTimeS;

        public EventData() {
        }

        public EventData(String eventId, String shiftId, String shiftDate, String shiftStartTime, String shiftEndTime, String eventHeading, String shiftDay, String shiftMonth, String shiftDayNum, String shiftStartTimeS, String shiftEndTimeS) {
            super();
            this.eventId = eventId;
            this.shiftId = shiftId;
            this.shiftDate = shiftDate;
            this.shiftStartTime = shiftStartTime;
            this.shiftEndTime = shiftEndTime;
            this.eventHeading = eventHeading;
            this.shiftDay = shiftDay;
            this.shiftMonth = shiftMonth;
            this.shiftDayNum = shiftDayNum;
            this.shiftStartTimeS = shiftStartTimeS;
            this.shiftEndTimeS = shiftEndTimeS;
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

        public String getShiftDay() {
            return shiftDay;
        }

        public void setShiftDay(String shiftDay) {
            this.shiftDay = shiftDay;
        }

        public String getShiftMonth() {
            return shiftMonth;
        }

        public void setShiftMonth(String shiftMonth) {
            this.shiftMonth = shiftMonth;
        }

        public String getShiftDayNum() {
            return shiftDayNum;
        }

        public void setShiftDayNum(String shiftDayNum) {
            this.shiftDayNum = shiftDayNum;
        }

        public String getShiftStartTimeS() {
            return shiftStartTimeS;
        }

        public void setShiftStartTimeS(String shiftStartTimeS) {
            this.shiftStartTimeS = shiftStartTimeS;
        }

        public String getShiftEndTimeS() {
            return shiftEndTimeS;
        }

        public void setShiftEndTimeS(String shiftEndTimeS) {
            this.shiftEndTimeS = shiftEndTimeS;
        }

    }
}