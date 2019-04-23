package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetProfileResponse implements Serializable {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private ResData resData;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public GetProfileResponse() {
    }

    public GetProfileResponse(String apiResKey, ResData resData, String resStatus) {
        super();
        this.apiResKey = apiResKey;
        this.resData = resData;
        this.resStatus = resStatus;
    }

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public ResData getResData() {
        return resData;
    }

    public void setResData(ResData resData) {
        this.resData = resData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public class ResData implements Serializable{

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("user_f_name")
        @Expose
        private String userFName;
        @SerializedName("user_l_name")
        @Expose
        private String userLName;
        @SerializedName("user_email")
        @Expose
        private String userEmail;
        @SerializedName("user_device")
        @Expose
        private String userDevice;
        @SerializedName("user_phone")
        @Expose
        private String userPhone;
        @SerializedName("user_city")
        @Expose
        private String userCity;
        @SerializedName("user_state")
        @Expose
        private String userState;
        @SerializedName("user_state_name")
        @Expose
        private String userStateName;
        @SerializedName("user_country")
        @Expose
        private String userCountry;
        @SerializedName("user_country_name")
        @Expose
        private String userCountryName;
        @SerializedName("user_zipcode")
        @Expose
        private String userZipcode;
        @SerializedName("user_address")
        @Expose
        private String userAddress;
        @SerializedName("user_longitude")
        @Expose
        private String userLongitude;
        @SerializedName("user_latitude")
        @Expose
        private String userLatitude;
        @SerializedName("user_dob")
        @Expose
        private String userDob;
        @SerializedName("user_gender")
        @Expose
        private String userGender;
        @SerializedName("user_id_file")
        @Expose
        private String userIdFile;
        @SerializedName("user_file_title")
        @Expose
        private String userFileTitle;
        @SerializedName("org_name")
        @Expose
        private String orgName;
        @SerializedName("org_email")
        @Expose
        private String orgEmail;
        @SerializedName("org_phone")
        @Expose
        private String orgPhone;
        @SerializedName("org_website")
        @Expose
        private String orgWebsite;
        @SerializedName("org_mission")
        @Expose
        private String orgMission;
        @SerializedName("org_cause")
        @Expose
        private String orgCause;
        @SerializedName("org_profile")
        @Expose
        private String orgProfile;
        @SerializedName("org_country")
        @Expose
        private String orgCountry;
        @SerializedName("org_country_name")
        @Expose
        private String orgCountryName;
        @SerializedName("org_state")
        @Expose
        private String orgState;
        @SerializedName("org_state_name")
        @Expose
        private String orgStateName;
        @SerializedName("org_city")
        @Expose
        private String orgCity;
        @SerializedName("org_zipcode")
        @Expose
        private String orgZipcode;
        @SerializedName("org_address")
        @Expose
        private String orgAddress;
        @SerializedName("org_logitude")
        @Expose
        private String orgLogitude;
        @SerializedName("org_latitude")
        @Expose
        private String orgLatitude;
        @SerializedName("org_taxid")
        @Expose
        private String orgTaxid;
        @SerializedName("org_501C3")
        @Expose
        private String org501C3;
        @SerializedName("org_non_profit")
        @Expose
        private String orgNonProfit;
        @SerializedName("org_service")
        @Expose
        private String orgService;
        @SerializedName("org_target")
        @Expose
        private String orgTarget;
        @SerializedName("org_volunteer_req")
        @Expose
        private String orgVolunteerReq;
        @SerializedName("org_min_time")
        @Expose
        private String orgMinTime;
        @SerializedName("org_voluteer_num")
        @Expose
        private String orgVoluteerNum;
        @SerializedName("org_voluteer_police")
        @Expose
        private String orgVoluteerPolice;
        @SerializedName("org_easy_access")
        @Expose
        private String orgEasyAccess;
        @SerializedName("school_id")
        @Expose
        private String schoolId;
        @SerializedName("school_desc")
        @Expose
        private String schoolDesc;
        @SerializedName("school_student_num")
        @Expose
        private String schoolStudentNum;
        @SerializedName("user_status")
        @Expose
        private String userStatus;
        @SerializedName("phone_valid")
        @Expose
        private String phoneValid;
        @SerializedName("email_valid")
        @Expose
        private String emailValid;

        /**
         * No args constructor for use in serialization
         *
         */
        public ResData() {
        }

        /**
         *
         * @param userDevice
         * @param orgVoluteerPolice
         * @param userDob
         * @param userZipcode
         * @param userCountryName
         * @param userType
         * @param userAddress
         * @param userState
         * @param userGender
         * @param orgEasyAccess
         * @param userCountry
         * @param userId
         * @param orgLatitude
         * @param orgVoluteerNum
         * @param userPhone
         * @param orgService
         * @param emailValid
         * @param userLName
         * @param orgStateName
         * @param orgCause
         * @param userStatus
         * @param userFileTitle
         * @param orgNonProfit
         * @param userStateName
         * @param orgState
         * @param orgAddress
         * @param userCity
         * @param userFName
         * @param userEmail
         * @param orgProfile
         * @param orgCountry
         * @param orgWebsite
         * @param orgPhone
         * @param userIdFile
         * @param orgEmail
         * @param phoneValid
         * @param schoolDesc
         * @param schoolStudentNum
         * @param userLongitude
         * @param orgMission
         * @param orgCity
         * @param orgTaxid
         * @param orgZipcode
         * @param orgName
         * @param org501C3
         * @param orgTarget
         * @param orgMinTime
         * @param orgVolunteerReq
         * @param orgCountryName
         * @param schoolId
         * @param orgLogitude
         * @param userLatitude
         */
        public ResData(String userId, String userType, String userFName, String userLName, String userEmail, String userDevice, String userPhone, String userCity, String userState, String userStateName, String userCountry, String userCountryName, String userZipcode, String userAddress, String userLongitude, String userLatitude, String userDob, String userGender, String userIdFile, String userFileTitle, String orgName, String orgEmail, String orgPhone, String orgWebsite, String orgMission, String orgCause, String orgProfile, String orgCountry, String orgCountryName, String orgState, String orgStateName, String orgCity, String orgZipcode, String orgAddress, String orgLogitude, String orgLatitude, String orgTaxid, String org501C3, String orgNonProfit, String orgService, String orgTarget, String orgVolunteerReq, String orgMinTime, String orgVoluteerNum, String orgVoluteerPolice, String orgEasyAccess, String schoolId, String schoolDesc, String schoolStudentNum, String userStatus, String phoneValid, String emailValid) {
            super();
            this.userId = userId;
            this.userType = userType;
            this.userFName = userFName;
            this.userLName = userLName;
            this.userEmail = userEmail;
            this.userDevice = userDevice;
            this.userPhone = userPhone;
            this.userCity = userCity;
            this.userState = userState;
            this.userStateName = userStateName;
            this.userCountry = userCountry;
            this.userCountryName = userCountryName;
            this.userZipcode = userZipcode;
            this.userAddress = userAddress;
            this.userLongitude = userLongitude;
            this.userLatitude = userLatitude;
            this.userDob = userDob;
            this.userGender = userGender;
            this.userIdFile = userIdFile;
            this.userFileTitle = userFileTitle;
            this.orgName = orgName;
            this.orgEmail = orgEmail;
            this.orgPhone = orgPhone;
            this.orgWebsite = orgWebsite;
            this.orgMission = orgMission;
            this.orgCause = orgCause;
            this.orgProfile = orgProfile;
            this.orgCountry = orgCountry;
            this.orgCountryName = orgCountryName;
            this.orgState = orgState;
            this.orgStateName = orgStateName;
            this.orgCity = orgCity;
            this.orgZipcode = orgZipcode;
            this.orgAddress = orgAddress;
            this.orgLogitude = orgLogitude;
            this.orgLatitude = orgLatitude;
            this.orgTaxid = orgTaxid;
            this.org501C3 = org501C3;
            this.orgNonProfit = orgNonProfit;
            this.orgService = orgService;
            this.orgTarget = orgTarget;
            this.orgVolunteerReq = orgVolunteerReq;
            this.orgMinTime = orgMinTime;
            this.orgVoluteerNum = orgVoluteerNum;
            this.orgVoluteerPolice = orgVoluteerPolice;
            this.orgEasyAccess = orgEasyAccess;
            this.schoolId = schoolId;
            this.schoolDesc = schoolDesc;
            this.schoolStudentNum = schoolStudentNum;
            this.userStatus = userStatus;
            this.phoneValid = phoneValid;
            this.emailValid = emailValid;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getUserFName() {
            return userFName;
        }

        public void setUserFName(String userFName) {
            this.userFName = userFName;
        }

        public String getUserLName() {
            return userLName;
        }

        public void setUserLName(String userLName) {
            this.userLName = userLName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserDevice() {
            return userDevice;
        }

        public void setUserDevice(String userDevice) {
            this.userDevice = userDevice;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserCity() {
            return userCity;
        }

        public void setUserCity(String userCity) {
            this.userCity = userCity;
        }

        public String getUserState() {
            return userState;
        }

        public void setUserState(String userState) {
            this.userState = userState;
        }

        public String getUserStateName() {
            return userStateName;
        }

        public void setUserStateName(String userStateName) {
            this.userStateName = userStateName;
        }

        public String getUserCountry() {
            return userCountry;
        }

        public void setUserCountry(String userCountry) {
            this.userCountry = userCountry;
        }

        public String getUserCountryName() {
            return userCountryName;
        }

        public void setUserCountryName(String userCountryName) {
            this.userCountryName = userCountryName;
        }

        public String getUserZipcode() {
            return userZipcode;
        }

        public void setUserZipcode(String userZipcode) {
            this.userZipcode = userZipcode;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public String getUserLongitude() {
            return userLongitude;
        }

        public void setUserLongitude(String userLongitude) {
            this.userLongitude = userLongitude;
        }

        public String getUserLatitude() {
            return userLatitude;
        }

        public void setUserLatitude(String userLatitude) {
            this.userLatitude = userLatitude;
        }

        public String getUserDob() {
            return userDob;
        }

        public void setUserDob(String userDob) {
            this.userDob = userDob;
        }

        public String getUserGender() {
            return userGender;
        }

        public void setUserGender(String userGender) {
            this.userGender = userGender;
        }

        public String getUserIdFile() {
            return userIdFile;
        }

        public void setUserIdFile(String userIdFile) {
            this.userIdFile = userIdFile;
        }

        public String getUserFileTitle() {
            return userFileTitle;
        }

        public void setUserFileTitle(String userFileTitle) {
            this.userFileTitle = userFileTitle;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getOrgEmail() {
            return orgEmail;
        }

        public void setOrgEmail(String orgEmail) {
            this.orgEmail = orgEmail;
        }

        public String getOrgPhone() {
            return orgPhone;
        }

        public void setOrgPhone(String orgPhone) {
            this.orgPhone = orgPhone;
        }

        public String getOrgWebsite() {
            return orgWebsite;
        }

        public void setOrgWebsite(String orgWebsite) {
            this.orgWebsite = orgWebsite;
        }

        public String getOrgMission() {
            return orgMission;
        }

        public void setOrgMission(String orgMission) {
            this.orgMission = orgMission;
        }

        public String getOrgCause() {
            return orgCause;
        }

        public void setOrgCause(String orgCause) {
            this.orgCause = orgCause;
        }

        public String getOrgProfile() {
            return orgProfile;
        }

        public void setOrgProfile(String orgProfile) {
            this.orgProfile = orgProfile;
        }

        public String getOrgCountry() {
            return orgCountry;
        }

        public void setOrgCountry(String orgCountry) {
            this.orgCountry = orgCountry;
        }

        public String getOrgCountryName() {
            return orgCountryName;
        }

        public void setOrgCountryName(String orgCountryName) {
            this.orgCountryName = orgCountryName;
        }

        public String getOrgState() {
            return orgState;
        }

        public void setOrgState(String orgState) {
            this.orgState = orgState;
        }

        public String getOrgStateName() {
            return orgStateName;
        }

        public void setOrgStateName(String orgStateName) {
            this.orgStateName = orgStateName;
        }

        public String getOrgCity() {
            return orgCity;
        }

        public void setOrgCity(String orgCity) {
            this.orgCity = orgCity;
        }

        public String getOrgZipcode() {
            return orgZipcode;
        }

        public void setOrgZipcode(String orgZipcode) {
            this.orgZipcode = orgZipcode;
        }

        public String getOrgAddress() {
            return orgAddress;
        }

        public void setOrgAddress(String orgAddress) {
            this.orgAddress = orgAddress;
        }

        public String getOrgLogitude() {
            return orgLogitude;
        }

        public void setOrgLogitude(String orgLogitude) {
            this.orgLogitude = orgLogitude;
        }

        public String getOrgLatitude() {
            return orgLatitude;
        }

        public void setOrgLatitude(String orgLatitude) {
            this.orgLatitude = orgLatitude;
        }

        public String getOrgTaxid() {
            return orgTaxid;
        }

        public void setOrgTaxid(String orgTaxid) {
            this.orgTaxid = orgTaxid;
        }

        public String getOrg501C3() {
            return org501C3;
        }

        public void setOrg501C3(String org501C3) {
            this.org501C3 = org501C3;
        }

        public String getOrgNonProfit() {
            return orgNonProfit;
        }

        public void setOrgNonProfit(String orgNonProfit) {
            this.orgNonProfit = orgNonProfit;
        }

        public String getOrgService() {
            return orgService;
        }

        public void setOrgService(String orgService) {
            this.orgService = orgService;
        }

        public String getOrgTarget() {
            return orgTarget;
        }

        public void setOrgTarget(String orgTarget) {
            this.orgTarget = orgTarget;
        }

        public String getOrgVolunteerReq() {
            return orgVolunteerReq;
        }

        public void setOrgVolunteerReq(String orgVolunteerReq) {
            this.orgVolunteerReq = orgVolunteerReq;
        }

        public String getOrgMinTime() {
            return orgMinTime;
        }

        public void setOrgMinTime(String orgMinTime) {
            this.orgMinTime = orgMinTime;
        }

        public String getOrgVoluteerNum() {
            return orgVoluteerNum;
        }

        public void setOrgVoluteerNum(String orgVoluteerNum) {
            this.orgVoluteerNum = orgVoluteerNum;
        }

        public String getOrgVoluteerPolice() {
            return orgVoluteerPolice;
        }

        public void setOrgVoluteerPolice(String orgVoluteerPolice) {
            this.orgVoluteerPolice = orgVoluteerPolice;
        }

        public String getOrgEasyAccess() {
            return orgEasyAccess;
        }

        public void setOrgEasyAccess(String orgEasyAccess) {
            this.orgEasyAccess = orgEasyAccess;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getSchoolDesc() {
            return schoolDesc;
        }

        public void setSchoolDesc(String schoolDesc) {
            this.schoolDesc = schoolDesc;
        }

        public String getSchoolStudentNum() {
            return schoolStudentNum;
        }

        public void setSchoolStudentNum(String schoolStudentNum) {
            this.schoolStudentNum = schoolStudentNum;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getPhoneValid() {
            return phoneValid;
        }

        public void setPhoneValid(String phoneValid) {
            this.phoneValid = phoneValid;
        }

        public String getEmailValid() {
            return emailValid;
        }

        public void setEmailValid(String emailValid) {
            this.emailValid = emailValid;
        }

    }
}
