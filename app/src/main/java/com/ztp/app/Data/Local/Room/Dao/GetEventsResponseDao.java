package com.ztp.app.Data.Local.Room.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;

import java.util.List;

@Dao
public interface GetEventsResponseDao {

    @Query("SELECT * FROM event")
    List<GetEventsResponse.Event> getAllEvents();

    @Insert
    void insertAll(List<GetEventsResponse.Event> eventList);

    @Insert
    void insert(GetEventsResponse.Event event);

    @Query("DELETE FROM event")
    void deleteAll();
}
