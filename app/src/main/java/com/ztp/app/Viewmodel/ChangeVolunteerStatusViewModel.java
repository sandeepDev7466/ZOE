package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.ChangeVolunteerStatusRequest;
import com.ztp.app.Data.Remote.Model.Response.ChangeVolunteerStatusResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeVolunteerStatusViewModel extends ViewModel {

    private MutableLiveData<ChangeVolunteerStatusResponse> changeVolunteerStatusResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private ChangeVolunteerStatusRequest changeVolunteerStatusRequest;


    public LiveData<ChangeVolunteerStatusResponse> changeStatus(ChangeVolunteerStatusRequest changeVolunteerStatusRequest) {
        changeVolunteerStatusResponseMutableLiveData = new MutableLiveData<>();
        this.changeVolunteerStatusRequest = changeVolunteerStatusRequest;
        getResponse();
        return changeVolunteerStatusResponseMutableLiveData;
    }

    public void getResponse()
    {
        Call<ChangeVolunteerStatusResponse> call = apiInterface.changeVolunteerStatus(changeVolunteerStatusRequest);


        call.enqueue(new Callback<ChangeVolunteerStatusResponse>() {
            @Override
            public void onResponse(Call<ChangeVolunteerStatusResponse> call, Response<ChangeVolunteerStatusResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        changeVolunteerStatusResponseMutableLiveData.postValue(response.body());

                    }
                }
                else
                {
                    changeVolunteerStatusResponseMutableLiveData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<ChangeVolunteerStatusResponse> call, Throwable t) {
                t.printStackTrace();
                changeVolunteerStatusResponseMutableLiveData.postValue(null);
            }
        });
    }
}
