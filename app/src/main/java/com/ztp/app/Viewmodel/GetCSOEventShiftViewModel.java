package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.GetCSOShiftRequest;
import com.ztp.app.Data.Remote.Model.Response.GetCSOShiftResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCSOEventShiftViewModel extends ViewModel {

    private MutableLiveData<GetCSOShiftResponse> getCSOShiftMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetCSOShiftRequest getCSOShiftRequest;

    public LiveData<GetCSOShiftResponse> getCSOShiftLiveData(GetCSOShiftRequest getCSOShiftRequest) {
        getCSOShiftMutableLiveData = new MutableLiveData<>();
        this.getCSOShiftRequest = getCSOShiftRequest;
        getResponse();
        return getCSOShiftMutableLiveData;
    }

    private void getResponse() {
        Call<GetCSOShiftResponse> call = apiInterface.getCSOShift(getCSOShiftRequest);

        call.enqueue(new Callback<GetCSOShiftResponse>() {
            @Override
            public void onResponse(Call<GetCSOShiftResponse> call, Response<GetCSOShiftResponse> response) {
                if (response.body() != null) {
                    getCSOShiftMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<GetCSOShiftResponse> call, Throwable t) {
                t.printStackTrace();
                getCSOShiftMutableLiveData.postValue(null);
            }
        });
    }
}
