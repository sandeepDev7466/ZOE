package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.CsoDashboardCombinedResponse;

import java.util.List;

public class DBSaveUpcomingEvent extends AsyncTask<Void, Void, Void> {
    private List<CsoDashboardCombinedResponse.EventData> upcomingEventList;
    private RoomDB roomDB;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveUpcomingEvent(Context context, List<CsoDashboardCombinedResponse.EventData> upcomingEventList) {
        this.upcomingEventList = upcomingEventList;
        this.context = context;
        roomDB = RoomDB.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (roomDB.getUpcomingEventsDao().getUpcomingEventDataList().size() == 0) {
            roomDB.getUpcomingEventsDao().insertAll(upcomingEventList);
        } else {
            roomDB.getUpcomingEventsDao().deleteAll();
            roomDB.getUpcomingEventsDao().insertAll(upcomingEventList);
        }
        return null;
    }
}
