package com.ztp.app.Data.Local.Room.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.GetShiftDetailResponse;

import java.util.List;

@Dao
public interface ShiftDetailResponseDao {

    @Query("SELECT * FROM shift_detail")
    List<GetShiftDetailResponse.ShiftDetail> getAllEventDetail();

    @Insert
    void insertAll(List<GetShiftDetailResponse.ShiftDetail> shiftDetailList);

    @Insert
    void insert(GetShiftDetailResponse.ShiftDetail shiftDetail);

    @Query("DELETE FROM shift_detail")
    void deleteAll();

    @Query("SELECT * FROM shift_detail WHERE shift_id = :shift_id")
    GetShiftDetailResponse.ShiftDetail getShiftDetailFromId(String shift_id);
}
