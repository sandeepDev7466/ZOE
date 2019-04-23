package com.ztp.app.Data.Remote.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlogSearchRequest {

    @SerializedName("blog_keyword")
    @Expose
    private String blogKeyword;
    @SerializedName("blog_page_index")
    @Expose
    private String blogPageIndex;
    @SerializedName("blog_page_size")
    @Expose
    private String blogPageSize;

    public BlogSearchRequest() {
    }

    public BlogSearchRequest(String blogKeyword, String blogPageIndex, String blogPageSize) {
        super();
        this.blogKeyword = blogKeyword;
        this.blogPageIndex = blogPageIndex;
        this.blogPageSize = blogPageSize;
    }

    public String getBlogKeyword() {
        return blogKeyword;
    }

    public void setBlogKeyword(String blogKeyword) {
        this.blogKeyword = blogKeyword;
    }

    public String getBlogPageIndex() {
        return blogPageIndex;
    }

    public void setBlogPageIndex(String blogPageIndex) {
        this.blogPageIndex = blogPageIndex;
    }

    public String getBlogPageSize() {
        return blogPageSize;
    }

    public void setBlogPageSize(String blogPageSize) {
        this.blogPageSize = blogPageSize;
    }

}