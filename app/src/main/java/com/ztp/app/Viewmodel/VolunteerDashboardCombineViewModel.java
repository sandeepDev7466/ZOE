package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.VolunteerDashboardCombineRequest;
import com.ztp.app.Data.Remote.Model.Response.VolunteerDashboardCombineResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerDashboardCombineViewModel extends ViewModel {
    private MutableLiveData<VolunteerDashboardCombineResponse> volunteerDashboardCombineResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private VolunteerDashboardCombineRequest volunteerDashboardCombineRequest;

    public LiveData<VolunteerDashboardCombineResponse> getVolunteerDashboardCombinedResponse(VolunteerDashboardCombineRequest volunteerDashboardCombineRequest) {
        volunteerDashboardCombineResponseMutableLiveData = new MutableLiveData<>();
        this.volunteerDashboardCombineRequest = volunteerDashboardCombineRequest;
        getResponse();
        return volunteerDashboardCombineResponseMutableLiveData;
    }

    public void getResponse() {
        Call<VolunteerDashboardCombineResponse> call = apiInterface.getVolunteerDashboardCombined(volunteerDashboardCombineRequest);

        call.enqueue(new Callback<VolunteerDashboardCombineResponse>() {
            @Override
            public void onResponse(Call<VolunteerDashboardCombineResponse> call, Response<VolunteerDashboardCombineResponse> response) {
                if (response.body() != null) {
                    volunteerDashboardCombineResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<VolunteerDashboardCombineResponse> call, Throwable t) {
                t.printStackTrace();
                volunteerDashboardCombineResponseMutableLiveData.postValue(null);
            }
        });
    }
}
