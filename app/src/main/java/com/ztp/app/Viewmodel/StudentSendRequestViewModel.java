package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.StudentSendRequest;
import com.ztp.app.Data.Remote.Model.Response.StudentSendResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentSendRequestViewModel extends ViewModel
{
    private MutableLiveData<StudentSendResponse> studentSendResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private StudentSendRequest studentSendRequest;

    public LiveData<StudentSendResponse> getStudentSendRequestResponse(StudentSendRequest studentSendRequest) {
        studentSendResponseMutableLiveData = new MutableLiveData<>();
        this.studentSendRequest = studentSendRequest;
        getResponse();
        return studentSendResponseMutableLiveData;
    }

    public void getResponse() {
        Call<StudentSendResponse> call = apiInterface.getStudentSendRequest(studentSendRequest);

        call.enqueue(new Callback<StudentSendResponse>() {
            @Override
            public void onResponse(Call<StudentSendResponse> call, Response<StudentSendResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        studentSendResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    studentSendResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<StudentSendResponse> call, Throwable t) {
                t.printStackTrace();
                studentSendResponseMutableLiveData.postValue(null);
            }
        });
    }
}
