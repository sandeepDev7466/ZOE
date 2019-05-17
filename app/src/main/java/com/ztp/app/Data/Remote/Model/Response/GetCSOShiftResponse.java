package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetCSOShiftResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<ResData> resData;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public GetCSOShiftResponse() {
    }

    public GetCSOShiftResponse(String apiResKey, List<ResData> resData, String resStatus) {
        super();
        this.apiResKey = apiResKey;
        this.resData = resData;
        this.resStatus = resStatus;
    }
    public List<ResData> getResData() {
        return resData;
    }
    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public void setResData(List<ResData> resData) {
        this.resData = resData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public class ResData implements Serializable {

        @SerializedName("shift_id")
        @Expose
        private String shiftId;
        @SerializedName("shift_date")
        @Expose
        private String shiftDate;
        @SerializedName("shift_vol_req")
        @Expose
        private String shiftVolReq;
        @SerializedName("shift_start_time")
        @Expose
        private String shiftStartTime;
        @SerializedName("shift_end_time")
        @Expose
        private String shiftEndTime;
        @SerializedName("shift_rank")
        @Expose
        private String shiftRank;
        @SerializedName("shift_task")
        @Expose
        private String shiftTask;
        @SerializedName("shift_status")
        @Expose
        private String shiftStatus;
        @SerializedName("shift_add_date")
        @Expose
        private String shiftAddDate;
        @SerializedName("shift_update_date")
        @Expose
        private String shiftUpdateDate;
        @SerializedName("shift_day")
        @Expose
        private String shift_day;
        @SerializedName("shift_month")
        @Expose
        private String shift_month;
        @SerializedName("shift_day_num")
        @Expose
        private String shift_day_num;
        @SerializedName("shift_start_time_s")
        @Expose
        private String shift_start_time_s;
        @SerializedName("shift_end_time_s")
        @Expose
        private String shift_end_time_s;

        public ResData() {
        }

        public ResData(String shiftId, String shiftDate, String shiftVolReq, String shiftStartTime, String shiftEndTime, String shiftRank, String shiftTask, String shiftStatus, String shiftAddDate, String shiftUpdateDate, String day, String month, String num, String start_time, String end_time) {
            super();
            this.shiftId = shiftId;
            this.shiftDate = shiftDate;
            this.shiftVolReq = shiftVolReq;
            this.shiftStartTime = shiftStartTime;
            this.shiftEndTime = shiftEndTime;
            this.shiftRank = shiftRank;
            this.shiftTask = shiftTask;
            this.shiftStatus = shiftStatus;
            this.shiftAddDate = shiftAddDate;
            this.shiftUpdateDate = shiftUpdateDate;
            this.shift_day = day;
            this.shift_day_num = num;
            this.shift_month = month;
            this.shift_end_time_s = end_time;
            this.shift_start_time_s = start_time;
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

        public String getShiftUpdateDate() {
            return shiftUpdateDate;
        }

        public void setShiftUpdateDate(String shiftUpdateDate) {
            this.shiftUpdateDate = shiftUpdateDate;
        }

        public String getShift_day() {
            return shift_day;
        }

        public void setShift_day(String shift_day) {
            this.shift_day = shift_day;
        }

        public String getShift_month() {
            return shift_month;
        }

        public void setShift_month(String shift_month) {
            this.shift_month = shift_month;
        }

        public String getShift_day_num() {
            return shift_day_num;
        }

        public void setShift_day_num(String shift_day_num) {
            this.shift_day_num = shift_day_num;
        }

        public String getShift_start_time_s() {
            return shift_start_time_s;
        }

        public void setShift_start_time_s(String shift_start_time_s) {
            this.shift_start_time_s = shift_start_time_s;
        }

        public String getShift_end_time_s() {
            return shift_end_time_s;
        }

        public void setShift_end_time_s(String shift_end_time_s) {
            this.shift_end_time_s = shift_end_time_s;
        }
    }
}