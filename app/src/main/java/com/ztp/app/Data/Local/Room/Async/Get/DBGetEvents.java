package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;

import java.util.ArrayList;
import java.util.List;

public class DBGetEvents {
    private Context context;
    private List<GetEventsResponse.Event> eventList = new ArrayList<>();

    public DBGetEvents(Context context) {
        this.context = context;
        new AsyncGetEvents().execute();
    }

    public List<GetEventsResponse.Event> getEventsList() {
        return eventList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetEvents extends AsyncTask<Void, Void, Void> {
        private RoomDB roomDB;

        @Override
        protected Void doInBackground(Void... voids) {
            roomDB = RoomDB.getInstance(context);
            eventList = roomDB.getEventsResponseDao().getAllEvents();
            return null;
        }
    }
}
