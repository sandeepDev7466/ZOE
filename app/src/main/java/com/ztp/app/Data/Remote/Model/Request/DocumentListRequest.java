package com.ztp.app.Data.Remote.Model.Request;

/**
 * Created by htl-dev on 03-07-2019.
 */

public class DocumentListRequest {

    String user_id="";


    public String getUser_id() {
        return user_id;
    }

    public DocumentListRequest(String userId) {
        super();
        this.user_id = userId;
    }

}
