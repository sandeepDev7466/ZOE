package com.ztp.app.Data.Local.Room.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.SchoolResponse;

import java.util.List;

@Dao
public interface SchoolDao {

    @Query("SELECT * FROM school")
    List<SchoolResponse.School> getAllSchool();

    @Insert
    void insertAll(List<SchoolResponse.School> schoolList);

    @Insert
    void insert(SchoolResponse.School school);

    @Query("DELETE FROM school")
    void deleteAll();
}
