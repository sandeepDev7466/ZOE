package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetShiftListResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<ShiftData> shiftData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;


    public GetShiftListResponse() {
    }

    public GetShiftListResponse(String apiResKey, List<ShiftData> shiftData, String resStatus) {
        super();
        this.apiResKey = apiResKey;
        this.shiftData = shiftData;
        this.resStatus = resStatus;
    }


    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public List<ShiftData> getShiftData() {
        return shiftData;
    }

    public void setEventData(List<ShiftData> shiftData) {
        this.shiftData = shiftData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }


    public class ShiftData
    {
        @SerializedName("shift_id")
        @Expose
        private String shift_id;
        @SerializedName("shift_date")
        @Expose
        private String shift_date;
        @SerializedName("shift_vol_req")
        @Expose
        private String shift_vol_req;
        @SerializedName("shift_start_time")
        @Expose
        private String shift_start_time;
        @SerializedName("shift_end_time")
        @Expose
        private String shift_end_time;
        @SerializedName("shift_rank")
        @Expose
        private String shift_rank;
        @SerializedName("shift_task")
        @Expose
        private String shift_task;
        @SerializedName("shift_status")
        @Expose
        private String shift_status;
        @SerializedName("shift_add_date")
        @Expose
        private String shift_add_date;
        @SerializedName("shift_update_date")
        @Expose
        private String shift_update_date;

        public String getShift_id() {
            return shift_id;
        }

        public void setShift_id(String shift_id) {
            this.shift_id = shift_id;
        }

        public String getShift_date() {
            return shift_date;
        }

        public void setShift_date(String shift_date) {
            this.shift_date = shift_date;
        }

        public String getShift_vol_req() {
            return shift_vol_req;
        }

        public void setShift_vol_req(String shift_vol_req) {
            this.shift_vol_req = shift_vol_req;
        }

        public String getShift_start_time() {
            return shift_start_time;
        }

        public void setShift_start_time(String shift_start_time) {
            this.shift_start_time = shift_start_time;
        }

        public String getShift_end_time() {
            return shift_end_time;
        }

        public void setShift_end_time(String shift_end_time) {
            this.shift_end_time = shift_end_time;
        }

        public String getShift_rank() {
            return shift_rank;
        }

        public void setShift_rank(String shift_rank) {
            this.shift_rank = shift_rank;
        }

        public String getShift_task() {
            return shift_task;
        }

        public void setShift_task(String shift_task) {
            this.shift_task = shift_task;
        }

        public String getShift_status() {
            return shift_status;
        }

        public void setShift_status(String shift_status) {
            this.shift_status = shift_status;
        }

        public String getShift_add_date() {
            return shift_add_date;
        }

        public void setShift_add_date(String shift_add_date) {
            this.shift_add_date = shift_add_date;
        }

        public String getShift_update_date() {
            return shift_update_date;
        }

        public void setShift_update_date(String shift_update_date) {
            this.shift_update_date = shift_update_date;
        }

    }


}
