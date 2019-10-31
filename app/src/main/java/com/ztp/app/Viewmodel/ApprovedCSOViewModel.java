package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.ApprovedCSORequest;
import com.ztp.app.Data.Remote.Model.Response.ApprovedCSOResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApprovedCSOViewModel extends ViewModel {

    private MutableLiveData<ApprovedCSOResponse> approvedCSOResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private ApprovedCSORequest approvedCSORequest;

    public LiveData<ApprovedCSOResponse> getApprovedCSOResponse(ApprovedCSORequest approvedCSORequest) {
        approvedCSOResponseMutableLiveData = new MutableLiveData<>();
        this.approvedCSORequest = approvedCSORequest;
        addEventResponse();
        return approvedCSOResponseMutableLiveData;
    }

    public void addEventResponse() {
        Call<ApprovedCSOResponse> call = apiInterface.approvedCSOList(approvedCSORequest);

        call.enqueue(new Callback<ApprovedCSOResponse>() {
            @Override
            public void onResponse(Call<ApprovedCSOResponse> call, Response<ApprovedCSOResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        approvedCSOResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    approvedCSOResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ApprovedCSOResponse> call, Throwable t) {
                t.printStackTrace();
                approvedCSOResponseMutableLiveData.postValue(null);
            }
        });
    }
}
