package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.DeleteShiftRequest;
import com.ztp.app.Data.Remote.Model.Response.DeleteShiftResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteShiftViewModel extends ViewModel {
    private MutableLiveData<DeleteShiftResponse> deleteShiftResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private DeleteShiftRequest deleteShiftRequest;

    public LiveData<DeleteShiftResponse> getDeleteShiftResponse(DeleteShiftRequest deleteShiftRequest) {

        deleteShiftResponseMutableLiveData = new MutableLiveData<>();
        this.deleteShiftRequest = deleteShiftRequest;
        loadResponse();
        return deleteShiftResponseMutableLiveData;
    }

    private void loadResponse() {

        Call<DeleteShiftResponse> call = apiInterface.getDeleteShift(deleteShiftRequest);

        call.enqueue(new Callback<DeleteShiftResponse>() {
            @Override
            public void onResponse(Call<DeleteShiftResponse> call, Response<DeleteShiftResponse> response) {
                if (response.body() != null) {
                    deleteShiftResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DeleteShiftResponse> call, Throwable t) {
                t.printStackTrace();
                deleteShiftResponseMutableLiveData.postValue(null);
            }
        });
    }
}
