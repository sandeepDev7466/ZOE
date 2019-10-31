package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.EventAddRequest;
import com.ztp.app.Data.Remote.Model.Request.PublishRequest;
import com.ztp.app.Data.Remote.Model.Response.AddEventResponse;
import com.ztp.app.Data.Remote.Model.Response.PublishResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublishViewModel extends ViewModel {

    private MutableLiveData<PublishResponse> publishResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private PublishRequest publishRequest;


    public LiveData<PublishResponse> getResponse(PublishRequest publishRequest) {
        publishResponseMutableLiveData = new MutableLiveData<>();
        this.publishRequest = publishRequest;
        addEventResponse();
        return publishResponseMutableLiveData;
    }

    public void addEventResponse()
    {
        Call<PublishResponse> call = apiInterface.publish(publishRequest);

        call.enqueue(new Callback<PublishResponse>() {
            @Override
            public void onResponse(Call<PublishResponse> call, Response<PublishResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        publishResponseMutableLiveData.postValue(response.body());

                    }
                }
                else
                {
                    publishResponseMutableLiveData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<PublishResponse> call, Throwable t) {
                t.printStackTrace();
                publishResponseMutableLiveData.postValue(null);
            }
        });
    }
}
