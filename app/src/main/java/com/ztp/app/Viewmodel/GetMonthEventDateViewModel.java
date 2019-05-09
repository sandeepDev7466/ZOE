package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.GetMonthEventDateRequest;
import com.ztp.app.Data.Remote.Model.Response.GetMonthEventDateResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMonthEventDateViewModel extends ViewModel {
    private MutableLiveData<GetMonthEventDateResponse> getMonthEventDateResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetMonthEventDateRequest getMonthEventDateRequest;


    public LiveData<GetMonthEventDateResponse> getMonthEventDateResponse(GetMonthEventDateRequest getMonthEventDateRequest) {
        getMonthEventDateResponseMutableLiveData = new MutableLiveData<>();
        this.getMonthEventDateRequest = getMonthEventDateRequest;
        getResponse();
        return getMonthEventDateResponseMutableLiveData;
    }

    public void getResponse() {
        Call<GetMonthEventDateResponse> call = apiInterface.getGetMonthEventDate(getMonthEventDateRequest);

        call.enqueue(new Callback<GetMonthEventDateResponse>() {
            @Override
            public void onResponse(Call<GetMonthEventDateResponse> call, Response<GetMonthEventDateResponse> response) {
                if (response.body() != null) {
                    getMonthEventDateResponseMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<GetMonthEventDateResponse> call, Throwable t) {
                t.printStackTrace();
                getMonthEventDateResponseMutableLiveData.postValue(null);
            }
        });
    }
}
