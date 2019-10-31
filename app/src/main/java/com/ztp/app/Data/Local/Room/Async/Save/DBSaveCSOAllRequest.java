package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;
import java.util.List;

public class DBSaveCSOAllRequest extends AsyncTask<Void, Void, Void> {
    private List<CSOAllResponse.CSOAllRequest> csoAllRequestList;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveCSOAllRequest(Context context, List<CSOAllResponse.CSOAllRequest> csoAllRequestList) {
        this.csoAllRequestList = csoAllRequestList;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        RoomDB roomDB = RoomDB.getInstance(context);
        if (roomDB.getCSOAllRequestDao().getCSOAllRequest().size() == 0) {
            roomDB.getCSOAllRequestDao().insertAll(csoAllRequestList);
        } else {
            roomDB.getCSOAllRequestDao().deleteAll();
            roomDB.getCSOAllRequestDao().insertAll(csoAllRequestList);
        }
        return null;
    }
}
