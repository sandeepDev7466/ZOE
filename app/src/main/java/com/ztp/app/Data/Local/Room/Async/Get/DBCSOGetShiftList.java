package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.GetCSOShiftResponse;

import java.util.ArrayList;
import java.util.List;

public class DBCSOGetShiftList
{
    private Context context;
    private List<GetCSOShiftResponse.ResDatum> shiftList = new ArrayList<>();
    private String event_id;

    public DBCSOGetShiftList(Context context,String event_id) {
        this.context = context;
        this.event_id = event_id;
        new AsyncGetShiftList().execute();
    }

    public List<GetCSOShiftResponse.ResDatum> getShiftList() {
        return shiftList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetShiftList extends AsyncTask<Void, Void, Void> {
        private RoomDB roomDB;

        @Override
        protected Void doInBackground(Void... voids) {
            roomDB = RoomDB.getInstance(context);
            shiftList = roomDB.getCSOShiftResponseDao().getCsoShiftData(event_id);
            return null;
        }
    }
}
