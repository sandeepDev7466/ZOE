package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventRatingResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_msg_code")
    @Expose
    private String resMsgCode;
    @SerializedName("res_data")
    @Expose
    private ResData resData;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_message")
    @Expose
    private String resMessage;

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public String getResMsgCode() {
        return resMsgCode;
    }

    public void setResMsgCode(String resMsgCode) {
        this.resMsgCode = resMsgCode;
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

    public String getResMessage() {
        return resMessage;
    }

    public void setResMessage(String resMessage) {
        this.resMessage = resMessage;
    }
    public class ResData {

        @SerializedName("total_count_rating")
        @Expose
        private String totalCountRating;
        @SerializedName("event_rate_avg")
        @Expose
        private String eventRateAvg;

        public String getTotalCountRating() {
            return totalCountRating;
        }

        public void setTotalCountRating(String totalCountRating) {
            this.totalCountRating = totalCountRating;
        }

        public String getEventRateAvg() {
            return eventRateAvg;
        }

        public void setEventRateAvg(String eventRateAvg) {
            this.eventRateAvg = eventRateAvg;
        }

    }
}