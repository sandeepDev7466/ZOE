package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_2;
import com.ztp.app.Data.Remote.Model.Response.CsoRegisterResponseStep_2;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CsoRegisterStep_2ViewModel extends ViewModel {
    private MutableLiveData<CsoRegisterResponseStep_2> csoRegisterResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private CsoRegisterRequestStep_2 csoRegisterRequestStep_2;

    public LiveData<CsoRegisterResponseStep_2> getRegisterResponse(CsoRegisterRequestStep_2 csoRegisterRequestStep_2) {
        csoRegisterResponseMutableLiveData = new MutableLiveData<>();
        this.csoRegisterRequestStep_2 = csoRegisterRequestStep_2;
        registerResponse();
        return csoRegisterResponseMutableLiveData;
    }

    private void registerResponse() {
        Call<CsoRegisterResponseStep_2> call = apiInterface.doCsoRegisterStep_2(csoRegisterRequestStep_2);

        call.enqueue(new Callback<CsoRegisterResponseStep_2>() {
            @Override
            public void onResponse(Call<CsoRegisterResponseStep_2> call, Response<CsoRegisterResponseStep_2> response) {
                if (response.body() != null) {
                    csoRegisterResponseMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<CsoRegisterResponseStep_2> call, Throwable t) {
                t.printStackTrace();
                csoRegisterResponseMutableLiveData.postValue(null);
            }
        });
    }
}
