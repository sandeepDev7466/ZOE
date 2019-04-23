package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_pass")
    @Expose
    private String userPass;

    @SerializedName("user_device")
    @Expose
    private String userDevice;

    @SerializedName("user_current_login")
    @Expose
    private String userCurrentLogin;



    public LoginRequest() {
    }

    public LoginRequest(String userEmail, String userPass, String userDevice, String userCurrentLogin) {
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userDevice = userDevice;
        this.userCurrentLogin = userCurrentLogin;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserDevice() {
        return userDevice;
    }

    public void setUserDevice(String userDevice) {
        this.userDevice = userDevice;
    }

    public String getUserCurrentLogin() {
        return userCurrentLogin;
    }

    public void setUserCurrentLogin(String userCurrentLogin) {
        this.userCurrentLogin = userCurrentLogin;
    }
}