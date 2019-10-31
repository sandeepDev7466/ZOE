package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class GetVolunteerShiftListResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<ShiftData> shiftData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;


    public GetVolunteerShiftListResponse() {
    }

    public GetVolunteerShiftListResponse(String apiResKey, List<ShiftData> shiftData, String resStatus) {
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


    @Entity(tableName = "shift_data")
    public static class ShiftData implements Serializable
    {
        @NonNull
        @PrimaryKey
        @SerializedName("shift_id")
        @Expose
        @ColumnInfo(name = "shift_id")
        private String shift_id;

        @ColumnInfo(name = "event_id")
        private String event_id;

        @SerializedName("shift_date")
        @Expose
        @ColumnInfo(name = "shift_date")
        private String shift_date;

        @SerializedName("shift_vol_req")
        @Expose
        @ColumnInfo(name = "shift_vol_req")
        private String shift_vol_req;

        @SerializedName("shift_start_time")
        @Expose
        @ColumnInfo(name = "shift_start_time")
        private String shift_start_time;

        @SerializedName("shift_end_time")
        @Expose
        @ColumnInfo(name = "shift_end_time")
        private String shift_end_time;

        @SerializedName("shift_rank")
        @Expose
        @ColumnInfo(name = "shift_rank")
        private String shift_rank;

        @SerializedName("shift_task")
        @Expose
        @ColumnInfo(name = "shift_task")
        private String shift_task;

        @SerializedName("shift_task_name")
        @Expose
        @ColumnInfo(name = "shift_task_name")
        private String shift_task_name;

        @SerializedName("shift_status")
        @Expose
        @ColumnInfo(name = "shift_status")
        private String shift_status;

        @SerializedName("shift_add_date")
        @Expose
        @ColumnInfo(name = "shift_add_date")
        private String shift_add_date;

        @SerializedName("shift_update_date")
        @Expose
        @ColumnInfo(name = "shift_update_date")
        private String shift_update_date;

        @SerializedName("csoa_id")
        @Expose
        @ColumnInfo(name = "csoa_id")
        private String csoa_id;

        @SerializedName("cso_id")
        @Expose
        @ColumnInfo(name = "cso_id")
        private String cso_id;

        @SerializedName("volunteer_apply")
        @Expose
        @ColumnInfo(name = "volunteer_apply")
        private String volunteer_apply;

        @SerializedName("volunteer_req_accepted")
        @Expose
        @ColumnInfo(name = "volunteer_req_accepted")
        private String volunteer_req_accepted;

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

        public String getCso_id() {
            return cso_id;
        }

        public void setCso_id(String cso_id) {
            this.cso_id = cso_id;
        }




        public String getVolunteer_apply() {
            return volunteer_apply;
        }

        public void setVolunteer_apply(String volunteer_apply) {
            this.volunteer_apply = volunteer_apply;
        }

        public String getShift_task_name() {
            return shift_task_name;
        }

        public void setShift_task_name(String shift_task_name) {
            this.shift_task_name = shift_task_name;
        }

        public String getCsoa_id() {
            return csoa_id;
        }

        public void setCsoa_id(String csoa_id) {
            this.csoa_id = csoa_id;
        }

        public String getVolunteer_req_accepted() {
            return volunteer_req_accepted;
        }

        public void setVolunteer_req_accepted(String volunteer_req_accepted) {
            this.volunteer_req_accepted = volunteer_req_accepted;
        }

        public String getEvent_id() {
            return event_id;
        }

        public void setEvent_id(String event_id) {
            this.event_id = event_id;
        }
    }
}
