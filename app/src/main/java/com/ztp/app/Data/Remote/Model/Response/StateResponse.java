package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_data")
    @Expose
    private List<State> stateList = null;

    public StateResponse() {
    }

    public StateResponse(String apiResKey, String resStatus, List<State> stateList) {
        super();
        this.apiResKey = apiResKey;
        this.resStatus = resStatus;
        this.stateList = stateList;
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

    public List<State> getStateList() {
        return stateList;
    }

    public void setStateList(List<State> stateList) {
        this.stateList = stateList;
    }

    @Entity(tableName = "state")
    public static class State {

        @PrimaryKey(autoGenerate = true)
        @NonNull
        @ColumnInfo(name = "id")
        private int id;

        @SerializedName("state_id")
        @Expose
        @ColumnInfo(name = "state_id")
        private String stateId;

        @SerializedName("state_code")
        @Expose
        @ColumnInfo(name = "state_code")
        private String stateCode;

        @SerializedName("state_name")
        @Expose
        @ColumnInfo(name = "state_name")
        private String stateName;

//        public State() {
//        }

        public State(int id, String stateId, String stateCode, String stateName) {
            this.id = id;
            this.stateId = stateId;
            this.stateCode = stateCode;
            this.stateName = stateName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public String getStateCode() {
            return stateCode;
        }

        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }


        @Override
        public String toString() {
            return stateName;
        }


    }

}
