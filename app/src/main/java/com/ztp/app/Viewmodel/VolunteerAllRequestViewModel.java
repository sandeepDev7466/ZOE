package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.VolunteerAllRequest;
import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerAllRequestViewModel extends ViewModel {
    private MutableLiveData<VolunteerAllResponse> volunteerAllResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private VolunteerAllRequest volunteerAllRequest;


    public LiveData<VolunteerAllResponse> getVolunteerAllRequetResponse(VolunteerAllRequest volunteerAllRequest) {
        volunteerAllResponseMutableLiveData = new MutableLiveData<>();
        this.volunteerAllRequest = volunteerAllRequest;
        getResponse();
        return volunteerAllResponseMutableLiveData;
    }

    public void getResponse() {
        Call<VolunteerAllResponse> call = apiInterface.getVolunteerAllRequest(volunteerAllRequest);

        call.enqueue(new Callback<VolunteerAllResponse>() {
            @Override
            public void onResponse(Call<VolunteerAllResponse> call, Response<VolunteerAllResponse> response) {
                if (response.body() != null) {
                    volunteerAllResponseMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<VolunteerAllResponse> call, Throwable t) {
                t.printStackTrace();
                volunteerAllResponseMutableLiveData.postValue(null);
            }
        });
    }
}
