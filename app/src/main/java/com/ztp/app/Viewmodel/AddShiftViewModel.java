package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.EventAddRequest;
import com.ztp.app.Data.Remote.Model.Request.SiftAddRequest;
import com.ztp.app.Data.Remote.Model.Response.AddEventResponse;
import com.ztp.app.Data.Remote.Model.Response.ShiftAddResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htl-dev on 16-04-2019.
 */

public class AddShiftViewModel extends ViewModel {


    private MutableLiveData<ShiftAddResponse> shiftAddResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private SiftAddRequest siftAddRequest;


    public LiveData<ShiftAddResponse> getAddShiftResponse(SiftAddRequest siftAddRequest) {
        shiftAddResponseMutableLiveData = new MutableLiveData<>();
        this.siftAddRequest = siftAddRequest;
        addShiftResponse();
        return shiftAddResponseMutableLiveData;
    }


    public void addShiftResponse()
    {

        Call<ShiftAddResponse> call = apiInterface.doAddShift(siftAddRequest);

        call.enqueue(new Callback<ShiftAddResponse>() {
            @Override
            public void onResponse(Call<ShiftAddResponse> call, Response<ShiftAddResponse> response) {
                if (response.body() != null) {
                    shiftAddResponseMutableLiveData.postValue(response.body());

                }
            }
            @Override
            public void onFailure(Call<ShiftAddResponse> call, Throwable t) {
                t.printStackTrace();
                shiftAddResponseMutableLiveData.postValue(null);
            }
        });


    }


}
