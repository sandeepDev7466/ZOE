package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_3;
import com.ztp.app.Data.Remote.Model.Response.CsoRegisterResponseStep_3;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CsoRegisterStep_3ViewModel extends ViewModel {
    private MutableLiveData<CsoRegisterResponseStep_3> csoRegisterResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private CsoRegisterRequestStep_3 csoRegisterRequestStep_3;

    public LiveData<CsoRegisterResponseStep_3> getRegisterResponse(CsoRegisterRequestStep_3 csoRegisterRequestStep_3) {
        csoRegisterResponseMutableLiveData = new MutableLiveData<>();
        this.csoRegisterRequestStep_3 = csoRegisterRequestStep_3;
        registerResponse();
        return csoRegisterResponseMutableLiveData;
    }

    private void registerResponse() {
        Call<CsoRegisterResponseStep_3> call = apiInterface.doCsoRegisterStep_3(csoRegisterRequestStep_3);

        call.enqueue(new Callback<CsoRegisterResponseStep_3>() {
            @Override
            public void onResponse(Call<CsoRegisterResponseStep_3> call, Response<CsoRegisterResponseStep_3> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        csoRegisterResponseMutableLiveData.postValue(response.body());

                    }
                }else {
                    csoRegisterResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CsoRegisterResponseStep_3> call, Throwable t) {
                t.printStackTrace();
                csoRegisterResponseMutableLiveData.postValue(null);
            }
        });
    }
}
