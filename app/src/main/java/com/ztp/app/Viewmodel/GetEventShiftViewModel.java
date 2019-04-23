package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.GetEventShiftRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventShiftResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetEventShiftViewModel extends ViewModel {

    private MutableLiveData<GetEventShiftResponse> getEventShiftMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetEventShiftRequest getEventShiftRequest;

    public LiveData<GetEventShiftResponse> getEventShiftResponseLiveData(GetEventShiftRequest getEventShiftRequest) {
        getEventShiftMutableLiveData = new MutableLiveData<>();
        this.getEventShiftRequest = getEventShiftRequest;
        getResponse();
        return getEventShiftMutableLiveData;
    }

    private void getResponse() {
        Call<GetEventShiftResponse> call = apiInterface.getEventShift(getEventShiftRequest);

        call.enqueue(new Callback<GetEventShiftResponse>() {
            @Override
            public void onResponse(Call<GetEventShiftResponse> call, Response<GetEventShiftResponse> response) {
                if (response.body() != null) {
                    getEventShiftMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<GetEventShiftResponse> call, Throwable t) {
                t.printStackTrace();
                getEventShiftMutableLiveData.postValue(null);
            }
        });
    }
}
