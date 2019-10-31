package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.CsoHangoutVolunteerRequest;
import com.ztp.app.Data.Remote.Model.Response.CsoHangoutVolunteerResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CsoHangoutVolunteerViewModel extends ViewModel {

    private MutableLiveData<CsoHangoutVolunteerResponse> csoHangoutVolunteerResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private CsoHangoutVolunteerRequest csoHangoutVolunteerRequest;


    public LiveData<CsoHangoutVolunteerResponse> getCsoVolunteers(CsoHangoutVolunteerRequest csoHangoutVolunteerRequest) {
        csoHangoutVolunteerResponseMutableLiveData = new MutableLiveData<>();
        this.csoHangoutVolunteerRequest = csoHangoutVolunteerRequest;
        getResponse();
        return csoHangoutVolunteerResponseMutableLiveData;
    }

    public void getResponse() {
        Call<CsoHangoutVolunteerResponse> call = apiInterface.getHangoutVolunteers(csoHangoutVolunteerRequest);

        call.enqueue(new Callback<CsoHangoutVolunteerResponse>() {
            @Override
            public void onResponse(Call<CsoHangoutVolunteerResponse> call, Response<CsoHangoutVolunteerResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        csoHangoutVolunteerResponseMutableLiveData.postValue(response.body());

                    }
                }
                else
                {
                    csoHangoutVolunteerResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CsoHangoutVolunteerResponse> call, Throwable t) {
                t.printStackTrace();
                csoHangoutVolunteerResponseMutableLiveData.postValue(null);
            }
        });
    }
}
