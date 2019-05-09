package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.gson.Gson;
import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_1;
import com.ztp.app.Data.Remote.Model.Request.StudentRegisterRequest;
import com.ztp.app.Data.Remote.Model.Response.CsoRegisterResponseStep_1;
import com.ztp.app.Data.Remote.Model.Response.StudentRegisterResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CsoRegisterStep_1ViewModel extends ViewModel {

    private MutableLiveData<CsoRegisterResponseStep_1> csoRegisterResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private CsoRegisterRequestStep_1 csoRegisterRequestStep_1;

    public LiveData<CsoRegisterResponseStep_1> getCSORegisterResponse(CsoRegisterRequestStep_1 csoRegisterRequestStep_1) {
        csoRegisterResponseMutableLiveData = new MutableLiveData<>();
        this.csoRegisterRequestStep_1 = csoRegisterRequestStep_1;
        registerResponse();
        return csoRegisterResponseMutableLiveData;

    }

    private void registerResponse() {
        Call<CsoRegisterResponseStep_1> call = apiInterface.doCsoRegisterStep_1(csoRegisterRequestStep_1);
        Log.i("REQUEST", "" + new Gson().toJson(csoRegisterRequestStep_1));
        call.enqueue(new Callback<CsoRegisterResponseStep_1>() {
            @Override
            public void onResponse(Call<CsoRegisterResponseStep_1> call, Response<CsoRegisterResponseStep_1> response) {
                if (response.body() != null) {
                    csoRegisterResponseMutableLiveData.postValue(response.body());
                    Log.i("RESPONSE", "" + new Gson().toJson(response.body()));
                }
            }
            @Override
            public void onFailure(Call<CsoRegisterResponseStep_1> call, Throwable t) {
                t.printStackTrace();
                csoRegisterResponseMutableLiveData.postValue(null);
            }
        });

    }
}
