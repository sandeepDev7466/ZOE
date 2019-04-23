package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.GetShiftDetailRequest;
import com.ztp.app.Data.Remote.Model.Response.GetShiftDetailResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetShiftDetailViewModel extends ViewModel {
    private MutableLiveData<GetShiftDetailResponse> getShiftDetailMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetShiftDetailRequest getShiftDetailRequest;

    public LiveData<GetShiftDetailResponse> getShiftDetailResponseLiveData(GetShiftDetailRequest getShiftDetailRequest) {
        getShiftDetailMutableLiveData = new MutableLiveData<>();
        this.getShiftDetailRequest = getShiftDetailRequest;
        getResponse();
        return getShiftDetailMutableLiveData;
    }

    private void getResponse() {
        Call<GetShiftDetailResponse> call = apiInterface.getShiftDetail(getShiftDetailRequest);

        call.enqueue(new Callback<GetShiftDetailResponse>() {
            @Override
            public void onResponse(Call<GetShiftDetailResponse> call, Response<GetShiftDetailResponse> response) {
                if (response.body() != null) {
                    getShiftDetailMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<GetShiftDetailResponse> call, Throwable t) {
                t.printStackTrace();
                getShiftDetailMutableLiveData.postValue(null);
            }
        });
    }
}
