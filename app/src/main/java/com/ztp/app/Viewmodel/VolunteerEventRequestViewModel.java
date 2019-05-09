package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.GetEventDetailRequest;
import com.ztp.app.Data.Remote.Model.Request.PostVolunteerRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventDetailResponse;
import com.ztp.app.Data.Remote.Model.Response.PostVolunteerRequestResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerEventRequestViewModel extends ViewModel {

    private MutableLiveData<PostVolunteerRequestResponse> getPostVolunteerMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private PostVolunteerRequest getPostVolunteerRequest;

    public LiveData<PostVolunteerRequestResponse> getPostVolunteerRequestResponseLiveData(PostVolunteerRequest getPostVolunteerRequest) {
        getPostVolunteerMutableLiveData = new MutableLiveData<>();
        this.getPostVolunteerRequest = getPostVolunteerRequest;
        getResponse();
        return getPostVolunteerMutableLiveData;
    }

    private void getResponse() {
        Call<PostVolunteerRequestResponse> call = apiInterface.postVolunteerRequest(getPostVolunteerRequest);

        call.enqueue(new Callback<PostVolunteerRequestResponse>() {
            @Override
            public void onResponse(Call<PostVolunteerRequestResponse> call, Response<PostVolunteerRequestResponse> response) {
                if (response.body() != null) {
                    getPostVolunteerMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<PostVolunteerRequestResponse> call, Throwable t) {
                t.printStackTrace();
                getPostVolunteerMutableLiveData.postValue(null);
            }


        });
    }
}
