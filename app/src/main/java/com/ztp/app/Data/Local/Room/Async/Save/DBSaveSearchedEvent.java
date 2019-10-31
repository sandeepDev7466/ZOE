package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.SearchEventResponse;

import java.util.List;

public class DBSaveSearchedEvent extends AsyncTask<Void, Void, Void> {
    private List<SearchEventResponse.SearchedEvent> searchedEventList;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveSearchedEvent(Context context, List<SearchEventResponse.SearchedEvent> searchedEventList) {
        this.searchedEventList = searchedEventList;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        RoomDB roomDB = RoomDB.getInstance(context);
        if (roomDB.getSearchEventResponseDao().getSearchEventResponse().size() == 0) {
            roomDB.getSearchEventResponseDao().insertAll(searchedEventList);
        } else {
            roomDB.getSearchEventResponseDao().deleteAll();
            roomDB.getSearchEventResponseDao().insertAll(searchedEventList);
        }
        return null;
    }
}
