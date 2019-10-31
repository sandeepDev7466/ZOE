package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.GetVolunteerShiftListResponse;

import java.util.List;

public class DBVolunteerSaveShiftList extends AsyncTask<Void, Void, Void> {
    private List<GetVolunteerShiftListResponse.ShiftData> shiftList;
    private RoomDB roomDB;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBVolunteerSaveShiftList(Context context, List<GetVolunteerShiftListResponse.ShiftData> shiftList) {
        this.shiftList = shiftList;
        this.context = context;
        roomDB = RoomDB.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {


       // if(roomDB.getVolunteerShiftListDao().getVolunteerShift())

        for(int i=0;i<shiftList.size();i++)
        {
            if(roomDB.getVolunteerShiftListDao().getVolunteerShift(shiftList.get(i).getShift_id())==null)
            {
                roomDB.getVolunteerShiftListDao().insert(shiftList.get(i));
            }
        }


        /*if (roomDB.getVolunteerShiftListDao().getShiftList().size() == 0) {
            roomDB.getVolunteerShiftListDao().insertAll(shiftList);
        } else {
            roomDB.getVolunteerShiftListDao().deleteAll();
            roomDB.getVolunteerShiftListDao().insertAll(shiftList);
        }*/
        return null;
    }
}
