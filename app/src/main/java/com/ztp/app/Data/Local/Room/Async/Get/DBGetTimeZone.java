package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.TimeZoneResponse;

import java.util.ArrayList;
import java.util.List;

public class DBGetTimeZone {
    Context context;
    private List<TimeZoneResponse.Timezone> timezoneList = new ArrayList<>();
    private RoomDB roomDB;

    public DBGetTimeZone(Context context) {
        this.context = context;
        roomDB = RoomDB.getInstance(context);
        new AsyncGetTimeZone().execute();
    }

    public List<TimeZoneResponse.Timezone> getTimeZoneList() {
        return timezoneList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetTimeZone extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            timezoneList = roomDB.getTimeZoneDao().getAllTimeZone();
            return null;
        }
    }
}
