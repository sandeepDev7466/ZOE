package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;

import java.util.ArrayList;
import java.util.List;

public class DBGetCSOAllRequest {
    private Context context;
    private List<CSOAllResponse.CSOAllRequest> csoAllRequestList = new ArrayList<>();

    public DBGetCSOAllRequest(Context context) {
        this.context = context;
        new AsyncGetCSOAllRequestData().execute();
    }

    public List<CSOAllResponse.CSOAllRequest> getCSOAllRequestDataList() {
        return csoAllRequestList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetCSOAllRequestData extends AsyncTask<Void, Void, Void> {
        private RoomDB roomDB;

        @Override
        protected Void doInBackground(Void... voids) {
            roomDB = RoomDB.getInstance(context);
            csoAllRequestList = roomDB.getCSOAllRequestDao().getCSOAllRequest();
            return null;
        }
    }
}
