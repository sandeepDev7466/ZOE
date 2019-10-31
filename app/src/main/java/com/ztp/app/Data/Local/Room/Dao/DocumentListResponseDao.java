package com.ztp.app.Data.Local.Room.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.DocumentListResponse;

import java.util.List;

@Dao
public interface DocumentListResponseDao
{

    @Query("SELECT * FROM document")
    List<DocumentListResponse.Document> getDocumentList();

    @Insert
    void insertAll(List<DocumentListResponse.Document> documentList);

    @Insert
    void insert(DocumentListResponse.Document document);

    @Query("DELETE FROM document")
    void deleteAll();
}
