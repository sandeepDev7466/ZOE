package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.LoginRequest;
import com.ztp.app.Data.Remote.Model.Response.LoginResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import com.ztp.app.Helper.MyProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginResponse> loginResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private LoginRequest loginRequest;

    public LiveData<LoginResponse> getLoginResponse(LoginRequest loginRequest) {

        loginResponseMutableLiveData = new MutableLiveData<>();
        this.loginRequest = loginRequest;
        loadResponse();
        return loginResponseMutableLiveData;
    }

    private void loadResponse() {

        Call<LoginResponse> call = apiInterface.doLogin(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loginResponseMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                loginResponseMutableLiveData.postValue(null);
            }
        });
    }
}
