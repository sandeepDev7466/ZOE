package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.DeleteEventRequest;
import com.ztp.app.Data.Remote.Model.Request.DuplicateEventRequest;
import com.ztp.app.Data.Remote.Model.Response.DeleteEventResponse;
import com.ztp.app.Data.Remote.Model.Response.DuplicateEventResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htl-dev on 19-04-2019.
 */

public class DuplicateEventViewModel extends ViewModel {


    private MutableLiveData<DuplicateEventResponse> duplicateEventResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private DuplicateEventRequest duplicateEventRequest;

    public LiveData<DuplicateEventResponse> getDupEventResponse(DuplicateEventRequest duplicateEventRequest) {
        duplicateEventResponseMutableLiveData = new MutableLiveData<>();
        this.duplicateEventRequest = duplicateEventRequest;
        dupEventMethod();
        return duplicateEventResponseMutableLiveData;
    }


    public void dupEventMethod() {

        Call<DuplicateEventResponse> call = apiInterface.duplicateEvent(duplicateEventRequest);

        call.enqueue(new Callback<DuplicateEventResponse>() {
            @Override
            public void onResponse(Call<DuplicateEventResponse> call, Response<DuplicateEventResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        duplicateEventResponseMutableLiveData.postValue(response.body());

                    }
                }
                else
                {
                    duplicateEventResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuplicateEventResponse> call, Throwable t) {
                t.printStackTrace();
                duplicateEventResponseMutableLiveData.postValue(null);
            }
        });
    }
}
