package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EthnicityResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_data")
    @Expose
    private List<Ethnicity> resData = null;

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

    public List<Ethnicity> getResData() {
        return resData;
    }

    public void setResData(List<Ethnicity> resData) {
        this.resData = resData;
    }

    public class Ethnicity {

        @SerializedName("ethnicity_id")
        @Expose
        private String ethnicityId;
        @SerializedName("ethnicity_code")
        @Expose
        private String ethnicityCode;
        @SerializedName("ethnicity_name")
        @Expose
        private String ethnicityName;

        public String getEthnicityId() {
            return ethnicityId;
        }

        public void setEthnicityId(String ethnicityId) {
            this.ethnicityId = ethnicityId;
        }

        public String getEthnicityCode() {
            return ethnicityCode;
        }

        public void setEthnicityCode(String ethnicityCode) {
            this.ethnicityCode = ethnicityCode;
        }

        public String getEthnicityName() {
            return ethnicityName;
        }

        public void setEthnicityName(String ethnicityName) {
            this.ethnicityName = ethnicityName;
        }
    }
}