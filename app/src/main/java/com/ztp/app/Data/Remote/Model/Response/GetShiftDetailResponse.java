
package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetShiftDetailResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private ShiftDetail shiftDetail;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public GetShiftDetailResponse() {
    }

    public GetShiftDetailResponse(String apiResKey, ShiftDetail shiftDetail, String resStatus) {
        super();
        this.apiResKey = apiResKey;
        this.shiftDetail = shiftDetail;
        this.resStatus = resStatus;
    }

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public ShiftDetail getShiftDetail() {
        return shiftDetail;
    }

    public void setShiftDetail(ShiftDetail shiftDetail) {
        this.shiftDetail = shiftDetail;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    @Entity(tableName = "shift_detail")
    public static class ShiftDetail {
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "shift_id")
        private int shiftId;

        @SerializedName("event_id")
        @Expose
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

        @SerializedName("shift_start_time_format")
        @Expose
        @ColumnInfo(name = "shift_start_time_format")
        private String shiftStartTimeFormat;

        @SerializedName("shift_end_time_format")
        @Expose
        @ColumnInfo(name = "shift_end_time_format")
        private String shiftEndTimeFormat;


        public int getShiftId() {
            return shiftId;
        }

        public void setShiftId(int shiftId) {
            this.shiftId = shiftId;
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

        public String getShiftTaskName() {
            return shiftTaskName;
        }

        public void setShiftTaskName(String shiftTaskName) {
            this.shiftTaskName = shiftTaskName;
        }

        public String getShiftStartTimeFormat() {
            return shiftStartTimeFormat;
        }

        public void setShiftStartTimeFormat(String shiftStartTimeFormat) {
            this.shiftStartTimeFormat = shiftStartTimeFormat;
        }

        public String getShiftEndTimeFormat() {
            return shiftEndTimeFormat;
        }

        public void setShiftEndTimeFormat(String shiftEndTimeFormat) {
            this.shiftEndTimeFormat = shiftEndTimeFormat;
        }
    }
}
