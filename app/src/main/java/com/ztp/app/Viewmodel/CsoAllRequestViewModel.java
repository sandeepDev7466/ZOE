package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ztp.app.Data.Local.Room.Async.Save.DBSaveCSOAllRequest;
import com.ztp.app.Data.Remote.Model.Request.CSOAllRequest;
import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CsoAllRequestViewModel extends ViewModel {

    private MutableLiveData<CSOAllResponse> csoAllResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private CSOAllRequest csoAllRequest;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public LiveData<CSOAllResponse> getCsoAllRequetResponse(Context context,CSOAllRequest csoAllRequest) {
        csoAllResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        this.csoAllRequest = csoAllRequest;
        getResponse();
        return csoAllResponseMutableLiveData;
    }

    public void getResponse() {
        Call<CSOAllResponse> call = apiInterface.getCSOAllRequest(csoAllRequest);

        call.enqueue(new Callback<CSOAllResponse>() {
            @Override
            public void onResponse(Call<CSOAllResponse> call, Response<CSOAllResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        csoAllResponseMutableLiveData.postValue(response.body());
                        if (response.body().getResData() != null && response.body().getResData().size() > 0)
                            new DBSaveCSOAllRequest(context, response.body().getResData()).execute();
                    }
                }
                else
                {
                    csoAllResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CSOAllResponse> call, Throwable t) {
                t.printStackTrace();
                csoAllResponseMutableLiveData.postValue(null);
            }
        });
    }
}
