package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CsoRegisterRequestStep_1 {

    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_device")
    @Expose
    private String userDevice;
    @SerializedName("school_id")
    @Expose
    private String schoolId;
    @SerializedName("user_f_name")
    @Expose
    private String userFName;
    @SerializedName("user_l_name")
    @Expose
    private String userLName;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_phone")
    @Expose
    private String userPhone;
    @SerializedName("user_country")
    @Expose
    private String userCountry;
    @SerializedName("user_state")
    @Expose
    private String userState;
    @SerializedName("user_city")
    @Expose
    private String userCity;
    @SerializedName("user_zipcode")
    @Expose
    private String userZipcode;
    @SerializedName("user_address")
    @Expose
    private String userAddress;
    @SerializedName("user_dob")
    @Expose
    private String userDob;
    @SerializedName("user_gender")
    @Expose
    private String userGender;
    @SerializedName("user_pass")
    @Expose
    private String userPass;

    public CsoRegisterRequestStep_1() {
    }

    public CsoRegisterRequestStep_1(String userType, String userDevice, String schoolId, String userFName, String userLName, String userEmail, String userPhone, String userCountry, String userState, String userCity, String userZipcode, String userAddress, String userDob, String userGender, String userPass) {
        super();
        this.userType = userType;
        this.userDevice = userDevice;
        this.schoolId = schoolId;
        this.userFName = userFName;
        this.userLName = userLName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userCountry = userCountry;
        this.userState = userState;
        this.userCity = userCity;
        this.userZipcode = userZipcode;
        this.userAddress = userAddress;
        this.userDob = userDob;
        this.userGender = userGender;
        this.userPass = userPass;
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

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
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

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

}