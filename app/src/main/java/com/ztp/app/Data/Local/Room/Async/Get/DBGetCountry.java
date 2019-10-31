package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;

import java.util.ArrayList;
import java.util.List;

public class DBGetCountry {
    private Context context;
    private List<CountryResponse.Country> countryList = new ArrayList<>();

    public DBGetCountry(Context context) {
        this.context = context;
        new AsyncGetCountry().execute();
    }

    public List<CountryResponse.Country> getCountryList() {
        return countryList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetCountry extends AsyncTask<Void, Void, Void> {
        private RoomDB roomDB;

        @Override
        protected Void doInBackground(Void... voids) {
            roomDB = RoomDB.getInstance(context);
            countryList = roomDB.getCountryDao().getAllCountry();
            return null;
        }
    }
}
