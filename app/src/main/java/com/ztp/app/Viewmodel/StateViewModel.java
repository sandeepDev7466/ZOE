package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Request.StateRequest;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StateViewModel extends ViewModel {
    private MutableLiveData<StateResponse> stateResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private StateRequest stateRequest;
    private RoomDB roomDB;

    public LiveData<StateResponse> getStateResponse(Context context, StateRequest stateRequest) {

        stateResponseMutableLiveData = new MutableLiveData<>();
        roomDB = RoomDB.getInstance(context);
        this.stateRequest = stateRequest;
        stateResponse();
        return stateResponseMutableLiveData;
    }

    private void stateResponse() {
        Call<StateResponse> call = apiInterface.getStates(stateRequest);

        call.enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                if (response.body() != null) {
                    stateResponseMutableLiveData.postValue(response.body());
                    new AsyncState(response.body().getStateList()).execute();

                }
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {
                t.printStackTrace();
                stateResponseMutableLiveData.postValue(null);
            }
        });
    }

    private class AsyncState extends AsyncTask<Void, Void, Void> {
        List<StateResponse.State> stateList;

        public AsyncState(List<StateResponse.State> stateList) {
            this.stateList = stateList;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (roomDB.getStateDao().getAllStates().size() == 0) {
                roomDB.getStateDao().insertAll(stateList);
            }
            else
            {
                roomDB.getStateDao().deleteAll();
                roomDB.getStateDao().insertAll(stateList);
            }
            return null;
        }
    }
}
