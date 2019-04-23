package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Response.SchoolResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchoolViewModel extends ViewModel {
    private MutableLiveData<SchoolResponse> schoolResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();

    public LiveData<SchoolResponse> getSchoolResponse() {

        schoolResponseMutableLiveData = new MutableLiveData<>();
        schoolResponse();
        return schoolResponseMutableLiveData;
    }

    private void schoolResponse() {
        Call<SchoolResponse> call = apiInterface.getSchool();

        call.enqueue(new Callback<SchoolResponse>() {
            @Override
            public void onResponse(Call<SchoolResponse> call, Response<SchoolResponse> response) {
                if (response.body() != null) {
                    schoolResponseMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<SchoolResponse> call, Throwable t) {
                t.printStackTrace();
                schoolResponseMutableLiveData.postValue(null);
            }
        });
    }
}
