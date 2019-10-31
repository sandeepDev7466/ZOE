package com.ztp.app.Data.Local.Room.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.CsoMyFollowerResponse;

import java.util.List;

@Dao
public interface CsoMyFollowerResponseDao
{
    @Query("SELECT * FROM see_follower")
    List<CsoMyFollowerResponse.SeeFollower> getMyFollowerResponse();

    @Insert
    void insertAll(List<CsoMyFollowerResponse.SeeFollower> csoAllFollowerList);

    @Insert
    void insert(CsoMyFollowerResponse.SeeFollower csoAllFollower);

    @Query("DELETE FROM see_follower")
    void deleteAll();
}
