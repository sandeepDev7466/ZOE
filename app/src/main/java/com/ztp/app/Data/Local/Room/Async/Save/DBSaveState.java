package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;

import java.util.List;

public class DBSaveState extends AsyncTask<Void, Void, Void> {
    private List<StateResponse.State> stateList;
    private RoomDB roomDB;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public DBSaveState(Context context,List<StateResponse.State> stateList) {
        this.stateList = stateList;
        this.context = context;
        roomDB = RoomDB.getInstance(context);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if (roomDB.getStateDao().getAllStates().size() == 0) {
            roomDB.getStateDao().insertAll(stateList);
        } else {
            roomDB.getStateDao().deleteAll();
            roomDB.getStateDao().insertAll(stateList);
        }
        return null;
    }
}