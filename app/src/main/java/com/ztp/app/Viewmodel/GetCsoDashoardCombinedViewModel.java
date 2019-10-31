package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.CsoDashboardCombinedRequest;
import com.ztp.app.Data.Remote.Model.Response.CsoDashboardCombinedResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCsoDashoardCombinedViewModel extends ViewModel {

    private MutableLiveData<CsoDashboardCombinedResponse> csoDashboardCombinedResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private CsoDashboardCombinedRequest csoDashboardCombinedRequest;

    public LiveData<CsoDashboardCombinedResponse> getCsoDashboardCombinedResponse(CsoDashboardCombinedRequest csoDashboardCombinedRequest) {
        csoDashboardCombinedResponseMutableLiveData = new MutableLiveData<>();
        this.csoDashboardCombinedRequest = csoDashboardCombinedRequest;
        getResponse();
        return csoDashboardCombinedResponseMutableLiveData;
    }

    public void getResponse() {
        Call<CsoDashboardCombinedResponse> call = apiInterface.getCsoDashoardCombined(csoDashboardCombinedRequest);

        call.enqueue(new Callback<CsoDashboardCombinedResponse>() {
            @Override
            public void onResponse(Call<CsoDashboardCombinedResponse> call, Response<CsoDashboardCombinedResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        csoDashboardCombinedResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    csoDashboardCombinedResponseMutableLiveData.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<CsoDashboardCombinedResponse> call, Throwable t) {
                t.printStackTrace();
                csoDashboardCombinedResponseMutableLiveData.postValue(null);
            }
        });
    }
}
