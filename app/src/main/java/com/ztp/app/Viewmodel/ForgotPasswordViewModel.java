package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.EventAddRequest;
import com.ztp.app.Data.Remote.Model.Request.ForgotPasswordRequest;
import com.ztp.app.Data.Remote.Model.Response.AddEventResponse;
import com.ztp.app.Data.Remote.Model.Response.ForgotPasswordResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordViewModel extends ViewModel {

    private MutableLiveData<ForgotPasswordResponse> forgotPasswordResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private ForgotPasswordRequest forgotPasswordRequest;

    public LiveData<ForgotPasswordResponse> getForgotPasswordResponse(ForgotPasswordRequest forgotPasswordRequest) {
        forgotPasswordResponseMutableLiveData = new MutableLiveData<>();
        this.forgotPasswordRequest = forgotPasswordRequest;
        addEventResponse();
        return forgotPasswordResponseMutableLiveData;
    }

    public void addEventResponse() {
        Call<ForgotPasswordResponse> call = apiInterface.forgotPassword(forgotPasswordRequest);

        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        forgotPasswordResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    forgotPasswordResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                t.printStackTrace();
                forgotPasswordResponseMutableLiveData.postValue(null);
            }
        });
    }
}
