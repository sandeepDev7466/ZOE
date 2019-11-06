package com.ztp.app.Data.Remote.Model.Response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchCSOResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<ResData> resData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

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

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public class ResData {

        @SerializedName("event_id")
        @Expose
        private String eventId;
        @SerializedName("cso_id")
        @Expose
        private String csoId;
        @SerializedName("cso_name")
        @Expose
        private String csoName;
        @SerializedName("cso_email")
        @Expose
        private String csoEmail;
        @SerializedName("cso_phone")
        @Expose
        private String csoPhone;
        @SerializedName("cso_website")
        @Expose
        private String csoWebsite;
        @SerializedName("cso_mission")
        @Expose
        private String csoMission;
        @SerializedName("cso_cause")
        @Expose
        private String csoCause;
        @SerializedName("cso_profile")
        @Expose
        private String csoProfile;
        @SerializedName("cso_country")
        @Expose
        private String csoCountry;
        @SerializedName("country_name")
        @Expose
        private String countryName;
        @SerializedName("cso_state")
        @Expose
        private String csoState;
        @SerializedName("state_name")
        @Expose
        private String stateName;
        @SerializedName("cso_city")
        @Expose
        private String csoCity;
        @SerializedName("cso_zipcode")
        @Expose
        private String csoZipcode;
        @SerializedName("cso_address")
        @Expose
        private String csoAddress;
        @SerializedName("cso_longitude")
        @Expose
        private String csoLongitude;
        @SerializedName("cso_image")
        @Expose
        private String csoImage;
        @SerializedName("cso_taxid")
        @Expose
        private String csoTaxid;
        @SerializedName("cso_501C3")
        @Expose
        private String cso501C3;
        @SerializedName("cso_nonprofit")
        @Expose
        private String csoNonprofit;
        @SerializedName("cso_service")
        @Expose
        private String csoService;
        @SerializedName("cso_target")
        @Expose
        private String csoTarget;
        @SerializedName("cso_volunteer_req")
        @Expose
        private String csoVolunteerReq;
        @SerializedName("cso_min_time")
        @Expose
        private String csoMinTime;
        @SerializedName("cso_volunteer_num")
        @Expose
        private String csoVolunteerNum;
        @SerializedName("cso_volunteer_police")
        @Expose
        private String csoVolunteerPolice;
        @SerializedName("cso_easy_access")
        @Expose
        private String csoEasyAccess;
        @SerializedName("cso_file_title")
        @Expose
        private String csoFileTitle;
        @SerializedName("cso_id_file")
        @Expose
        private String csoIdFile;
        @SerializedName("map_id")
        @Expose
        private String mapId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("map_status")
        @Expose
        private String mapStatus;

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getCsoId() {
            return csoId;
        }

        public void setCsoId(String csoId) {
            this.csoId = csoId;
        }

        public String getCsoName() {
            return csoName;
        }

        public void setCsoName(String csoName) {
            this.csoName = csoName;
        }

        public String getCsoEmail() {
            return csoEmail;
        }

        public void setCsoEmail(String csoEmail) {
            this.csoEmail = csoEmail;
        }

        public String getCsoPhone() {
            return csoPhone;
        }

        public void setCsoPhone(String csoPhone) {
            this.csoPhone = csoPhone;
        }

        public String getCsoWebsite() {
            return csoWebsite;
        }

        public void setCsoWebsite(String csoWebsite) {
            this.csoWebsite = csoWebsite;
        }

        public String getCsoMission() {
            return csoMission;
        }

        public void setCsoMission(String csoMission) {
            this.csoMission = csoMission;
        }

        public String getCsoCause() {
            return csoCause;
        }

        public void setCsoCause(String csoCause) {
            this.csoCause = csoCause;
        }

        public String getCsoProfile() {
            return csoProfile;
        }

        public void setCsoProfile(String csoProfile) {
            this.csoProfile = csoProfile;
        }

        public String getCsoCountry() {
            return csoCountry;
        }

        public void setCsoCountry(String csoCountry) {
            this.csoCountry = csoCountry;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCsoState() {
            return csoState;
        }

        public void setCsoState(String csoState) {
            this.csoState = csoState;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getCsoCity() {
            return csoCity;
        }

        public void setCsoCity(String csoCity) {
            this.csoCity = csoCity;
        }

        public String getCsoZipcode() {
            return csoZipcode;
        }

        public void setCsoZipcode(String csoZipcode) {
            this.csoZipcode = csoZipcode;
        }

        public String getCsoAddress() {
            return csoAddress;
        }

        public void setCsoAddress(String csoAddress) {
            this.csoAddress = csoAddress;
        }

        public String getCsoLongitude() {
            return csoLongitude;
        }

        public void setCsoLongitude(String csoLongitude) {
            this.csoLongitude = csoLongitude;
        }

        public String getCsoImage() {
            return csoImage;
        }

        public void setCsoImage(String csoImage) {
            this.csoImage = csoImage;
        }

        public String getCsoTaxid() {
            return csoTaxid;
        }

        public void setCsoTaxid(String csoTaxid) {
            this.csoTaxid = csoTaxid;
        }

        public String getCso501C3() {
            return cso501C3;
        }

        public void setCso501C3(String cso501C3) {
            this.cso501C3 = cso501C3;
        }

        public String getCsoNonprofit() {
            return csoNonprofit;
        }

        public void setCsoNonprofit(String csoNonprofit) {
            this.csoNonprofit = csoNonprofit;
        }

        public String getCsoService() {
            return csoService;
        }

        public void setCsoService(String csoService) {
            this.csoService = csoService;
        }

        public String getCsoTarget() {
            return csoTarget;
        }

        public void setCsoTarget(String csoTarget) {
            this.csoTarget = csoTarget;
        }

        public String getCsoVolunteerReq() {
            return csoVolunteerReq;
        }

        public void setCsoVolunteerReq(String csoVolunteerReq) {
            this.csoVolunteerReq = csoVolunteerReq;
        }

        public String getCsoMinTime() {
            return csoMinTime;
        }

        public void setCsoMinTime(String csoMinTime) {
            this.csoMinTime = csoMinTime;
        }

        public String getCsoVolunteerNum() {
            return csoVolunteerNum;
        }

        public void setCsoVolunteerNum(String csoVolunteerNum) {
            this.csoVolunteerNum = csoVolunteerNum;
        }

        public String getCsoVolunteerPolice() {
            return csoVolunteerPolice;
        }

        public void setCsoVolunteerPolice(String csoVolunteerPolice) {
            this.csoVolunteerPolice = csoVolunteerPolice;
        }

        public String getCsoEasyAccess() {
            return csoEasyAccess;
        }

        public void setCsoEasyAccess(String csoEasyAccess) {
            this.csoEasyAccess = csoEasyAccess;
        }

        public String getCsoFileTitle() {
            return csoFileTitle;
        }

        public void setCsoFileTitle(String csoFileTitle) {
            this.csoFileTitle = csoFileTitle;
        }

        public String getCsoIdFile() {
            return csoIdFile;
        }

        public void setCsoIdFile(String csoIdFile) {
            this.csoIdFile = csoIdFile;
        }

        public String getMapId() {
            return mapId;
        }

        public void setMapId(String mapId) {
            this.mapId = mapId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getMapStatus() {
            return mapStatus;
        }

        public void setMapStatus(String mapStatus) {
            this.mapStatus = mapStatus;
        }
    }
}
