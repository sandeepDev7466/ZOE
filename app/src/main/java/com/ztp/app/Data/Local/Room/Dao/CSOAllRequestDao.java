package com.ztp.app.Data.Local.Room.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;

import java.util.List;

@Dao
public interface CSOAllRequestDao {

    @Query("SELECT * FROM cso_all_request")
    List<CSOAllResponse.CSOAllRequest> getCSOAllRequest();

    @Insert
    void insertAll(List<CSOAllResponse.CSOAllRequest> csoAllRequestList);

    @Insert
    void insert(CSOAllResponse.CSOAllRequest csoAllRequest);

    @Query("DELETE FROM cso_all_request")
    void deleteAll();
}
