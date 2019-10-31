package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.MarkAttendanceRequest;
import com.ztp.app.Data.Remote.Model.Response.MarkAttendanceResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkAttendanceViewModel extends ViewModel {
    private MutableLiveData<MarkAttendanceResponse> markAttendanceResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private MarkAttendanceRequest markAttendanceRequest;

    public LiveData<MarkAttendanceResponse> markAttendanceResponse(MarkAttendanceRequest markAttendanceRequest) {
        markAttendanceResponseMutableLiveData = new MutableLiveData<>();
        this.markAttendanceRequest = markAttendanceRequest;
        getResponse();
        return markAttendanceResponseMutableLiveData;
    }

    public void getResponse() {
        Call<MarkAttendanceResponse> call = apiInterface.markAttendance(markAttendanceRequest);

        call.enqueue(new Callback<MarkAttendanceResponse>() {
            @Override
            public void onResponse(Call<MarkAttendanceResponse> call, Response<MarkAttendanceResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        markAttendanceResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    markAttendanceResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MarkAttendanceResponse> call, Throwable t) {
                t.printStackTrace();
                markAttendanceResponseMutableLiveData.postValue(null);
            }
        });
    }
}
