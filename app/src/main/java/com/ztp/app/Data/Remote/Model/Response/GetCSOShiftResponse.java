package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCSOShiftResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<ResDatum> resData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public List<ResDatum> getResData() {
        return resData;
    }

    public void setResData(List<ResDatum> resData) {
        this.resData = resData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    @Entity(tableName = "shift_list")
    public static class ResDatum implements Serializable {

        @NonNull
        @PrimaryKey
        @SerializedName("shift_id")
        @Expose
        @ColumnInfo(name = "shift_id")
        private String shiftId;

        @ColumnInfo(name = "event_id")
        private String eventId;

        @SerializedName("shift_date")
        @Expose
        @ColumnInfo(name = "shift_date")
        private String shiftDate;

        @SerializedName("shift_vol_req")
        @Expose
        @ColumnInfo(name = "shift_vol_req")
        private String shiftVolReq;

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

        @SerializedName("shift_status")
        @Expose
        @ColumnInfo(name = "shift_status")
        private String shiftStatus;

        @SerializedName("shift_add_date")
        @Expose
        @ColumnInfo(name = "shift_add_date")
        private String shiftAddDate;

        @SerializedName("shift_update_date")
        @Expose
        @ColumnInfo(name = "shift_update_date")
        private String shiftUpdateDate;

        @SerializedName("shift_day")
        @Expose
        @ColumnInfo(name = "shift_day")
        private String shiftDay;

        @SerializedName("shift_month")
        @Expose
        @ColumnInfo(name = "shift_month")
        private String shiftMonth;

        @SerializedName("shift_day_num")
        @Expose
        @ColumnInfo(name = "shift_day_num")
        private String shiftDayNum;

        @SerializedName("shift_start_time_s")
        @Expose
        @ColumnInfo(name = "shift_start_time_s")
        private String shiftStartTimeS;

        @SerializedName("shift_end_time_s")
        @Expose
        @ColumnInfo(name = "shift_end_time_s")
        private String shiftEndTimeS;


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

        public String getShiftVolReq() {
            return shiftVolReq;
        }

        public void setShiftVolReq(String shiftVolReq) {
            this.shiftVolReq = shiftVolReq;
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

        public String getShiftTaskName() {
            return shiftTaskName;
        }

        public void setShiftTaskName(String shiftTaskName) {
            this.shiftTaskName = shiftTaskName;
        }

        public String getShiftStatus() {
            return shiftStatus;
        }

        public void setShiftStatus(String shiftStatus) {
            this.shiftStatus = shiftStatus;
        }

        public String getShiftAddDate() {
            return shiftAddDate;
        }

        public void setShiftAddDate(String shiftAddDate) {
            this.shiftAddDate = shiftAddDate;
        }

        public String getShiftUpdateDate() {
            return shiftUpdateDate;
        }

        public void setShiftUpdateDate(String shiftUpdateDate) {
            this.shiftUpdateDate = shiftUpdateDate;
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

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }
    }
}