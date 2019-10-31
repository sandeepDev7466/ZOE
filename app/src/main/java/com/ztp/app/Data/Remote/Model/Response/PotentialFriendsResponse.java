package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PotentialFriendsResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<ResDatum> resData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public List<ResDatum> getResData() {
        return resData;
    }

    public void setResData(List<ResDatum> resData) {
        this.resData = resData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public class ResDatum {

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
        @SerializedName("user_profile_pic")
        @Expose
        private String userProfilePic;
        @SerializedName("user_cover_pic")
        @Expose
        private String userCoverPic;
        @SerializedName("user_id_file")
        @Expose
        private String userIdFile;
        @SerializedName("user_file_title")
        @Expose
        private String userFileTitle;
        @SerializedName("school_user_id")
        @Expose
        private String schoolUserId;
        @SerializedName("school_id")
        @Expose
        private String schoolId;
        @SerializedName("school_name")
        @Expose
        private String schoolName;
        @SerializedName("user_grade")
        @Expose
        private String userGrade;
        @SerializedName("user_grade_number")
        @Expose
        private String userGradeNumber;
        @SerializedName("user_grade_name")
        @Expose
        private String userGradeName;
        @SerializedName("user_ethnicity")
        @Expose
        private String userEthnicity;
        @SerializedName("ethnicity_code")
        @Expose
        private String ethnicityCode;
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
        @SerializedName("map_id")
        @Expose
        private String mapId;
        @SerializedName("map_status")
        @Expose
        private String mapStatus;


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

        public String getUserProfilePic() {
            return userProfilePic;
        }

        public void setUserProfilePic(String userProfilePic) {
            this.userProfilePic = userProfilePic;
        }

        public String getUserCoverPic() {
            return userCoverPic;
        }

        public void setUserCoverPic(String userCoverPic) {
            this.userCoverPic = userCoverPic;
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

        public String getSchoolUserId() {
            return schoolUserId;
        }

        public void setSchoolUserId(String schoolUserId) {
            this.schoolUserId = schoolUserId;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
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

        public String getMapId() {
            return mapId;
        }

        public void setMapId(String mapId) {
            this.mapId = mapId;
        }

        public String getMapStatus() {
            return mapStatus;
        }

        public void setMapStatus(String mapStatus) {
            this.mapStatus = mapStatus;
        }
    }
}