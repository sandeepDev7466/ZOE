package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_1;
import com.ztp.app.Data.Remote.Model.Request.EventAddRequest;
import com.ztp.app.Data.Remote.Model.Response.AddEventResponse;
import com.ztp.app.Data.Remote.Model.Response.CsoRegisterResponseStep_1;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventViewModel extends ViewModel {

    private MutableLiveData<AddEventResponse> eventAddResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private EventAddRequest eventAddRequest;

    public LiveData<AddEventResponse> getRegisterResponse(EventAddRequest eventAddRequest) {
        eventAddResponseMutableLiveData = new MutableLiveData<>();
        this.eventAddRequest = eventAddRequest;
        addEventResponse();
        return eventAddResponseMutableLiveData;
    }

    public void addEventResponse()
    {
        Call<AddEventResponse> call = apiInterface.doAddEvent(eventAddRequest);


        call.enqueue(new Callback<AddEventResponse>() {
            @Override
            public void onResponse(Call<AddEventResponse> call, Response<AddEventResponse> response) {
                if (response.body() != null) {
                    eventAddResponseMutableLiveData.postValue(response.body());

                }
            }
            @Override
            public void onFailure(Call<AddEventResponse> call, Throwable t) {
                t.printStackTrace();
                eventAddResponseMutableLiveData.postValue(null);
            }
        });
    }
}
