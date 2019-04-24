package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.ShiftUpdateRequest;
import com.ztp.app.Data.Remote.Model.Response.ShiftUpdateResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htl-dev on 24-04-2019.
 */

public class UpdateShiftViewModel extends ViewModel {

    private MutableLiveData<ShiftUpdateResponse> shiftUpdateResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private ShiftUpdateRequest shiftUpdateRequest;

    public LiveData<ShiftUpdateResponse> getUpdateShiftResponse(ShiftUpdateRequest shiftUpdateRequest) {
        shiftUpdateResponseMutableLiveData = new MutableLiveData<>();
        this.shiftUpdateRequest = shiftUpdateRequest;
        updateShiftResponse();
        return shiftUpdateResponseMutableLiveData;
    }

    public void updateShiftResponse() {
        Call<ShiftUpdateResponse> call = apiInterface.doUpdateShift(shiftUpdateRequest);

        call.enqueue(new Callback<ShiftUpdateResponse>() {
            @Override
            public void onResponse(Call<ShiftUpdateResponse> call, Response<ShiftUpdateResponse> response) {
                if (response.body() != null) {
                    shiftUpdateResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ShiftUpdateResponse> call, Throwable t) {
                t.printStackTrace();
                shiftUpdateResponseMutableLiveData.postValue(null);
            }
        });
    }


}
