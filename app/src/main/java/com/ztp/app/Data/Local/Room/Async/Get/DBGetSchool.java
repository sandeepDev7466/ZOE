package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.SchoolResponse;

import java.util.ArrayList;
import java.util.List;

public class DBGetSchool {
    Context context;
    private List<SchoolResponse.School> schoolList = new ArrayList<>();
    private RoomDB roomDB;

    public DBGetSchool(Context context) {
        this.context = context;
        roomDB = RoomDB.getInstance(context);
        new AsyncGetSchool().execute();
    }

    public List<SchoolResponse.School> getSchoolList()
    {
        return schoolList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetSchool extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            schoolList = roomDB.getSchoolDao().getAllSchool();
            return null;
        }
    }
}
