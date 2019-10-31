package com.ztp.app.Data.Local.Room.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.VolunteerDashboardCombineResponse;

import java.util.List;

@Dao
public interface UpcomingVolunteerEventsDao {

    @Insert
    void insertAll(List<VolunteerDashboardCombineResponse.EventData> eventDataList);

    @Query("SELECT * FROM volunteer_upcoming_events")
    List<VolunteerDashboardCombineResponse.EventData> getAll();

    @Query("DELETE FROM volunteer_upcoming_events")
    void deleteAll();

   /* @Query("SELECT * FROM country")
    LiveData<List<CountryResponse.Country>> getAll();

    @Query("SELECT * FROM country")
    List<CountryResponse.Country> getAllCountry();

    @Insert
    void insertAll(List<CountryResponse.Country> countryList);

    @Insert
    void insert(CountryResponse.Country country);

    @Query("DELETE FROM country")
    void deleteAll();*/

}
