package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.MarkRankRequest;
import com.ztp.app.Data.Remote.Model.Response.MarkRankResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkRankViewModel extends ViewModel {
    private MutableLiveData<MarkRankResponse> markRankResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private MarkRankRequest markRankRequest;

    public LiveData<MarkRankResponse> getMarkRankResponse(MarkRankRequest markRankRequest) {
        markRankResponseMutableLiveData = new MutableLiveData<>();
        this.markRankRequest = markRankRequest;
        getResponse();
        return markRankResponseMutableLiveData;
    }

    public void getResponse() {
        Call<MarkRankResponse> call = apiInterface.markRank(markRankRequest);

        call.enqueue(new Callback<MarkRankResponse>() {
            @Override
            public void onResponse(Call<MarkRankResponse> call, Response<MarkRankResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        markRankResponseMutableLiveData.postValue(response.body());

                    }
                }
                else
                {
                    markRankResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MarkRankResponse> call, Throwable t) {
                t.printStackTrace();
                markRankResponseMutableLiveData.postValue(null);
            }
        });

    }
}
