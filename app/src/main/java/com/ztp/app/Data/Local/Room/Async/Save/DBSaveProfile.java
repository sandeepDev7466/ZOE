package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.GetProfileResponse;

public class DBSaveProfile extends AsyncTask<Void, Void, Void> {
    private GetProfileResponse.ResData getProfileResponse;
    private RoomDB roomDB;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveProfile(Context context, GetProfileResponse.ResData getProfileResponse) {
        this.getProfileResponse = getProfileResponse;
        this.context = context;
        roomDB = RoomDB.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        roomDB.getProfileResponseDao().deleteAll();
        roomDB.getProfileResponseDao().insert(getProfileResponse);
        return null;
    }
}
