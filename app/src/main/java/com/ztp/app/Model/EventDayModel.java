package com.ztp.app.Model;

import com.prolificinteractive.materialcalendarview.CalendarDay;

public class EventDayModel {
    private CalendarDay calendarDay;
    private String eventId;
    private String eventHeading;
    private String shiftDate;
    private String shiftTask;
    private String shiftTaskName;
    private String shiftStartTime;
    private String shiftEndTime;
    private String shiftId;
    private String mapId;
    private String mapStatus;
    private String userName;
    private String userEmail;
    private String comment;
    private String rankComment;

    public EventDayModel() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRankComment() {
        return rankComment;
    }

    public void setRankComment(String rankComment) {
        this.rankComment = rankComment;
    }

    public String getShiftTaskName() {
        return shiftTaskName;
    }

    public void setShiftTaskName(String shiftTaskName) {
        this.shiftTaskName = shiftTaskName;
    }
}
