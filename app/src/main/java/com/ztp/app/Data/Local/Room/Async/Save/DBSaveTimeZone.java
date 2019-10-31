package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.TimeZoneResponse;
import java.util.List;

public class DBSaveTimeZone extends AsyncTask<Void, Void, Void> {
    private List<TimeZoneResponse.Timezone> timezoneList;
    private RoomDB roomDB;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveTimeZone(Context context, List<TimeZoneResponse.Timezone> timezoneList) {
        this.timezoneList = timezoneList;
        this.context = context;
        roomDB = RoomDB.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (roomDB.getTimeZoneDao().getAllTimeZone().size() == 0) {
            roomDB.getTimeZoneDao().insertAll(timezoneList);
        } else {
            roomDB.getTimeZoneDao().deleteAll();
            roomDB.getTimeZoneDao().insertAll(timezoneList);
        }
        return null;
    }
}
