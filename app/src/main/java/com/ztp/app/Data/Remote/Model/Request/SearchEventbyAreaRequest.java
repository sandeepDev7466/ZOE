package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchEventbyAreaRequest {



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
    private String search_city;
    @SerializedName("search_state")
    @Expose
    private String search_state;
    @SerializedName("search_postcode")
    @Expose
    private String search_postcode;
    @SerializedName("search_event_type")
    @Expose
    private String search_event_type;
    @SerializedName("search_org")
    @Expose
    private String search_org;

    public SearchEventbyAreaRequest() {
    }

    public SearchEventbyAreaRequest(String searchKeyword, String seachRowNumber, String searchPageSize, String search_city,String search_state, String search_postcode,String search_event_type,String search_org) {
        this.searchKeyword = searchKeyword;
        this.seachRowNumber = seachRowNumber;
        this.searchPageSize = searchPageSize;
        this.search_city = search_city;
        this.search_state = search_state;
        this.search_postcode = search_postcode;
        this.search_event_type = search_event_type;
        this.search_org = search_org;
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

    public String getSearch_city() {
        return search_city;
    }

    public void setSearch_city(String search_city) {
        this.search_city = search_city;
    }

    public String getSearch_state() {
        return search_state;
    }

    public void setSearch_state(String search_state) {
        this.search_state = search_state;
    }

    public String getSearch_postcode() {
        return search_postcode;
    }

    public void setSearch_postcode(String search_postcode) {
        this.search_postcode = search_postcode;
    }

    public String getSearch_event_type() {
        return search_event_type;
    }

    public void setSearch_event_type(String search_event_type) {
        this.search_event_type = search_event_type;
    }

    public String getSearch_org() {
        return search_org;
    }

    public void setSearch_org(String search_org) {
        this.search_org = search_org;
    }
}