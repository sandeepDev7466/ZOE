package com.ztp.app.Data.Local.Room.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.CountryResponse;

import java.util.List;

@Dao
public interface CountryDao {

    @Query("SELECT * FROM country")
    LiveData<List<CountryResponse.Country>> getAll();

    @Query("SELECT * FROM country")
    List<CountryResponse.Country> getAllCountry();

    @Insert
    void insertAll(List<CountryResponse.Country> countryList);

    @Insert
    void insert(CountryResponse.Country country);

    @Query("DELETE FROM country")
    void deleteAll();
}
