package com.ztp.app.Model;

import com.prolificinteractive.materialcalendarview.CalendarDay;

public class EventDayModel {
    private CalendarDay calendarDay;
    private String eventId;
    private String eventHeading;
    private String shiftDate;
    private String shiftTask;
    private String shiftStartTime;
    private String shiftEndTime;
    private String shiftId;
    private String mapId;
    private String mapStatus;

    public EventDayModel() {
    }

    public EventDayModel(CalendarDay calendarDay, String eventId, String eventHeading, String shiftDate, String shiftTask, String shiftStartTime, String shiftEndTime, String shiftId, String mapId, String mapStatus) {
        this.calendarDay = calendarDay;
        this.eventId = eventId;
        this.eventHeading = eventHeading;
        this.shiftDate = shiftDate;
        this.shiftTask = shiftTask;
        this.shiftStartTime = shiftStartTime;
        this.shiftEndTime = shiftEndTime;
        this.shiftId = shiftId;
        this.mapId = mapId;
        this.mapStatus = mapStatus;
    }

    public CalendarDay getCalendarDay() {
        return calendarDay;
    }

    public void setCalendarDay(CalendarDay calendarDay) {
        this.calendarDay = calendarDay;
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

    public String getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(String shiftDate) {
        this.shiftDate = shiftDate;
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

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getMapStatus() {
        return mapStatus;
    }

    public void setMapStatus(String mapStatus) {
        this.mapStatus = mapStatus;
    }
}
