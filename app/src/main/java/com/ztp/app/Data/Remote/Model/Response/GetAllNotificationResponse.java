package com.ztp.app.Data.Remote.Model.Response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllNotificationResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<ResData> resData = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

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

        @SerializedName("notification_id")
        @Expose
        private String notificationId;
        @SerializedName("sender_id")
        @Expose
        private String senderId;
        @SerializedName("receiver_id")
        @Expose
        private String receiverId;
        @SerializedName("notification_title")
        @Expose
        private String notificationTitle;
        @SerializedName("notification_msg")
        @Expose
        private String notificationMsg;
        @SerializedName("notification_status")
        @Expose
        private String notificationStatus;
        @SerializedName("notifiation_add_date")
        @Expose
        private String notifiationAddDate;
        @SerializedName("notification_update_date")
        @Expose
        private Object notificationUpdateDate;

        public String getNotificationId() {
            return notificationId;
        }

        public void setNotificationId(String notificationId) {
            this.notificationId = notificationId;
        }

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public String getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(String receiverId) {
            this.receiverId = receiverId;
        }

        public String getNotificationTitle() {
            return notificationTitle;
        }

        public void setNotificationTitle(String notificationTitle) {
            this.notificationTitle = notificationTitle;
        }

        public String getNotificationMsg() {
            return notificationMsg;
        }

        public void setNotificationMsg(String notificationMsg) {
            this.notificationMsg = notificationMsg;
        }

        public String getNotificationStatus() {
            return notificationStatus;
        }

        public void setNotificationStatus(String notificationStatus) {
            this.notificationStatus = notificationStatus;
        }

        public String getNotifiationAddDate() {
            return notifiationAddDate;
        }

        public void setNotifiationAddDate(String notifiationAddDate) {
            this.notifiationAddDate = notifiationAddDate;
        }

        public Object getNotificationUpdateDate() {
            return notificationUpdateDate;
        }

        public void setNotificationUpdateDate(Object notificationUpdateDate) {
            this.notificationUpdateDate = notificationUpdateDate;
        }

    }

}

