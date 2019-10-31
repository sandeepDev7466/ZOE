package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ztp.app.Data.Local.Room.Async.Save.DBSaveState;
import com.ztp.app.Data.Remote.Model.Request.StateRequest;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StateViewModel extends ViewModel {
    private MutableLiveData<StateResponse> stateResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private StateRequest stateRequest;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public LiveData<StateResponse> getStateResponse(Context context, StateRequest stateRequest) {

        stateResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        this.stateRequest = stateRequest;
        stateResponse();
        return stateResponseMutableLiveData;
    }

    private void stateResponse() {
        Call<StateResponse> call = apiInterface.getStates(stateRequest);

        call.enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        stateResponseMutableLiveData.postValue(response.body());
                        if (response.body().getStateList() != null && response.body().getStateList().size() > 0)
                            new DBSaveState(context, response.body().getStateList()).execute();

                    }
                }
                else
                {
                    stateResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {
                t.printStackTrace();
                stateResponseMutableLiveData.postValue(null);
            }
        });
    }
}
