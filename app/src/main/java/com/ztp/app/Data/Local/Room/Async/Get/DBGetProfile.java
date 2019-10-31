package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.GetProfileResponse;

import java.util.List;

public class DBGetProfile {
    private Context context;
    private List<GetProfileResponse.ResData> getProfileResponse;
    private String user_id;

    public DBGetProfile(Context context, String user_id) {
        this.context = context;
        this.user_id = user_id;
        new AsyncGetProfile().execute();
    }

    public List<GetProfileResponse.ResData> getProfile() {
        return getProfileResponse;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetProfile extends AsyncTask<Void, Void, Void> {
        private RoomDB roomDB;

        @Override
        protected Void doInBackground(Void... voids) {
            roomDB = RoomDB.getInstance(context);
            getProfileResponse = roomDB.getProfileResponseDao().getProfile(user_id);
            return null;
        }
    }
}
