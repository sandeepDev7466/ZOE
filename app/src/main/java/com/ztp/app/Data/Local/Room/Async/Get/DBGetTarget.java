package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.VolunteerTargetResponse;

import java.util.ArrayList;
import java.util.List;

public class DBGetTarget
{
    Context context;
    private List<VolunteerTargetResponse.TargetData> targetList = new ArrayList<>();
    private RoomDB roomDB;

    public DBGetTarget(Context context) {
        this.context = context;
        roomDB = RoomDB.getInstance(context);
        new AsyncGetTarget().execute();
    }

    public List<VolunteerTargetResponse.TargetData> getTargetList()
    {
        return targetList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetTarget extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            targetList = roomDB.getVolunteerTargetResponseDao().getTargetData();
            return null;
        }
    }
}

