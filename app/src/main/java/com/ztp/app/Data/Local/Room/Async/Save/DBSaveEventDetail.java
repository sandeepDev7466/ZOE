package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.GetEventDetailResponse;

public class DBSaveEventDetail extends AsyncTask<Void, Void, Void> {

    private GetEventDetailResponse.EventDetail eventDetail;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveEventDetail(Context context, GetEventDetailResponse.EventDetail eventDetail) {
        this.eventDetail = eventDetail;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        RoomDB roomDB = RoomDB.getInstance(context);
        roomDB.getEventDetailResponseDao().insert(eventDetail);
        return null;
    }
}
