package com.ztp.app.Data.Local.Room.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.SearchEventResponse;

import java.util.List;

@Dao
public interface SearchEventResponseDao {

    @Query("SELECT * FROM searched_event")
    List<SearchEventResponse.SearchedEvent> getSearchEventResponse();

    @Insert
    void insertAll(List<SearchEventResponse.SearchedEvent> searchEventResponseList);

    @Insert
    void insert(SearchEventResponse.SearchedEvent searchedEvent);

    @Query("DELETE FROM searched_event")
    void deleteAll();
}
