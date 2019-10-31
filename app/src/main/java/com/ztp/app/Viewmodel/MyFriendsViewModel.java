package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.MyFriendsRequest;
import com.ztp.app.Data.Remote.Model.Response.MyFriendsResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFriendsViewModel extends ViewModel {
    private MutableLiveData<MyFriendsResponse> myFriendsResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private MyFriendsRequest myFriendsRequest;

    public LiveData<MyFriendsResponse> getMyFriendsResponse(MyFriendsRequest myFriendsRequest) {
        myFriendsResponseMutableLiveData = new MutableLiveData<>();
        this.myFriendsRequest = myFriendsRequest;
        getResponse();
        return myFriendsResponseMutableLiveData;
    }

    public void getResponse() {
        Call<MyFriendsResponse> call = apiInterface.getMyFriendsResponse(myFriendsRequest);

        call.enqueue(new Callback<MyFriendsResponse>() {
            @Override
            public void onResponse(Call<MyFriendsResponse> call, Response<MyFriendsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        myFriendsResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    myFriendsResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MyFriendsResponse> call, Throwable t) {
                t.printStackTrace();
                myFriendsResponseMutableLiveData.postValue(null);
            }
        });
    }
}
