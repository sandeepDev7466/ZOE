package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.CsoMyVolunteerRequest;
import com.ztp.app.Data.Remote.Model.Response.CsoMyVolunteerResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CsoMyVolunteerViewModel extends ViewModel {

    private MutableLiveData<CsoMyVolunteerResponse> csoMyVolunteerResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private CsoMyVolunteerRequest csoMyVolunteerRequest;

    public LiveData<CsoMyVolunteerResponse> getCsoMyVolunteerResponse(CsoMyVolunteerRequest csoMyVolunteerRequest) {
        csoMyVolunteerResponseMutableLiveData = new MutableLiveData<>();
        this.csoMyVolunteerRequest = csoMyVolunteerRequest;
        getResponse();
        return csoMyVolunteerResponseMutableLiveData;
    }

    public void getResponse() {
        Call<CsoMyVolunteerResponse> call = apiInterface.getCsoMyVolunteer(csoMyVolunteerRequest);

        call.enqueue(new Callback<CsoMyVolunteerResponse>() {
            @Override
            public void onResponse(Call<CsoMyVolunteerResponse> call, Response<CsoMyVolunteerResponse> response) {
                if (response.body() != null) {
                    csoMyVolunteerResponseMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<CsoMyVolunteerResponse> call, Throwable t) {
                t.printStackTrace();
                csoMyVolunteerResponseMutableLiveData.postValue(null);
            }
        });
    }
}
