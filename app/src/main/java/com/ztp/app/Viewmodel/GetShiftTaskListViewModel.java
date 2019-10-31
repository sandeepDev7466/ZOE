package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import com.ztp.app.Data.Remote.Model.Request.ShiftTaskRequest;
import com.ztp.app.Data.Remote.Model.Response.ShiftTaskResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetShiftTaskListViewModel extends ViewModel {
    private MutableLiveData<ShiftTaskResponse> shiftTaskResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private ShiftTaskRequest shiftTaskRequest;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public LiveData<ShiftTaskResponse> getShiftTaskResponse(Context context, ShiftTaskRequest shiftTaskRequest) {

        shiftTaskResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        this.shiftTaskRequest = shiftTaskRequest;
        getResponse();
        return shiftTaskResponseMutableLiveData;
    }

    private void getResponse() {
        Call<ShiftTaskResponse> call = apiInterface.getShiftTaskList(shiftTaskRequest);

        call.enqueue(new Callback<ShiftTaskResponse>() {
            @Override
            public void onResponse(Call<ShiftTaskResponse> call, Response<ShiftTaskResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        shiftTaskResponseMutableLiveData.postValue(response.body());
                       /* if (response.body().getShiftTaskList() != null && response.body().getShiftTaskList().size() > 0)
                            new DBSaveState(context, response.body().getStateList()).execute();*/

                    }
                } else {
                    shiftTaskResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ShiftTaskResponse> call, Throwable t) {
                t.printStackTrace();
                shiftTaskResponseMutableLiveData.postValue(null);
            }
        });
    }
}
