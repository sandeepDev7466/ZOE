package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.GetAllNotificationRequest;
import com.ztp.app.Data.Remote.Model.Response.GetAllNotificationResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllNotificationViewModel extends ViewModel
{
    private MutableLiveData<GetAllNotificationResponse> getAllNotificationResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetAllNotificationRequest getAllNotificationRequest;

    public LiveData<GetAllNotificationResponse> getAllNotificationResponse(GetAllNotificationRequest getAllNotificationRequest) {
        getAllNotificationResponseMutableLiveData = new MutableLiveData<>();
        this.getAllNotificationRequest = getAllNotificationRequest;
        getResponse();
        return getAllNotificationResponseMutableLiveData;
    }

    public void getResponse() {
        Call<GetAllNotificationResponse> call = apiInterface.getAllNotification(getAllNotificationRequest);

        call.enqueue(new Callback<GetAllNotificationResponse>() {
            @Override
            public void onResponse(Call<GetAllNotificationResponse> call, Response<GetAllNotificationResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        getAllNotificationResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    getAllNotificationResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetAllNotificationResponse> call, Throwable t) {
                t.printStackTrace();
                getAllNotificationResponseMutableLiveData.postValue(null);
            }
        });
    }
}
