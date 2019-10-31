package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.CsoMyFollowerResponse;
import java.util.List;

public class DBSaveFollower extends AsyncTask<Void, Void, Void> {
    private List<CsoMyFollowerResponse.SeeFollower> seeFollowerList;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveFollower(Context context, List<CsoMyFollowerResponse.SeeFollower> seeFollowerList) {
        this.seeFollowerList = seeFollowerList;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        RoomDB roomDB = RoomDB.getInstance(context);
        if (roomDB.getCsoMyFollowerResponse().getMyFollowerResponse().size() == 0) {
            roomDB.getCsoMyFollowerResponse().insertAll(seeFollowerList);
        } else {
            roomDB.getCsoMyFollowerResponse().deleteAll();
            roomDB.getCsoMyFollowerResponse().insertAll(seeFollowerList);
        }
        return null;
    }
}
