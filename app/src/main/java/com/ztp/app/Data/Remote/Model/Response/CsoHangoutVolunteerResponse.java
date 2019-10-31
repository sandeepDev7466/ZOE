package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CsoHangoutVolunteerResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String res_status;
    @SerializedName("res_data")
    @Expose
    private List<ResData> resData = null;


    public CsoHangoutVolunteerResponse() {
    }

    public CsoHangoutVolunteerResponse(String apiResKey, List<ResData> resData,String res_status) {
        super();
        this.apiResKey = apiResKey;
        this.resData = resData;
        this.res_status = res_status;

    }

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public List<ResData> getResData() {
        return resData;
    }

    public void setResData(List<ResData> resData) {
        this.resData = resData;
    }
    public String getRes_status() {
        return res_status;
    }

    public void setRes_status(String res_status) {
        this.res_status = res_status;
    }


    public class ResData {

        @SerializedName("vol_id")
        @Expose
        private String vol_id;
        @SerializedName("vol_f_name")
        @Expose
        private String vol_f_name;
        @SerializedName("vol_l_name")
        @Expose
        private String vol_l_name;
        @SerializedName("vol_profile_pic")
        @Expose
        private String vol_profile_pic;
        @SerializedName("vol_email")
        @Expose
        private String vol_email;


        public ResData() {
        }

        public ResData(String vol_id, String vol_email, String vol_f_name, String vol_l_name, String vol_profile_pic) {
            super();
            this.vol_id = vol_id;
            this.vol_email = vol_email;
            this.vol_f_name = vol_f_name;
            this.vol_l_name = vol_l_name;
            this.vol_profile_pic = vol_profile_pic;

        }

        public String getVol_id() {
            return vol_id;
        }

        public void setVol_id(String vol_id) {
            this.vol_id = vol_id;
        }

        public String getVol_f_name() {
            return vol_f_name;
        }

        public void setVol_f_name(String vol_f_name) {
            this.vol_f_name = vol_f_name;
        }

        public String getVol_l_name() {
            return vol_l_name;
        }

        public void setVol_l_name(String vol_l_name) {
            this.vol_l_name = vol_l_name;
        }

        public String getVol_profile_pic() {
            return vol_profile_pic;
        }

        public void setVol_profile_pic(String vol_profile_pic) {
            this.vol_profile_pic = vol_profile_pic;
        }

        public String getVol_email() {
            return vol_email;
        }

        public void setVol_email(String vol_email) {
            this.vol_email = vol_email;
        }
    }
}