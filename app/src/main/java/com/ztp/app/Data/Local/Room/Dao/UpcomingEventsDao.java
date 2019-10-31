package com.ztp.app.Data.Local.Room.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.ztp.app.Data.Remote.Model.Response.CsoDashboardCombinedResponse;
import java.util.List;

@Dao
public interface UpcomingEventsDao {

    @Query("SELECT * FROM upcoming_event")
    List<CsoDashboardCombinedResponse.EventData> getUpcomingEventDataList();

    @Insert
    void insertAll(List<CsoDashboardCombinedResponse.EventData> eventDataList);

    @Insert
    void insert(CsoDashboardCombinedResponse.EventData eventData);

    @Query("DELETE FROM upcoming_event")
    void deleteAll();
}
