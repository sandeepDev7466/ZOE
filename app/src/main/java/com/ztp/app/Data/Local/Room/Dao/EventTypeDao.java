package com.ztp.app.Data.Local.Room.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.EventTypeResponse;

import java.util.List;

/**
 * Created by htl-dev on 15-04-2019.
 */
@Dao
public interface EventTypeDao {

    @Query("SELECT * FROM event_type")
    LiveData<List<EventTypeResponse.EventType>> getAll();

    @Query("SELECT * FROM event_type")
    List<EventTypeResponse.EventType> getAllEventType();

    @Insert
    void insertAll(List<EventTypeResponse.EventType> eventTypeList);

    @Insert
    void insert(EventTypeResponse.EventType eventType);

    @Query("DELETE FROM event_type")
    void deleteAll();

}
