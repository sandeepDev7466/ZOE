package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.GetVolunteerShiftListResponse;

import java.util.ArrayList;
import java.util.List;

public class DBVolunteerGetShiftList
{
    private Context context;
    private List<GetVolunteerShiftListResponse.ShiftData> shiftList = new ArrayList<>();
    private String event_id;

    public DBVolunteerGetShiftList(Context context,String event_id) {
        this.context = context;
        this.event_id = event_id;
        new AsyncGetShiftList().execute();
    }

    public List<GetVolunteerShiftListResponse.ShiftData> getShiftList() {
        return shiftList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetShiftList extends AsyncTask<Void, Void, Void> {
        private RoomDB roomDB;

        @Override
        protected Void doInBackground(Void... voids) {
            roomDB = RoomDB.getInstance(context);
            shiftList = roomDB.getVolunteerShiftListDao().getVolunteerShiftData(event_id);
            return null;
        }
    }
}
