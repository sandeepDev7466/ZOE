package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.CsoDashboardCombinedResponse;

import java.util.ArrayList;
import java.util.List;

public class DBGetCalendarData {
    private Context context;
    private List<CsoDashboardCombinedResponse.CalendarData> calendarDataList = new ArrayList<>();

    public DBGetCalendarData(Context context) {
        this.context = context;
        new AsyncGetCalendarData().execute();
    }

    public List<CsoDashboardCombinedResponse.CalendarData> getCalendarDataList() {
        return calendarDataList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetCalendarData extends AsyncTask<Void, Void, Void> {
        private RoomDB roomDB;

        @Override
        protected Void doInBackground(Void... voids) {
            roomDB = RoomDB.getInstance(context);
            calendarDataList = roomDB.getCalendarDataDao().getCalendarData();
            return null;
        }
    }
}
