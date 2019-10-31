package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import com.ztp.app.Data.Remote.Model.Response.CSOQuestionResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CSOQuestionViewModel extends ViewModel {
    private MutableLiveData<CSOQuestionResponse> csoQuestionResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public LiveData<CSOQuestionResponse> getCSOQuestionResponse(Context context) {

        csoQuestionResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        getResponse();
        return csoQuestionResponseMutableLiveData;
    }

    private void getResponse() {
        Call<CSOQuestionResponse> call = apiInterface.getCSOQuestionResponse();

        call.enqueue(new Callback<CSOQuestionResponse>() {
            @Override
            public void onResponse(Call<CSOQuestionResponse> call, Response<CSOQuestionResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        csoQuestionResponseMutableLiveData.postValue(response.body());

                        /*if (response.body().getResData() != null && response.body().getResData().size() > 0)
                            new DBSaveCountry(context, response.body().getResData()).execute();*/
                    }
                } else {
                    csoQuestionResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CSOQuestionResponse> call, Throwable t) {
                t.printStackTrace();
                csoQuestionResponseMutableLiveData.postValue(null);
            }
        });
    }
}
