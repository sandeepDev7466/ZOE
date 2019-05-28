package com.ztp.app.Data.Remote.Model.Response;

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

        public ResData() {
        }

        public ResData(List<CalendarData> calendarData, List<EventData> eventData) {
            super();
            this.calendarData = calendarData;
            this.eventData = eventData;
        }

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

    }

    public class CalendarData {

        @SerializedName("event_id")
        @Expose
        private String eventId;
        @SerializedName("shift_id")
        @Expose
        private String shiftId;
        @SerializedName("shift_date")
        @Expose
        private String shiftDate;
        @SerializedName("yr")
        @Expose
        private String yr;
        @SerializedName("mn")
        @Expose
        private String mn;
        @SerializedName("dt")
        @Expose
        private String dt;
        @SerializedName("shift_task")
        @Expose
        private String shiftTask;
        @SerializedName("shift_start_time")
        @Expose
        private String shiftStartTime;
        @SerializedName("shift_end_time")
        @Expose
        private String shiftEndTime;
        @SerializedName("event_heading")
        @Expose
        private String eventHeading;

        public CalendarData() {
        }

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
    }
    public class EventData {

        @SerializedName("event_id")
        @Expose
        private String eventId;
        @SerializedName("event_heading")
        @Expose
        private String eventHeading;
        @SerializedName("event_status")
        @Expose
        private String eventStatus;
        @SerializedName("event_add_date")
        @Expose
        private String eventAddDate;
        @SerializedName("event_register_start_date")
        @Expose
        private String eventRegisterStartDate;
        @SerializedName("event_register_end_date")
        @Expose
        private String eventRegisterEndDate;
        @SerializedName("shift_id")
        @Expose
        private String shiftId;
        @SerializedName("shift_task")
        @Expose
        private String shiftTask;
        @SerializedName("shift_date")
        @Expose
        private String shiftDate;
        @SerializedName("shift_start_time")
        @Expose
        private String shiftStartTime;
        @SerializedName("shift_end_time")
        @Expose
        private String shiftEndTime;

        public EventData() {
        }

        public EventData(String eventId, String eventHeading, String eventStatus, String eventAddDate, String eventRegisterStartDate, String eventRegisterEndDate, String shiftId, String shiftTask, String shiftDate, String shiftStartTime, String shiftEndTime) {
            super();
            this.eventId = eventId;
            this.eventHeading = eventHeading;
            this.eventStatus = eventStatus;
            this.eventAddDate = eventAddDate;
            this.eventRegisterStartDate = eventRegisterStartDate;
            this.eventRegisterEndDate = eventRegisterEndDate;
            this.shiftId = shiftId;
            this.shiftTask = shiftTask;
            this.shiftDate = shiftDate;
            this.shiftStartTime = shiftStartTime;
            this.shiftEndTime = shiftEndTime;
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

    }
}