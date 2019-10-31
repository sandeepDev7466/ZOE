package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CSOQuestionResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_data")
    @Expose
    private ResData resData;
    @SerializedName("res_status")
    @Expose
    private String resStatus;

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public ResData getResData() {
        return resData;
    }

    public void setResData(ResData resData) {
        this.resData = resData;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }
    public class ResData {

        @SerializedName("question1_data")
        @Expose
        private List<Question1Data> question1Data = null;
        @SerializedName("question2_data")
        @Expose
        private List<Question2Data> question2Data = null;
        @SerializedName("question3_data")
        @Expose
        private List<Question3Data> question3Data = null;

        public List<Question1Data> getQuestion1Data() {
            return question1Data;
        }

        public void setQuestion1Data(List<Question1Data> question1Data) {
            this.question1Data = question1Data;
        }

        public List<Question2Data> getQuestion2Data() {
            return question2Data;
        }

        public void setQuestion2Data(List<Question2Data> question2Data) {
            this.question2Data = question2Data;
        }

        public List<Question3Data> getQuestion3Data() {
            return question3Data;
        }

        public void setQuestion3Data(List<Question3Data> question3Data) {
            this.question3Data = question3Data;
        }
    }

    public class Question1Data {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("question_id")
        @Expose
        private String questionId;
        @SerializedName("answer_details")
        @Expose
        private String answerDetails;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("question_add_date")
        @Expose
        private String questionAddDate;
        @SerializedName("question_update_date")
        @Expose
        private Object questionUpdateDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getAnswerDetails() {
            return answerDetails;
        }

        public void setAnswerDetails(String answerDetails) {
            this.answerDetails = answerDetails;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getQuestionAddDate() {
            return questionAddDate;
        }

        public void setQuestionAddDate(String questionAddDate) {
            this.questionAddDate = questionAddDate;
        }

        public Object getQuestionUpdateDate() {
            return questionUpdateDate;
        }

        public void setQuestionUpdateDate(Object questionUpdateDate) {
            this.questionUpdateDate = questionUpdateDate;
        }
    }

    public class Question2Data {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("question_id")
        @Expose
        private String questionId;
        @SerializedName("answer_details")
        @Expose
        private String answerDetails;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("question_add_date")
        @Expose
        private String questionAddDate;
        @SerializedName("question_update_date")
        @Expose
        private Object questionUpdateDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getAnswerDetails() {
            return answerDetails;
        }

        public void setAnswerDetails(String answerDetails) {
            this.answerDetails = answerDetails;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getQuestionAddDate() {
            return questionAddDate;
        }

        public void setQuestionAddDate(String questionAddDate) {
            this.questionAddDate = questionAddDate;
        }

        public Object getQuestionUpdateDate() {
            return questionUpdateDate;
        }

        public void setQuestionUpdateDate(Object questionUpdateDate) {
            this.questionUpdateDate = questionUpdateDate;
        }
    }
    public class Question3Data {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("question_id")
        @Expose
        private String questionId;
        @SerializedName("answer_details")
        @Expose
        private String answerDetails;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("question_add_date")
        @Expose
        private String questionAddDate;
        @SerializedName("question_update_date")
        @Expose
        private Object questionUpdateDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getAnswerDetails() {
            return answerDetails;
        }

        public void setAnswerDetails(String answerDetails) {
            this.answerDetails = answerDetails;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getQuestionAddDate() {
            return questionAddDate;
        }

        public void setQuestionAddDate(String questionAddDate) {
            this.questionAddDate = questionAddDate;
        }

        public Object getQuestionUpdateDate() {
            return questionUpdateDate;
        }

        public void setQuestionUpdateDate(Object questionUpdateDate) {
            this.questionUpdateDate = questionUpdateDate;
        }
    }

}
