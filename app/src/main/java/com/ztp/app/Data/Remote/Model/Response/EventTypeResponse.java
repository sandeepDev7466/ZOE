package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by htl-dev on 15-04-2019.
 */

public class EventTypeResponse {


    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_data")
    @Expose
    private List<EventType> resData = null;

    public EventTypeResponse() {
    }

    public EventTypeResponse(String apiResKey, String resStatus, List<EventType> resData) {
        super();
        this.apiResKey = apiResKey;
        this.resStatus = resStatus;
        this.resData = resData;
    }

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public List<EventType> getResData() {
        return resData;
    }

    public void setResData(List<EventType> resData) {
        this.resData = resData;
    }


    @Entity(tableName = "event_type")
    public static class EventType {

        @PrimaryKey(autoGenerate = true)
        @NonNull
        @ColumnInfo(name = "id")
        private int id;

        @SerializedName("event_type_id")
        @Expose
        @ColumnInfo(name = "event_type_id")
        private String eventTypeId;

        @SerializedName("event_type_name")
        @Expose
        @ColumnInfo(name = "event_type_name")
        private String eventTypeName;

        @SerializedName("event_type")
        @Expose
        @ColumnInfo(name = "event_type")
        private String eventType;

        public EventType() {
        }

        public EventType(int id, String eventTypeId, String eventTypeName, String eventType) {
            this.id = id;
            this.eventTypeId = eventTypeId;
            this.eventTypeName = eventTypeName;
            this.eventType = eventType;
        }

        @NonNull
        public int getId() {
            return id;
        }

        public void setId(@NonNull int id) {
            this.id = id;
        }

        public String getEventTypeId() {
            return eventTypeId;
        }

        public void setEventTypeId(String eventTypeId) {
            this.eventTypeId = eventTypeId;
        }

        public String getEventTypeName() {
            return eventTypeName;
        }

        public void setEventTypeName(String eventTypeName) {
            this.eventTypeName = eventTypeName;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        @Override
        public String toString() {
            return eventTypeName;
        }


    }

}
