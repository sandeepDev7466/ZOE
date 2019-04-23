package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.TimeZoneResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htl-dev on 12-04-2019.
 */

public class TimeZoneViewModel extends ViewModel {
    private MutableLiveData<TimeZoneResponse> timezoneResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private RoomDB roomDB;


    public LiveData<TimeZoneResponse> getTimezoneResponse(Context context) {

        timezoneResponseMutableLiveData = new MutableLiveData<>();
        roomDB = RoomDB.getInstance(context);
        timezoneResponse();
        return timezoneResponseMutableLiveData;
    }

    private void timezoneResponse() {
        Call<TimeZoneResponse> call = apiInterface.getTimezone();

        call.enqueue(new Callback<TimeZoneResponse>() {
            @Override
            public void onResponse(Call<TimeZoneResponse> call, Response<TimeZoneResponse> response) {
                if (response.body() != null) {
                    timezoneResponseMutableLiveData.postValue(response.body());
                 //  new AsyncTimezone(response.body().getResData()).execute();
                }
            }

            @Override
            public void onFailure(Call<TimeZoneResponse> call, Throwable t) {
                t.printStackTrace();
                timezoneResponseMutableLiveData.postValue(null);
            }
        });
    }

    private class AsyncTimezone extends AsyncTask<Void, Void, Void> {
        List<TimeZoneResponse.Timezone> timeZoneList;

        public AsyncTimezone(List<TimeZoneResponse.Timezone> timeZoneList) {
            this.timeZoneList = timeZoneList;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (roomDB.getTimeZoneDao().getAllTimeZone().size() == 0) {
                roomDB.getTimeZoneDao().insertAll(timeZoneList);
            }
            else
            {
                roomDB.getTimeZoneDao().deleteAll();
                roomDB.getTimeZoneDao().insertAll(timeZoneList);
            }

            return null;
        }
    }

}
