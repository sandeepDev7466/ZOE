
package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CsoRegisterResponseStep_3 {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_data")
    @Expose
    private ResData resData;

    public CsoRegisterResponseStep_3() {
    }

    public CsoRegisterResponseStep_3(String apiResKey, String resStatus, ResData resData) {
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

    public ResData getResData() {
        return resData;
    }

    public void setResData(ResData resData) {
        this.resData = resData;
    }

    public class ResData {

        @SerializedName("phone_otp")
        @Expose
        private String phoneOtp;

        public ResData() {
        }

        public ResData(String phoneOtp) {
            super();
            this.phoneOtp = phoneOtp;
        }

        public String getPhoneOtp() {
            return phoneOtp;
        }

        public void setPhoneOtp(String phoneOtp) {
            this.phoneOtp = phoneOtp;
        }

    }
}

