package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.UpdateProfileRequest;
import com.ztp.app.Data.Remote.Model.Response.UpdateProfileResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileViewModel extends ViewModel {
    private MutableLiveData<UpdateProfileResponse> updateProfileMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private UpdateProfileRequest updateProfileRequest;

    public LiveData<UpdateProfileResponse> updateProfileResponse(UpdateProfileRequest updateProfileRequest) {
        updateProfileMutableLiveData = new MutableLiveData<>();
        this.updateProfileRequest = updateProfileRequest;
        profileResponse();
        return updateProfileMutableLiveData;
    }

    private void profileResponse() {
        Call<UpdateProfileResponse> call = apiInterface.doUpdateProfile(updateProfileRequest);

        call.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        updateProfileMutableLiveData.postValue(response.body());
                    }
                }
                else
                {
                    updateProfileMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                t.printStackTrace();
                updateProfileMutableLiveData.postValue(null);
            }
        });
    }
}
