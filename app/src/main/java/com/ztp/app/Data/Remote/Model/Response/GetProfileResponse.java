package com.ztp.app.Data.Remote.Model.Response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

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

//    public GetProfileResponse() {
//    }

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

    @Entity(tableName = "profile")
    public static class ResData implements Serializable {

        @NonNull
        @PrimaryKey
        @SerializedName("user_id")
        @Expose
        @ColumnInfo(name = "user_id")
        private String userId;

        @SerializedName("user_type")
        @Expose
        @ColumnInfo(name = "user_type")
        private String userType;

        @SerializedName("user_f_name")
        @Expose
        @ColumnInfo(name = "user_f_name")
        private String userFName;

        @SerializedName("user_l_name")
        @Expose
        @ColumnInfo(name = "user_l_name")
        private String userLName;

        @SerializedName("user_email")
        @Expose
        @ColumnInfo(name = "user_email")
        private String userEmail;

        @SerializedName("user_device")
        @Expose
        @ColumnInfo(name = "user_device")
        private String userDevice;

        @SerializedName("user_phone")
        @Expose
        @ColumnInfo(name = "user_phone")
        private String userPhone;

        @SerializedName("user_city")
        @Expose
        @ColumnInfo(name = "user_city")
        private String userCity;

        @SerializedName("user_state")
        @Expose
        @ColumnInfo(name = "user_state")
        private String userState;

        @SerializedName("user_state_name")
        @Expose
        @ColumnInfo(name = "user_state_name")
        private String userStateName;

        @SerializedName("user_country")
        @Expose
        @ColumnInfo(name = "user_country")
        private String userCountry;

        @SerializedName("user_country_name")
        @Expose
        @ColumnInfo(name = "user_country_name")
        private String userCountryName;

        @SerializedName("user_zipcode")
        @Expose
        @ColumnInfo(name = "user_zipcode")
        private String userZipcode;

        @SerializedName("user_address")
        @Expose
        @ColumnInfo(name = "user_address")
        private String userAddress;

        @SerializedName("user_longitude")
        @Expose
        @ColumnInfo(name = "user_longitude")
        private String userLongitude;

        @SerializedName("user_latitude")
        @Expose
        @ColumnInfo(name = "user_latitude")
        private String userLatitude;

        @SerializedName("user_dob")
        @Expose
        @ColumnInfo(name = "user_dob")
        private String userDob;

        @SerializedName("user_gender")
        @Expose
        @ColumnInfo(name = "user_gender")
        private String userGender;

        @SerializedName("user_id_file")
        @Expose
        @ColumnInfo(name = "user_id_file")
        private String userIdFile;

        @SerializedName("user_file_title")
        @Expose
        @ColumnInfo(name = "user_file_title")
        private String userFileTitle;

        @SerializedName("org_name")
        @Expose
        @ColumnInfo(name = "org_name")
        private String orgName;

        @SerializedName("org_email")
        @Expose
        @ColumnInfo(name = "org_email")
        private String orgEmail;

        @SerializedName("org_phone")
        @Expose
        @ColumnInfo(name = "org_phone")
        private String orgPhone;

        @SerializedName("org_website")
        @Expose
        @ColumnInfo(name = "org_website")
        private String orgWebsite;

        @SerializedName("org_mission")
        @Expose
        @ColumnInfo(name = "org_mission")
        private String orgMission;

        @SerializedName("org_cause")
        @Expose
        @ColumnInfo(name = "org_cause")
        private String orgCause;

        @SerializedName("org_profile")
        @Expose
        @ColumnInfo(name = "org_profile")
        private String orgProfile;

        @SerializedName("org_country")
        @Expose
        @ColumnInfo(name = "org_country")
        private String orgCountry;

        @SerializedName("org_country_name")
        @Expose
        @ColumnInfo(name = "org_country_name")
        private String orgCountryName;

        @SerializedName("org_state")
        @Expose
        @ColumnInfo(name = "org_state")
        private String orgState;

        @SerializedName("org_state_name")
        @Expose
        @ColumnInfo(name = "org_state_name")
        private String orgStateName;

        @SerializedName("org_city")
        @Expose
        @ColumnInfo(name = "org_city")
        private String orgCity;

        @SerializedName("org_zipcode")
        @Expose
        @ColumnInfo(name = "org_zipcode")
        private String orgZipcode;

        @SerializedName("org_address")
        @Expose
        @ColumnInfo(name = "org_address")
        private String orgAddress;

        @SerializedName("org_logitude")
        @Expose
        @ColumnInfo(name = "org_logitude")
        private String orgLogitude;

        @SerializedName("org_latitude")
        @Expose
        @ColumnInfo(name = "org_latitude")
        private String orgLatitude;

        @SerializedName("org_taxid")
        @Expose
        @ColumnInfo(name = "org_taxid")
        private String orgTaxid;

        @SerializedName("org_501C3")
        @Expose
        @ColumnInfo(name = "org_501C3")
        private String org501C3;

        @SerializedName("org_non_profit")
        @Expose
        @ColumnInfo(name = "org_non_profit")
        private String orgNonProfit;

        @SerializedName("org_service")
        @Expose
        @ColumnInfo(name = "org_service")
        private String orgService;

        @SerializedName("org_target")
        @Expose
        @ColumnInfo(name = "org_target")
        private String orgTarget;

        @SerializedName("org_volunteer_req")
        @Expose
        @ColumnInfo(name = "org_volunteer_req")
        private String orgVolunteerReq;

        @SerializedName("org_min_time")
        @Expose
        @ColumnInfo(name = "org_min_time")
        private String orgMinTime;

        @SerializedName("org_voluteer_num")
        @Expose
        @ColumnInfo(name = "org_voluteer_num")
        private String orgVoluteerNum;

        @SerializedName("org_voluteer_police")
        @Expose
        @ColumnInfo(name = "org_voluteer_police")
        private String orgVoluteerPolice;

        @SerializedName("org_easy_access")
        @Expose
        @ColumnInfo(name = "org_easy_access")
        private String orgEasyAccess;

        @SerializedName("school_id")
        @Expose
        @ColumnInfo(name = "school_id")
        private String schoolId;

        @SerializedName("school_desc")
        @Expose
        @ColumnInfo(name = "school_desc")
        private String schoolDesc;

        @SerializedName("school_student_num")
        @Expose
        @ColumnInfo(name = "school_student_num")
        private String schoolStudentNum;

        @SerializedName("user_status")
        @Expose
        @ColumnInfo(name = "user_status")
        private String userStatus;

        @SerializedName("phone_valid")
        @Expose
        @ColumnInfo(name = "phone_valid")
        private String phoneValid;

        @SerializedName("email_valid")
        @Expose
        @ColumnInfo(name = "email_valid")
        private String emailValid;

        @SerializedName("is_synced")
        @Expose
        @ColumnInfo(name = "is_synced")
        private boolean isSynced;

        @SerializedName("user_grade")
        @Expose
        @ColumnInfo(name = "user_grade")
        private String userGrade;

        @SerializedName("user_grade_number")
        @Expose
        @ColumnInfo(name = "user_grade_number")
        private String userGradeNumber;

        @SerializedName("user_grade_name")
        @Expose
        @ColumnInfo(name = "user_grade_name")
        private String userGradeName;

        @SerializedName("user_ethnicity")
        @Expose
        @ColumnInfo(name = "user_ethnicity")
        private String userEthnicity;

        @SerializedName("ethnicity_code")
        @Expose
        @ColumnInfo(name = "ethnicity_code")
        private String ethnicityCode;

        @SerializedName("ethnicity_name")
        @Expose
        @ColumnInfo(name = "ethnicity_name")
        private String ethnicityName;

        @SerializedName("user_parent_email")
        @Expose
        @ColumnInfo(name = "user_parent_email")
        private String userParentEmail;

        @NonNull
        public String getUserId() {
            return userId;
        }

        public void setUserId(@NonNull String userId) {
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

        public boolean isSynced() {
            return isSynced;
        }

        public void setSynced(boolean synced) {
            isSynced = synced;
        }

        public String getUserGrade() {
            return userGrade;
        }

        public void setUserGrade(String userGrade) {
            this.userGrade = userGrade;
        }

        public String getUserGradeNumber() {
            return userGradeNumber;
        }

        public void setUserGradeNumber(String userGradeNumber) {
            this.userGradeNumber = userGradeNumber;
        }

        public String getUserGradeName() {
            return userGradeName;
        }

        public void setUserGradeName(String userGradeName) {
            this.userGradeName = userGradeName;
        }

        public String getUserEthnicity() {
            return userEthnicity;
        }

        public void setUserEthnicity(String userEthnicity) {
            this.userEthnicity = userEthnicity;
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

        public String getUserParentEmail() {
            return userParentEmail;
        }

        public void setUserParentEmail(String userParentEmail) {
            this.userParentEmail = userParentEmail;
        }
    }
}
