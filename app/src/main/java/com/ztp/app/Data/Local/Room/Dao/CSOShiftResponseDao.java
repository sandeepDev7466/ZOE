package com.ztp.app.Data.Local.Room.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.ztp.app.Data.Remote.Model.Response.GetCSOShiftResponse;
import java.util.List;

@Dao
public interface CSOShiftResponseDao {
    @Query("SELECT * FROM shift_list")
    List<GetCSOShiftResponse.ResDatum> getShiftList();

    @Insert
    void insertAll(List<GetCSOShiftResponse.ResDatum> shiftList);

    @Insert
    void insert(GetCSOShiftResponse.ResDatum shiftData);

    @Query("DELETE FROM shift_list")
    void deleteAll();

    @Query("SELECT * FROM shift_list WHERE event_id = :event_id")
    List<GetCSOShiftResponse.ResDatum> getCsoShiftData(String event_id);

    @Query("SELECT * FROM shift_list WHERE shift_id = :shift_id")
    GetCSOShiftResponse.ResDatum getCsoShift(String shift_id);

}
