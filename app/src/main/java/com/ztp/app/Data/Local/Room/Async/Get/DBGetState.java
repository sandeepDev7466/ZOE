package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;

import java.util.ArrayList;
import java.util.List;

public class DBGetState {
    Context context;
    private List<StateResponse.State> stateList = new ArrayList<>();
    private RoomDB roomDB;

    public DBGetState(Context context) {
        this.context = context;
        roomDB = RoomDB.getInstance(context);
        new AsyncGetState().execute();
    }

    public List<StateResponse.State> getStateList()
    {
        return stateList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetState extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            stateList = roomDB.getStateDao().getAllStates();
            return null;
        }
    }
}
