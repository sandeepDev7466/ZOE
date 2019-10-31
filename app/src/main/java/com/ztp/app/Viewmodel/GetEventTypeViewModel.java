package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Async.Save.DBSaveEventType;
import com.ztp.app.Data.Local.Room.Async.Save.DBSaveTimeZone;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.EventTypeResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htl-dev on 15-04-2019.
 */

public class GetEventTypeViewModel extends ViewModel {
    private MutableLiveData<EventTypeResponse> eventResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    @SuppressLint("StaticFieldLeak")
    Context context;

    public LiveData<EventTypeResponse> getEventTypeResponse(Context context) {

        eventResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        eventTypeResponse();
        return eventResponseMutableLiveData;
    }


    private void eventTypeResponse() {
        Call<EventTypeResponse> call = apiInterface.getEventtype();

        call.enqueue(new Callback<EventTypeResponse>() {
            @Override
            public void onResponse(Call<EventTypeResponse> call, Response<EventTypeResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        eventResponseMutableLiveData.postValue(response.body());
                        if (response.body().getResData() != null && response.body().getResData().size() > 0)
                            new DBSaveEventType(context, response.body().getResData()).execute();
                    }
                }
                else
                {
                    eventResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<EventTypeResponse> call, Throwable t) {
                t.printStackTrace();
                eventResponseMutableLiveData.postValue(null);
            }
        });
    }
}
