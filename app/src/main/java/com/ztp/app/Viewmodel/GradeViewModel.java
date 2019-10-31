package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ztp.app.Data.Remote.Model.Request.GradeRequest;
import com.ztp.app.Data.Remote.Model.Response.GradeResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import com.ztp.app.Helper.MyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradeViewModel extends ViewModel {
    private MutableLiveData<GradeResponse> gradeResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    GradeRequest gradeRequest;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    MyToast myToast;

    public LiveData<GradeResponse> getGradeResponse(Context context, GradeRequest gradeRequest) {

        gradeResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        this.gradeRequest = gradeRequest;
        getResponse();
        return gradeResponseMutableLiveData;
    }

    private void getResponse() {
        Call<GradeResponse> call = apiInterface.getGrade(gradeRequest);

        call.enqueue(new Callback<GradeResponse>() {
            @Override
            public void onResponse(Call<GradeResponse> call, Response<GradeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        gradeResponseMutableLiveData.postValue(response.body());
                        /*if (response.body().getResData() != null && response.body().getResData().size() > 0)
                            new DBSaveCountry(context, response.body().getResData()).execute();*/
                    }
                } else {
                    gradeResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GradeResponse> call, Throwable t) {
                t.printStackTrace();
                gradeResponseMutableLiveData.postValue(null);
            }
        });
    }
}

