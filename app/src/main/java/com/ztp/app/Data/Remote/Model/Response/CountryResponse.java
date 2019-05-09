package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_data")
    @Expose
    private List<Country> resData = null;

    public CountryResponse() {
    }

    public CountryResponse(String apiResKey, String resStatus, List<Country> resData) {
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

    public List<Country> getResData() {
        return resData;
    }

    public void setResData(List<Country> resData) {
        this.resData = resData;
    }

    @Entity(tableName = "country")
    public static class Country {

        @PrimaryKey(autoGenerate = true)
        @NonNull
        @ColumnInfo(name = "id")
        private int id;

        @SerializedName("country_id")
        @Expose
        @ColumnInfo(name = "country_id")
        private String countryId;

        @SerializedName("country_code")
        @Expose
        @ColumnInfo(name = "country_code")
        private String countryCode;

        @SerializedName("country_name")
        @Expose
        @ColumnInfo(name = "country_name")
        private String countryName;

//        public Country() {
//        }

        public Country(int id, String countryId, String countryCode, String countryName) {
            this.id = id;
            this.countryId = countryId;
            this.countryCode = countryCode;
            this.countryName = countryName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        @Override
        public String toString() {
            return countryName;
        }

    }

}


