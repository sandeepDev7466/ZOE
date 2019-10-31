package com.ztp.app.Data.Local.SharedPrefrence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

public class SharedPref {
    private String SHARED_PREF = "Ztp";
    private static SharedPref instance;
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
    private String pushNotification = "pushNotification";
    private String otp = "otp";
    private String isChanged = "isChanged";
    private String eventImageBase64 = "eventImageBase64";
    private String setLocation = "setLocation";
    private String eventLatitude = "eventLatitude";
    private String eventLongitude = "eventLongitude";
    private String eventAddress = "eventAddress";
    private String eventCity = "eventCity";
    private String eventPostalCode = "eventPostalCode";
    private String profileImage = "profileImage";
    private String coverImage = "coverImage";
    private String unreadPushNotif = "unreadPushNotif";
    private String timezone = "timezone";
    private String daylight = "daylight";
    private String volRank = "volRank";
    private String volHours = "volHours";
    private String userStatus = "userStatus";
    private String address = "address";
    private String city = "city";
    private String postalCode = "postalCode";
    private String country = "country";
    private String state = "state";
    private String phone = "phone";

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

    public String getEventLatitude() {
        return sharedPreferences.getString(eventLatitude, "0.00");
    }

    public void setEventLatitude(String event_latitude) {
        editor.putString(eventLatitude, event_latitude).apply();
    }

    public String getEventLongitude() {
        return sharedPreferences.getString(eventLongitude, "0.00");
    }

    public void setEventLongitude(String event_longitude) {
        editor.putString(eventLongitude, event_longitude).apply();
    }

    public String getEventAddress() {
        return sharedPreferences.getString(eventAddress, "");
    }

    public void setEventAddress(String event_address) {
        editor.putString(eventAddress, event_address).apply();
    }

    public String getEventCity() {
        return sharedPreferences.getString(eventCity, "");
    }

    public void setEventCity(String event_city) {
        editor.putString(eventCity, event_city).apply();
    }
    public String getEventPostalCode() {
        return sharedPreferences.getString(eventPostalCode, "");
    }

    public void setEventPostalCode(String event_postal_code) {
        editor.putString(eventPostalCode, event_postal_code).apply();
    }

    public boolean getPushNotification() {
        return sharedPreferences.getBoolean(pushNotification, true);
    }

    public void setPushNotification(boolean push) {
        editor.putBoolean(pushNotification, push).apply();
    }
    public String getCoverImage() {
        return sharedPreferences.getString(coverImage, "");
    }

    public void setCoverImage(String coverimage) {
        editor.putString(coverImage, coverimage).apply();
    }
    public String getProfileImage() {
        return sharedPreferences.getString(profileImage, "");
    }

    public void setProfileImage(String profileimage) {
        editor.putString(profileImage, profileimage).apply();
    }

    public String getUnreadPushNotif() {
        return sharedPreferences.getString(unreadPushNotif, "0");
    }

    public void setUnreadPushNotif(String count) {
        editor.putString(unreadPushNotif, count).apply();
    }

    public String getTimezone() {
        return sharedPreferences.getString(timezone, "");
    }

    public void setTimezone(String time_zone) {
        editor.putString(timezone, time_zone).apply();
    }

    public String getDaylight() {
        return sharedPreferences.getString(daylight, "");
    }

    public void setDaylight(String day_light) {
        editor.putString(daylight, day_light).apply();
    }

    public String getVolRank() {
        return sharedPreferences.getString(volRank, "");
    }

    public void setVolRank(String vol_rank) {
        editor.putString(volRank, vol_rank).apply();
    }

    public String getVolHours() {
        return sharedPreferences.getString(volHours, "");
    }

    public void setVolHours(String vol_hours) {
        editor.putString(volHours, vol_hours).apply();
    }

    public String getAddress() {
        return sharedPreferences.getString(address, "");
    }

    public void setAddress(String add) {
        editor.putString(address, add).apply();
    }

    public String getCity() {
        return sharedPreferences.getString(city, "");
    }

    public void setCity(String cty) {
        editor.putString(city, cty).apply();
    }

    public String getPostalCode() {
        return sharedPreferences.getString(postalCode, "");
    }

    public void setPostalCode(String postCode) {
        editor.putString(postalCode, postCode).apply();
    }

    public String getCountry() {
        return sharedPreferences.getString(country, "");
    }

    public void setCountry(String cntry) {
        editor.putString(country, cntry).apply();
    }

    public String getState() {
        return sharedPreferences.getString(state, "");
    }

    public void setState(String state1) {
        editor.putString(state, state1).apply();
    }

    public String getPhone() {
        return sharedPreferences.getString(phone, "");
    }

    public void setPhone(String phn) {
        editor.putString(phone, phn).apply();
    }

    public String getUserStatus() {
        return sharedPreferences.getString(userStatus, "");
    }

    public void setUserStatus(String user_status) {
        editor.putString(userStatus, user_status).apply();
    }

}
