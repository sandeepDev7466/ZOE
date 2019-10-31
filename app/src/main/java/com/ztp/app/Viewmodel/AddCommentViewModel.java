package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.AddCommentRequest;
import com.ztp.app.Data.Remote.Model.Response.AddCommentResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCommentViewModel extends ViewModel {
    private MutableLiveData<AddCommentResponse> addCommentResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private AddCommentRequest addCommentRequest;

    public LiveData<AddCommentResponse> addCommentResponse(AddCommentRequest addCommentRequest) {
        addCommentResponseMutableLiveData = new MutableLiveData<>();
        this.addCommentRequest = addCommentRequest;
        getResponse();
        return addCommentResponseMutableLiveData;
    }

    public void getResponse() {
        Call<AddCommentResponse> call = apiInterface.addComment(addCommentRequest);

        call.enqueue(new Callback<AddCommentResponse>() {
            @Override
            public void onResponse(Call<AddCommentResponse> call, Response<AddCommentResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        addCommentResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    addCommentResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<AddCommentResponse> call, Throwable t) {
                t.printStackTrace();
                addCommentResponseMutableLiveData.postValue(null);
            }
        });
    }
}
