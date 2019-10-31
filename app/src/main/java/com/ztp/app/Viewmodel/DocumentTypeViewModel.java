package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ztp.app.Data.Local.Room.Async.Save.DBSaveCountry;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.DocumentTypeResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import com.ztp.app.Helper.MyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentTypeViewModel extends ViewModel {
    private MutableLiveData<DocumentTypeResponse> documentTypeResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public LiveData<DocumentTypeResponse> getDocumentResponse(Context context) {

        documentTypeResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        documentResponse();
        return documentTypeResponseMutableLiveData;
    }

    private void documentResponse() {
        Call<DocumentTypeResponse> call = apiInterface.getDocumentType();

        call.enqueue(new Callback<DocumentTypeResponse>() {
            @Override
            public void onResponse(Call<DocumentTypeResponse> call, Response<DocumentTypeResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        documentTypeResponseMutableLiveData.postValue(response.body());

                    }
                }
                else
                {
                    documentTypeResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DocumentTypeResponse> call, Throwable t) {
                t.printStackTrace();
                documentTypeResponseMutableLiveData.postValue(null);
            }
        });
    }
}
