package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CsoRegisterRequestStep_3 {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_device")
    @Expose
    private String userDevice;
    @SerializedName("org_nonprofit")
    @Expose
    private String orgNonprofit;
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
    @SerializedName("org_volunteer_num")
    @Expose
    private String orgVolunteerNum;
    @SerializedName("org_volunteer_police")
    @Expose
    private String orgVolunteerPolice;
    @SerializedName("org_easy_access")
    @Expose
    private String orgEasyAccess;
    @SerializedName("org_501C3")
    @Expose
    private String org_501C3;


    public CsoRegisterRequestStep_3() {
    }

    public CsoRegisterRequestStep_3(String userId, String userType, String userDevice, String orgNonprofit, String orgService, String orgTarget, String orgVolunteerReq, String orgMinTime, String orgVolunteerNum, String orgVolunteerPolice, String orgEasyAccess, String org_501C3) {
        this.userId = userId;
        this.userType = userType;
        this.userDevice = userDevice;
        this.orgNonprofit = orgNonprofit;
        this.orgService = orgService;
        this.orgTarget = orgTarget;
        this.orgVolunteerReq = orgVolunteerReq;
        this.orgMinTime = orgMinTime;
        this.orgVolunteerNum = orgVolunteerNum;
        this.orgVolunteerPolice = orgVolunteerPolice;
        this.orgEasyAccess = orgEasyAccess;
        this.org_501C3 = org_501C3;
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

    public String getOrgNonprofit() {
        return orgNonprofit;
    }

    public void setOrgNonprofit(String orgNonprofit) {
        this.orgNonprofit = orgNonprofit;
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

    public String getOrgVolunteerNum() {
        return orgVolunteerNum;
    }

    public void setOrgVolunteerNum(String orgVolunteerNum) {
        this.orgVolunteerNum = orgVolunteerNum;
    }

    public String getOrgVolunteerPolice() {
        return orgVolunteerPolice;
    }

    public void setOrgVolunteerPolice(String orgVolunteerPolice) {
        this.orgVolunteerPolice = orgVolunteerPolice;
    }

    public String getOrgEasyAccess() {
        return orgEasyAccess;
    }

    public void setOrgEasyAccess(String orgEasyAccess) {
        this.orgEasyAccess = orgEasyAccess;
    }

    public String getOrg_501C3() {
        return org_501C3;
    }

    public void setOrg_501C3(String org_501C3) {
        this.org_501C3 = org_501C3;
    }
}