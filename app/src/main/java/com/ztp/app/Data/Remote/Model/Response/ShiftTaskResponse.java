

package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShiftTaskResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private List<ShiftTask> shiftTaskList = null;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public List<ShiftTask> getShiftTaskList() {
        return shiftTaskList;
    }

    public void setShiftTaskList(List<ShiftTask> shiftTaskList) {
        this.shiftTaskList = shiftTaskList;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public class ShiftTask {

        @SerializedName("shift_task_id")
        @Expose
        private String shiftTaskId;
        @SerializedName("cso_id")
        @Expose
        private String csoId;
        @SerializedName("shift_task_type")
        @Expose
        private String shiftTaskType;
        @SerializedName("shift_task_name")
        @Expose
        private String shiftTaskName;
        @SerializedName("shift_task_status")
        @Expose
        private String shiftTaskStatus;
        @SerializedName("shift_task_add_date")
        @Expose
        private String shiftTaskAddDate;
        @SerializedName("shift_task_update_date")
        @Expose
        private String shiftTaskUpdateDate;

        public String getShiftTaskId() {
            return shiftTaskId;
        }

        public void setShiftTaskId(String shiftTaskId) {
            this.shiftTaskId = shiftTaskId;
        }

        public String getCsoId() {
            return csoId;
        }

        public void setCsoId(String csoId) {
            this.csoId = csoId;
        }

        public String getShiftTaskType() {
            return shiftTaskType;
        }

        public void setShiftTaskType(String shiftTaskType) {
            this.shiftTaskType = shiftTaskType;
        }

        public String getShiftTaskName() {
            return shiftTaskName;
        }

        public void setShiftTaskName(String shiftTaskName) {
            this.shiftTaskName = shiftTaskName;
        }

        public String getShiftTaskStatus() {
            return shiftTaskStatus;
        }

        public void setShiftTaskStatus(String shiftTaskStatus) {
            this.shiftTaskStatus = shiftTaskStatus;
        }

        public String getShiftTaskAddDate() {
            return shiftTaskAddDate;
        }

        public void setShiftTaskAddDate(String shiftTaskAddDate) {
            this.shiftTaskAddDate = shiftTaskAddDate;
        }

        public String getShiftTaskUpdateDate() {
            return shiftTaskUpdateDate;
        }

        public void setShiftTaskUpdateDate(String shiftTaskUpdateDate) {
            this.shiftTaskUpdateDate = shiftTaskUpdateDate;
        }

    }

}



