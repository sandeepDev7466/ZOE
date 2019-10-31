package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchEventRequest {


    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("search_keyword")
    @Expose
    private String searchKeyword;
    @SerializedName("seach_row_number")
    @Expose
    private String seachRowNumber;
    @SerializedName("search_page_size")
    @Expose
    private String searchPageSize;
    @SerializedName("current_date")
    @Expose
    private String currentDate;

    public SearchEventRequest() {
    }

    public SearchEventRequest(String userId, String searchKeyword, String seachRowNumber, String searchPageSize, String currentDate) {
        this.userId = userId;
        this.searchKeyword = searchKeyword;
        this.seachRowNumber = seachRowNumber;
        this.searchPageSize = searchPageSize;
        this.currentDate = currentDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getSeachRowNumber() {
        return seachRowNumber;
    }

    public void setSeachRowNumber(String seachRowNumber) {
        this.seachRowNumber = seachRowNumber;
    }

    public String getSearchPageSize() {
        return searchPageSize;
    }

    public void setSearchPageSize(String searchPageSize) {
        this.searchPageSize = searchPageSize;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}