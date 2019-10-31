package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.CsoDashboardCombinedResponse;

import java.util.ArrayList;
import java.util.List;

public class DBGetUpcomingEvent {
    private Context context;
    private List<CsoDashboardCombinedResponse.EventData> upcomingEventList = new ArrayList<>();

    public DBGetUpcomingEvent(Context context) {
        this.context = context;
        new AsyncGetUpcomingEvents().execute();
    }

    public List<CsoDashboardCombinedResponse.EventData> getUpcomingEventList() {
        return upcomingEventList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetUpcomingEvents extends AsyncTask<Void, Void, Void> {
        private RoomDB roomDB;

        @Override
        protected Void doInBackground(Void... voids) {
            roomDB = RoomDB.getInstance(context);
            upcomingEventList = roomDB.getUpcomingEventsDao().getUpcomingEventDataList();
            return null;
        }
    }
}
