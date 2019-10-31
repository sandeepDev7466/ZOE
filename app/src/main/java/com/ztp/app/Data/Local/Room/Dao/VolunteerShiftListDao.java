package com.ztp.app.Data.Local.Room.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.GetVolunteerShiftListResponse;

import java.util.List;

@Dao
public interface VolunteerShiftListDao
{
    @Query("SELECT * FROM shift_data")
    List<GetVolunteerShiftListResponse.ShiftData> getShiftList();

    @Insert
    void insertAll(List<GetVolunteerShiftListResponse.ShiftData> shiftList);

    @Insert
    void insert(GetVolunteerShiftListResponse.ShiftData shiftData);

    @Query("DELETE FROM shift_data")
    void deleteAll();

    @Query("SELECT * FROM shift_data WHERE event_id = :event_id")
    List<GetVolunteerShiftListResponse.ShiftData> getVolunteerShiftData(String event_id);

    @Query("SELECT * FROM shift_data WHERE shift_id = :shift_id")
    GetVolunteerShiftListResponse.ShiftData getVolunteerShift(String shift_id);
}
