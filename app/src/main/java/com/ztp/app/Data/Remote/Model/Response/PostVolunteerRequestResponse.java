package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostVolunteerRequestResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private ResData resData;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public PostVolunteerRequestResponse() {
    }

    public PostVolunteerRequestResponse(String apiResKey, ResData resData, String resStatus) {
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
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("event_type_id")
        @Expose
        private String eventTypeId;
        @SerializedName("event_heading")
        @Expose
        private String eventHeading;
        @SerializedName("event_details")
        @Expose
        private String eventDetails;
        @SerializedName("event_address")
        @Expose
        private String eventAddress;
        @SerializedName("event_country")
        @Expose
        private String eventCountry;
        @SerializedName("event_state")
        @Expose
        private String eventState;
        @SerializedName("event_city")
        @Expose
        private String eventCity;
        @SerializedName("event_postcode")
        @Expose
        private String eventPostcode;
        @SerializedName("event_timezone")
        @Expose
        private String eventTimezone;
        @SerializedName("event_latitude")
        @Expose
        private String eventLatitude;
        @SerializedName("event_longitude")
        @Expose
        private String eventLongitude;
        @SerializedName("event_email")
        @Expose
        private String eventEmail;
        @SerializedName("event_phone")
        @Expose
        private String eventPhone;
        @SerializedName("event_image")
        @Expose
        private String eventImage;
        @SerializedName("event_register_start_date")
        @Expose
        private String eventRegisterStartDate;
        @SerializedName("event_register_end_date")
        @Expose
        private String eventRegisterEndDate;
        @SerializedName("event_add_date")
        @Expose
        private String eventAddDate;
        @SerializedName("event_update_date")
        @Expose
        private String eventUpdateDate;
        @SerializedName("event_status")
        @Expose
        private String eventStatus;

        public ResData() {
        }

        public ResData(String eventId, String userId, String eventTypeId, String eventHeading, String eventDetails, String eventAddress, String eventCountry, String eventState, String eventCity, String eventPostcode, String eventTimezone, String eventLatitude, String eventLongitude, String eventEmail, String eventPhone, String eventImage, String eventRegisterStartDate, String eventRegisterEndDate, String eventAddDate, String eventUpdateDate, String eventStatus) {
            super();

        }

    }}