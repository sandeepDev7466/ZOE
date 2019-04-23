
package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetShiftDetailResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private ResData resData;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public GetShiftDetailResponse() {
    }

    public GetShiftDetailResponse(String apiResKey, ResData resData, String resStatus) {
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

        @SerializedName("event_id")
        @Expose
        private String eventId;
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

        public ResData() {
        }

        public ResData(String eventId, String shiftDate, String shiftVolReq, String shiftStartTime, String shiftEndTime, String shiftRank, String shiftTask, String shiftStatus, String shiftAddDate) {
            super();
            this.eventId = eventId;
            this.shiftDate = shiftDate;
            this.shiftVolReq = shiftVolReq;
            this.shiftStartTime = shiftStartTime;
            this.shiftEndTime = shiftEndTime;
            this.shiftRank = shiftRank;
            this.shiftTask = shiftTask;
            this.shiftStatus = shiftStatus;
            this.shiftAddDate = shiftAddDate;
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

    }
}
