package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ztp.app.Data.Remote.Model.Response.EthnicityResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import com.ztp.app.Helper.MyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EthnicityViewModel extends ViewModel {
    private MutableLiveData<EthnicityResponse> ethnicityResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    @SuppressLint("StaticFieldLeak")
    private Context context;
    MyToast myToast;

    public LiveData<EthnicityResponse> getEthnicityResponse(Context context) {

        ethnicityResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        getResponse();
        return ethnicityResponseMutableLiveData;
    }

    private void getResponse() {
        Call<EthnicityResponse> call = apiInterface.getEthnicity();

        call.enqueue(new Callback<EthnicityResponse>() {
            @Override
            public void onResponse(Call<EthnicityResponse> call, Response<EthnicityResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ethnicityResponseMutableLiveData.postValue(response.body());
                        /*if (response.body().getResData() != null && response.body().getResData().size() > 0)
                            new DBSaveCountry(context, response.body().getResData()).execute();*/
                    }
                } else {
                    ethnicityResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<EthnicityResponse> call, Throwable t) {
                t.printStackTrace();
                ethnicityResponseMutableLiveData.postValue(null);
            }
        });
    }
}
