package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidateOtpRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_device")
    @Expose
    private String userDevice;
    @SerializedName("phone_otp")
    @Expose
    private String phoneOtp;

    public ValidateOtpRequest() {
    }

    public ValidateOtpRequest(String userId, String userType, String userDevice, String phoneOtp) {
        super();
        this.userId = userId;
        this.userType = userType;
        this.userDevice = userDevice;
        this.phoneOtp = phoneOtp;
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

    public String getPhoneOtp() {
        return phoneOtp;
    }

    public void setPhoneOtp(String phoneOtp) {
        this.phoneOtp = phoneOtp;
    }

}