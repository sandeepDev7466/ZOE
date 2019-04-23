package com.ztp.app.Data.Local.Room.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;

import java.util.List;

@Dao
public interface StateDao {

    @Query("SELECT * FROM state")
    LiveData<List<StateResponse.State>> getAll();

    @Query("SELECT * FROM state")
    List<StateResponse.State> getAllStates();

    @Insert
    void insertAll(List<StateResponse.State> stateList);

    @Insert
    void insert(StateResponse.State state);

    @Query("DELETE FROM state")
    void deleteAll();
}
