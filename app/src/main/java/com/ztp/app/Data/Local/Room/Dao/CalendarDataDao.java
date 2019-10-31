package com.ztp.app.Data.Local.Room.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ztp.app.Data.Remote.Model.Response.CsoDashboardCombinedResponse;

import java.util.List;

@Dao
public interface CalendarDataDao {

    @Query("SELECT * FROM calendar_data")
    List<CsoDashboardCombinedResponse.CalendarData> getCalendarData();

    @Insert
    void insertAll(List<CsoDashboardCombinedResponse.CalendarData> calendarDataList);

    @Insert
    void insert(CsoDashboardCombinedResponse.CalendarData calendarData);

    @Query("DELETE FROM calendar_data")
    void deleteAll();
}
