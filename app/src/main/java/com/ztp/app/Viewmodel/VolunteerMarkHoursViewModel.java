package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.VolunteerMarkHoursRequest;
import com.ztp.app.Data.Remote.Model.Response.VolunteerMarkHoursResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerMarkHoursViewModel extends ViewModel {

    private MutableLiveData<VolunteerMarkHoursResponse> volunteerMarkHoursResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private VolunteerMarkHoursRequest request;

    public LiveData<VolunteerMarkHoursResponse> getMarkHoursResponse(VolunteerMarkHoursRequest request) {
        volunteerMarkHoursResponseMutableLiveData = new MutableLiveData<>();
        this.request = request;
        getResponse();
        return volunteerMarkHoursResponseMutableLiveData;
    }

    public void getResponse() {
        Call<VolunteerMarkHoursResponse> call = apiInterface.volunteerMarkHoursCompleted(request);

        call.enqueue(new Callback<VolunteerMarkHoursResponse>() {
            @Override
            public void onResponse(Call<VolunteerMarkHoursResponse> call, Response<VolunteerMarkHoursResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        volunteerMarkHoursResponseMutableLiveData.postValue(response.body());
                    }
                } else {
                    volunteerMarkHoursResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<VolunteerMarkHoursResponse> call, Throwable t) {
                t.printStackTrace();
                volunteerMarkHoursResponseMutableLiveData.postValue(null);
            }
        });
    }
}
