package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMonthEventDateResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<ResData> resData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public GetMonthEventDateResponse() {
    }

    public GetMonthEventDateResponse(String apiResKey, List<ResData> resData, String resStatus) {
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

    public List<ResData> getResData() {
        return resData;
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

    public class ResData {

        @SerializedName("event_id")
        @Expose
        private String eventId;
        @SerializedName("event_register_start_date")
        @Expose
        private String eventRegisterStartDate;

        public ResData() {
        }

        public ResData(String eventId, String eventRegisterStartDate) {
            super();
            this.eventId = eventId;
            this.eventRegisterStartDate = eventRegisterStartDate;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getEventRegisterStartDate() {
            return eventRegisterStartDate;
        }

        public void setEventRegisterStartDate(String eventRegisterStartDate) {
            this.eventRegisterStartDate = eventRegisterStartDate;
        }

    }
}