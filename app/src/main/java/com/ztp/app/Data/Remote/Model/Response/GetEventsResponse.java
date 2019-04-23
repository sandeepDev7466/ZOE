package com.ztp.app.Data.Remote.Model.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetEventsResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<EventData> eventData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public GetEventsResponse() {
    }

    public GetEventsResponse(String apiResKey, List<EventData> eventData, String resStatus) {
        super();
        this.apiResKey = apiResKey;
        this.eventData = eventData;
        this.resStatus = resStatus;
    }

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public List<EventData> getEventData() {
        return eventData;
    }

    public void setEventData(List<EventData> eventData) {
        this.eventData = eventData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public class EventData implements Serializable {

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

        public EventData() {
        }

        public EventData(String eventId, String userId, String eventTypeId, String eventHeading, String eventDetails, String eventAddress, String eventCountry, String eventState, String eventCity, String eventPostcode, String eventTimezone, String eventLatitude, String eventLongitude, String eventEmail, String eventPhone, String eventImage, String eventRegisterStartDate, String eventRegisterEndDate, String eventAddDate, String eventUpdateDate, String eventStatus) {
            super();
            this.eventId = eventId;
            this.userId = userId;
            this.eventTypeId = eventTypeId;
            this.eventHeading = eventHeading;
            this.eventDetails = eventDetails;
            this.eventAddress = eventAddress;
            this.eventCountry = eventCountry;
            this.eventState = eventState;
            this.eventCity = eventCity;
            this.eventPostcode = eventPostcode;
            this.eventTimezone = eventTimezone;
            this.eventLatitude = eventLatitude;
            this.eventLongitude = eventLongitude;
            this.eventEmail = eventEmail;
            this.eventPhone = eventPhone;
            this.eventImage = eventImage;
            this.eventRegisterStartDate = eventRegisterStartDate;
            this.eventRegisterEndDate = eventRegisterEndDate;
            this.eventAddDate = eventAddDate;
            this.eventUpdateDate = eventUpdateDate;
            this.eventStatus = eventStatus;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getEventTypeId() {
            return eventTypeId;
        }

        public void setEventTypeId(String eventTypeId) {
            this.eventTypeId = eventTypeId;
        }

        public String getEventHeading() {
            return eventHeading;
        }

        public void setEventHeading(String eventHeading) {
            this.eventHeading = eventHeading;
        }

        public String getEventDetails() {
            return eventDetails;
        }

        public void setEventDetails(String eventDetails) {
            this.eventDetails = eventDetails;
        }

        public String getEventAddress() {
            return eventAddress;
        }

        public void setEventAddress(String eventAddress) {
            this.eventAddress = eventAddress;
        }

        public String getEventCountry() {
            return eventCountry;
        }

        public void setEventCountry(String eventCountry) {
            this.eventCountry = eventCountry;
        }

        public String getEventState() {
            return eventState;
        }

        public void setEventState(String eventState) {
            this.eventState = eventState;
        }

        public String getEventCity() {
            return eventCity;
        }

        public void setEventCity(String eventCity) {
            this.eventCity = eventCity;
        }

        public String getEventPostcode() {
            return eventPostcode;
        }

        public void setEventPostcode(String eventPostcode) {
            this.eventPostcode = eventPostcode;
        }

        public String getEventTimezone() {
            return eventTimezone;
        }

        public void setEventTimezone(String eventTimezone) {
            this.eventTimezone = eventTimezone;
        }

        public String getEventLatitude() {
            return eventLatitude;
        }

        public void setEventLatitude(String eventLatitude) {
            this.eventLatitude = eventLatitude;
        }

        public String getEventLongitude() {
            return eventLongitude;
        }

        public void setEventLongitude(String eventLongitude) {
            this.eventLongitude = eventLongitude;
        }

        public String getEventEmail() {
            return eventEmail;
        }

        public void setEventEmail(String eventEmail) {
            this.eventEmail = eventEmail;
        }

        public String getEventPhone() {
            return eventPhone;
        }

        public void setEventPhone(String eventPhone) {
            this.eventPhone = eventPhone;
        }

        public String getEventImage() {
            return eventImage;
        }

        public void setEventImage(String eventImage) {
            this.eventImage = eventImage;
        }

        public String getEventRegisterStartDate() {
            return eventRegisterStartDate;
        }

        public void setEventRegisterStartDate(String eventRegisterStartDate) {
            this.eventRegisterStartDate = eventRegisterStartDate;
        }

        public String getEventRegisterEndDate() {
            return eventRegisterEndDate;
        }

        public void setEventRegisterEndDate(String eventRegisterEndDate) {
            this.eventRegisterEndDate = eventRegisterEndDate;
        }

        public String getEventAddDate() {
            return eventAddDate;
        }

        public void setEventAddDate(String eventAddDate) {
            this.eventAddDate = eventAddDate;
        }

        public String getEventUpdateDate() {
            return eventUpdateDate;
        }

        public void setEventUpdateDate(String eventUpdateDate) {
            this.eventUpdateDate = eventUpdateDate;
        }

        public String getEventStatus() {
            return eventStatus;
        }

        public void setEventStatus(String eventStatus) {
            this.eventStatus = eventStatus;
        }

    }
}