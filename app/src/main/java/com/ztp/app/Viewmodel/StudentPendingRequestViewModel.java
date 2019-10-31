package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.StudentPendingRequest;
import com.ztp.app.Data.Remote.Model.Response.StudentPendingResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentPendingRequestViewModel extends ViewModel {
    private MutableLiveData<StudentPendingResponse> studentPendingResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private StudentPendingRequest studentPendingRequest;

    public LiveData<StudentPendingResponse> getStudentPendingRequestResponse(StudentPendingRequest studentPendingRequest) {
        studentPendingResponseMutableLiveData = new MutableLiveData<>();
        this.studentPendingRequest = studentPendingRequest;
        getResponse();
        return studentPendingResponseMutableLiveData;
    }

    public void getResponse() {
        Call<StudentPendingResponse> call = apiInterface.getStudentPendingRequest(studentPendingRequest);

        call.enqueue(new Callback<StudentPendingResponse>() {
            @Override
            public void onResponse(Call<StudentPendingResponse> call, Response<StudentPendingResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        studentPendingResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    studentPendingResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<StudentPendingResponse> call, Throwable t) {
                t.printStackTrace();
                studentPendingResponseMutableLiveData.postValue(null);
            }
        });
    }
}
