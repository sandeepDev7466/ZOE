package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.EventTypeResponse;

import java.util.ArrayList;
import java.util.List;

public class DBGetEventType {
    Context context;
    private List<EventTypeResponse.EventType> eventTypeList = new ArrayList<>();
    private RoomDB roomDB;

    public DBGetEventType(Context context) {
        this.context = context;
        roomDB = RoomDB.getInstance(context);
        new AsyncGetEventType().execute();
    }

    public List<EventTypeResponse.EventType> getEventTypeList() {
        return eventTypeList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetEventType extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            eventTypeList = roomDB.getEventTypeDao().getAllEventType();
            return null;
        }
    }
}
