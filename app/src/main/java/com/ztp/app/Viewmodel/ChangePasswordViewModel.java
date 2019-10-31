package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.ChangePasswordRequest;
import com.ztp.app.Data.Remote.Model.Request.ForgotPasswordRequest;
import com.ztp.app.Data.Remote.Model.Response.ChangePasswordResponse;
import com.ztp.app.Data.Remote.Model.Response.ForgotPasswordResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordViewModel extends ViewModel {

    private MutableLiveData<ChangePasswordResponse> changePasswordResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private ChangePasswordRequest changePasswordRequest;

    public LiveData<ChangePasswordResponse> getChangePasswordResponse(ChangePasswordRequest changePasswordRequest) {
        changePasswordResponseMutableLiveData = new MutableLiveData<>();
        this.changePasswordRequest = changePasswordRequest;
        getResponse();
        return changePasswordResponseMutableLiveData;
    }

    public void getResponse() {
        Call<ChangePasswordResponse> call = apiInterface.changePassword(changePasswordRequest);

        call.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        changePasswordResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    changePasswordResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                t.printStackTrace();
                changePasswordResponseMutableLiveData.postValue(null);
            }
        });
    }
}
