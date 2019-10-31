package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentListResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_data")
    @Expose
    private List<Document> resData = null;

    public DocumentListResponse() {
    }

    public DocumentListResponse(String apiResKey, String resStatus, List<Document> resData) {
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

    public List<Document> getResData() {
        return resData;
    }

    public void setResData(List<Document> resData) {
        this.resData = resData;
    }


    @Entity(tableName = "document")
    public static class Document{

        @PrimaryKey
        @NonNull
        @Expose
        @SerializedName("document_id")
        @ColumnInfo(name = "document_id")
        private String document_id;

        @Expose
        @SerializedName("document_type")
        @ColumnInfo(name = "document_type")
        private String document_type;

        @Expose
        @SerializedName("document_type_name")
        @ColumnInfo(name = "document_type_name")
        private String document_type_name;

        @Expose
        @SerializedName("user_id")
        @ColumnInfo(name = "user_id")
        private String user_id;

        @Expose
        @SerializedName("document_name")
        @ColumnInfo(name = "document_name")
        private String document_name;

        @Expose
        @SerializedName("document_file_name")
        @ColumnInfo(name = "document_file_name")
        private String document_file_name;

        @Expose
        @SerializedName("document_status")
        @ColumnInfo(name = "document_status")
        private String document_status;

        @Expose
        @SerializedName("document_add_date")
        @ColumnInfo(name = "document_add_date")
        private String document_add_date;

        public Document(@NonNull String document_id, String document_type, String document_type_name, String user_id, String document_name, String document_file_name, String document_status, String document_add_date) {
            this.document_id = document_id;
            this.document_type = document_type;
            this.document_type_name = document_type_name;
            this.user_id = user_id;
            this.document_name = document_name;
            this.document_file_name = document_file_name;
            this.document_status = document_status;
            this.document_add_date = document_add_date;
        }

        @NonNull
        public String getDocument_id() {
            return document_id;
        }

        public void setDocument_id(@NonNull String document_id) {
            this.document_id = document_id;
        }

        public String getDocument_type() {
            return document_type;
        }

        public void setDocument_type(String document_type) {
            this.document_type = document_type;
        }

        public String getDocument_type_name() {
            return document_type_name;
        }

        public void setDocument_type_name(String document_type_name) {
            this.document_type_name = document_type_name;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getDocument_name() {
            return document_name;
        }

        public void setDocument_name(String document_name) {
            this.document_name = document_name;
        }

        public String getDocument_file_name() {
            return document_file_name;
        }

        public void setDocument_file_name(String document_file_name) {
            this.document_file_name = document_file_name;
        }

        public String getDocument_status() {
            return document_status;
        }

        public void setDocument_status(String document_status) {
            this.document_status = document_status;
        }

        public String getDocument_add_date() {
            return document_add_date;
        }

        public void setDocument_add_date(String document_add_date) {
            this.document_add_date = document_add_date;
        }
    }
}


