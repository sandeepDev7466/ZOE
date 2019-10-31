package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.GetUserTimezoneRequest;
import com.ztp.app.Data.Remote.Model.Request.UpdateUserTimezoneRequest;
import com.ztp.app.Data.Remote.Model.Response.UpdateUserTimezoneResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserTimezoneViewModel extends ViewModel
{
    private MutableLiveData<UpdateUserTimezoneResponse> updateUserTimezoneResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private UpdateUserTimezoneRequest updateUserTimezoneRequest;

    public LiveData<UpdateUserTimezoneResponse> updateUserTimezoneResponse(UpdateUserTimezoneRequest updateUserTimezoneRequest) {
        updateUserTimezoneResponseMutableLiveData = new MutableLiveData<>();
        this.updateUserTimezoneRequest = updateUserTimezoneRequest;
        getResponse();
        return updateUserTimezoneResponseMutableLiveData;
    }

    public void getResponse() {
        Call<UpdateUserTimezoneResponse> call = apiInterface.updateUserTimezone(updateUserTimezoneRequest);

        call.enqueue(new Callback<UpdateUserTimezoneResponse>() {
            @Override
            public void onResponse(Call<UpdateUserTimezoneResponse> call, Response<UpdateUserTimezoneResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        updateUserTimezoneResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    updateUserTimezoneResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<UpdateUserTimezoneResponse> call, Throwable t) {
                t.printStackTrace();
                updateUserTimezoneResponseMutableLiveData.postValue(null);
            }
        });

    }
}
