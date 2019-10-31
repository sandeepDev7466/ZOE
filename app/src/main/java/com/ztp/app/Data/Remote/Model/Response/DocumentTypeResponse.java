package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentTypeResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_data")
    @Expose
    private List<Document> resData = null;

    public DocumentTypeResponse() {
    }

    public DocumentTypeResponse(String apiResKey, String resStatus, List<Document> resData) {
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


    public static class Document{


        @Expose
        @SerializedName("document_type_id")
        private String document_type_id;


        @Expose
        @SerializedName("document_type")
        private String document_type;


        @Expose
        @SerializedName("document_type_name")
        private String document_type_name;



        public Document(String document_type_id, String document_type, String document_type_name ) {
            this.document_type_id = document_type_id;
            this.document_type = document_type;
            this.document_type_name = document_type_name;

        }

        public String getDocument_type_id() {
            return document_type_id;
        }

        public void setDocument_type_id(String document_type_id) {
            this.document_type_id = document_type_id;
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
    }

}


