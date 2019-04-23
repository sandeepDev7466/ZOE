package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.EventUpdateRequest;
import com.ztp.app.Data.Remote.Model.Response.UpdateEventResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEventViewModel extends ViewModel {

    private MutableLiveData<UpdateEventResponse> eventUpdateResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private EventUpdateRequest eventUpdateRequest;

    public LiveData<UpdateEventResponse> getUpdateEventResponse(EventUpdateRequest eventUpdateRequest) {
        eventUpdateResponseMutableLiveData = new MutableLiveData<>();
        this.eventUpdateRequest = eventUpdateRequest;
        updateEventResponse();
        return eventUpdateResponseMutableLiveData;
    }

    public void updateEventResponse() {
        Call<UpdateEventResponse> call = apiInterface.doUpdateEvent(eventUpdateRequest);
        call.enqueue(new Callback<UpdateEventResponse>() {
            @Override
            public void onResponse(Call<UpdateEventResponse> call, Response<UpdateEventResponse> response) {
                if (response.body() != null) {
                    eventUpdateResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UpdateEventResponse> call, Throwable t) {
                t.printStackTrace();
                eventUpdateResponseMutableLiveData.postValue(null);
            }
        });
    }


}
