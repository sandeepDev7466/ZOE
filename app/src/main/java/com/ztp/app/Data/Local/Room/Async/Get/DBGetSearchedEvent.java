package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.SearchEventResponse;
import java.util.ArrayList;
import java.util.List;

public class DBGetSearchedEvent {
    private Context context;
    private List<SearchEventResponse.SearchedEvent> searchedEventList = new ArrayList<>();

    public DBGetSearchedEvent(Context context) {
        this.context = context;
        new AsyncGetSearchedEvent().execute();
    }

    public List<SearchEventResponse.SearchedEvent> getSearchedEventList() {
        return searchedEventList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetSearchedEvent extends AsyncTask<Void, Void, Void> {
        private RoomDB roomDB;

        @Override
        protected Void doInBackground(Void... voids) {
            roomDB = RoomDB.getInstance(context);
            searchedEventList = roomDB.getSearchEventResponseDao().getSearchEventResponse();
            return null;
        }
    }
}
