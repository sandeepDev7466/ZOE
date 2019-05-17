package com.ztp.app.Data.Local.SharedPrefrence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private String SHARED_PREF = "Ztp";private static SharedPref instance;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private String isLogin = "isLogin";
    private String firstName = "firstName";
    private String lastName = "lastName";
    private String userType = "userType";
    private String userId = "userId";
    private String firstRun = "firstRun";
    private String themeApp = "themeApp";
    private String email = "email";
    private String language = "language";
    private String otp = "otp";
    private String isChanged = "isChanged";
    private String eventImageBase64 = "eventImageBase64";
    private String setLocation = "setLocation";

    public static SharedPref getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPref(context);
        }
        return instance;
    }

    @SuppressLint("CommitPrefEdits")
    private SharedPref(Context context) {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean getIsLogin() {
        return sharedPreferences.getBoolean(isLogin, false);
    }

    public void setIsLogin(boolean status) {
        editor.putBoolean(isLogin, status).apply();
    }


    public String getFirstName() {
        return sharedPreferences.getString(firstName, "");
    }

    public void setFirstName(String first_name) {
        editor.putString(firstName, first_name).apply();
    }

    public String getLastName() {
        return sharedPreferences.getString(lastName, "");
    }

    public void setLastName(String last_name) {
        editor.putString(lastName, last_name).apply();
    }

    public String getUserType() {
        return sharedPreferences.getString(userType, "");
    }

    public void setUserType(String user_type) {
        editor.putString(userType, user_type).apply();
    }

    public boolean getFirstRun() {
        return sharedPreferences.getBoolean(firstRun, false);
    }

    public void setFirstRun(boolean status) {
            editor.putBoolean(firstRun, status).apply();
    }

    public boolean getTheme() {
        return sharedPreferences.getBoolean(themeApp, false);
    }

    public void setTheme(boolean theme) {
        editor.putBoolean(themeApp, theme).apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(email, "");
    }

    public void setEmail(String email_str) {
        editor.putString(email, email_str).apply();
    }

    public boolean getLanguage() {
        return sharedPreferences.getBoolean(language, false);
    }

    public void setLanguage(boolean language_txt) {
        editor.putBoolean(language, language_txt).apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(userId, "");
    }

    public void setUserId(String user_id) {
        editor.putString(userId, user_id).apply();
    }

    public String getOtp() {
        return sharedPreferences.getString(otp, "");
    }

    public void setOtp(String otp_txt) {
        editor.putString(otp, otp_txt).apply();
    }

    public boolean getIsChanged() {
        return sharedPreferences.getBoolean(isChanged, false);
    }

    public void setIsChanged(boolean isChange) {
        editor.putBoolean(isChanged, isChange).apply();
    }

    public String getEventImageBase64() {
       return sharedPreferences.getString(eventImageBase64, "");
    }

    public void setEventImageBase64(String eventimagebase64) {
        editor.putString(eventImageBase64, eventimagebase64).apply();
    }

    public boolean getLocation() {
        return sharedPreferences.getBoolean(setLocation, false);
    }

    public void setLocation(boolean location) {
        editor.putBoolean(setLocation, location).apply();
    }
}
