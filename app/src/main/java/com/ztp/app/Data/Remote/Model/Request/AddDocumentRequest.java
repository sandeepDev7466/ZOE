package com.ztp.app.Data.Remote.Model.Request;

/**
 * Created by htl-dev on 15-04-2019.
 */

public class AddDocumentRequest {

    String document_type="";
    String user_id="";
    String document_name="";
    String document_file_name="";


    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }

    public String getDocument_file_name() {
        return document_file_name;
    }

    public void setDocument_file_name(String document_file_name) {
        this.document_file_name = document_file_name;
    }
}
