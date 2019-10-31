package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.GetShiftDetailResponse;

public class DBSaveShiftDetail extends AsyncTask<Void, Void, Void> {
    private GetShiftDetailResponse.ShiftDetail shiftDetail;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveShiftDetail(Context context, GetShiftDetailResponse.ShiftDetail shiftDetail) {
        this.shiftDetail = shiftDetail;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        RoomDB roomDB = RoomDB.getInstance(context);
        roomDB.getShiftDetailResponseDao().insert(shiftDetail);
        return null;
    }
}
