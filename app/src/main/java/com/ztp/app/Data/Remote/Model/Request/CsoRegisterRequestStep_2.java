package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CsoRegisterRequestStep_2 {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_device")
    @Expose
    private String userDevice;
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
    @SerializedName("org_state")
    @Expose
    private String orgState;
    @SerializedName("org_city")
    @Expose
    private String orgCity;
    @SerializedName("org_zipcode")
    @Expose
    private String orgZipcode;
    @SerializedName("org_address")
    @Expose
    private String orgAddress;
    @SerializedName("org_taxid")
    @Expose
    private String orgTaxid;
    @SerializedName("user_id_file")
    @Expose
    private String userIdFile;
    @SerializedName("user_file_title")
    @Expose
    private String userFileTitle;
    @SerializedName("org_longitude")
    @Expose
    private String orgLongitude;
    @SerializedName("org_latitude")
    @Expose
    private String orgLatitude;

    public CsoRegisterRequestStep_2() {
    }

    public CsoRegisterRequestStep_2(String userId, String userType, String userDevice, String orgName, String orgEmail, String orgPhone, String orgWebsite, String orgMission, String orgCause, String orgProfile, String orgCountry, String orgState, String orgCity, String orgZipcode, String orgAddress, String orgTaxid, String userIdFile, String userFileTitle, String orgLongitude, String orgLatitude) {
        super();
        this.userId = userId;
        this.userType = userType;
        this.userDevice = userDevice;
        this.orgName = orgName;
        this.orgEmail = orgEmail;
        this.orgPhone = orgPhone;
        this.orgWebsite = orgWebsite;
        this.orgMission = orgMission;
        this.orgCause = orgCause;
        this.orgProfile = orgProfile;
        this.orgCountry = orgCountry;
        this.orgState = orgState;
        this.orgCity = orgCity;
        this.orgZipcode = orgZipcode;
        this.orgAddress = orgAddress;
        this.orgTaxid = orgTaxid;
        this.userIdFile = userIdFile;
        this.userFileTitle = userFileTitle;
        this.orgLongitude = orgLongitude;
        this.orgLatitude = orgLatitude;
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

    public String getUserDevice() {
        return userDevice;
    }

    public void setUserDevice(String userDevice) {
        this.userDevice = userDevice;
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

    public String getOrgState() {
        return orgState;
    }

    public void setOrgState(String orgState) {
        this.orgState = orgState;
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

    public String getOrgTaxid() {
        return orgTaxid;
    }

    public void setOrgTaxid(String orgTaxid) {
        this.orgTaxid = orgTaxid;
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

    public String getOrgLongitude() {
        return orgLongitude;
    }

    public void setOrgLongitude(String orgLongitude) {
        this.orgLongitude = orgLongitude;
    }

    public String getOrgLatitude() {
        return orgLatitude;
    }

    public void setOrgLatitude(String orgLatitude) {
        this.orgLatitude = orgLatitude;
    }

}