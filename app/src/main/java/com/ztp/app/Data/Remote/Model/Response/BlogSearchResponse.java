package com.ztp.app.Data.Remote.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlogSearchResponse {

    @SerializedName("api_res_key")
    @Expose
    private String apiResKey;
    @SerializedName("res_status")
    @Expose
    private String resStatus;
    @SerializedName("res_data")
    @Expose
    private List<ResDatum> resData = null;

    public BlogSearchResponse() {
    }

    public BlogSearchResponse(String apiResKey, String resStatus, List<ResDatum> resData) {
        super();
        this.apiResKey = apiResKey;
        this.resStatus = resStatus;
        this.resData = resData;
    }

    public String getApiResKey() {
        return apiResKey;
    }

    public void setApiResKey(String apiResKey) {
        this.apiResKey = apiResKey;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public List<ResDatum> getResData() {
        return resData;
    }

    public void setResData(List<ResDatum> resData) {
        this.resData = resData;
    }

    public class ResDatum {

        @SerializedName("blog_id")
        @Expose
        private String blogId;
        @SerializedName("blog_title")
        @Expose
        private String blogTitle;
        @SerializedName("blog_short_desc")
        @Expose
        private String blogShortDesc;
        @SerializedName("blog_long_desc")
        @Expose
        private String blogLongDesc;
        @SerializedName("blog_published_date")
        @Expose
        private String blogPublishedDate;
        @SerializedName("blog_image")
        @Expose
        private String blogImage;
        @SerializedName("blog_category_name")
        @Expose
        private String blogCategoryName;

        public ResDatum() {
        }

        public ResDatum(String blogId, String blogTitle, String blogShortDesc, String blogLongDesc, String blogPublishedDate, String blogImage, String blogCategoryName) {
            super();
            this.blogId = blogId;
            this.blogTitle = blogTitle;
            this.blogShortDesc = blogShortDesc;
            this.blogLongDesc = blogLongDesc;
            this.blogPublishedDate = blogPublishedDate;
            this.blogImage = blogImage;
            this.blogCategoryName = blogCategoryName;
        }

        public String getBlogId() {
            return blogId;
        }

        public void setBlogId(String blogId) {
            this.blogId = blogId;
        }

        public String getBlogTitle() {
            return blogTitle;
        }

        public void setBlogTitle(String blogTitle) {
            this.blogTitle = blogTitle;
        }

        public String getBlogShortDesc() {
            return blogShortDesc;
        }

        public void setBlogShortDesc(String blogShortDesc) {
            this.blogShortDesc = blogShortDesc;
        }

        public String getBlogLongDesc() {
            return blogLongDesc;
        }

        public void setBlogLongDesc(String blogLongDesc) {
            this.blogLongDesc = blogLongDesc;
        }

        public String getBlogPublishedDate() {
            return blogPublishedDate;
        }

        public void setBlogPublishedDate(String blogPublishedDate) {
            this.blogPublishedDate = blogPublishedDate;
        }

        public String getBlogImage() {
            return blogImage;
        }

        public void setBlogImage(String blogImage) {
            this.blogImage = blogImage;
        }

        public String getBlogCategoryName() {
            return blogCategoryName;
        }

        public void setBlogCategoryName(String blogCategoryName) {
            this.blogCategoryName = blogCategoryName;
        }

    }

}

