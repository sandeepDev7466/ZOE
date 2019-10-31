package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.GetProfileRequest;
import com.ztp.app.Data.Remote.Model.Response.GetProfileResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetProfileViewModel extends ViewModel {
    private MutableLiveData<GetProfileResponse> getProfileMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetProfileRequest getProfileRequest;

    public LiveData<GetProfileResponse> getProfileResponse(GetProfileRequest getProfileRequest) {
        getProfileMutableLiveData = new MutableLiveData<>();
        this.getProfileRequest = getProfileRequest;
        profileResponse();
        return getProfileMutableLiveData;
    }

    private void profileResponse() {
        Call<GetProfileResponse> call = apiInterface.doGetProfile(getProfileRequest);

        call.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        getProfileMutableLiveData.postValue(response.body());
                    }
                }
                else
                {
                    getProfileMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                t.printStackTrace();
                getProfileMutableLiveData.postValue(null);
            }
        });
    }
}
