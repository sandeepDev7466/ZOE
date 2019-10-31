package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ztp.app.Data.Local.Room.Async.Save.DBSaveCountry;
import com.ztp.app.Data.Local.Room.Async.Save.DBSaveDocument;
import com.ztp.app.Data.Remote.Model.Request.DocumentListRequest;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.DocumentListResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import com.ztp.app.Helper.MyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentListViewModel extends ViewModel {
    private MutableLiveData<DocumentListResponse> documentListResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private DocumentListRequest documentListRequest;

    public LiveData<DocumentListResponse> getDocumentListResponse(DocumentListRequest documentListRequest) {

        documentListResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        this.documentListRequest = documentListRequest;
        documentListResponse();
        return documentListResponseMutableLiveData;
    }

    private void documentListResponse() {
        Call<DocumentListResponse> call = apiInterface.getDocumentList(documentListRequest);

        call.enqueue(new Callback<DocumentListResponse>() {
            @Override
            public void onResponse(Call<DocumentListResponse> call, Response<DocumentListResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        documentListResponseMutableLiveData.postValue(response.body());
                        if (response.body().getResData() != null && response.body().getResData().size() > 0)
                            new DBSaveDocument(context, response.body().getResData()).execute();
                    }
                }
                else
                {
                    documentListResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DocumentListResponse> call, Throwable t) {
                t.printStackTrace();
                documentListResponseMutableLiveData.postValue(null);
            }
        });
    }
}
