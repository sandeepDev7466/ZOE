package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.DeleteEventRequest;
import com.ztp.app.Data.Remote.Model.Response.DeleteEventResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htl-dev on 19-04-2019.
 */

public class EventDeleteViewModel extends ViewModel {


    private MutableLiveData<DeleteEventResponse> deleteEventResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private DeleteEventRequest deleteEventRequest;

    public LiveData<DeleteEventResponse> getDeleteEventResponse(DeleteEventRequest deleteEventRequest) {
        deleteEventResponseMutableLiveData = new MutableLiveData<>();
        this.deleteEventRequest = deleteEventRequest;
        deleteEventMethod();
        return deleteEventResponseMutableLiveData;
    }


    public void deleteEventMethod() {

        Call<DeleteEventResponse> call = apiInterface.getDeleteEvent(deleteEventRequest);

        call.enqueue(new Callback<DeleteEventResponse>() {
            @Override
            public void onResponse(Call<DeleteEventResponse> call, Response<DeleteEventResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        deleteEventResponseMutableLiveData.postValue(response.body());

                    }
                }
                else
                {
                    deleteEventResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DeleteEventResponse> call, Throwable t) {
                t.printStackTrace();
                deleteEventResponseMutableLiveData.postValue(null);
            }
        });
    }
}
