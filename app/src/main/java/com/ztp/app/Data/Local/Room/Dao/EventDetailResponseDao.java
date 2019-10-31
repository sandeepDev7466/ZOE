package com.ztp.app.Data.Local.Room.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.GetEventDetailResponse;

import java.util.List;

@Dao
public interface EventDetailResponseDao
{

    @Query("SELECT * FROM event_detail")
    List<GetEventDetailResponse.EventDetail> getAllEventDetail();

    @Insert
    void insertAll(List<GetEventDetailResponse.EventDetail> eventDetailList);

    @Insert
    void insert(GetEventDetailResponse.EventDetail eventDetail);

    @Query("DELETE FROM event_detail")
    void deleteAll();

    @Query("SELECT * FROM event_detail WHERE event_id = :event_id")
    GetEventDetailResponse.EventDetail getEventDetailFromId(String event_id);
}
