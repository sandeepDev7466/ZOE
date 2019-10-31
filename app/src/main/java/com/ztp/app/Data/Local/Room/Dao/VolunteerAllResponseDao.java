package com.ztp.app.Data.Local.Room.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;
import java.util.List;

@Dao
public interface VolunteerAllResponseDao
{
    @Query("SELECT * FROM all_volunteer_response")
    List<VolunteerAllResponse.VolunteerResponse> getVolunteerAllResponse();

    @Insert
    void insertAll(List<VolunteerAllResponse.VolunteerResponse> volunteerResponseList);

    @Insert
    void insert(VolunteerAllResponse.VolunteerResponse volunteerResponse);

    @Query("DELETE FROM all_volunteer_response")
    void deleteAll();
}
