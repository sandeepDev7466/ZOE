package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.PotentialFriendsRequest;
import com.ztp.app.Data.Remote.Model.Response.PotentialFriendsResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPotentialFriendsViewModel extends ViewModel {
    private MutableLiveData<PotentialFriendsResponse> getPotentialFriendsResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private PotentialFriendsRequest getPotentialFriendsRequest;

    public LiveData<PotentialFriendsResponse> getPotentialFriends(PotentialFriendsRequest getPotentialFriendsRequest) {
        getPotentialFriendsResponseMutableLiveData = new MutableLiveData<>();
        this.getPotentialFriendsRequest = getPotentialFriendsRequest;
        addEventResponse();
        return getPotentialFriendsResponseMutableLiveData;
    }

    public void addEventResponse() {
        Call<PotentialFriendsResponse> call = apiInterface.getPotentialFriends(getPotentialFriendsRequest);

        call.enqueue(new Callback<PotentialFriendsResponse>() {
            @Override
            public void onResponse(Call<PotentialFriendsResponse> call, Response<PotentialFriendsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        getPotentialFriendsResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    getPotentialFriendsResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PotentialFriendsResponse> call, Throwable t) {
                t.printStackTrace();
                getPotentialFriendsResponseMutableLiveData.postValue(null);
            }
        });
    }
}
