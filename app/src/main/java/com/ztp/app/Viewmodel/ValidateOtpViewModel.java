package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import com.ztp.app.Data.Remote.Model.Request.ValidateOtpRequest;
import com.ztp.app.Data.Remote.Model.Response.ValidateOtpResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import com.ztp.app.Helper.MyProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateOtpViewModel extends ViewModel {
    private MutableLiveData<ValidateOtpResponse> validateOtpResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private ValidateOtpRequest validateOtpRequest;

    public LiveData<ValidateOtpResponse> getValidateOtpResponse(ValidateOtpRequest validateOtpRequest) {

        validateOtpResponseMutableLiveData = new MutableLiveData<>();
        this.validateOtpRequest = validateOtpRequest;
        loadResponse();
        return validateOtpResponseMutableLiveData;
    }

    private void loadResponse() {

        Call<ValidateOtpResponse> call = apiInterface.validateOtp(validateOtpRequest);

        call.enqueue(new Callback<ValidateOtpResponse>() {
            @Override
            public void onResponse(Call<ValidateOtpResponse> call, Response<ValidateOtpResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null)
                        validateOtpResponseMutableLiveData.postValue(response.body());
                }
                else {
                    validateOtpResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ValidateOtpResponse> call, Throwable t) {
                t.printStackTrace();
                validateOtpResponseMutableLiveData.postValue(null);
            }
        });

    }
}
