package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.CsoMyFollowerResponse;

import java.util.ArrayList;
import java.util.List;

public class DBGetAllFollower {
    private Context context;
    private List<CsoMyFollowerResponse.SeeFollower> followerList = new ArrayList<>();

    public DBGetAllFollower(Context context) {
        this.context = context;
        new AsyncGetFollower().execute();
    }

    public List<CsoMyFollowerResponse.SeeFollower> getFollowerList() {
        return followerList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetFollower extends AsyncTask<Void, Void, Void> {
        private RoomDB roomDB;

        @Override
        protected Void doInBackground(Void... voids) {
            roomDB = RoomDB.getInstance(context);
            followerList = roomDB.getCsoMyFollowerResponse().getMyFollowerResponse();
            return null;
        }
    }
}
