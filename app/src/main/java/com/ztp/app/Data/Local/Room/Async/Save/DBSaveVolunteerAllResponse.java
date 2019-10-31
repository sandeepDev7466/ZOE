package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;
import java.util.List;

public class DBSaveVolunteerAllResponse extends AsyncTask<Void, Void, Void> {
    private List<VolunteerAllResponse.VolunteerResponse> volunteerResponseList;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveVolunteerAllResponse(Context context, List<VolunteerAllResponse.VolunteerResponse> volunteerResponseList) {
        this.volunteerResponseList = volunteerResponseList;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        RoomDB roomDB = RoomDB.getInstance(context);
        if (roomDB.getVolunteerAllResponseDao().getVolunteerAllResponse().size() == 0) {
            roomDB.getVolunteerAllResponseDao().insertAll(volunteerResponseList);
        } else {
            roomDB.getVolunteerAllResponseDao().deleteAll();
            roomDB.getVolunteerAllResponseDao().insertAll(volunteerResponseList);
        }
        return null;
    }
}
