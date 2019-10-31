package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.EventTypeResponse;

import java.util.List;

public class DBSaveEventType extends AsyncTask<Void, Void, Void> {
    private List<EventTypeResponse.EventType> eventTypeList;
    private RoomDB roomDB;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveEventType(Context context, List<EventTypeResponse.EventType> eventTypeList) {
        this.eventTypeList = eventTypeList;
        this.context = context;
        roomDB = RoomDB.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (roomDB.getEventTypeDao().getAllEventType().size() == 0) {
            roomDB.getEventTypeDao().insertAll(eventTypeList);
        } else {
            roomDB.getEventTypeDao().deleteAll();
            roomDB.getEventTypeDao().insertAll(eventTypeList);
        }
        return null;
    }
}
