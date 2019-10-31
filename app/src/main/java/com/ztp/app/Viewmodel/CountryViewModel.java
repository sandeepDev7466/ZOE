package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ztp.app.Data.Local.Room.Async.Save.DBSaveCountry;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import com.ztp.app.Helper.MyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryViewModel extends ViewModel {
    private MutableLiveData<CountryResponse> countryResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public LiveData<CountryResponse> getCountryResponse(Context context) {

        countryResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        countryResponse();
        return countryResponseMutableLiveData;
    }

    private void countryResponse() {
        Call<CountryResponse> call = apiInterface.getCountry();

        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        countryResponseMutableLiveData.postValue(response.body());
                        if (response.body().getResData() != null && response.body().getResData().size() > 0)
                            new DBSaveCountry(context, response.body().getResData()).execute();
                    }
                }
                else
                {
                    countryResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                t.printStackTrace();
                countryResponseMutableLiveData.postValue(null);
            }
        });
    }
}
