package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.GetCSOShiftRequest;
import com.ztp.app.Data.Remote.Model.Request.GetSearchShiftListRequest;
import com.ztp.app.Data.Remote.Model.Request.GetShiftListRequest;
import com.ztp.app.Data.Remote.Model.Response.GetCSOShiftResponse;
import com.ztp.app.Data.Remote.Model.Response.GetVolunteerShiftListResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htl-dev on 17-04-2019.
 */

public class GetVolunteerShiftListViewModel extends ViewModel {

    private MutableLiveData<GetVolunteerShiftListResponse> getShiftListResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetSearchShiftListRequest getSearchShiftRequest;


    public LiveData<GetVolunteerShiftListResponse> getSearchShiftResponseLiveData(GetSearchShiftListRequest getSearchShiftRequest) {
        getShiftListResponseMutableLiveData = new MutableLiveData<>();
        this.getSearchShiftRequest = getSearchShiftRequest;
        getSearchResponse();
        return getShiftListResponseMutableLiveData;
    }


    private void getSearchResponse() {
        Call<GetVolunteerShiftListResponse> call = apiInterface.getSearchShiftList(getSearchShiftRequest);

        call.enqueue(new Callback<GetVolunteerShiftListResponse>() {
            @Override
            public void onResponse(Call<GetVolunteerShiftListResponse> call, Response<GetVolunteerShiftListResponse> response) {
                if (response.body() != null) {
                    getShiftListResponseMutableLiveData.postValue(response.body());

                }
            }
            @Override
            public void onFailure(Call<GetVolunteerShiftListResponse> call, Throwable t) {
                t.printStackTrace();
                getShiftListResponseMutableLiveData.postValue(null);
            }
        });
    }


}
