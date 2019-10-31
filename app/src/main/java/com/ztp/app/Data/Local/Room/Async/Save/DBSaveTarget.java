package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.VolunteerTargetResponse;

import java.util.List;

public class DBSaveTarget extends AsyncTask<Void, Void, Void>
{
    private List<VolunteerTargetResponse.TargetData> targetList;
    private RoomDB roomDB;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveTarget(Context context, List<VolunteerTargetResponse.TargetData> targetList) {
        this.targetList = targetList;
        this.context = context;
        roomDB = RoomDB.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (roomDB.getVolunteerTargetResponseDao().getTargetData().size() == 0) {
            roomDB.getVolunteerTargetResponseDao().insertAll(targetList);
        } else {
            roomDB.getVolunteerTargetResponseDao().deleteAll();
            roomDB.getVolunteerTargetResponseDao().insertAll(targetList);
        }
        return null;
    }
}
