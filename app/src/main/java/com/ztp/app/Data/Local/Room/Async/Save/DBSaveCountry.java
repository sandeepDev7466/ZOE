package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import java.util.List;

public class DBSaveCountry extends AsyncTask<Void, Void, Void> {
    private List<CountryResponse.Country> countryList;
    private RoomDB roomDB;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveCountry(Context context,List<CountryResponse.Country> countryList) {
        this.countryList = countryList;
        this.context = context;
        roomDB = RoomDB.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (roomDB.getCountryDao().getAllCountry().size() == 0) {
            roomDB.getCountryDao().insertAll(countryList);
        } else {
            roomDB.getCountryDao().deleteAll();
            roomDB.getCountryDao().insertAll(countryList);
        }
        return null;
    }
}