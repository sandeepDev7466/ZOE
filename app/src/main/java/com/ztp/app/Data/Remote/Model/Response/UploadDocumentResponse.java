package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadDocumentResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_message")
    @Expose
    private String resMessage;
    @SerializedName("res_data")
    @Expose
    private ResData resData;

    public UploadDocumentResponse() {
    }

    public UploadDocumentResponse(String apiResKey, String resStatus, String resMessage, ResData resData) {
        super();
        this.apiResKey = apiResKey;
        this.resStatus = resStatus;
        this.resMessage = resMessage;
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

    public String getResMessage() {
        return resMessage;
    }

    public void setResMessage(String resMessage) {
        this.resMessage = resMessage;
    }

    public ResData getResData() {
        return resData;
    }

    public void setResData(ResData resData) {
        this.resData = resData;
    }

    public class ResData {

        @SerializedName("user_id_file")
        @Expose
        private String userIdFile;

        public ResData() {
        }

        public ResData(String userIdFile) {
            super();
            this.userIdFile = userIdFile;
        }

        public String getUserIdFile() {
            return userIdFile;
        }

        public void setUserIdFile(String userIdFile) {
            this.userIdFile = userIdFile;
        }

    }
}