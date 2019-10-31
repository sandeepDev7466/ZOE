package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ztp.app.Data.Local.Room.Async.Save.DBCSOSaveShiftList;
import com.ztp.app.Data.Remote.Model.Request.GetCSOShiftRequest;
import com.ztp.app.Data.Remote.Model.Response.GetCSOShiftResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCSOEventShiftViewModel extends ViewModel {

    private MutableLiveData<GetCSOShiftResponse> getCSOShiftMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetCSOShiftRequest getCSOShiftRequest;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    List<GetCSOShiftResponse.ResDatum> shiftData = new ArrayList<>();

    public LiveData<GetCSOShiftResponse> getCSOShiftLiveData(Context context,GetCSOShiftRequest getCSOShiftRequest) {
        getCSOShiftMutableLiveData = new MutableLiveData<>();
        this.context = context;
        this.getCSOShiftRequest = getCSOShiftRequest;
        getResponse();
        return getCSOShiftMutableLiveData;
    }

    private void getResponse() {
        Call<GetCSOShiftResponse> call = apiInterface.getCSOShift(getCSOShiftRequest);

        call.enqueue(new Callback<GetCSOShiftResponse>() {
            @Override
            public void onResponse(Call<GetCSOShiftResponse> call, Response<GetCSOShiftResponse> response) {
                if(response.isSuccessful())
                {
                    if (response.body() != null) {
                        getCSOShiftMutableLiveData.postValue(response.body());
                        shiftData = response.body().getResData();
                        if(shiftData != null) {
                            for (int i = 0; i < shiftData.size(); i++) {
                                shiftData.get(i).setEventId(getCSOShiftRequest.getEventId());
                            }
                            new DBCSOSaveShiftList(context, shiftData).execute();
                        }
                    }
                }
                else
                {
                    getCSOShiftMutableLiveData.postValue(null);
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
