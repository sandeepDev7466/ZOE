package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.GetCSOShiftResponse;

import java.util.List;

public class DBCSOSaveShiftList extends AsyncTask<Void, Void, Void>
{
    private List<GetCSOShiftResponse.ResDatum> shiftList;
    private RoomDB roomDB;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBCSOSaveShiftList(Context context, List<GetCSOShiftResponse.ResDatum> shiftList) {
        this.shiftList = shiftList;
        this.context = context;
        roomDB = RoomDB.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {


        for(int i=0;i<shiftList.size();i++)
        {
            if(roomDB.getCSOShiftResponseDao().getCsoShift(shiftList.get(i).getShiftId())==null)
            {
                roomDB.getCSOShiftResponseDao().insert(shiftList.get(i));
            }
        }

       /* if (roomDB.getCSOShiftResponseDao().getShiftList().size() == 0) {
            roomDB.getCSOShiftResponseDao().insertAll(shiftList);
        } else {
            roomDB.getCSOShiftResponseDao().deleteAll();
            roomDB.getCSOShiftResponseDao().insertAll(shiftList);
        }*/
        return null;
    }
}
