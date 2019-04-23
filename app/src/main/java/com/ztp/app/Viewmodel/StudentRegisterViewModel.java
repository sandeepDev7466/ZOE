package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.StudentRegisterRequest;
import com.ztp.app.Data.Remote.Model.Response.StudentRegisterResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRegisterViewModel extends ViewModel {

    private MutableLiveData<StudentRegisterResponse> registerResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    StudentRegisterRequest studentRegisterRequest;

    public LiveData<StudentRegisterResponse> getRegisterResponse(StudentRegisterRequest studentRegisterRequest) {
        registerResponseMutableLiveData = new MutableLiveData<>();
        this.studentRegisterRequest = studentRegisterRequest;
        registerResponse();
        return registerResponseMutableLiveData;
    }

    private void registerResponse() {
        Call<StudentRegisterResponse> call = apiInterface.doStudentRegister(studentRegisterRequest);

        call.enqueue(new Callback<StudentRegisterResponse>() {
            @Override
            public void onResponse(Call<StudentRegisterResponse> call, Response<StudentRegisterResponse> response) {
                if (response.body() != null) {
                    registerResponseMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<StudentRegisterResponse> call, Throwable t) {
                t.printStackTrace();
                registerResponseMutableLiveData.postValue(null);
            }
        });
    }
}
