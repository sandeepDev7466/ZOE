package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.DocumentDeleteRequest;
import com.ztp.app.Data.Remote.Model.Response.DocumentDeleteResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentDeleteViewModel extends ViewModel
{
    private MutableLiveData<DocumentDeleteResponse> documentDeleteResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private DocumentDeleteRequest documentDeleteRequest;

    public LiveData<DocumentDeleteResponse> deleteDocument(DocumentDeleteRequest documentDeleteRequest) {
        documentDeleteResponseMutableLiveData = new MutableLiveData<>();
        this.documentDeleteRequest = documentDeleteRequest;
        getResponse();
        return documentDeleteResponseMutableLiveData;
    }

    public void getResponse() {
        Call<DocumentDeleteResponse> call = apiInterface.deleteDocument(documentDeleteRequest);

        call.enqueue(new Callback<DocumentDeleteResponse>() {
            @Override
            public void onResponse(Call<DocumentDeleteResponse> call, Response<DocumentDeleteResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        documentDeleteResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    documentDeleteResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DocumentDeleteResponse> call, Throwable t) {
                t.printStackTrace();
                documentDeleteResponseMutableLiveData.postValue(null);
            }
        });
    }
}
