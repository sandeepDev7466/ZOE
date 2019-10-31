package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.ChangeStatusByCSORequest;
import com.ztp.app.Data.Remote.Model.Response.ChangeStatusByCSOResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeStatusByCSOViewModel extends ViewModel {

    private MutableLiveData<ChangeStatusByCSOResponse> changeStatusByCSOResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private ChangeStatusByCSORequest changeStatusByCSORequest;


    public LiveData<ChangeStatusByCSOResponse> changeStatus(ChangeStatusByCSORequest changeStatusByCSORequest) {
        changeStatusByCSOResponseMutableLiveData = new MutableLiveData<>();
        this.changeStatusByCSORequest = changeStatusByCSORequest;
        getResponse();
        return changeStatusByCSOResponseMutableLiveData;
    }

    public void getResponse()
    {
        Call<ChangeStatusByCSOResponse> call = apiInterface.changeStatusByCSO(changeStatusByCSORequest);


        call.enqueue(new Callback<ChangeStatusByCSOResponse>() {
            @Override
            public void onResponse(Call<ChangeStatusByCSOResponse> call, Response<ChangeStatusByCSOResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        changeStatusByCSOResponseMutableLiveData.postValue(response.body());

                    }
                }
                else
                {
                    changeStatusByCSOResponseMutableLiveData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<ChangeStatusByCSOResponse> call, Throwable t) {
                t.printStackTrace();
                changeStatusByCSOResponseMutableLiveData.postValue(null);
            }
        });
    }
}
