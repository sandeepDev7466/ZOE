package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.VolunteerDashboardCombineResponse;
import java.util.ArrayList;
import java.util.List;

public class DBGetVolunteerUpcomingEvents
{
    private Context context;
    private List<VolunteerDashboardCombineResponse.EventData> upcomingEventList = new ArrayList<>();

    public DBGetVolunteerUpcomingEvents(Context context) {
        this.context = context;
        new AsyncGetVolunteerUpcomingEvents().execute();
    }

    public List<VolunteerDashboardCombineResponse.EventData> getUpcomingVolunteerEventList() {
        return upcomingEventList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetVolunteerUpcomingEvents extends AsyncTask<Void, Void, Void> {
        private RoomDB roomDB;

        @Override
        protected Void doInBackground(Void... voids) {
            roomDB = RoomDB.getInstance(context);
            upcomingEventList = roomDB.getUpcomingVolunteerEventsDao().getAll();
            return null;
        }
    }
}
