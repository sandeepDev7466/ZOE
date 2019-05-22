package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


    public class CSOAllResponse {

        @SerializedName("api_res_key")
        @Expose
        private String apiResKey;
        @SerializedName("res_data")
        @Expose
        private List<ResData> resData = null;
        @SerializedName("res_status")
        @Expose
        private String resStatus;

        public CSOAllResponse() {
        }

        public CSOAllResponse(String apiResKey, List<ResData> resData, String resStatus) {
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

        public List<ResData> getResData() {
            return resData;
        }

        public void setResData(List<ResData> resData) {
            this.resData = resData;
        }

        public String getResStatus() {
            return resStatus;
        }

        public void setResStatus(String resStatus) {
            this.resStatus = resStatus;
        }



        public class ResData {

            @SerializedName("map_id")
            @Expose
            private String mapId;
            @SerializedName("user_id")
            @Expose
            private String userId;
            @SerializedName("event_id")
            @Expose
            private String eventId;
            @SerializedName("cso_id")
            @Expose
            private String csoId;
            @SerializedName("shift_id")
            @Expose
            private String shiftId;
            @SerializedName("attend_present")
            @Expose
            private String attendPresent;
            @SerializedName("attend_in_time")
            @Expose
            private String attendInTime;
            @SerializedName("attend_out_time")
            @Expose
            private String attendOutTime;
            @SerializedName("attend_hours")
            @Expose
            private String attendHours;
            @SerializedName("attend_cso_grade")
            @Expose
            private String attendCsoGrade;
            @SerializedName("attend_grade_date")
            @Expose
            private String attendGradeDate;
            @SerializedName("attend_cso_remarks")
            @Expose
            private String attendCsoRemarks;
            @SerializedName("attend_remarks_date")
            @Expose
            private String attendRemarksDate;
            @SerializedName("attend_rank")
            @Expose
            private String attendRank;
            @SerializedName("attend_rate")
            @Expose
            private String attendRate;
            @SerializedName("attend_rate_remarks")
            @Expose
            private String attendRateRemarks;
            @SerializedName("attend_rate_date")
            @Expose
            private String attendRateDate;
            @SerializedName("map_status")
            @Expose
            private String mapStatus;
            @SerializedName("map_rank_comment")
            @Expose
            private String mapRankComment;
            @SerializedName("map_add_date")
            @Expose
            private String mapAddDate;
            @SerializedName("map_update_date")
            @Expose
            private String mapUpdateDate;
            @SerializedName("event_heading")
            @Expose
            private String eventHeading;
            @SerializedName("shift_date")
            @Expose
            private String shiftDate;
            @SerializedName("shift_start_time")
            @Expose
            private String shiftStartTime;
            @SerializedName("shift_end_time")
            @Expose
            private String shiftEndTime;
            @SerializedName("shift_rank")
            @Expose
            private String shiftRank;
            @SerializedName("shift_task")
            @Expose
            private String shiftTask;
            @SerializedName("user_f_name")
            @Expose
            private String userFName;
            @SerializedName("user_l_name")
            @Expose
            private String userLName;
            @SerializedName("user_email")
            @Expose
            private String userEmail;

            public ResData() {
            }

            public ResData(String mapId, String userId, String eventId, String csoId, String shiftId, String attendPresent, String attendInTime, String attendOutTime, String attendHours, String attendCsoGrade, String attendGradeDate, String attendCsoRemarks, String attendRemarksDate, String attendRank, String attendRate, String attendRateRemarks, String attendRateDate, String mapStatus, String mapRankComment, String mapAddDate, String mapUpdateDate, String eventHeading, String shiftDate, String shiftStartTime, String shiftEndTime, String shiftRank, String shiftTask, String userFName, String userLName, String userEmail) {
                super();
                this.mapId = mapId;
                this.userId = userId;
                this.eventId = eventId;
                this.csoId = csoId;
                this.shiftId = shiftId;
                this.attendPresent = attendPresent;
                this.attendInTime = attendInTime;
                this.attendOutTime = attendOutTime;
                this.attendHours = attendHours;
                this.attendCsoGrade = attendCsoGrade;
                this.attendGradeDate = attendGradeDate;
                this.attendCsoRemarks = attendCsoRemarks;
                this.attendRemarksDate = attendRemarksDate;
                this.attendRank = attendRank;
                this.attendRate = attendRate;
                this.attendRateRemarks = attendRateRemarks;
                this.attendRateDate = attendRateDate;
                this.mapStatus = mapStatus;
                this.mapRankComment = mapRankComment;
                this.mapAddDate = mapAddDate;
                this.mapUpdateDate = mapUpdateDate;
                this.eventHeading = eventHeading;
                this.shiftDate = shiftDate;
                this.shiftStartTime = shiftStartTime;
                this.shiftEndTime = shiftEndTime;
                this.shiftRank = shiftRank;
                this.shiftTask = shiftTask;
                this.userFName = userFName;
                this.userLName = userLName;
                this.userEmail = userEmail;
            }

            public String getMapId() {
                return mapId;
            }

            public void setMapId(String mapId) {
                this.mapId = mapId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getEventId() {
                return eventId;
            }

            public void setEventId(String eventId) {
                this.eventId = eventId;
            }

            public String getCsoId() {
                return csoId;
            }

            public void setCsoId(String csoId) {
                this.csoId = csoId;
            }

            public String getShiftId() {
                return shiftId;
            }

            public void setShiftId(String shiftId) {
                this.shiftId = shiftId;
            }

            public String getAttendPresent() {
                return attendPresent;
            }

            public void setAttendPresent(String attendPresent) {
                this.attendPresent = attendPresent;
            }

            public String getAttendInTime() {
                return attendInTime;
            }

            public void setAttendInTime(String attendInTime) {
                this.attendInTime = attendInTime;
            }

            public String getAttendOutTime() {
                return attendOutTime;
            }

            public void setAttendOutTime(String attendOutTime) {
                this.attendOutTime = attendOutTime;
            }

            public String getAttendHours() {
                return attendHours;
            }

            public void setAttendHours(String attendHours) {
                this.attendHours = attendHours;
            }

            public String getAttendCsoGrade() {
                return attendCsoGrade;
            }

            public void setAttendCsoGrade(String attendCsoGrade) {
                this.attendCsoGrade = attendCsoGrade;
            }

            public String getAttendGradeDate() {
                return attendGradeDate;
            }

            public void setAttendGradeDate(String attendGradeDate) {
                this.attendGradeDate = attendGradeDate;
            }

            public String getAttendCsoRemarks() {
                return attendCsoRemarks;
            }

            public void setAttendCsoRemarks(String attendCsoRemarks) {
                this.attendCsoRemarks = attendCsoRemarks;
            }

            public String getAttendRemarksDate() {
                return attendRemarksDate;
            }

            public void setAttendRemarksDate(String attendRemarksDate) {
                this.attendRemarksDate = attendRemarksDate;
            }

            public String getAttendRank() {
                return attendRank;
            }

            public void setAttendRank(String attendRank) {
                this.attendRank = attendRank;
            }

            public String getAttendRate() {
                return attendRate;
            }

            public void setAttendRate(String attendRate) {
                this.attendRate = attendRate;
            }

            public String getAttendRateRemarks() {
                return attendRateRemarks;
            }

            public void setAttendRateRemarks(String attendRateRemarks) {
                this.attendRateRemarks = attendRateRemarks;
            }

            public String getAttendRateDate() {
                return attendRateDate;
            }

            public void setAttendRateDate(String attendRateDate) {
                this.attendRateDate = attendRateDate;
            }

            public String getMapStatus() {
                return mapStatus;
            }

            public void setMapStatus(String mapStatus) {
                this.mapStatus = mapStatus;
            }

            public String getMapRankComment() {
                return mapRankComment;
            }

            public void setMapRankComment(String mapRankComment) {
                this.mapRankComment = mapRankComment;
            }

            public String getMapAddDate() {
                return mapAddDate;
            }

            public void setMapAddDate(String mapAddDate) {
                this.mapAddDate = mapAddDate;
            }

            public String getMapUpdateDate() {
                return mapUpdateDate;
            }

            public void setMapUpdateDate(String mapUpdateDate) {
                this.mapUpdateDate = mapUpdateDate;
            }

            public String getEventHeading() {
                return eventHeading;
            }

            public void setEventHeading(String eventHeading) {
                this.eventHeading = eventHeading;
            }

            public String getShiftDate() {
                return shiftDate;
            }

            public void setShiftDate(String shiftDate) {
                this.shiftDate = shiftDate;
            }

            public String getShiftStartTime() {
                return shiftStartTime;
            }

            public void setShiftStartTime(String shiftStartTime) {
                this.shiftStartTime = shiftStartTime;
            }

            public String getShiftEndTime() {
                return shiftEndTime;
            }

            public void setShiftEndTime(String shiftEndTime) {
                this.shiftEndTime = shiftEndTime;
            }

            public String getShiftRank() {
                return shiftRank;
            }

            public void setShiftRank(String shiftRank) {
                this.shiftRank = shiftRank;
            }

            public String getShiftTask() {
                return shiftTask;
            }

            public void setShiftTask(String shiftTask) {
                this.shiftTask = shiftTask;
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

        }
}