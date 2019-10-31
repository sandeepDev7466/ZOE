package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ztp.app.Data.Local.Room.Async.Save.DBSaveTimeZone;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.TimeZoneResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

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
    @SuppressLint("StaticFieldLeak")
    Context context;

    public LiveData<TimeZoneResponse> getTimezoneResponse(Context context) {

        timezoneResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        roomDB = RoomDB.getInstance(context);
        timezoneResponse();
        return timezoneResponseMutableLiveData;
    }

    private void timezoneResponse() {
        Call<TimeZoneResponse> call = apiInterface.getTimezone();

        call.enqueue(new Callback<TimeZoneResponse>() {
            @Override
            public void onResponse(Call<TimeZoneResponse> call, Response<TimeZoneResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        timezoneResponseMutableLiveData.postValue(response.body());
                        if (response.body().getResData() != null && response.body().getResData().size() > 0)
                            new DBSaveTimeZone(context, response.body().getResData()).execute();
                    }
                }
                else
                {
                    timezoneResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<TimeZoneResponse> call, Throwable t) {
                t.printStackTrace();
                timezoneResponseMutableLiveData.postValue(null);
            }
        });
    }
}
