package com.ztp.app.Data.Remote.Model.Response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserTimezoneResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<ResDatum> resData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public List<ResDatum> getResData() {
        return resData;
    }

    public void setResData(List<ResDatum> resData) {
        this.resData = resData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }


    public class ResDatum {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("login_daylight")
        @Expose
        private String loginDaylight;
        @SerializedName("login_timezone")
        @Expose
        private String loginTimezone;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getLoginDaylight() {
            return loginDaylight;
        }

        public void setLoginDaylight(String loginDaylight) {
            this.loginDaylight = loginDaylight;
        }

        public String getLoginTimezone() {
            return loginTimezone;
        }

        public void setLoginTimezone(String loginTimezone) {
            this.loginTimezone = loginTimezone;
        }

    }

}
