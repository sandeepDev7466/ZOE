package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;

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
    private RoomDB roomDB;

    public LiveData<EventTypeResponse> getEventTypeResponse(Context context) {

        eventResponseMutableLiveData = new MutableLiveData<>();
        roomDB = RoomDB.getInstance(context);
        eventTypeResponse();
        return eventResponseMutableLiveData;
    }


    private void eventTypeResponse() {
        Call<EventTypeResponse> call = apiInterface.getEventtype();

        call.enqueue(new Callback<EventTypeResponse>() {
            @Override
            public void onResponse(Call<EventTypeResponse> call, Response<EventTypeResponse> response) {
                if (response.body() != null) {
                    eventResponseMutableLiveData.postValue(response.body());
                   // new AsyncEventType(response.body().getResData()).execute();
                }
            }

            @Override
            public void onFailure(Call<EventTypeResponse> call, Throwable t) {
                t.printStackTrace();
                eventResponseMutableLiveData.postValue(null);
            }
        });
    }


    private class AsyncEventType extends AsyncTask<Void, Void, Void> {
        List<EventTypeResponse.EventType> eventTypesList;

        public AsyncEventType(List<EventTypeResponse.EventType> eventTypesList) {
            this.eventTypesList = eventTypesList;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (roomDB.getEventTypeDao().getAllEventType().size() == 0) {
                roomDB.getEventTypeDao().insertAll(eventTypesList);
            }
            else
            {
                roomDB.getEventTypeDao().deleteAll();
                roomDB.getEventTypeDao().insertAll(eventTypesList);
            }

            return null;
        }
    }


}
