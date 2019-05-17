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
        @SerializedName("count_down_data")
        @Expose
        private List<CountDownData> countDownData = null;

        public ResData() {
        }

        public ResData(List<CalendarData> calendarData, List<EventData> eventData, List<CountDownData> countDownData) {
            super();
            this.calendarData = calendarData;
            this.eventData = eventData;
            this.countDownData = countDownData;
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

        public List<CountDownData> getCountDownData() {
            return countDownData;
        }

        public void setCountDownData(List<CountDownData> countDownData) {
            this.countDownData = countDownData;
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
        @SerializedName("shift_day")
        @Expose
        private String shiftDay;
        @SerializedName("shift_month")
        @Expose
        private String shiftMonth;
        @SerializedName("shift_day_num")
        @Expose
        private String shiftDayNum;

        public EventData() {
        }

        public EventData(String eventId, String eventHeading, String eventStatus, String eventAddDate, String eventRegisterStartDate, String eventRegisterEndDate, String shiftDay, String shiftMonth, String shiftDayNum) {
            super();
            this.eventId = eventId;
            this.eventHeading = eventHeading;
            this.eventStatus = eventStatus;
            this.eventAddDate = eventAddDate;
            this.eventRegisterStartDate = eventRegisterStartDate;
            this.eventRegisterEndDate = eventRegisterEndDate;
            this.shiftDay = shiftDay;
            this.shiftMonth = shiftMonth;
            this.shiftDayNum = shiftDayNum;
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

    }

    public class CountDownData {

        @SerializedName("event_id")
        @Expose
        private String eventId;
        @SerializedName("shift_date")
        @Expose
        private String shiftDate;
        @SerializedName("shift_start_time")
        @Expose
        private String shiftStartTime;

        public CountDownData() {
        }

        public CountDownData(String eventId, String shiftDate, String shiftStartTime) {
            super();
            this.eventId = eventId;
            this.shiftDate = shiftDate;
            this.shiftStartTime = shiftStartTime;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
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

    }

    public class CalendarData {

        @SerializedName("event_id")
        @Expose
        private String eventId;
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
        @SerializedName("event_heading")
        @Expose
        private String eventHeading;

        public CalendarData() {
        }

        public CalendarData(String eventId, String shiftDate, String yr, String mn, String dt, String eventHeading) {
            super();
            this.eventId = eventId;
            this.shiftDate = shiftDate;
            this.yr = yr;
            this.mn = mn;
            this.dt = dt;
            this.eventHeading = eventHeading;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
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

        public String getEventHeading() {
            return eventHeading;
        }

        public void setEventHeading(String eventHeading) {
            this.eventHeading = eventHeading;
        }
    }
}
