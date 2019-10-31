package com.ztp.app.Data.Local.Room.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.VolunteerTargetResponse;

import java.util.List;

@Dao
public interface VolunteerTargetResponseDao
{
    @Query("SELECT * FROM target_data")
    List<VolunteerTargetResponse.TargetData> getTargetData();

    @Insert
    void insertAll(List<VolunteerTargetResponse.TargetData> targetList);

    @Insert
    void insert(VolunteerTargetResponse.TargetData target);

    @Query("DELETE FROM target_data")
    void deleteAll();
}

