package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchCSORequest {

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
    @SerializedName("search_city")
    @Expose
    private String searchCity;
    @SerializedName("search_state")
    @Expose
    private String searchState;
    @SerializedName("search_postcode")
    @Expose
    private String searchPostcode;
    @SerializedName("search_org")
    @Expose
    private String searchOrg;
    @SerializedName("search_event_type")
    @Expose
    private String searchEventType;

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

    public String getSearchCity() {
        return searchCity;
    }

    public void setSearchCity(String searchCity) {
        this.searchCity = searchCity;
    }

    public String getSearchState() {
        return searchState;
    }

    public void setSearchState(String searchState) {
        this.searchState = searchState;
    }

    public String getSearchPostcode() {
        return searchPostcode;
    }

    public void setSearchPostcode(String searchPostcode) {
        this.searchPostcode = searchPostcode;
    }

    public String getSearchOrg() {
        return searchOrg;
    }

    public void setSearchOrg(String searchOrg) {
        this.searchOrg = searchOrg;
    }

    public String getSearchEventType() {
        return searchEventType;
    }

    public void setSearchEventType(String searchEventType) {
        this.searchEventType = searchEventType;
    }

}