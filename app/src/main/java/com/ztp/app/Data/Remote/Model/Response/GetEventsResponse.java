package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEventsResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<Event> resData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public GetEventsResponse() {
    }

    public GetEventsResponse(String apiResKey, List<Event> resData, String resStatus) {
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

    public List<Event> getResData() {
        return resData;
    }

    public void setResData(List<Event> resData) {
        this.resData = resData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    @Entity(tableName = "event")
    public static class Event implements Serializable {

        @NonNull
        @PrimaryKey
        @SerializedName("event_id")
        @Expose
        @ColumnInfo(name = "event_id")
        private String eventId;

        @SerializedName("user_id")
        @Expose
        @ColumnInfo(name = "user_id")
        private String userId;

        @SerializedName("event_type_id")
        @Expose
        @ColumnInfo(name = "event_type_id")
        private String eventTypeId;

        @SerializedName("event_heading")
        @Expose
        @ColumnInfo(name = "event_heading")
        private String eventHeading;

        @SerializedName("event_details")
        @Expose
        @ColumnInfo(name = "event_details")
        private String eventDetails;

        @SerializedName("event_address")
        @Expose
        @ColumnInfo(name = "event_address")
        private String eventAddress;

        @SerializedName("event_country")
        @Expose
        @ColumnInfo(name = "event_country")
        private String eventCountry;

        @SerializedName("event_country_name")
        @Expose
        @ColumnInfo(name = "event_country_name")
        private String eventCountryName;

        @SerializedName("event_state")
        @Expose
        @ColumnInfo(name = "event_state")
        private String eventState;

        @SerializedName("event_state_name")
        @Expose
        @ColumnInfo(name = "event_state_name")
        private String eventStateName;

        @SerializedName("event_city")
        @Expose
        @ColumnInfo(name = "event_city")
        private String eventCity;

        @SerializedName("event_postcode")
        @Expose
        @ColumnInfo(name = "event_postcode")
        private String eventPostcode;

        @SerializedName("event_timezone")
        @Expose
        @ColumnInfo(name = "event_timezone")
        private String eventTimezone;

        @SerializedName("event_latitude")
        @Expose
        @ColumnInfo(name = "event_latitude")
        private String eventLatitude;

        @SerializedName("event_longitude")
        @Expose
        @ColumnInfo(name = "event_longitude")
        private String eventLongitude;

        @SerializedName("event_email")
        @Expose
        @ColumnInfo(name = "event_email")
        private String eventEmail;

        @SerializedName("event_phone")
        @Expose
        @ColumnInfo(name = "event_phone")
        private String eventPhone;

        @SerializedName("event_image")
        @Expose
        @ColumnInfo(name = "event_image")
        private String eventImage;

        @SerializedName("event_register_start_date")
        @Expose
        @ColumnInfo(name = "event_register_start_date")
        private String eventRegisterStartDate;

        @SerializedName("event_register_end_date")
        @Expose
        @ColumnInfo(name = "event_register_end_date")
        private String eventRegisterEndDate;

        @SerializedName("event_add_date")
        @Expose
        @ColumnInfo(name = "event_add_date")
        private String eventAddDate;

        @SerializedName("event_update_date")
        @Expose
        @ColumnInfo(name = "event_update_date")
        private String eventUpdateDate;

        @SerializedName("event_status")
        @Expose
        @ColumnInfo(name = "event_status")
        private String eventStatus;

        @SerializedName("event_image_name")
        @Expose
        @ColumnInfo(name = "event_image_name")
        private String eventImageName;

        @SerializedName("shift_count")
        @Expose
        @ColumnInfo(name = "shift_count")
        private String shiftCount;

        @SerializedName("total_vol_req")
        @Expose
        @ColumnInfo(name = "total_vol_req")
        private String totalVolReq;

        @SerializedName("event_waiver_req")
        @Expose
        @ColumnInfo(name = "event_waiver_req")
        private String eventWaiverRequired;

        @SerializedName("event_waiver_doc")
        @Expose
        @ColumnInfo(name = "event_waiver_doc")
        private String eventWaiverDocument;

        @SerializedName("event_start_time")
        @Expose
        @ColumnInfo(name = "event_start_time")
        private String eventStartTime;

        @SerializedName("event_end_time")
        @Expose
        @ColumnInfo(name = "event_end_time")
        private String eventEndTime;

        public Event(@NonNull String eventId, String userId, String eventTypeId, String eventHeading, String eventDetails, String eventAddress, String eventCountry, String eventCountryName, String eventState, String eventStateName, String eventCity, String eventPostcode, String eventTimezone, String eventLatitude, String eventLongitude, String eventEmail, String eventPhone, String eventImage, String eventRegisterStartDate, String eventRegisterEndDate, String eventAddDate, String eventUpdateDate, String eventStatus, String eventImageName, String shiftCount, String totalVolReq, String eventWaiverRequired, String eventWaiverDocument, String eventStartTime, String eventEndTime) {
            this.eventId = eventId;
            this.userId = userId;
            this.eventTypeId = eventTypeId;
            this.eventHeading = eventHeading;
            this.eventDetails = eventDetails;
            this.eventAddress = eventAddress;
            this.eventCountry = eventCountry;
            this.eventCountryName = eventCountryName;
            this.eventState = eventState;
            this.eventStateName = eventStateName;
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
            this.eventImageName = eventImageName;
            this.shiftCount = shiftCount;
            this.totalVolReq = totalVolReq;
            this.eventWaiverRequired = eventWaiverRequired;
            this.eventWaiverDocument = eventWaiverDocument;
            this.eventStartTime = eventStartTime;
            this.eventEndTime = eventEndTime;
        }

        @NonNull
        public String getEventId() {
            return eventId;
        }

        public void setEventId(@NonNull String eventId) {
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

        public String getEventCountryName() {
            return eventCountryName;
        }

        public void setEventCountryName(String eventCountryName) {
            this.eventCountryName = eventCountryName;
        }

        public String getEventState() {
            return eventState;
        }

        public void setEventState(String eventState) {
            this.eventState = eventState;
        }

        public String getEventStateName() {
            return eventStateName;
        }

        public void setEventStateName(String eventStateName) {
            this.eventStateName = eventStateName;
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

        public String getEventImageName() {
            return eventImageName;
        }

        public void setEventImageName(String eventImageName) {
            this.eventImageName = eventImageName;
        }

        public String getShiftCount() {
            return shiftCount;
        }

        public void setShiftCount(String shiftCount) {
            this.shiftCount = shiftCount;
        }

        public String getTotalVolReq() {
            return totalVolReq;
        }

        public void setTotalVolReq(String totalVolReq) {
            this.totalVolReq = totalVolReq;
        }

        public String getEventWaiverRequired() {
            return eventWaiverRequired;
        }

        public void setEventWaiverRequired(String eventWaiverRequired) {
            this.eventWaiverRequired = eventWaiverRequired;
        }

        public String getEventWaiverDocument() {
            return eventWaiverDocument;
        }

        public void setEventWaiverDocument(String eventWaiverDocument) {
            this.eventWaiverDocument = eventWaiverDocument;
        }

        public String getEventStartTime() {
            return eventStartTime;
        }

        public void setEventStartTime(String eventStartTime) {
            this.eventStartTime = eventStartTime;
        }

        public String getEventEndTime() {
            return eventEndTime;
        }

        public void setEventEndTime(String eventEndTime) {
            this.eventEndTime = eventEndTime;
        }
    }
}