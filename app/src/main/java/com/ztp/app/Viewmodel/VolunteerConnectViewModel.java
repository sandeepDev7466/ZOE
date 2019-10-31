package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.VolunteerConnectRequest;
import com.ztp.app.Data.Remote.Model.Response.VolunteerConnectResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerConnectViewModel extends ViewModel {
    private MutableLiveData<VolunteerConnectResponse> volunteerConnectResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private VolunteerConnectRequest volunteerConnectRequest;

    public LiveData<VolunteerConnectResponse> getVolunteerConnectResponse(VolunteerConnectRequest volunteerConnectRequest) {
        volunteerConnectResponseMutableLiveData = new MutableLiveData<>();
        this.volunteerConnectRequest = volunteerConnectRequest;
        getResponse();
        return volunteerConnectResponseMutableLiveData;
    }

    public void getResponse() {
        Call<VolunteerConnectResponse> call = apiInterface.volunteerConnect(volunteerConnectRequest);

        call.enqueue(new Callback<VolunteerConnectResponse>() {
            @Override
            public void onResponse(Call<VolunteerConnectResponse> call, Response<VolunteerConnectResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        volunteerConnectResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    volunteerConnectResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<VolunteerConnectResponse> call, Throwable t) {
                t.printStackTrace();
                volunteerConnectResponseMutableLiveData.postValue(null);
            }
        });
    }
}
