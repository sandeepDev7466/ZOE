package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.InsertFirebaseIdRequest;
import com.ztp.app.Data.Remote.Model.Response.InsertFirebaseIdResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertFirebaseIdViewModel extends ViewModel {
    private MutableLiveData<InsertFirebaseIdResponse> insertFirebaseIdResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private InsertFirebaseIdRequest insertFirebaseIdRequest;

    public LiveData<InsertFirebaseIdResponse> getInsertFirebaseIdResponse(InsertFirebaseIdRequest insertFirebaseIdRequest) {

        insertFirebaseIdResponseMutableLiveData = new MutableLiveData<>();
        this.insertFirebaseIdRequest = insertFirebaseIdRequest;
        loadResponse();
        return insertFirebaseIdResponseMutableLiveData;
    }

    private void loadResponse() {

        Call<InsertFirebaseIdResponse> call = apiInterface.insertFirebaseId(insertFirebaseIdRequest);

        call.enqueue(new Callback<InsertFirebaseIdResponse>() {
            @Override
            public void onResponse(Call<InsertFirebaseIdResponse> call, Response<InsertFirebaseIdResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        insertFirebaseIdResponseMutableLiveData.postValue(response.body());
                } else {
                    insertFirebaseIdResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<InsertFirebaseIdResponse> call, Throwable t) {
                t.printStackTrace();
                insertFirebaseIdResponseMutableLiveData.postValue(null);
            }
        });
    }
}
