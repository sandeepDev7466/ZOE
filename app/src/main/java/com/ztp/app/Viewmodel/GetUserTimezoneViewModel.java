package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.GetUserTimezoneRequest;
import com.ztp.app.Data.Remote.Model.Response.GetUserTimezoneResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserTimezoneViewModel extends ViewModel {
    private MutableLiveData<GetUserTimezoneResponse> userTimezoneResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetUserTimezoneRequest getUserTimezoneRequest;

    public LiveData<GetUserTimezoneResponse> getUserTimezoneResponse(GetUserTimezoneRequest getUserTimezoneRequest) {
        userTimezoneResponseMutableLiveData = new MutableLiveData<>();
        this.getUserTimezoneRequest = getUserTimezoneRequest;
        getResponse();
        return userTimezoneResponseMutableLiveData;
    }

    public void getResponse() {
        Call<GetUserTimezoneResponse> call = apiInterface.getUserTimezone(getUserTimezoneRequest);

        call.enqueue(new Callback<GetUserTimezoneResponse>() {
            @Override
            public void onResponse(Call<GetUserTimezoneResponse> call, Response<GetUserTimezoneResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        userTimezoneResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    userTimezoneResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetUserTimezoneResponse> call, Throwable t) {
                t.printStackTrace();
                userTimezoneResponseMutableLiveData.postValue(null);
            }
        });
    }
}
