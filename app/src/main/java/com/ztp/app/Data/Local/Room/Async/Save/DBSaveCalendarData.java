package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.CsoDashboardCombinedResponse;

import java.util.List;

public class DBSaveCalendarData extends AsyncTask<Void, Void, Void> {
    private List<CsoDashboardCombinedResponse.CalendarData> calendarDataList;
    private RoomDB roomDB;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveCalendarData(Context context, List<CsoDashboardCombinedResponse.CalendarData> calendarDataList) {
        this.calendarDataList = calendarDataList;
        this.context = context;
        roomDB = RoomDB.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (roomDB.getCalendarDataDao().getCalendarData().size() == 0) {
            roomDB.getCalendarDataDao().insertAll(calendarDataList);
        } else {
            roomDB.getCalendarDataDao().deleteAll();
            roomDB.getCalendarDataDao().insertAll(calendarDataList);
        }
        return null;
    }
}
