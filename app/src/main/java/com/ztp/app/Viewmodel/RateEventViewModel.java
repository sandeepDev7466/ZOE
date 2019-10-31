package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.EventRatingRequest;
import com.ztp.app.Data.Remote.Model.Response.EventRatingResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateEventViewModel extends ViewModel
{
    private MutableLiveData<EventRatingResponse> eventRateResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private EventRatingRequest eventRatingRequest;

    public LiveData<EventRatingResponse> getEventRatingResponse(EventRatingRequest eventRatingRequest) {
        eventRateResponseMutableLiveData = new MutableLiveData<>();
        this.eventRatingRequest = eventRatingRequest;
        getResponse();
        return eventRateResponseMutableLiveData;
    }

    public void getResponse() {
        Call<EventRatingResponse> call = apiInterface.rateEvent(eventRatingRequest);

        call.enqueue(new Callback<EventRatingResponse>() {
            @Override
            public void onResponse(Call<EventRatingResponse> call, Response<EventRatingResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        eventRateResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    eventRateResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<EventRatingResponse> call, Throwable t) {
                t.printStackTrace();
                eventRateResponseMutableLiveData.postValue(null);
            }
        });
    }
}
