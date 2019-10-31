package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.SchoolResponse;

import java.util.List;

public class DBSaveSchool extends AsyncTask<Void, Void, Void> {
    private List<SchoolResponse.School> schoolList;
    private RoomDB roomDB;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveSchool(Context context, List<SchoolResponse.School> schoolList) {
        this.schoolList = schoolList;
        this.context = context;
        roomDB = RoomDB.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (roomDB.getSchoolDao().getAllSchool().size() == 0) {
            roomDB.getSchoolDao().insertAll(schoolList);
        } else {
            roomDB.getSchoolDao().deleteAll();
            roomDB.getSchoolDao().insertAll(schoolList);
        }
        return null;
    }
}
