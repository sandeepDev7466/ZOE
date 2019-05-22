package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.CsoMarkHoursRequest;
import com.ztp.app.Data.Remote.Model.Response.CsoMarkHoursResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CsoMarkHoursViewModel extends ViewModel {

    private MutableLiveData<CsoMarkHoursResponse> csoMarkHoursResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private CsoMarkHoursRequest csoMarkHoursRequest;

    public LiveData<CsoMarkHoursResponse> getCsoMarkHoursResponse(CsoMarkHoursRequest csoMarkHoursRequest) {
        csoMarkHoursResponseMutableLiveData = new MutableLiveData<>();
        this.csoMarkHoursRequest = csoMarkHoursRequest;
        getResponse();
        return csoMarkHoursResponseMutableLiveData;
    }

    public void getResponse() {
        Call<CsoMarkHoursResponse> call = apiInterface.csoMarkHours(csoMarkHoursRequest);

        call.enqueue(new Callback<CsoMarkHoursResponse>() {
            @Override
            public void onResponse(Call<CsoMarkHoursResponse> call, Response<CsoMarkHoursResponse> response) {
                if (response.body() != null) {
                    csoMarkHoursResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CsoMarkHoursResponse> call, Throwable t) {
                t.printStackTrace();
                csoMarkHoursResponseMutableLiveData.postValue(null);
            }
        });
    }
}
