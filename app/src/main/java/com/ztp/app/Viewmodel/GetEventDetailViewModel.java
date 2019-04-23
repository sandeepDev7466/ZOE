package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.GetEventDetailRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventDetailResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetEventDetailViewModel extends ViewModel {

    private MutableLiveData<GetEventDetailResponse> getEventDetailMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetEventDetailRequest getEventDetailRequest;

    public LiveData<GetEventDetailResponse> getEventDetailResponseLiveData(GetEventDetailRequest getEventDetailRequest) {
        getEventDetailMutableLiveData = new MutableLiveData<>();
        this.getEventDetailRequest = getEventDetailRequest;
        getResponse();
        return getEventDetailMutableLiveData;
    }

    private void getResponse() {
        Call<GetEventDetailResponse> call = apiInterface.getEventDetail(getEventDetailRequest);

        call.enqueue(new Callback<GetEventDetailResponse>() {
            @Override
            public void onResponse(Call<GetEventDetailResponse> call, Response<GetEventDetailResponse> response) {
                if (response.body() != null) {
                    getEventDetailMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<GetEventDetailResponse> call, Throwable t) {
                t.printStackTrace();
                getEventDetailMutableLiveData.postValue(null);
            }
        });
    }
}
