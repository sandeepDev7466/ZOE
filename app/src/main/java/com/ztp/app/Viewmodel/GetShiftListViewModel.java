package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.GetEventShiftRequest;
import com.ztp.app.Data.Remote.Model.Request.GetEventsRequest;
import com.ztp.app.Data.Remote.Model.Request.GetSearchShiftListRequest;
import com.ztp.app.Data.Remote.Model.Request.GetShiftListRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;
import com.ztp.app.Data.Remote.Model.Response.GetShiftListResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htl-dev on 17-04-2019.
 */

public class GetShiftListViewModel extends ViewModel {

    private MutableLiveData<GetShiftListResponse> getShiftListResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetShiftListRequest getEventShiftRequest;
    private GetSearchShiftListRequest getSearchShiftRequest;

    public LiveData<GetShiftListResponse> getShiftResponseLiveData(GetShiftListRequest getEventShiftRequest) {
        getShiftListResponseMutableLiveData = new MutableLiveData<>();
        this.getEventShiftRequest = getEventShiftRequest;
        getResponse();
        return getShiftListResponseMutableLiveData;
    }
    public LiveData<GetShiftListResponse> getSearchShiftResponseLiveData(GetSearchShiftListRequest getSearchShiftRequest) {
        getShiftListResponseMutableLiveData = new MutableLiveData<>();
        this.getSearchShiftRequest = getSearchShiftRequest;
        getSearchResponse();
        return getShiftListResponseMutableLiveData;
    }

    private void getResponse() {
        Call<GetShiftListResponse> call = apiInterface.getShiftList(getEventShiftRequest);

        call.enqueue(new Callback<GetShiftListResponse>() {
            @Override
            public void onResponse(Call<GetShiftListResponse> call, Response<GetShiftListResponse> response) {
                if (response.body() != null) {
                    getShiftListResponseMutableLiveData.postValue(response.body());

                }
            }
            @Override
            public void onFailure(Call<GetShiftListResponse> call, Throwable t) {
                t.printStackTrace();
                getShiftListResponseMutableLiveData.postValue(null);
            }
        });
    }
    private void getSearchResponse() {
        Call<GetShiftListResponse> call = apiInterface.getSearchShiftList(getSearchShiftRequest);

        call.enqueue(new Callback<GetShiftListResponse>() {
            @Override
            public void onResponse(Call<GetShiftListResponse> call, Response<GetShiftListResponse> response) {
                if (response.body() != null) {
                    getShiftListResponseMutableLiveData.postValue(response.body());

                }
            }
            @Override
            public void onFailure(Call<GetShiftListResponse> call, Throwable t) {
                t.printStackTrace();
                getShiftListResponseMutableLiveData.postValue(null);
            }
        });
    }


}
