package com.ztp.app.Data.Local.Room.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.TimeZoneResponse;

import java.util.List;

/**
 * Created by htl-dev on 12-04-2019.
 */
@Dao
public interface TimezoneDao {

    @Query("SELECT * FROM timezone")
    List<TimeZoneResponse.Timezone> getAllTimeZone();

    @Insert
    void insertAll(List<TimeZoneResponse.Timezone> timezoneList);

    @Insert
    void insert(TimeZoneResponse.Timezone timezone);

    @Query("DELETE FROM timezone")
    void deleteAll();
}
