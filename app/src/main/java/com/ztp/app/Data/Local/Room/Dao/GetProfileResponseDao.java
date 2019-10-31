package com.ztp.app.Data.Local.Room.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.ztp.app.Data.Remote.Model.Response.GetProfileResponse;
import java.util.List;

@Dao
public interface GetProfileResponseDao {

    @Query("SELECT * FROM profile WHERE user_id = :user_id")
    List<GetProfileResponse.ResData> getProfile(String user_id);

    @Query("SELECT * FROM profile WHERE is_synced = :is_synced")
    List<GetProfileResponse.ResData> getProfileListToBeSynced(boolean is_synced);

    @Insert
    void insert(GetProfileResponse.ResData getProfileResponse);

    @Query("DELETE FROM profile")
    void deleteAll();
}
