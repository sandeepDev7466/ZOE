package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;

import java.util.ArrayList;
import java.util.List;

public class DBGetAllVolunteer
{
    private Context context;
    private List<VolunteerAllResponse.VolunteerResponse> volunteerResponseList = new ArrayList<>();

    public DBGetAllVolunteer(Context context) {
        this.context = context;
        new AsyncGetAllVolunteer().execute();
    }

    public List<VolunteerAllResponse.VolunteerResponse> getAllVolunteerList() {
        return volunteerResponseList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetAllVolunteer extends AsyncTask<Void, Void, Void> {
        private RoomDB roomDB;

        @Override
        protected Void doInBackground(Void... voids) {
            roomDB = RoomDB.getInstance(context);
            volunteerResponseList = roomDB.getVolunteerAllResponseDao().getVolunteerAllResponse();
            return null;
        }
    }
}
