package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.VolunteerDashboardCombineResponse;

import java.util.List;

public class DBSaveUpcomingEventsForVolunteer extends AsyncTask<Void, Void, Void> {
    private List<VolunteerDashboardCombineResponse.EventData> eventDataList;
    @SuppressLint("StaticFieldLeak")
    Context context;
    private RoomDB roomDB;

    public DBSaveUpcomingEventsForVolunteer(Context context, List<VolunteerDashboardCombineResponse.EventData> eventDataList) {
        this.eventDataList = eventDataList;
        this.context = context;
        roomDB = RoomDB.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (roomDB.getUpcomingVolunteerEventsDao().getAll().size() == 0) {
            roomDB.getUpcomingVolunteerEventsDao().insertAll(eventDataList);
        } else {
            roomDB.getUpcomingVolunteerEventsDao().deleteAll();
            roomDB.getUpcomingVolunteerEventsDao().insertAll(eventDataList);
        }
        return null;
    }
}
