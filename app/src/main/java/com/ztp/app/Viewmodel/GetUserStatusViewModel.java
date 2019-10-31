package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.GetUserStatusRequest;
import com.ztp.app.Data.Remote.Model.Response.GetUserStatusResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserStatusViewModel extends ViewModel {
    private MutableLiveData<GetUserStatusResponse> getUserStatusResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetUserStatusRequest getUserStatusRequest;

    public LiveData<GetUserStatusResponse> getUserStatusResponse(GetUserStatusRequest getUserStatusRequest) {
        getUserStatusResponseMutableLiveData = new MutableLiveData<>();
        this.getUserStatusRequest = getUserStatusRequest;
        getResponse();
        return getUserStatusResponseMutableLiveData;
    }

    public void getResponse() {
        Call<GetUserStatusResponse> call = apiInterface.getUserStatus(getUserStatusRequest);

        call.enqueue(new Callback<GetUserStatusResponse>() {
            @Override
            public void onResponse(Call<GetUserStatusResponse> call, Response<GetUserStatusResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        getUserStatusResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    getUserStatusResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetUserStatusResponse> call, Throwable t) {
                t.printStackTrace();
                getUserStatusResponseMutableLiveData.postValue(null);
            }
        });
    }
}
