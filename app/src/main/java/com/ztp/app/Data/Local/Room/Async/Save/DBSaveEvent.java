package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;

import java.util.List;

public class DBSaveEvent extends AsyncTask<Void, Void, Void> {
    private List<GetEventsResponse.Event> eventList;
    private RoomDB roomDB;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveEvent(Context context, List<GetEventsResponse.Event> eventList) {
        this.eventList = eventList;
        this.context = context;
        roomDB = RoomDB.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (roomDB.getEventsResponseDao().getAllEvents().size() == 0) {
            roomDB.getEventsResponseDao().insertAll(eventList);
        } else {
            roomDB.getEventsResponseDao().deleteAll();
            roomDB.getEventsResponseDao().insertAll(eventList);
        }
        return null;
    }
}
